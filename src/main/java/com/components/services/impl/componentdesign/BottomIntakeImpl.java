package com.components.services.impl.componentdesign;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.components.entities.componentdesign.BottomIntake;
import com.components.entities.endowment.GrossEndowment;
import com.components.repositories.componentdesign.BottomIntakeRepository;
import com.components.services.interfaces.componentdesign.BottomIntakeService;
import com.components.services.interfaces.endowments.GrossEndowmentService;
@Service
public class BottomIntakeImpl implements BottomIntakeService {

	private BottomIntakeRepository btmIntkRepo;
	private GrossEndowmentService grossEndowmentService;
	
	public BottomIntakeImpl(BottomIntakeRepository btmIntkRepo, GrossEndowmentService grossEndowmentService) {
		this.btmIntkRepo = btmIntkRepo;
		this.grossEndowmentService = grossEndowmentService;
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
	public BottomIntake save(BottomIntake bottomIntake) throws ArithmeticException, ClassNotFoundException {
		
		//Calculations for the design flow for the bottom intakes
		Long idAqueduct = bottomIntake.getAqueduct().getIdAqueduct();
		 Optional<GrossEndowment> finalEndowment= 
				 grossEndowmentService.findByAqueduct(idAqueduct);
		 
		 if (finalEndowment.isEmpty()) {
			throw new ClassNotFoundException("There is no projection related to the aqueduct with id:"
					+ idAqueduct);
		}
		 
		 float initialFlow = finalEndowment.get().getMaximumDailyFlow();
		 float flowMultiplier = bottomIntake.getFlowMultiplier();
		 double finalDesignFlow =  initialFlow * flowMultiplier;
		 
		 //Calculations to obtain the height of the water sheet
		 
		 float weirLength= bottomIntake.getDamWidth();
		 float divider= (float) (1.84 * weirLength);
		 float heigthWaterSheet = (float) Math.pow(finalDesignFlow/divider, 2/3);
		 
		 //Calculation for the correction of the length of the weir
		 
		 float NumOfContractions= bottomIntake.getLateralContractions();
		 float lengthCorrection= (float) (weirLength-(0.1*NumOfContractions*heigthWaterSheet));
		 
		 //Calculation to speed of the river over the dam
		 
		 float speedOverDam= (float) (finalDesignFlow/ lengthCorrection*heigthWaterSheet);
		 
		 //CALCULATIONS FOR THE GRID DESIGN
		 
		 double topEdgeReach  = (0.36*Math.pow(speedOverDam,2/3)) + (0.60*Math.pow(heigthWaterSheet, 4/7));
		 double bottomEdgeReach = (0.18*Math.pow(speedOverDam, 4/7))+(0.74*Math.pow(heigthWaterSheet, 3/4));
		 float adductionCanalWidth = (float) (topEdgeReach + 0.10);
		 	//first calculate to An
		 double An1 = finalDesignFlow/ 0.9 * bottomIntake.getSpeedBetweenBars();
		 
		 float a= bottomIntake.getSpacingBetweenBars();
		 float b= bottomIntake.getBarsDiameter();
		 	//Length of the grid
		 double Lr = (An1*(a+b))/(adductionCanalWidth*a);
		 	//Recalculation of An
		 double An2 = (a/a+b)*(adductionCanalWidth*Lr);
		 	//Calculation for the number of holes
		 double N = An2/(a*adductionCanalWidth);
		 	//Last recalculation for An
		 double An3= a*adductionCanalWidth*N;
		 	//Calculation for speed between bars
		 double Vb= finalDesignFlow/0.9*An3;
		
		
		return btmIntkRepo.save(bottomIntake);
	}

	@Override
	public void delete(Long idBottomIntake) {
		btmIntkRepo.deleteById(idBottomIntake);
	}
	
}
