package com.components.controllers.projections;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
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

import com.components.dtos.projections.GeometricDTO;
import com.components.entities.aqueduct.AqueductDesign;
import com.components.entities.projection.FinalProjection;
import com.components.entities.projection.GeometricProjection;
import com.components.services.interfaces.aqueduct.AqueductService;
import com.components.services.interfaces.projections.FinalProjectionService;
import com.components.services.interfaces.projections.GeometricService;

@RestController
@RequestMapping("projection/geometric")
public class GeometricController {

	private GeometricService geometricService;
	private AqueductService aqueductService;
	private FinalProjectionService finProjectServ;
	private Logger log = Logger.getLogger(GeometricController.class);

	public GeometricController(GeometricService geometricService, AqueductService aqueductService,
			FinalProjectionService finProjectServ) {
		this.geometricService = geometricService;
		this.aqueductService = aqueductService;
		this.finProjectServ = finProjectServ;
	}

	@PostMapping
	public ResponseEntity<GeometricDTO> create(@RequestBody GeometricDTO geometricProjection) {

		Optional<AqueductDesign> attachedAqueduct = aqueductService.findById(geometricProjection.getAqueductId());

		if (attachedAqueduct.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		GeometricProjection newGeometricProjection = new GeometricProjection();

		newGeometricProjection.setFinalTime(geometricProjection.getFinalTime());
		newGeometricProjection.setPopulationInitialCensus(geometricProjection.getPopulationInitialCensus());
		newGeometricProjection.setPopulationLastCensus(geometricProjection.getPopulationLastCensus());
		newGeometricProjection.setYearInitialCensus(geometricProjection.getYearInitialCensus());
		newGeometricProjection.setYearLastCensus(geometricProjection.getYearLastCensus());
		newGeometricProjection.setAnnualGrowthRate(geometricProjection.getAnnualGrowthRate());
		newGeometricProjection.setAqueduct(attachedAqueduct.get());

		GeometricProjection savedProjection = geometricService.save(newGeometricProjection);

		geometricProjection.setAnnualGrowthRate(savedProjection.getAnnualGrowthRate());
		geometricProjection.setPopulationFinal(savedProjection.getPopulationFinal());
		geometricProjection.setIdProyection(savedProjection.getIdProyection());

		Optional<FinalProjection> finalProjection = finProjectServ
				.findFinProjectionByAqueduct(geometricProjection.getAqueductId());

		if (finalProjection.isEmpty()
				|| (finalProjection.get().getIdFinalProjection() == 0 
				&& finalProjection.get().getDivider() == 0)) {
			FinalProjection populationFinal = new FinalProjection();
			populationFinal.setFinalProjection(savedProjection.getPopulationFinal());
			populationFinal.setDivider(1);
			populationFinal.setAqueduct(attachedAqueduct.get());
			finProjectServ.save(populationFinal);
		} else {
			float preexistingProjections = finalProjection.get().getFinalProjection();
			int preexistingDividers = finalProjection.get().getDivider();

			float finalPopulation = preexistingProjections + savedProjection.getPopulationFinal();
			int finalDivider = preexistingDividers + 1;
			
			finalProjection.get().setFinalProjection(finalPopulation);
			finalProjection.get().setDivider(finalDivider);

			finProjectServ.save(finalProjection.get());
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(geometricProjection);
	}

	@GetMapping
	public ResponseEntity<List<GeometricProjection>> readall() {
		List<GeometricProjection> listOfProjections = geometricService.findAll();

		return ResponseEntity.status(HttpStatus.OK).body(listOfProjections);
	}

	@GetMapping("/{id}")
	public ResponseEntity<GeometricProjection> read(@PathVariable(name = "id") long projectionId) {

		Optional<GeometricProjection> recoveredProjection = geometricService.findById(projectionId);

		if (recoveredProjection.isEmpty()) {
			log.info("There aren't projections with the id : '" + projectionId + "'");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.ok(recoveredProjection.get());
	}

	@PutMapping("/{id}")
	public ResponseEntity<GeometricProjection> update(@RequestBody GeometricDTO geometricDTO,
			@PathVariable(name = "id") long projectionId) {
		Optional<AqueductDesign> attachedAqueduct = aqueductService.findById(geometricDTO.getAqueductId());

		if (attachedAqueduct.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		Optional<GeometricProjection> recoveredProjection = geometricService.findById(projectionId);

		if (recoveredProjection.isEmpty()) {
			log.info("There aren't projection with the id '" + projectionId
					+ "cannot be updated without a previous projection");
			return ResponseEntity.notFound().build();
		}

		recoveredProjection.get().setFinalTime(geometricDTO.getFinalTime());
		recoveredProjection.get().setPopulationInitialCensus(geometricDTO.getPopulationInitialCensus());
		recoveredProjection.get().setPopulationLastCensus(geometricDTO.getPopulationLastCensus());
		recoveredProjection.get().setYearInitialCensus(geometricDTO.getYearInitialCensus());
		recoveredProjection.get().setYearLastCensus(geometricDTO.getYearLastCensus());

		GeometricProjection updatedProjection = geometricService.save(recoveredProjection.get());

		recoveredProjection.get().setAnnualGrowthRate(updatedProjection.getAnnualGrowthRate());
		recoveredProjection.get().setIdProyection(updatedProjection.getIdProyection());
		recoveredProjection.get().setPopulationFinal(updatedProjection.getPopulationFinal());
		recoveredProjection.get().setAqueduct(attachedAqueduct.get());

		return ResponseEntity.status(HttpStatus.CREATED).body(recoveredProjection.get());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> delete(@PathVariable(name = "id") long projectionId) {

		Optional<GeometricProjection> recoveredProjection = geometricService.findById(projectionId);

		if (recoveredProjection.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		geometricService.delete(projectionId);

		return ResponseEntity.status(HttpStatus.OK).build();

	}
}
