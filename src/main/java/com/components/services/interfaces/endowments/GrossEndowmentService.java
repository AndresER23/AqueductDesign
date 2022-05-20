package com.components.services.interfaces.endowments;

import java.util.List;
import java.util.Optional;

import com.components.entities.endowment.GrossEndowment;

public interface GrossEndowmentService {

	public List<GrossEndowment> findAll();

	public Optional<GrossEndowment> findById(Long idEndowment);

	public GrossEndowment save(GrossEndowment endowment) throws ArithmeticException;

	public void delete(Long idEndowment);
}
