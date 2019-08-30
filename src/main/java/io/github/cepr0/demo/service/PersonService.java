package io.github.cepr0.demo.service;

import io.github.cepr0.crud.event.EntityEvent;
import io.github.cepr0.crud.service.AbstractCrudService;
import io.github.cepr0.demo.dto.CarResponse;
import io.github.cepr0.demo.dto.PersonRequest;
import io.github.cepr0.demo.dto.PersonResponse;
import io.github.cepr0.demo.mapper.PersonMapper;
import io.github.cepr0.demo.model.Car;
import io.github.cepr0.demo.model.Person;
import io.github.cepr0.demo.repo.mongo.PersonRepo;
import io.github.cepr0.demo.service.event.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class PersonService extends AbstractCrudService<Person, String, PersonRequest, PersonResponse> {

	public PersonService(final PersonRepo repo, final PersonMapper mapper) {
		super(repo, mapper);
	}

	@Override
	protected EntityEvent<Person> onCreateEvent(final Person entity) {
		return new PersonCreateEvent(entity);
	}

	@Override
	protected EntityEvent<Person> onUpdateEvent(final Person entity) {
		return new PersonUpdateEvent(entity);
	}

	@Override
	protected EntityEvent<Person> onDeleteEvent(final Person entity) {
		return new PersonDeleteEvent(entity);
	}

	@Retryable(maxAttempts = 5, backoff = @Backoff(delay = 100, multiplier = 1.5))
	@Override
	public Optional<PersonResponse> update(String id, PersonRequest source) {
		return super.update(id, source);
	}

	@Recover
	public Optional<CarResponse> recoverUpdate(Exception e, String id, PersonRequest source) {
		log.error("[!] Updating the person #'{}' with {} is failed due to {}", id, source, e.toString());
		return Optional.empty();
	}

	@EventListener
	public void onCarCreate(CarCreateEvent event) {
		Car car = event.getEntity();
		Person person = car.getPerson();
		person.getCars().add(car);
		((PersonRepo) repo).save(person);
	}

	@EventListener
	public void onCarDelete(CarDeleteEvent event) {
		Car car = event.getEntity();
		String personId = car.getPerson().getId();
		repo.getById(personId).ifPresent(person -> {
			person.getCars().remove(car);
			((PersonRepo) repo).save(person);
		});
	}
}
