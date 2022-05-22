package com.components.services.impl.endowments;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.components.entities.endowment.GrossEndowment;
import com.components.entities.projection.FinalProjection;
import com.components.repositories.endowments.GrossEndowmentRepository;
import com.components.services.interfaces.endowments.GrossEndowmentService;
import com.components.services.interfaces.projections.FinalProjectionService;

@Service
public class GrossEndowmentImpl implements GrossEndowmentService {

	private GrossEndowmentRepository grossEndowmentRepo;

	private FinalProjectionService finProjectService;

	public GrossEndowmentImpl(GrossEndowmentRepository grossEndowmentRepo, FinalProjectionService finProjectService  ) {
		this.grossEndowmentRepo = grossEndowmentRepo;
		this.finProjectService = finProjectService;
	}

	@Override
	public List<GrossEndowment> findAll() {
		return grossEndowmentRepo.findAll();
	}

	@Override
	public Optional<GrossEndowment> findById(Long idEndowment) {
		return grossEndowmentRepo.findById(idEndowment);
	}

	@Override
	public GrossEndowment save(GrossEndowment endowment) throws ArithmeticException, ClassNotFoundException {
		
		DecimalFormatSymbols decimalSymbols= new DecimalFormatSymbols();
		decimalSymbols.setDecimalSeparator('.');
		DecimalFormat decimalFormat = new DecimalFormat("#.###" , decimalSymbols);
		
		// CALCULATIONS FOR THE GROSS ENDOWMENT//
		float waterLosses = endowment.getWaterLosses();
		float netEndowment = endowment.getNetEndowment();

		// final value of gross endowment
		float grossEndowment = Float.parseFloat(decimalFormat.format(netEndowment / (1 - waterLosses)));

		// CALCULATIONS FOR THE AVARAGE DAILY FLOW//
		
		Optional<FinalProjection> finalProjection = finProjectService.findFinProjectionByAqueduct(endowment.getAqueduct().getIdAqueduct());
		if (finalProjection.isEmpty()) {
			throw new ClassNotFoundException("There isn't projections.");
		}
		float dividend = finalProjection.get().getFinalProjection();
		float divider = finalProjection.get().getDivider();
		float averagePopulation = dividend / divider;

		// final valor of average daily flow
		float qmd = Float.parseFloat(decimalFormat.format(((averagePopulation * grossEndowment) / 86400)));

		// CALCULATIONS FOR MAXIMUM DAILY FLOW//

		float k1 = endowment.getConsumptionCoefficient1();

		if ((averagePopulation <= 12500 && k1 > 1.3) || (averagePopulation > 12500 && k1 > (float) 1.2)) {
			throw new ArithmeticException("for populations less than or equal 12500 "
					+ "the factor k1 cannot be greater than 1.3, for populations greater than 12500 the factor k1"
					+ "cannot be greater than 1.2");
		}

		// final valor of maximum daily flow
		float QMD =Float.parseFloat(decimalFormat.format(qmd * k1));

		// CALCULATIONS FOR MAXIMUM HOURLY FLOW//
		float k2 = endowment.getConsumptionCoefficient2();

		// final valor of maximum hourly flow
		float QMH =Float.parseFloat(decimalFormat.format(QMD * k2));

		// Assigning values to its attributes

		endowment.setTotalGrossEndowment(grossEndowment);
		endowment.setAverageDailyFlow(qmd);
		endowment.setMaximumDailyFlow(QMD);
		endowment.setMaximumHourlyFlow(QMH);

		return grossEndowmentRepo.save(endowment);
	}

	@Override
	public void delete(Long idEndowment) {
		grossEndowmentRepo.deleteById(idEndowment);
	}
	
	public Optional<GrossEndowment> findByAqueduct(Long idAquduct){
		return grossEndowmentRepo.findEndowmentByAqueduct(idAquduct);
	}

}
