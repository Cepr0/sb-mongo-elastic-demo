package io.github.cepr0.demo.repo.elastic;

import io.github.cepr0.demo.model.elastic.ElasticPerson;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PersonElasticRepo extends ElasticsearchRepository<ElasticPerson, String> {
}
