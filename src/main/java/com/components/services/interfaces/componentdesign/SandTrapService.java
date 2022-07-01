package com.components.services.interfaces.componentdesign;

import com.components.dtos.componentdesign.SandTrapDTO;
import com.components.response.Response;

public interface SandTrapService {
	
	public Response findAll();

	public Response findById(Long idSandTrap);

	public Response save(SandTrapDTO sandTrap) throws ArithmeticException, ClassNotFoundException;

	public Response delete(Long idSandTrap);
}
