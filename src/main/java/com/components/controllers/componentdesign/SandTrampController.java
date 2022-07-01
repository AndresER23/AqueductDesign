package com.components.controllers.componentdesign;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.components.dtos.componentdesign.SandTrapDTO;
import com.components.response.Response;
import com.components.services.interfaces.componentdesign.SandTrapService;

@RestController
@RequestMapping("/sandtrap")
public class SandTrampController {
	
	private SandTrapService sandTrapServ;
	
	
	public SandTrampController(SandTrapService sandTrapServ) {
		this.sandTrapServ = sandTrapServ;
	}

	@PostMapping
	public Response save(@RequestBody SandTrapDTO sandTramp) {
		Response response = new Response();
		try {
			response = sandTrapServ.save(sandTramp);
		} catch (ArithmeticException | ClassNotFoundException e) {
			response.setMessage(e.getMessage());
			response.setStatus(404);
		}
		return response;
	}
	
	@GetMapping("/{id}")
	public Response readById(@PathVariable(value="id")Long id) {
		return sandTrapServ.findById(id);
	}
	
	@GetMapping
	public Response readAll() {
		return sandTrapServ.findAll();
	} 
	
	@DeleteMapping("/{id}")
	public Response deleteById(@PathVariable(value="id") Long id) {
		return sandTrapServ.delete(id);
	}
}
