package com.components.services.interfaces.projections;

import java.util.List;
import java.util.Optional;

import com.components.entities.projection.GeometricProjection;

public interface GeometricService {
	
	public List<GeometricProjection> findAll();

	public Optional<GeometricProjection> findById(Long id);

	public GeometricProjection save(GeometricProjection geometric);

	public void delete(Long idGeometric);
	
	public Optional<GeometricProjection> findProjectionByIdAqueduct(Long aqueductId);
}
