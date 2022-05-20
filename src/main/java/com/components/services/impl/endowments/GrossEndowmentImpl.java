package com.components.services.impl.endowments;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.components.entities.endowment.GrossEndowment;
import com.components.entities.projection.ArithmeticProjection;
import com.components.entities.projection.ExponentialProjection;
import com.components.entities.projection.GeometricProjection;
import com.components.repositories.endowments.GrossEndowmentRepository;
import com.components.services.interfaces.endowments.GrossEndowmentService;
import com.components.services.interfaces.projections.ArithmeticService;
import com.components.services.interfaces.projections.ExponentialService;
import com.components.services.interfaces.projections.GeometricService;

@Service
public class GrossEndowmentImpl implements GrossEndowmentService {

	private GrossEndowmentRepository grossEndowmentRepo;

	private GeometricService geometricService;
	private ArithmeticService arithmeticService;
	private ExponentialService exponentialService;

	public GrossEndowmentImpl(GrossEndowmentRepository grossEndowmentRepo, GeometricService geometricService,
			ArithmeticService arithmeticService, ExponentialService exponentialService) {
		this.grossEndowmentRepo = grossEndowmentRepo;
		this.geometricService = geometricService;
		this.arithmeticService = arithmeticService;
		this.exponentialService = exponentialService;
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
	public GrossEndowment save(GrossEndowment endowment) throws ArithmeticException {
		
		DecimalFormatSymbols decimalSymbols= new DecimalFormatSymbols();
		decimalSymbols.setDecimalSeparator('.');
		DecimalFormat decimalFormat = new DecimalFormat("#.###" , decimalSymbols);
		
		// CALCULATIONS FOR THE GROSS ENDOWMENT//
		float waterLosses = endowment.getWaterLosses();
		float netEndowment = endowment.getNetEndowment();

		// final value of gross endowment
		float grossEndowment = Float.parseFloat(decimalFormat.format(netEndowment / (1 - waterLosses)));

		// CALCULATIONS FOR THE AVARAGE DAILY FLOW//
		Optional<ArithmeticProjection> arithmeticProjection = arithmeticService
				.findProjectionByIdAqueduct(endowment.getAqueduct().getIdAqueduct());

		Optional<GeometricProjection> geometricProjection = geometricService
				.findProjectionByIdAqueduct(endowment.getAqueduct().getIdAqueduct());

		Optional<ExponentialProjection> exponentialProjection = exponentialService
				.findProjectionByIdAqueduct(endowment.getAqueduct().getIdAqueduct());

		int divider = 0;
		int dividend = 0;
		
		List<Integer> projections= new ArrayList<>();
		projections.add(
				exponentialProjection.isPresent()
				? exponentialProjection.get().getFinalPopulation() : 0);
		projections.add(
				geometricProjection.isPresent()
				? geometricProjection.get().getPopulationFinal() : 0);
		projections.add(
				arithmeticProjection.isPresent()
				? arithmeticProjection.get().getPopulationFinal() : 0);
		
		for(Integer p : projections) {
			if (p != 0) {
				dividend += p;
				divider ++;
			}
		}
		
		if (divider == 0 || dividend == 0) {
			throw new ArithmeticException("there is something wrong in the calculation of the average daily flow, "
					+ "the population average may be zero ");
		}

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

}
