package io.github.cepr0.demo.dto;

import com.fasterxml.jackson.annotation.JsonView;
import io.github.cepr0.crud.dto.CrudResponse;
import lombok.Data;

@Data
public class CarResponse implements CrudResponse<String> {
	@JsonView(Views.ForPerson.class) private String id;
	@JsonView(Views.ForPerson.class) private String brand;
	@JsonView(Views.ForPerson.class) private String model;
	@JsonView(Views.ForPerson.class) private Integer year;
	@JsonView(Views.ForCar.class) private PersonDto person;
}
