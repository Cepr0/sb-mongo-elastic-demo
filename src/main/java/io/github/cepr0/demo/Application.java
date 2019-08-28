package io.github.cepr0.demo;

import io.github.cepr0.demo.model.Car;
import io.github.cepr0.demo.model.Person;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Async;

@SpringBootApplication
public class Application {

	private final MongoTemplate mongoTemplate;

	public Application(final MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	// @Profile("replSet")
	@Bean
	MongoTransactionManager transactionManager(MongoDbFactory dbFactory) {
		return new MongoTransactionManager(dbFactory);
	}

	@Async
	@EventListener(ApplicationReadyEvent.class)
	public void onReady() {

		if (mongoTemplate.collectionExists(Person.class)) {
			mongoTemplate.dropCollection(Person.class);
		}
		mongoTemplate.createCollection(Person.class);

		if (mongoTemplate.collectionExists(Car.class)) {
			mongoTemplate.dropCollection(Car.class);
		}
		mongoTemplate.createCollection(Car.class);
	}
}
