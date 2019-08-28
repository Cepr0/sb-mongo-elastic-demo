package io.github.cepr0.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "cars")
@TypeAlias("car")
public class Car extends BaseDoc {
	private String brand;
	private String model;
	private Integer year;
	@DBRef(lazy = true) private Person person;
}
