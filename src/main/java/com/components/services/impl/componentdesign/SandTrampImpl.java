package com.components.services.impl.componentdesign;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.components.dtos.componentdesign.SandTrapDTO;
import com.components.entities.componentdesign.SandTrap;
import com.components.entities.endowment.GrossEndowment;
import com.components.repositories.componentdesign.SandTrapRepository;
import com.components.response.Response;
import com.components.services.interfaces.componentdesign.SandTrampService;
import com.components.services.interfaces.endowments.GrossEndowmentService;

public class SandTrampImpl implements SandTrampService{
	
	private SandTrapRepository sandTrapRepo;
	private GrossEndowmentService endowmentService;
	
	public SandTrampImpl(SandTrapRepository sandTrapRepo , GrossEndowmentService endowmentService) {
		this.sandTrapRepo = sandTrapRepo;
		this.endowmentService = endowmentService;
	}

	@Override
	public Response findAll() {
		
		Response response = new Response();
		
		List<SandTrap> listOfSandTraps = sandTrapRepo.findAll();
		
		if (listOfSandTraps.isEmpty()) {
			response.setMessage("There are not sand traps");
			response.setStatus(404);
			return response;
		}
		
		response.setMessage("OK");
		response.setStatus(200);
		response.setResult(listOfSandTraps);
		
		return response; 
	}

	@Override
	public Response findById(Long idSandTrap) {
		
		Response response = new Response();
		
		Optional<SandTrap> sandTrap = sandTrapRepo.findById(idSandTrap);
		if (sandTrap.isEmpty()) {
			response.setMessage("There is not a sand trap with the id provided" + idSandTrap + "in the path");
			response.setStatus(404);
			return response;
		}
		
		response.setMessage("OK");
		response.setResult(sandTrap.get());
		response.setStatus(200);
		return response;
	}

	@Override
	public Response save(SandTrapDTO sandTrap) throws ArithmeticException, ClassNotFoundException {
		
		Response response = new Response();
		
		//Retrieving the necessary data for the calculation of the Sand trap.
		
		Optional<GrossEndowment> endowment = endowmentService.findByAqueduct(sandTrap.getAquedutId());
		
			if (endowment.isEmpty()) {
				response.setMessage("There is not a water endowment with the id " + sandTrap.getAquedutId() + ", or cannot be recovered.");
				response.setStatus(404);
				return response;
			}
		float flowDesign = endowment.get().getAverageDailyFlow();
		double kinematicViscosity = getKinematicViscosity(sandTrap.getAverageTownTemperature());
		float particleDiamter = sandTrap.getParticleDiameter();
		int sandTrapGrade = sandTrap.getSandTrapGrade();
		float remotionPercentage = sandTrap.getRemovalRate();
		double sandTrapDepth = sandTrap.getDepth() * 100;
		int sandTrapDimensions = sandTrap.getRelationWidthHeight();
		
		//1. Calculate the sedimentation velocity of the particle.
		double vsp = 
				((980*(2.65-1)*Math.pow(particleDiamter, 2)) / (18 * kinematicViscosity))
		;
		
		//2. Retrieve Hazen's number
		
		double hazenNumber = this.getHazenNumber(remotionPercentage,sandTrapGrade);
		
		//3. Calculate the time it would take for the particle to reach the bottom of the tank.
		
		double timeBottomTank= sandTrapDepth / vsp;
		
		//4. Calculate the time of hydraulic retention.
		
		double hydraulicRetention =  ((hazenNumber * timeBottomTank) / 3600 );
		
		// DESIGN THE SAND TRAP FOR THE OPERATING CONDITIONS
		
		//5. Determine the volume of the sand trap.
		
		double volumeOftheSandTrap = hydraulicRetention * flowDesign;
		
		//6. Calculate the surface area of the sand trap.
		
		double surfaceArea = volumeOftheSandTrap / (sandTrapDepth/100);
		
		//7. Sand trap dimensions.
		
		double sandTrapwidth  = Math.sqrt((surfaceArea/sandTrapDimensions));
		double sandTraplength = sandTrapDepth * sandTrapwidth;
		
		//8. Surface load of the sand trap.
			
		double surfaceLoad = (flowDesign / surfaceArea) * 86400;
		
		//9. Calculate particle sedimentation critique under theoretical conditions.
		
		double insideSquareRoot= (
				(((18*kinematicViscosity) * (surfaceLoad*100))/ (980*(2.65 -1)))
						);
		double critiqueDiameter = Math.sqrt(insideSquareRoot);
		
		if ((critiqueDiameter*10) < 0.1) {
			response.setMessage("The sand trap remove particules bigger than the allowed in the resolution, "
					+ "change de design value for that the diametro de particulas that remove the desarenador this between the value allows ");
			response.setStatus(500);
		}
		
		//10. Determine the horizontal velocity and the maximum horizontal velocity.
		
		double horizontalVelocity  = ((surfaceLoad*100) * 100) / sandTrapDepth; 
		double maxHorizontalVelocity= 20 * vsp ;
		
		if (horizontalVelocity > maxHorizontalVelocity || horizontalVelocity > 0.25) {
			response.setMessage("The horizontal speed exceeds the maximum horizontal speed allowed in the resolution");
			response.setStatus(500);
		}
		
		//11. Calculate drag speed
		
		double insideSquareRootDrag = ((0.38*980*(2.65-1)*critiqueDiameter)/(0.03));
		double dragVelocity = Math.sqrt(insideSquareRootDrag);
		
		if (horizontalVelocity > dragVelocity) {
			response.setMessage("The drag must be greater than the speed\r\n"
					+ "horizontal to avoid resuspension of the sediment."
					+ " Drag velocity: " + dragVelocity + ", horizontal velocity" + horizontalVelocity);
			response.setStatus(500);
			return response;
		}
		
		HashMap<String, Double> sandTrapDesign = new HashMap<>();
			sandTrapDesign.put("sedimentationVelocity", vsp);
			sandTrapDesign.put("hazenNumber", hazenNumber);
			sandTrapDesign.put("timeBottomTank", timeBottomTank);
			sandTrapDesign.put("hydraulicRetention", hydraulicRetention);
			sandTrapDesign.put("volumeOftheSandTrap", volumeOftheSandTrap);
			sandTrapDesign.put("surfaceArea", surfaceArea);
			sandTrapDesign.put("sandTrapwidth", sandTrapwidth);
			sandTrapDesign.put("sandTraplength", sandTraplength);
			sandTrapDesign.put("surfaceLoad", surfaceLoad);
			sandTrapDesign.put("critiqueDiameter", critiqueDiameter);
			sandTrapDesign.put("horizontalVelocity", horizontalVelocity);
			sandTrapDesign.put("maxHorizontalVelocity", maxHorizontalVelocity);
			sandTrapDesign.put("dragVelocity", dragVelocity);
			
			
		response.setMessage("OK");
		response.setResult(sandTrapDesign);
		response.setStatus(200);
		
		return response;
	}

	@Override
	public Response delete(Long idSandTrap) {
		
		Response response= new Response ();
		sandTrapRepo.deleteById(idSandTrap);
		if (sandTrapRepo.findById(idSandTrap).isEmpty()) {
			response.setMessage("Something went wrong, the sand trap with the id " + idSandTrap + "Still present in the data base. ");
			response.setStatus(500);
			return response;
		}
		
		response.setMessage("OK");
		response.setStatus(200);
		return response;
		
	}
	
	
	public float formatNumber(double num) {
		DecimalFormatSymbols decimalSymbols = new DecimalFormatSymbols();
		decimalSymbols.setDecimalSeparator('.');
		DecimalFormat decimalFormat = new DecimalFormat("#.####", decimalSymbols);
		
		return Float.parseFloat(decimalFormat.format(num));
	}
	public double getKinematicViscosity(int temperature) {
		
		HashMap<Integer, Double> values= new HashMap<>();
			values.put(0, 0.01792);
			values.put(2, 0.01763);
			values.put(4, 0.01567);
			values.put(6, 0.01473);
			values.put(8, 0.01386);
			values.put(10, 0.01308);
			values.put(12, 0.01237);
			values.put(14, 0.01172);
			values.put(15, 0.01146);
			values.put(16, 0.01112);
			values.put(18, 0.01059);
			values.put(20, 0.01007);
			values.put(22, 0.00960);
			values.put(24, 0.00917);
			values.put(26, 0.00876);
			values.put(28, 0.00839);
			values.put(30, 0.00804);
			values.put(32, 0.00772);
			values.put(34, 0.00741);
			values.put(36, 0.00713);
			
		return values.get(temperature);
			
	}
	
	
	public double getHazenNumber(float remotionPercentage  , int conditions) {
		
		double hazenNumber= 0;
		
		if (remotionPercentage == 50 && conditions == 1) {
			hazenNumber = 1;
		}else if(remotionPercentage == 50 && conditions == 3) {
			hazenNumber = 0.76;
			return hazenNumber;
		}else if(remotionPercentage == 50 && conditions == 4) {
			hazenNumber = 0.50;
			return hazenNumber;
		}else if(remotionPercentage == 55 && conditions == 1) {
			hazenNumber = 1.30;
			return hazenNumber;
		}else if(remotionPercentage == 60 && conditions == 1) {
			hazenNumber = 1.50;
			return hazenNumber;
		}else if(remotionPercentage == 65 && conditions == 1) {
			hazenNumber = 1.80;
			return hazenNumber;
		}else if(remotionPercentage == 70 && conditions == 1) {
			hazenNumber = 2.30;
			return hazenNumber;
		}else if(remotionPercentage == 75 && conditions == 1) {
			hazenNumber = 3;
			return hazenNumber;
		}else if(remotionPercentage == 75 && conditions == 3) {
			hazenNumber = 1.66;
			return hazenNumber;
		}else if(remotionPercentage == 75 && conditions == 4) {
			hazenNumber = 1.52;
			return hazenNumber;
		}else if(remotionPercentage == 80 && conditions == 1) {
			hazenNumber = 4;
			return hazenNumber;
		}else if(remotionPercentage == 87.5 && conditions == 1) {
			hazenNumber = 7;
			return hazenNumber;
		}else if(remotionPercentage == 87.5 && conditions == 3) {
			hazenNumber = 2.75;
			return hazenNumber;
		}else if(remotionPercentage == 87.5 && conditions == 4) {
			hazenNumber = 2.37;
			return hazenNumber;
		}
		
		return hazenNumber;
	}

}
