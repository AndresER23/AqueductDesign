package com.components.services.interfaces.aqueduct;

import java.util.List;
import java.util.Optional;

import com.components.entities.aqueduct.AqueductDesign;

public interface AqueductService {
	public List<AqueductDesign> findAll();

	public Optional<AqueductDesign> findById(Long idAqueduct);

	public AqueductDesign save(AqueductDesign aqueduct) throws ArithmeticException;

	public void delete(Long idAqueduct);
}
