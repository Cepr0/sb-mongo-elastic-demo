package io.github.cepr0.demo.service;

import io.github.cepr0.crud.service.AbstractCrudService;
import io.github.cepr0.demo.model.Car;
import io.github.cepr0.demo.model.Person;
import io.github.cepr0.demo.dto.PersonRequest;
import io.github.cepr0.demo.dto.PersonResponse;
import io.github.cepr0.demo.mapper.PersonMapper;
import io.github.cepr0.demo.repo.PersonRepo;
import io.github.cepr0.demo.service.event.CarCreateEvent;
import io.github.cepr0.demo.service.event.CarDeleteEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class PersonService extends AbstractCrudService<Person, String, PersonRequest, PersonResponse> {

	public PersonService(final PersonRepo repo, final PersonMapper mapper) {
		super(repo, mapper);
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
