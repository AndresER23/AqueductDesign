package com.components.services.impl.componentdesign;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.components.entities.componentdesign.BottomIntake;
import com.components.repositories.componentdesign.BottomIntakeRepository;
import com.components.services.impl.projections.ArithmeticServiceImpl;
import com.components.services.interfaces.componentdesign.BottomIntakeService;
@Service
public class BottomIntakeImpl implements BottomIntakeService {

	private BottomIntakeRepository btmIntkRepo;
	
	public BottomIntakeImpl(BottomIntakeRepository btmIntkRepo) {
		this.btmIntkRepo = btmIntkRepo;
	}

	@Override
	public List<BottomIntake> findAll() {
		return btmIntkRepo.findAll();
	}

	@Override
	public Optional<BottomIntake> findById(Long idBottomIntake) {
		return btmIntkRepo.findById(idBottomIntake);
	}

	@Override
	public BottomIntake save(BottomIntake bottomIntake) throws ArithmeticException {
		
		return btmIntkRepo.save(bottomIntake);
	}

	@Override
	public void delete(Long idBottomIntake) {
		btmIntkRepo.deleteById(idBottomIntake);
	}

}
