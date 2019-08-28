package io.github.cepr0.demo.service.event;

import io.github.cepr0.crud.event.EntityEvent;
import io.github.cepr0.demo.model.Car;

public class CarCreateEvent extends EntityEvent<Car> {
	public CarCreateEvent(final Car entity) {
		super(entity);
	}
}
