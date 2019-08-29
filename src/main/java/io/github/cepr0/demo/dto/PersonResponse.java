package io.github.cepr0.demo.dto;

import com.fasterxml.jackson.annotation.JsonView;
import io.github.cepr0.crud.dto.CrudResponse;
import io.github.cepr0.crud.model.ContentAlias;
import lombok.Data;

import java.util.Set;

@ContentAlias("people")
@Data
public class PersonResponse implements CrudResponse<String> {
	@JsonView(Views.ForPerson.class) private String id;
	@JsonView(Views.ForPerson.class) private String name;
	@JsonView(Views.ForPerson.class) private Set<CarResponse> cars;
}
