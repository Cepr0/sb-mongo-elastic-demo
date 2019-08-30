package io.github.cepr0.demo.model.elastic;

import io.github.cepr0.crud.model.ContentAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.elasticsearch.annotations.Document;

@ContentAlias("cars")
@Data
@EqualsAndHashCode(callSuper = true)
@Document(indexName = "car")
public class ElasticCar extends BaseElasticEntity {
	private String brand;
	private String model;
	private Integer year;
}
