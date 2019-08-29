package io.github.cepr0.demo.service;

import io.github.cepr0.crud.event.EntityEvent;
import io.github.cepr0.crud.service.AbstractCrudService;
import io.github.cepr0.demo.dto.CarRequest;
import io.github.cepr0.demo.dto.CarResponse;
import io.github.cepr0.demo.mapper.CarMapper;
import io.github.cepr0.demo.model.Car;
import io.github.cepr0.demo.model.elastic.ElasticCar;
import io.github.cepr0.demo.repo.elastic.CarElasticRepo;
import io.github.cepr0.demo.repo.mongo.CarRepo;
import io.github.cepr0.demo.service.event.CarCreateEvent;
import io.github.cepr0.demo.service.event.CarDeleteEvent;
import io.github.cepr0.demo.service.event.CarUpdateEvent;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.engine.VersionConflictEngineException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Optional;

@Slf4j
@Service
public class CarService extends AbstractCrudService<Car, String, CarRequest, CarResponse> {

	private final CarElasticRepo carElasticRepo;

	public CarService(final CarRepo repo, final CarMapper mapper, CarElasticRepo carElasticRepo) {
		super(repo, mapper);
		this.carElasticRepo = carElasticRepo;
	}

	@Override
	protected EntityEvent<Car> onCreateEvent(final Car entity) {
		return new CarCreateEvent(entity);
	}

	@Override
	protected EntityEvent<Car> onUpdateEvent(final Car entity) {
		return new CarUpdateEvent(entity);
	}

	@Override
	protected EntityEvent<Car> onDeleteEvent(final Car entity) {
		return new CarDeleteEvent(entity);
	}

	@Retryable(maxAttempts = 5, backoff = @Backoff(delay = 100, multiplier = 1.5))
	@Override
	public Optional<CarResponse> update(String id, CarRequest source) {
		return super.update(id, source);
	}

	@Recover
	public Optional<CarResponse> recoverUpdate(Exception e, String id, CarRequest source) {
		log.error("[!] Updating the car #'{}' with {} is failed due to {}", id, source, e.toString());
		return Optional.empty();
	}

	@Async
	@TransactionalEventListener
	public void putToElasticOnCreate(CarCreateEvent event) {
		Car car = event.getEntity();
		ElasticCar elasticCar = new ElasticCar()
				.setId(car.getId())
				.setBrand(car.getBrand())
				.setModel(car.getModel())
				.setYear(car.getYear());

		carElasticRepo.save(elasticCar);
		log.debug("[d] Put new car to elastic: {}", elasticCar);
	}

	@Async
	@TransactionalEventListener
	public void changeInElasticOnUpdate(CarUpdateEvent event) {
		Car car = event.getEntity();
		String carId = car.getId();
		try {
			Optional<ElasticCar> result = carElasticRepo.findById(carId)
					.map(elasticCar -> {
						elasticCar.setVersion(car.getVersion() + 1L);
						elasticCar.setBrand(car.getBrand());
						elasticCar.setModel(car.getModel());
						elasticCar.setYear(car.getYear());
						carElasticRepo.save(elasticCar);
						log.debug("[d] Car changed in elastic: {}", elasticCar);
						return elasticCar;
					});
			if (!result.isPresent()) {
				log.warn("[w] Car not found in elastic: {}", car);
			}
		} catch (VersionConflictEngineException e) {
			log.error("[!] Version conflict for: {}. Cause: {}", car, e.toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
	}

	@Async
	@TransactionalEventListener
	public void removeInElasticOnDelete(CarDeleteEvent event) {
		Car car = event.getEntity();
		String carId = car.getId();
		carElasticRepo.deleteById(carId);
		log.debug("[d] Car removed from elastic: {}", car);
	}
}
