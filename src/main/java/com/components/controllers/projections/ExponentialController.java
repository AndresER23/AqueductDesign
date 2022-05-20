package com.components.controllers.projections;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.components.dtos.projections.ExponentialDTO;
import com.components.entities.aqueduct.AqueductDesign;
import com.components.entities.projection.ExponentialProjection;
import com.components.entities.projection.FinalProjection;
import com.components.services.interfaces.aqueduct.AqueductService;
import com.components.services.interfaces.projections.ExponentialService;
import com.components.services.interfaces.projections.FinalProjectionService;

@RestController
@RequestMapping("projection/exponential")
public class ExponentialController {

	private ExponentialService exponentialService;
	private AqueductService aqueductService;
	private FinalProjectionService finProjectServ;

	@Autowired
	public ExponentialController(ExponentialService exponentialService, AqueductService aqueductService,
			FinalProjectionService finProjectServ) {
		this.exponentialService = exponentialService;
		this.aqueductService = aqueductService;
		this.finProjectServ = finProjectServ;
	}

	@PostMapping
	public ResponseEntity<ExponentialDTO> post(@RequestBody ExponentialDTO exponentialData) {

		Optional<AqueductDesign> attachedAqueduct = aqueductService.findById(exponentialData.getAqueductId());

		if (attachedAqueduct.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		ExponentialProjection exponentialProjection = new ExponentialProjection();

		exponentialProjection.setFinalTime(exponentialData.getFinalTime());
		exponentialProjection.setLaterCensusYear(exponentialData.getLaterCensusYear());
		exponentialProjection.setPopulationLastCensus(exponentialData.getPopulationLastCensus());
		exponentialProjection.setPreviousCensusPopulation(exponentialData.getPreviousCensusPopulation());
		exponentialProjection.setPreviousCensusYear(exponentialData.getPreviousCensusYear());
		exponentialProjection.setAqueduct(attachedAqueduct.get());

		ExponentialProjection savedProjection = exponentialService.save(exponentialProjection);

		exponentialData.setIdExponential(savedProjection.getIdExponential());
		exponentialData.setFinalPopulation(savedProjection.getFinalPopulation());
		exponentialData.setGrowthRate(savedProjection.getGrowthRate());

		Optional<FinalProjection> finalProjection = finProjectServ
				.findFinProjectionByAqueduct(exponentialData.getAqueductId());

		if (finalProjection.isEmpty()
				|| (finalProjection.get().getIdFinalProjection() == 0 && finalProjection.get().getDivider() == 0)) {
			FinalProjection populationFinal = new FinalProjection();
			populationFinal.setFinalProjection(savedProjection.getFinalPopulation());
			populationFinal.setDivider(1);
			populationFinal.setAqueduct(attachedAqueduct.get());
			finProjectServ.save(populationFinal);
		} else {
			float preexistingProjections = finalProjection.get().getFinalProjection();
			int preexistingDividers = finalProjection.get().getDivider();

			float finalPopulation = preexistingProjections + savedProjection.getFinalPopulation();
			int finalDivider = preexistingDividers + 1;
			
			finalProjection.get().setFinalProjection(finalPopulation);
			finalProjection.get().setDivider(finalDivider);
			
			finProjectServ.save(finalProjection.get());
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(exponentialData);
	}

	@GetMapping
	public ResponseEntity<List<ExponentialProjection>> readAll() {

		List<ExponentialProjection> allProjections = exponentialService.findAll();

		if (allProjections.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.ok(allProjections);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ExponentialProjection> readByID(@PathVariable(value = "id") long projectionId) {

		Optional<ExponentialProjection> recoveredProjection = exponentialService.findById(projectionId);

		if (recoveredProjection.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.ok(recoveredProjection.get());

	}

	@PutMapping("/{id}")
	public ResponseEntity<ExponentialDTO> update(@RequestBody ExponentialDTO exponentialDTO,
			@PathVariable(name = "id") long projectionId) {

		Optional<AqueductDesign> attachedAqueduct = aqueductService.findById(exponentialDTO.getAqueductId());

		if (attachedAqueduct.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		Optional<ExponentialProjection> recoveredProjection = exponentialService.findById(projectionId);

		if (recoveredProjection.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		recoveredProjection.get().setFinalTime(exponentialDTO.getFinalTime());
		recoveredProjection.get().setLaterCensusYear(exponentialDTO.getLaterCensusYear());
		recoveredProjection.get().setPopulationLastCensus(exponentialDTO.getPopulationLastCensus());
		recoveredProjection.get().setPreviousCensusPopulation(exponentialDTO.getPreviousCensusPopulation());
		recoveredProjection.get().setPreviousCensusYear(exponentialDTO.getPreviousCensusYear());
		recoveredProjection.get().setAqueduct(attachedAqueduct.get());

		ExponentialProjection savedProjection = exponentialService.save(recoveredProjection.get());

		exponentialDTO.setFinalPopulation(savedProjection.getFinalPopulation());
		exponentialDTO.setIdExponential(savedProjection.getIdExponential());
		exponentialDTO.setGrowthRate(savedProjection.getGrowthRate());

		return ResponseEntity.status(HttpStatus.CREATED).body(exponentialDTO);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> delete(@PathVariable(name = "id") long projectionId) {

		Optional<ExponentialProjection> recoveredProjection = exponentialService.findById(projectionId);

		if (recoveredProjection.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		exponentialService.delete(projectionId);

		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
