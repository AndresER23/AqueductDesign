package com.components.services.impl.componentdesign;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Optional;

import org.hibernate.stat.SecondLevelCacheStatistics;
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
		//Decimal format
		DecimalFormatSymbols decimalSymbols= new DecimalFormatSymbols();
		decimalSymbols.setDecimalSeparator('.');
		DecimalFormat decimalFormat = new DecimalFormat("#.###" , decimalSymbols);
		
		//Calculations for the design flow for the bottom intakes
		Long idAqueduct = bottomIntake.getAqueduct().getIdAqueduct();
		 Optional<GrossEndowment> finalEndowment= 
				 grossEndowmentService.findByAqueduct(idAqueduct);
		 
		 if (finalEndowment.isEmpty()) {
			throw new ClassNotFoundException("There is no projection related to the aqueduct with id:"
					+ idAqueduct);
		}
		 
		 float initialFlow = finalEndowment.get().getMaximumDailyFlow();
		 
		 float flowMultiplier= 0;
		 float finalDesignFlow = 0;
		 if (bottomIntake.getFlowMultiplier() != 0) {
			
			 flowMultiplier = bottomIntake.getFlowMultiplier();
			 finalDesignFlow =  initialFlow * flowMultiplier;
   		}else {
   			finalDesignFlow= initialFlow;
   		}
		 
		 //Calculations to obtain the height of the water sheet
		 
		 float weirLength= bottomIntake.getDamWidth();
		 float divider= (float) (1.84 * weirLength);
		 float heigthWaterSheet = Float.parseFloat(
				 decimalFormat.format(
						 Math.pow((finalDesignFlow/divider), 0.6666666667))); 
		 
		 //Calculation for the correction of the length of the weir
		 
		 float numOfContractions= bottomIntake.getLateralContractions();
		 float lengthCorrection= (float) (weirLength-(0.1*numOfContractions*heigthWaterSheet));
		 
		 //Calculation to speed of the river over the dam
		 
		 float speedOverDam= Float.parseFloat(
				 decimalFormat.format(
						 finalDesignFlow/ (lengthCorrection*heigthWaterSheet)));
		 
		 //CALCULATIONS FOR THE GRID DESIGN
		 
		 float topEdgeReach  = Float.parseFloat(
				 decimalFormat.format(
						 (0.36*Math.pow(speedOverDam,0.6666666667)) 
						 + (0.60*Math.pow(heigthWaterSheet, 0.5714285714))
						 )
				 );
				 
		 float bottomEdgeReach = Float.parseFloat(
				 decimalFormat.format(
						 (0.18*Math.pow(speedOverDam, 0.5714285714))
						 +(0.74*Math.pow(heigthWaterSheet, 0.75))
						 )
				 );
				 
		 float adductionCanalWidth = Float.parseFloat(
				 decimalFormat.format(
						 (topEdgeReach + 0.10)
						 )
				 );
				 
		 	//first calculate to An
		 float aN1 = Float.parseFloat(
				 decimalFormat.format(
						 finalDesignFlow/ (0.9 * bottomIntake.getSpeedBetweenBars())
						 )
				 );
		 
		 float a= bottomIntake.getSpacingBetweenBars();
		 float b= bottomIntake.getBarsDiameter();
		 	//Length of the grid
		 float lR = formatNumber(((aN1*(a+b))/(adductionCanalWidth*a)));
		 	//Recalculation of An
		 float aN2 = formatNumber(((a/(a+b))*(adductionCanalWidth*lR)));
		 	//Calculation for the number of holes
		 float n = formatNumber((aN2/(a*adductionCanalWidth)));
		 	//Last recalculation for An
		 float aN3= formatNumber((a*adductionCanalWidth*n));
		 	//Calculation for speed between bars
		 float vB1= this.formatNumber((finalDesignFlow/(0.9*aN3)));
				 	
		//CALCULATIONS FOR THE ADDUCTION CANAL
		 
		 float channelSlope= bottomIntake.getChannelSlope();
		 float wallThickness = bottomIntake.getWallThickness();
		 
		 
		 /*to make the operation easier to read, 
		 the calculation will be separated into different parts,
		 the first encapsulated in the variable "firstCalculate" 
		 who contains the division and the second one variable "secondCalculate" 
		 contains the elevation. */
		 
		 	//Calculations to find the height downstream
		 float Q= formatNumber(Math.pow(finalDesignFlow, 2));
		 float B= formatNumber(Math.pow(adductionCanalWidth, 2));
		 float calculate= formatNumber((Q/(9.8*B)));
		 
		 float downStreamHeight = formatNumber((Math.pow(calculate, 0.3333333333)));				
						 
		 	//Calculations to find the height upstream
		 
		 		//First statement of the formula
		 float firstStatement= formatNumber((2 * Math.pow(downStreamHeight, 2)));
		 
		 		//Second statement of the formula
		 float lC = lR + wallThickness;
		 float secondStatement = downStreamHeight - ((channelSlope*lC)/3);
		 
		 		//Third statement of the formula
		 double squaredSecondStatement= Math.pow(secondStatement, 2);
		
		 
		 		//First part of the formula
		 float sumOfstatements = formatNumber((firstStatement + squaredSecondStatement));
		 float partOneElevated = (float) Math.pow(sumOfstatements, 0.5);
		 		
		 		//Second part of the formula
		 float secondPartFormula= formatNumber(((0.6666666667) * (channelSlope * lC)));
		 
		 		//Final result of the height upstream
		 float heightUpstream = formatNumber((partOneElevated - secondPartFormula));//
		 
		 //Calculations to find the total height
		 
		 	float h0 = heightUpstream + bottomIntake.getFreeEdge();//
		 	float hE = h0 + (channelSlope*lC);//
		 	
		//Calculations to find the speed of the water at the end of the channel.
		 	
		 	float vE= formatNumber((finalDesignFlow/(adductionCanalWidth*downStreamHeight)));//
		 
		 	
		//COLLECTION CHAMBER CALCULATIONS 
		 	
		 	float xS= formatNumber(
		 			((0.36*Math.pow(vE, 0.6666666667)) + (0.60*Math.pow(downStreamHeight, 0.5714285714)))
		 			);
		 	float xI = formatNumber(
		 			((0.18*Math.pow(vE, 0.5714285714))+(0.74*Math.pow(downStreamHeight, 0.75)))
		 			);
		 	
		 	float bChamber= formatNumber((xS + 0.30));
		 			
		 	
		return btmIntkRepo.save(bottomIntake);
	}

	@Override
	public void delete(Long idBottomIntake) {
		btmIntkRepo.deleteById(idBottomIntake);
	}

	public float formatNumber(double num) {
		DecimalFormatSymbols decimalSymbols = new DecimalFormatSymbols();
		decimalSymbols.setDecimalSeparator('.');
		DecimalFormat decimalFormat = new DecimalFormat("#.####", decimalSymbols);

		return Float.parseFloat(decimalFormat.format(num));
	}
}
