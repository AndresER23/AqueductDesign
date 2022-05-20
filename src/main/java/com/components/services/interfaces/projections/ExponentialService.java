package com.components.services.interfaces.projections;

import java.util.List;
import java.util.Optional;

import com.components.entities.projection.ExponentialProjection;

public interface ExponentialService {

	public List<ExponentialProjection> findAll();

	public Optional<ExponentialProjection> findById(Long id);

	public ExponentialProjection save(ExponentialProjection exponential);

	public void delete(Long idExponential);
	
	public Optional<ExponentialProjection> findProjectionByIdAqueduct(Long aqueductId);
}
