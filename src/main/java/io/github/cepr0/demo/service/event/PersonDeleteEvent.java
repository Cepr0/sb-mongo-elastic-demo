package io.github.cepr0.demo.service.event;

import io.github.cepr0.crud.event.EntityEvent;
import io.github.cepr0.demo.model.Person;

public class PersonDeleteEvent extends EntityEvent<Person> {
	public PersonDeleteEvent(final Person entity) {
		super(entity);
	}
}
