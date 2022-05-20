package com.components.services.interfaces.projections;

import java.util.List;
import java.util.Optional;

import com.components.entities.projection.FinalProjection;

public interface FinalProjectionService {
	public List<FinalProjection> findAll();

	public Optional<FinalProjection> findById(Long idFinalProjection);

	public FinalProjection save(FinalProjection finalProjection);

	public void delete(Long idFinalProjection);
	
	public Optional<FinalProjection> findFinProjectionByAqueduct(Long id);
}
