package io.github.cepr0.demo;

import io.github.cepr0.demo.model.Car;
import io.github.cepr0.demo.model.Person;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.Async;

@EnableMongoRepositories("io.github.cepr0.demo.repo.mongo")
@EnableElasticsearchRepositories("io.github.cepr0.demo.repo.elastic")
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

	// @Bean
	// public Client client() throws UnknownHostException {
	// 	Settings elasticsearchSettings = Settings.builder()
	// 			.put("cluster.name", "elasticsearch")
	// 			.build();
	//
	// 	TransportClient client = new PreBuiltTransportClient(elasticsearchSettings);
	// 	client.addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9200));
	// 	return client;
	// }

	// @Bean
	// public ElasticsearchOperations elasticsearchTemplate(Client client) {
	// 	return new ElasticsearchTemplate(client);
	// }

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
