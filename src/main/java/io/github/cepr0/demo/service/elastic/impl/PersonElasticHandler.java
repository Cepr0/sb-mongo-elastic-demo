package io.github.cepr0.demo.service.elastic.impl;

import io.github.cepr0.demo.model.Person;
import io.github.cepr0.demo.model.elastic.ElasticPerson;
import io.github.cepr0.demo.repo.elastic.PersonElasticRepo;
import io.github.cepr0.demo.service.elastic.ElasticHandler;
import io.github.cepr0.demo.service.event.PersonCreateEvent;
import io.github.cepr0.demo.service.event.PersonDeleteEvent;
import io.github.cepr0.demo.service.event.PersonUpdateEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class PersonElasticHandler extends ElasticHandler<Person, ElasticPerson, String> {

	protected PersonElasticHandler(PersonElasticMapper elasticMapper, PersonElasticRepo elasticsearchRepo) {
		super(elasticMapper, elasticsearchRepo);
	}

	@Async
	@TransactionalEventListener
	public void handleOnCreate(PersonCreateEvent event) {
		super.handleOnCreate(event.getEntity());
	}

	@Async
	@TransactionalEventListener
	public void handleOnUpdate(PersonUpdateEvent event) {
		super.handleOnUpdate(event.getEntity());
	}

	@Async
	@TransactionalEventListener
	public void handleOnDelete(PersonDeleteEvent event) {
		super.handleOnDelete(event.getEntity());
	}
}
