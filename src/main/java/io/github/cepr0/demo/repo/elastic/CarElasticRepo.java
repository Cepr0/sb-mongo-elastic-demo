package io.github.cepr0.demo.repo.elastic;

import io.github.cepr0.demo.model.ElasticCar;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CarElasticRepo extends ElasticsearchRepository<ElasticCar, String> {
}
