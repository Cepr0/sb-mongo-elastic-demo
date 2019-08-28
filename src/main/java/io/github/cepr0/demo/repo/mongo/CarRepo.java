package io.github.cepr0.demo.repo.mongo;

import io.github.cepr0.crud.repo.MongoRepo;
import io.github.cepr0.demo.model.Car;

public interface CarRepo extends MongoRepo<Car, String> {
}
