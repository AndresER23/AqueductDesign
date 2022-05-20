package com.components.controllers.aqueduct;

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

import com.components.dtos.aqueduct.AqueductDTO;
import com.components.entities.aqueduct.AqueductDesign;
import com.components.services.impl.aqueduct.AqueductImpl;
import com.components.services.interfaces.aqueduct.AqueductService;

@RestController
@RequestMapping("/desing")
public class AqueductController {

	private AqueductService aqueductImpl;
	@Autowired
	public AqueductController(AqueductImpl aqueductImpl) {
		this.aqueductImpl = aqueductImpl;
	}

	@PostMapping
	public ResponseEntity<AqueductDesign> create(@RequestBody AqueductDTO aqueduct) {
		AqueductDesign newAqueduct = new AqueductDesign();
		newAqueduct.setAqueductName(aqueduct.getAqueductName());
		AqueductDesign savedAcueduct = aqueductImpl.save(newAqueduct);
		newAqueduct.setIdAqueduct(savedAcueduct.getIdAqueduct());

		return ResponseEntity.ok(newAqueduct);
	}

	@GetMapping
	public ResponseEntity<List<AqueductDesign>> readAll() {
		List<AqueductDesign> listAqueducts = aqueductImpl.findAll();
		return ResponseEntity.ok(listAqueducts);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AqueductDesign> readById(@PathVariable(value = "id") Long aqueductId) {
		Optional<AqueductDesign> recoveredAqueduct = aqueductImpl.findById(aqueductId);
		if (recoveredAqueduct.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(recoveredAqueduct.get());
	}

	@PutMapping("{id}")
	public ResponseEntity<AqueductDesign> update(@RequestBody AqueductDTO aqueduct,
			@PathVariable(value = "id") Long aqueductId) {
		Optional<AqueductDesign> preexistingAqueduct = aqueductImpl.findById(aqueductId);
		
		if (preexistingAqueduct.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		preexistingAqueduct.get().setAqueductName(aqueduct.getAqueductName());
		
		return ResponseEntity.ok(aqueductImpl.save(preexistingAqueduct.get()));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> delete(@PathVariable(value="id") Long aqueductId){
		Optional<AqueductDesign> recoveredAqueduct = aqueductImpl.findById(aqueductId);
		
		if (recoveredAqueduct.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		aqueductImpl.delete(aqueductId);
		
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
