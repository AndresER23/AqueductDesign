package com.components.controllers.endowments;

import java.util.Optional;

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

import com.components.dtos.endowments.GrossEndowmentDTO;
import com.components.entities.aqueduct.AqueductDesign;
import com.components.entities.endowment.GrossEndowment;
import com.components.services.interfaces.aqueduct.AqueductService;
import com.components.services.interfaces.endowments.GrossEndowmentService;

@RestController
@RequestMapping("/endowments")
public class GrossEndowmentController {
	
	private GrossEndowmentService grossEndowmentServ;
	private AqueductService aqueductService;
	
	public GrossEndowmentController(GrossEndowmentService grossEndowmentServ, AqueductService aqueductService) {
		this.grossEndowmentServ = grossEndowmentServ;
		this.aqueductService = aqueductService;
	}

	@PostMapping
	public ResponseEntity<GrossEndowmentDTO> create(@RequestBody GrossEndowmentDTO endowment){
		
		Optional<AqueductDesign>  attachedAqueduct = aqueductService.findById(endowment.getAqueductId());
		if (attachedAqueduct.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		GrossEndowment newEndowment = new GrossEndowment();
		
		newEndowment.setConsumptionCoefficient1(endowment.getConsumptionCoefficient1());
		newEndowment.setConsumptionCoefficient2(endowment.getConsumptionCoefficient2());
		newEndowment.setWaterLosses(endowment.getWaterLosses());
		newEndowment.setNetEndowment(endowment.getNetEndowment());
		newEndowment.setAqueduct(attachedAqueduct.get());
		
		
		GrossEndowment savedEndowment = grossEndowmentServ.save(newEndowment);
		
		endowment.setAverageDailyFlow(savedEndowment.getAverageDailyFlow());
		endowment.setMaximumDailyFlow(savedEndowment.getMaximumDailyFlow());
		endowment.setMaximumHourlyFlow(savedEndowment.getMaximumHourlyFlow());
		endowment.setTotalGrossEndowment(savedEndowment.getTotalGrossEndowment());
				
		
		return ResponseEntity.ok(endowment);
		
	}
	
	@GetMapping
	public ResponseEntity<GrossEndowmentDTO> read(@RequestBody GrossEndowmentDTO endowment){
		return null;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<GrossEndowmentDTO> readById(@PathVariable(name="id") long endowmentId){
		return null;
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<GrossEndowmentDTO> update(@RequestBody GrossEndowmentDTO endowment){
		return null;
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<GrossEndowmentDTO> delete(@RequestBody GrossEndowmentDTO endowment){
		return null;
	}
}
