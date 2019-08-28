package io.github.cepr0.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.cepr0.crud.api.OnCreate;
import io.github.cepr0.crud.api.OnUpdate;
import io.github.cepr0.crud.dto.CrudRequest;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
public class CarRequest implements CrudRequest {
	@NotNull(groups = OnCreate.class)
	private String brand;

	@NotNull(groups = OnCreate.class)
	private String model;

	@NotNull(groups = OnCreate.class)
	@Min(value = 1900, groups = {OnCreate.class, OnUpdate.class})
	private Integer year;

	@NotNull(groups = OnCreate.class)
	@Null(groups = OnUpdate.class)
	@JsonProperty("personId") private String person;
}
