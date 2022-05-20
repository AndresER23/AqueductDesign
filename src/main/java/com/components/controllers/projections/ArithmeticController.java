package com.components.controllers.projections;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.components.dtos.projections.ArithmeticDTO;
import com.components.entities.aqueduct.AqueductDesign;
import com.components.entities.projection.ArithmeticProjection;
import com.components.entities.projection.FinalProjection;
import com.components.services.interfaces.aqueduct.AqueductService;
import com.components.services.interfaces.projections.ArithmeticService;
import com.components.services.interfaces.projections.FinalProjectionService;

@RestController
@RequestMapping("/projection/arithmetic")
public class ArithmeticController {

	private ArithmeticService arithmeticService;
	private FinalProjectionService finProjectServ;
	private AqueductService aqueductService;
	Logger log= LoggerFactory.getLogger(ArithmeticController.class);
	
	public ArithmeticController(ArithmeticService arithmeticService, AqueductService aqueductService, 
			FinalProjectionService finProjectServ) {
		this.arithmeticService = arithmeticService;
		this.aqueductService = aqueductService;
		this.finProjectServ = finProjectServ;
	}

	@PostMapping
	public ResponseEntity<ArithmeticProjection> create(@RequestBody ArithmeticDTO arithmetic) {
		
		Optional<AqueductDesign> attachedAqueduct = aqueductService.findById(arithmetic.getAqueductId());
		
		if (attachedAqueduct.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		ArithmeticProjection projectionArithmetic = new ArithmeticProjection();
		
		projectionArithmetic.setFinalTime(arithmetic.getFinalTime());
		projectionArithmetic.setPopulationInitialCensus(arithmetic.getPopulationInitialCensus());
		projectionArithmetic.setPopulationLastCensus(arithmetic.getPopulationLastCensus());
		projectionArithmetic.setYearInitialCensus(arithmetic.getYearInitialCensus());
		projectionArithmetic.setYearLastCensus(arithmetic.getYearLastCensus());
		projectionArithmetic.setAqueduct(attachedAqueduct.get());
		
		ArithmeticProjection savedProjection= arithmeticService.save(projectionArithmetic);
		
		Optional<FinalProjection> finalProjection = finProjectServ.findFinProjectionByAqueduct(arithmetic.getAqueductId());
		
		if (finalProjection.isEmpty() || (finalProjection.get().getIdFinalProjection() == 0 
											&& finalProjection.get().getDivider() == 0 )) {
			FinalProjection populationFinal = new FinalProjection();
			populationFinal.setFinalProjection(savedProjection.getPopulationFinal());
			populationFinal.setDivider(1);
			populationFinal.setAqueduct(attachedAqueduct.get());
			finProjectServ.save(populationFinal);
		}else {
			float preexistingProjections = finalProjection.get().getFinalProjection();
			int preexistingDividers= finalProjection.get().getDivider();
			
			float finalPopulation = preexistingProjections + savedProjection.getPopulationFinal();
			int finalDivider= preexistingDividers + 1;
			
			finalProjection.get().setFinalProjection(finalPopulation);
			finalProjection.get().setDivider(finalDivider);
			
			finProjectServ.save(finalProjection.get());
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(savedProjection);
	}

	@GetMapping
	public ResponseEntity<List<ArithmeticProjection>> readAll() {
		List<ArithmeticProjection> listAll = arithmeticService.findAll();

		if (listAll.isEmpty()) {
			log.info("arithmetic records do not exist or cannot be found");
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(listAll);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ArithmeticProjection> readById(@PathVariable(value = "id") Long projectionId) {
		Optional<ArithmeticProjection> arithmeticProjection = arithmeticService.findById(projectionId);

		if (arithmeticProjection.isEmpty()) {
			log.info("Don't exist arithmetic records with id" + projectionId + "or couldn't be retrieved");
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(arithmeticProjection.get());
	}

	@PutMapping("/{id}")
	public ResponseEntity<ArithmeticDTO> update(@RequestBody ArithmeticDTO arithmeticDTO,
			@PathVariable(value = "id") Long projectiontId) {
		Optional<ArithmeticProjection> previousProjection = arithmeticService.findById(projectiontId);
		
		if (previousProjection.isEmpty()) {
			log.info("Failed to update arithmetic records because previous records with the id :" + projectiontId
					+ "do not exist or couldn't be retrieved.");
			return ResponseEntity.notFound().build();
		}
		
		Optional<AqueductDesign> attachedAqueduct = aqueductService.findById(arithmeticDTO.getAqueductId());
		if (attachedAqueduct.isEmpty() ) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		previousProjection.get().setFinalTime(arithmeticDTO.getFinalTime());
		previousProjection.get().setPopulationInitialCensus(arithmeticDTO.getPopulationInitialCensus());
		previousProjection.get().setPopulationLastCensus(arithmeticDTO.getPopulationLastCensus());
		previousProjection.get().setYearInitialCensus(arithmeticDTO.getYearInitialCensus());
		previousProjection.get().setYearLastCensus(arithmeticDTO.getYearLastCensus());
		previousProjection.get().setGrowthRate(arithmeticDTO.getGrowthRate());
		previousProjection.get().setAqueduct(attachedAqueduct.get());

		ArithmeticProjection updatedProjection = arithmeticService.save(previousProjection.get());
		arithmeticDTO.setGrowthRate(updatedProjection.getGrowthRate());
		arithmeticDTO.setIdProyection(updatedProjection.getIdProyection());
		arithmeticDTO.setPopulationFinal(updatedProjection.getPopulationFinal());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(arithmeticDTO);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ArithmeticDTO> delete(@PathVariable(value = "id") Long projectionId) {
		Optional<ArithmeticProjection> recoveredArithmeticData = arithmeticService.findById(projectionId);

		if (recoveredArithmeticData.isEmpty()) {
			log.info("for delete arithmetic records is necessary a previous records, "
					+ "failed in retrieve those previous records, verify the id provided : '" + projectionId + "'");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		arithmeticService.delete(projectionId);

		return ResponseEntity.status(HttpStatus.OK).build();

	}
}
