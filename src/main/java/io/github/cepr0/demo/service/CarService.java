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
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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

	@Async
	@EventListener
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
	@EventListener
	public void changeInElasticOnUpdate(CarUpdateEvent event) {
		Car car = event.getEntity();
		String carId = car.getId();
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
	}

	@Async
	@EventListener
	public void removeInElasticOnDelete(CarDeleteEvent event) {
		Car car = event.getEntity();
		String carId = car.getId();
		carElasticRepo.deleteById(carId);
		log.debug("[d] Car removed from elastic: {}", car);
	}
}
