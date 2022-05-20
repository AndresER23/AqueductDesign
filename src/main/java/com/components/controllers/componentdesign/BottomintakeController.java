package com.components.controllers.componentdesign;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.components.dtos.componentdesign.BottomIntakeDTO;
import com.components.entities.aqueduct.AqueductDesign;
import com.components.entities.componentdesign.BottomIntake;
import com.components.services.impl.aqueduct.AqueductImpl;
import com.components.services.impl.componentdesign.BottomIntakeImpl;

@RestController
@RequestMapping("/bottomintake")
public class BottomintakeController {
	
	private BottomIntakeImpl btmIntkImpl;
	private AqueductImpl aqueductImpl;
	@PostMapping
	public ResponseEntity<BottomIntakeDTO> create(@RequestBody BottomIntakeDTO bottomIntake){
		
		Optional<AqueductDesign> attachedAqueduct= aqueductImpl.findById(bottomIntake.getIdAttachedAqueduct());
		
		if (attachedAqueduct.isEmpty()) {
			throw new EntityNotFoundException("There isn't attached aqueduct associated with the id provided in the path"
					+ "or cannot be found.");
		}
		
		
		BottomIntake newBottomIntake= new BottomIntake();
		newBottomIntake.setAqueduct(attachedAqueduct.get());
		newBottomIntake.setBarsDiameter(bottomIntake.getBarsDiameter());
		newBottomIntake.setDamWidth(bottomIntake.getDamWidth());
		newBottomIntake.setLateralContractions(bottomIntake.getLateralContractions());
		newBottomIntake.setMaxRiverFlow(bottomIntake.getMaxRiverFlow());
		newBottomIntake.setMinRiverFlow(bottomIntake.getMinRiverFlow());
		newBottomIntake.setMeanRiverFlow(bottomIntake.getMeanRiverFlow());
		newBottomIntake.setRiverWidth(bottomIntake.getRiverWidth());
		newBottomIntake.setSpacingBetweenBars(bottomIntake.getSpacingBetweenBars());
		newBottomIntake.setSpeedBetweenBars(bottomIntake.getSpeedBetweenBars());
		
		BottomIntake savedBottomIntake= btmIntkImpl.save(newBottomIntake);
		
		bottomIntake.setIdBottomIntake(savedBottomIntake.getIdBottomIntake());
		
		return ResponseEntity.ok(bottomIntake);
	}
}
