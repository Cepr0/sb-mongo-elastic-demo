package io.github.cepr0.demo.dto;

import io.github.cepr0.crud.api.OnCreate;
import io.github.cepr0.crud.api.OnUpdate;
import io.github.cepr0.crud.dto.CrudRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PersonRequest implements CrudRequest {
	@NotNull(groups = {OnCreate.class, OnUpdate.class})
	private String name;
}
