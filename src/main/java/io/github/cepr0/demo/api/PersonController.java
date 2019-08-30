package io.github.cepr0.demo.api;

import io.github.cepr0.crud.api.AbstractCrudController;
import io.github.cepr0.crud.api.OnCreate;
import io.github.cepr0.crud.api.OnUpdate;
import io.github.cepr0.demo.dto.PersonRequest;
import io.github.cepr0.demo.dto.PersonResponse;
import io.github.cepr0.demo.model.Person;
import io.github.cepr0.demo.service.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("people")
public class PersonController extends AbstractCrudController<Person, String, PersonRequest, PersonResponse> {
	public PersonController(final PersonService service) {
		super(service);
	}

	@PostMapping
	@Override
	public ResponseEntity<PersonResponse> create(@Validated(OnCreate.class) @RequestBody final PersonRequest request) {
		return super.create(request);
	}

	@PatchMapping("/{id}")
	@Override
	public ResponseEntity<PersonResponse> update(@PathVariable("id") final String id, @Validated(OnUpdate.class) @RequestBody final PersonRequest request) {
		return super.update(id, request);
	}

	@DeleteMapping("/{id}")
	@Override
	public ResponseEntity<?> delete(@PathVariable("id") final String id) {
		return super.delete(id);
	}

	@GetMapping("/{id}")
	@Override
	public ResponseEntity<PersonResponse> getOne(@PathVariable("id") final String id) {
		return super.getOne(id);
	}

	@GetMapping
	@Override
	public ResponseEntity<Page<PersonResponse>> getAll(Pageable pageable) {
		return super.getAll(pageable);
	}
}
