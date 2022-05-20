package com.components.services.interfaces.projections;

import java.util.List;
import java.util.Optional;

import com.components.entities.projection.ArithmeticProjection;


public interface ArithmeticService {
	
	public List<ArithmeticProjection> findAll();

	public Optional<ArithmeticProjection> findById(Long id);

	public ArithmeticProjection save(ArithmeticProjection arithmetic) throws ArithmeticException;

	public void delete(Long idArithmetic);
	
	public Optional<ArithmeticProjection> findProjectionByIdAqueduct(Long aqueductId);
}
