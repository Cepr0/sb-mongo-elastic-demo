package io.github.cepr0.demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

@Data
@Document(indexName = "car")
public class ElasticCar implements Serializable {
	@Id private String id;
	private String brand;
	private String model;
	private Integer year;
}
