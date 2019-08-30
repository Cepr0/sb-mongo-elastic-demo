package io.github.cepr0.demo.service.elastic.impl;

import io.github.cepr0.crud.mapper.CrudMapper;
import io.github.cepr0.demo.model.Person;
import io.github.cepr0.demo.model.elastic.ElasticPerson;
import io.github.cepr0.demo.service.elastic.ElasticMapper;
import org.mapstruct.Mapper;

@Mapper(config = CrudMapper.class)
public abstract class PersonElasticMapper implements ElasticMapper<Person, ElasticPerson> {
}
