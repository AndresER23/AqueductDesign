package com.components.services.impl.aqueduct;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.components.entities.aqueduct.AqueductDesign;
import com.components.repositories.aqueduct.AqueductRepository;
import com.components.services.interfaces.aqueduct.AqueductService;
@Service
public class AqueductImpl implements AqueductService {

	private AqueductRepository aqueductRepo;	

	public AqueductImpl(AqueductRepository aqueductRepo) {
		this.aqueductRepo = aqueductRepo;
	}


	@Override
	public List<AqueductDesign> findAll() {
		return aqueductRepo.findAll();
	}

	@Override
	public Optional<AqueductDesign> findById(Long idAqueduct) {
		return aqueductRepo.findById(idAqueduct);
	}

	@Override
	public AqueductDesign save(AqueductDesign aqueduct) throws ArithmeticException {
		return aqueductRepo.save(aqueduct);
	}

	@Override
	public void delete(Long idAqueduct) {
		aqueductRepo.deleteById(idAqueduct);
	}
}
