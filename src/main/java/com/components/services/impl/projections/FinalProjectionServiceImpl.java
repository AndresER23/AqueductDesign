package com.components.services.impl.projections;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.components.entities.projection.FinalProjection;
import com.components.repositories.projection.FinalProjectionRepository;
import com.components.services.interfaces.projections.FinalProjectionService;

@Service
public class FinalProjectionServiceImpl implements FinalProjectionService{
	
	private FinalProjectionRepository finalProjectionRepo;
	
	public FinalProjectionServiceImpl(FinalProjectionRepository finalProjectionRepo) {
		this.finalProjectionRepo = finalProjectionRepo;
	}

	@Override
	public List<FinalProjection> findAll() {
		return finalProjectionRepo.findAll();
	}

	@Override
	public Optional<FinalProjection> findById(Long idFinalProjection) {
		return finalProjectionRepo.findById(idFinalProjection);
	}

	@Override
	public FinalProjection save(FinalProjection finalProjection) {
		return finalProjectionRepo.save(finalProjection);
	}

	@Override
	public void delete(Long idFinalProjection) {
		finalProjectionRepo.deleteById(idFinalProjection);
	}
	
	@Override
	public Optional<FinalProjection> findFinProjectionByAqueduct(Long id){
		return finalProjectionRepo.findFinProjectionByAqueduct(id);	} 
}
