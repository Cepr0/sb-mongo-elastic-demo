package io.github.cepr0.demo.service.elastic;

import io.github.cepr0.demo.model.base.MongoEntity;
import io.github.cepr0.demo.model.elastic.BaseElasticEntity;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.io.Serializable;
import java.util.Optional;

@Slf4j
public abstract class ElasticHandler<T extends MongoEntity<ID>, E extends BaseElasticEntity, ID extends Serializable> {

	private final ElasticMapper<T, E> elasticMapper;
	private final ElasticsearchRepository<E, ID> elasticsearchRepo;

	protected ElasticHandler(ElasticMapper<T, E> elasticMapper, ElasticsearchRepository<E, ID> elasticsearchRepo) {
		this.elasticMapper = elasticMapper;
		this.elasticsearchRepo = elasticsearchRepo;
	}

	protected void handleOnCreate(@NonNull T entity) {
		try {
			E elasticEntity = elasticMapper.toElasticEntity(entity);
			elasticsearchRepo.save(elasticEntity);
			log.debug("[d] Put a new entity to elastic: {}", entity);
		} catch (Exception e) {
			log.error("[!] Couldn't put entity to elastic for: {}. Cause: {}", entity, e.toString());
		}
	}

	protected void handleOnUpdate(@NonNull T source) {
		ID entityId = source.getId();
		try {
			Optional<E> result = elasticsearchRepo.findById(entityId)
					.map(elasticEntity -> {
						elasticMapper.updateElasticEntity(elasticEntity, source);
						elasticsearchRepo.save(elasticEntity);
						log.debug("[d] Entity changed in elastic: {}", elasticEntity);
						return elasticEntity;
					});
			if (!result.isPresent()) {
				log.warn("[w] Entity not found in elastic: {}", source);
			}
		} catch (Exception e) {
			log.error("[!] Couldn't update entity in elastic for: {}. Cause: {}", source, e.toString());
		}
	}

	protected void handleOnDelete(@NonNull T entity) {
		ID entityId = entity.getId();
		try {
			elasticsearchRepo.deleteById(entityId);
			log.debug("[d] Entity removed from elastic: {}", entity);
		} catch (Exception e) {
			log.error("[!] Couldn't delete entity in elastic for: {}. Cause: {}", entity, e.toString());
		}
	}
}
