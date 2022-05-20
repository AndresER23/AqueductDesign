package com.components.services.impl.projections;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.components.entities.projection.GeometricProjection;
import com.components.repositories.projection.GeometricRepository;
import com.components.services.interfaces.projections.GeometricService;

@Service
public class GeometricServiceImpl implements GeometricService{
	
	private GeometricRepository geometricRepo;
	
	public GeometricServiceImpl(GeometricRepository geometricRepo) {
		this.geometricRepo = geometricRepo;
	}

	@Override
	public List<GeometricProjection> findAll() {
		return geometricRepo.findAll();
	}

	@Override
	public Optional<GeometricProjection> findById(Long id) {
		return geometricRepo.findById(id);
	}

	@Override
	public GeometricProjection save(GeometricProjection geometric) {
		
		int puc= geometric.getPopulationLastCensus();
		int pci= geometric.getPopulationInitialCensus();
		int tuc= geometric.getYearLastCensus();
		int tci= geometric.getYearInitialCensus();
		int tf= geometric.getFinalTime();
		
		double r= geometric.getAnnualGrowthRate();
		double base= (double) puc / (double) pci;
		double exponential = 1/((double) tuc - (double) tci);
		
		if (r <= 0) {
			DecimalFormatSymbols decimalSymbol = new DecimalFormatSymbols();
			decimalSymbol.setDecimalSeparator('.');
			DecimalFormat formatDecimals = new DecimalFormat("#.###", decimalSymbol);
			
			r = Double.parseDouble(formatDecimals.format(Math.pow(base,exponential)-1));
		}
		
		double pf= puc*Math.pow((1+r), (tf-tuc));
		
		geometric.setAnnualGrowthRate(r);
		geometric.setPopulationFinal((int)pf);
		geometric.getAnnualGrowthRate();
		geometric.getPopulationFinal();
		
		return geometricRepo.save(geometric);
	}

	@Override
	public void delete(Long idGeometric) {
		geometricRepo.deleteById(idGeometric);
	}
	
	@Override
	public Optional<GeometricProjection> findProjectionByIdAqueduct(Long aqueductId){
		return geometricRepo.findProjectionByIdAqueduct(aqueductId);
	}
}
