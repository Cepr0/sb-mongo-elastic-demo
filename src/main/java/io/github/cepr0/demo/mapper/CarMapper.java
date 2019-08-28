package io.github.cepr0.demo.mapper;

import io.github.cepr0.crud.mapper.CrudMapper;
import io.github.cepr0.demo.dto.CarRequest;
import io.github.cepr0.demo.dto.CarResponse;
import io.github.cepr0.demo.model.Car;
import io.github.cepr0.demo.repo.mongo.PersonRepo;
import org.mapstruct.Mapper;

@Mapper(config = CrudMapper.class, uses = {PersonRepo.class, PersonMapper.class})
public abstract class CarMapper implements CrudMapper<Car, CarRequest, CarResponse> {
}
