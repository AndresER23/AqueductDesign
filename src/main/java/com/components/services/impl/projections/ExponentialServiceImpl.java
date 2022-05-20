package com.components.services.impl.projections;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.components.entities.projection.ExponentialProjection;
import com.components.repositories.projection.ExponentialRepository;
import com.components.services.interfaces.projections.ExponentialService;

@Service
public class ExponentialServiceImpl implements ExponentialService{
	
	private ExponentialRepository exponentialRepo;
	
	public ExponentialServiceImpl(ExponentialRepository exponentialRepo) {
		this.exponentialRepo = exponentialRepo;
	}

	@Override
	public List<ExponentialProjection> findAll() {
		
		return exponentialRepo.findAll();
	}

	@Override
	public Optional<ExponentialProjection> findById(Long id) {
		
		return exponentialRepo.findById(id);
	}

	@Override
	public ExponentialProjection save(ExponentialProjection exponential) {
		
		int tf= exponential.getFinalTime();
		int puc= exponential.getPopulationLastCensus();
		int pca= exponential.getPreviousCensusPopulation();
		int tcp= exponential.getLaterCensusYear();
		int tca= exponential.getPreviousCensusYear();
		
		DecimalFormatSymbols decimalSymbols= new DecimalFormatSymbols();
		decimalSymbols.setDecimalSeparator('.');
		DecimalFormat decimalFormat = new DecimalFormat("#.###" , decimalSymbols);
		
		double k = Double.parseDouble(decimalFormat.format((Math.log(puc) - Math.log(pca))/(tcp-tca)));
		
		double pf= puc * Math.pow(Math.E, k*(tf-tca));
		
		exponential.setFinalPopulation((int) pf);
		exponential.setGrowthRate(k);
		
		return exponentialRepo.save(exponential);
	}

	@Override
	public void delete(Long idExponential) {
		exponentialRepo.deleteById(idExponential);
	}

	@Override
	public Optional<ExponentialProjection> findProjectionByIdAqueduct(Long aqueductId) {
		
		return exponentialRepo.findProjectionByAqueduct(aqueductId);
	}
}
