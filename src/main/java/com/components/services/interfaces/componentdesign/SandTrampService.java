package com.components.services.interfaces.componentdesign;

import java.util.List;
import java.util.Optional;

import com.components.dtos.componentdesign.SandTrapDTO;
import com.components.entities.componentdesign.SandTrap;
import com.components.response.Response;

public interface SandTrampService {
	
	public Response findAll();

	public Response findById(Long idSandTrap);

	public Response save(SandTrapDTO sandTrap) throws ArithmeticException, ClassNotFoundException;

	public Response delete(Long idSandTrap);
}
