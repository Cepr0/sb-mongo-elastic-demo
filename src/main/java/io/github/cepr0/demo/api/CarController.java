/*
 * Copyright 2019 Generic-CRUD contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.cepr0.demo.api;

import io.github.cepr0.crud.api.AbstractCrudController;
import io.github.cepr0.crud.api.OnCreate;
import io.github.cepr0.crud.api.OnUpdate;
import io.github.cepr0.demo.model.Car;
import io.github.cepr0.demo.dto.CarRequest;
import io.github.cepr0.demo.dto.CarResponse;
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
