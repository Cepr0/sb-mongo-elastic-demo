package io.github.cepr0.demo.mapper;

import io.github.cepr0.crud.mapper.CrudMapper;
import io.github.cepr0.demo.dto.PersonDto;
import io.github.cepr0.demo.dto.PersonRequest;
import io.github.cepr0.demo.dto.PersonResponse;
import io.github.cepr0.demo.model.Person;
import io.github.cepr0.demo.repo.mongo.CarRepo;
import org.mapstruct.Mapper;

@Mapper(config = CrudMapper.class, uses = {CarRepo.class, CarMapper.class})
public abstract class PersonMapper implements CrudMapper<Person, PersonRequest, PersonResponse> {
	public abstract PersonDto toPersonDto(Person person);
}
