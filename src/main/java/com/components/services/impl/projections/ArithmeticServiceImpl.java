package com.components.services.impl.projections;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.components.entities.projection.ArithmeticProjection;
import com.components.repositories.projection.ArithmeticRepository;
import com.components.services.interfaces.projections.ArithmeticService;

@Service
public class ArithmeticServiceImpl  implements ArithmeticService{
	
	private ArithmeticRepository arithmeticRepository;
	
	public ArithmeticServiceImpl(ArithmeticRepository arithmeticRepository) {
		this.arithmeticRepository = arithmeticRepository;
	}

	@Override
	public List<ArithmeticProjection> findAll() {
		
		return arithmeticRepository.findAll();
	}

	@Override
	public Optional<ArithmeticProjection> findById(Long id) {
		
		return arithmeticRepository.findById(id);
	}

	@Override
	public ArithmeticProjection save(ArithmeticProjection arithmetic) throws ArithmeticException {
		
		int puc= arithmetic.getPopulationLastCensus();
		int tf= arithmetic.getFinalTime();
		int tuc= arithmetic.getYearLastCensus();
		int pci= arithmetic.getPopulationInitialCensus();
		int tci= arithmetic.getYearInitialCensus();
		
		if( puc < pci || tf < tci || tf < tuc || tuc < tci) {
			throw new ArithmeticException();
		}
		
		int c = (puc - pci)/(tuc - tci);
		int pf = puc + c * (tf - tuc);
		
		arithmetic.setGrowthRate(c);
		arithmetic.setPopulationFinal(pf);
		
		return arithmeticRepository.save(arithmetic);
	}
	
	@Override
	public Optional<ArithmeticProjection> findProjectionByIdAqueduct(Long aqueductId){
		return arithmeticRepository.findArithmeticProjectionByAqueductIdAcueduct(aqueductId);
	} 
	
	@Override
	public void delete(Long idArithmetic) {
		
		arithmeticRepository.deleteById(idArithmetic);
	}
}
