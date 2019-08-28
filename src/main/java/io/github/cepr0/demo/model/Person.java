package io.github.cepr0.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "people")
@TypeAlias("person")
public class Person extends BaseDoc {
	private String name;

	@DBRef(lazy = true)
	@Field(type = FieldType.Nested, includeInParent = true)
	private Set<Car> cars = new HashSet<>();
}
