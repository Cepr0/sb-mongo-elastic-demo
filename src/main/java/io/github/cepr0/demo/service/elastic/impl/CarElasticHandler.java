package io.github.cepr0.demo.service.elastic.impl;

import io.github.cepr0.demo.model.Car;
import io.github.cepr0.demo.model.elastic.ElasticCar;
import io.github.cepr0.demo.repo.elastic.CarElasticRepo;
import io.github.cepr0.demo.service.elastic.ElasticHandler;
import io.github.cepr0.demo.service.event.CarCreateEvent;
import io.github.cepr0.demo.service.event.CarDeleteEvent;
import io.github.cepr0.demo.service.event.CarUpdateEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class CarElasticHandler extends ElasticHandler<Car, ElasticCar, String> {

	protected CarElasticHandler(CarElasticMapper elasticMapper, CarElasticRepo elasticsearchRepo) {
		super(elasticMapper, elasticsearchRepo);
	}

	@Async
	@TransactionalEventListener
	public void handleOnCreate(CarCreateEvent event) {
		super.handleOnCreate(event.getEntity());
	}

	@Async
	@TransactionalEventListener
	public void handleOnUpdate(CarUpdateEvent event) {
		super.handleOnUpdate(event.getEntity());
	}

	@Async
	@TransactionalEventListener
	public void handleOnDelete(CarDeleteEvent event) {
		super.handleOnDelete(event.getEntity());
	}
}
