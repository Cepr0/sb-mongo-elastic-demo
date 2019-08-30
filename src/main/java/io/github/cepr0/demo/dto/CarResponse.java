package io.github.cepr0.demo.dto;

import io.github.cepr0.crud.dto.CrudResponse;
import lombok.Data;

@Data
public class CarResponse implements CrudResponse<String> {
	private String id;
	private String brand;
	private String model;
	private Integer year;
	private PersonDto person;
}
