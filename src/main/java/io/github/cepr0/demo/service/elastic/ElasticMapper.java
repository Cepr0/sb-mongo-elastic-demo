package io.github.cepr0.demo.service.elastic;

import io.github.cepr0.demo.model.base.MongoEntity;
import io.github.cepr0.demo.model.elastic.BaseElasticEntity;
import org.mapstruct.MappingTarget;

public interface ElasticMapper<T extends MongoEntity, E extends BaseElasticEntity> {
	E toElasticEntity(T doc);
	void updateElasticEntity(@MappingTarget E target, T source);
}
