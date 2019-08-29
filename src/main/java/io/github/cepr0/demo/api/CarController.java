package io.github.cepr0.demo.api;

import io.github.cepr0.crud.api.AbstractCrudController;
import io.github.cepr0.crud.api.OnCreate;
import io.github.cepr0.crud.api.OnUpdate;
import io.github.cepr0.demo.dto.CarRequest;
import io.github.cepr0.demo.dto.CarResponse;
import io.github.cepr0.demo.model.Car;
import io.github.cepr0.demo.service.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cars")
public class CarController extends AbstractCrudController<Car, String, CarRequest, CarResponse> {

	public CarController(final CarService service) {
		super(service);
	}

	@PostMapping
	@Override
	public ResponseEntity<CarResponse> create(@Validated(OnCreate.class) @RequestBody final CarRequest request) {
		return super.create(request);
	}

	@PatchMapping("/{id}")
	@Override
	public ResponseEntity<CarResponse> update(@PathVariable("id") final String id, @Validated(OnUpdate.class) @RequestBody final CarRequest request) {
		return super.update(id, request);
	}

	@DeleteMapping("/{id}")
	@Override
	public ResponseEntity<?> delete(@PathVariable("id") final String id) {
		return super.delete(id);
	}

	@GetMapping("/{id}")
	@Override
	public ResponseEntity<CarResponse> getOne(@PathVariable("id") final String id) {
		return super.getOne(id);
	}

	@GetMapping
	@Override
	public ResponseEntity<List<CarResponse>> getAll() {
		return super.getAll();
	}
}
