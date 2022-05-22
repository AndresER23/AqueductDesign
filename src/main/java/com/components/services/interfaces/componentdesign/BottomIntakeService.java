package com.components.services.interfaces.componentdesign;

import java.util.List;
import java.util.Optional;

import com.components.entities.componentdesign.BottomIntake;

public interface BottomIntakeService {
	public List<BottomIntake> findAll();

	public Optional<BottomIntake> findById(Long idBottomIntake);

	public BottomIntake save(BottomIntake bottomIntake) throws ArithmeticException, ClassNotFoundException;

	public void delete(Long idBottomIntake);
}
