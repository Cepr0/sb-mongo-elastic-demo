package io.github.cepr0.demo;

import io.github.cepr0.demo.model.Car;
import io.github.cepr0.demo.model.ElasticCar;
import io.github.cepr0.demo.model.ElasticPerson;
import io.github.cepr0.demo.model.Person;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableMongoRepositories("io.github.cepr0.demo.repo.mongo")
@EnableElasticsearchRepositories("io.github.cepr0.demo.repo.elastic")
@SpringBootApplication
public class Application {

	private final MongoTemplate mongoTemplate;
	private final ElasticsearchTemplate elasticsearchTemplate;

	public Application(final MongoTemplate mongoTemplate, ElasticsearchTemplate elasticsearchTemplate) {
		this.mongoTemplate = mongoTemplate;
		this.elasticsearchTemplate = elasticsearchTemplate;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

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

		if (elasticsearchTemplate.indexExists(ElasticPerson.class)) {
			elasticsearchTemplate.deleteIndex(ElasticPerson.class);
		}

		if (elasticsearchTemplate.indexExists(ElasticCar.class)) {
			elasticsearchTemplate.deleteIndex(ElasticCar.class);
		}
	}
}
