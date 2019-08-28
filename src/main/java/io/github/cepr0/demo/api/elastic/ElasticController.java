package io.github.cepr0.demo.api.elastic;

import io.github.cepr0.demo.model.elastic.ElasticCar;
import io.github.cepr0.demo.model.elastic.ElasticPerson;
import io.github.cepr0.demo.repo.elastic.CarElasticRepo;
import io.github.cepr0.demo.repo.elastic.PersonElasticRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/elastic")
public class ElasticController {

	private final CarElasticRepo carElasticRepo;
	private final PersonElasticRepo personElasticRepo;

	public ElasticController(CarElasticRepo carElasticRepo, PersonElasticRepo personElasticRepo) {
		this.carElasticRepo = carElasticRepo;
		this.personElasticRepo = personElasticRepo;
	}

	@GetMapping("/cars")
	public Page<ElasticCar> getCars(Pageable pageable) {
		return carElasticRepo.findAll(pageable);
	}

	@GetMapping("/people")
	public Page<ElasticPerson> getPeople(Pageable pageable) {
		return personElasticRepo.findAll(pageable);
	}
}
