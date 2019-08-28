package io.github.cepr0.demo.dto;

import io.github.cepr0.crud.dto.CrudResponse;
import io.github.cepr0.crud.model.ContentAlias;
import lombok.Data;

import java.util.Set;

@ContentAlias("people")
@Data
public class PersonResponse implements CrudResponse<String> {
	private String id;
	private String name;
	private Set<CarResponse> cars;
}
