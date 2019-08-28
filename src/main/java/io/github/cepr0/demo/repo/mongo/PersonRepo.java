package io.github.cepr0.demo.repo.mongo;

import io.github.cepr0.crud.repo.MongoRepo;
import io.github.cepr0.demo.model.Person;

public interface PersonRepo extends MongoRepo<Person, String> {
}
