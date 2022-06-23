package com.components.services.impl.componentdesign;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.components.dtos.componentdesign.AdductionChannelDTO;
import com.components.entities.componentdesign.AdductionChannel;
import com.components.entities.endowment.GrossEndowment;
import com.components.repositories.componentdesign.AdductionChannelRepository;
import com.components.response.Response;
import com.components.services.interfaces.componentdesign.AdductionChannelService;
import com.components.services.interfaces.endowments.GrossEndowmentService;

@Service
public class AdductionChannelImpl implements AdductionChannelService{
	
	private AdductionChannelRepository adductionChannelRepo;
	private GrossEndowmentService grossEndowmentService;	
	
	
	
	
	public AdductionChannelImpl(AdductionChannelRepository adductionChannelRepo,
			GrossEndowmentService grossEndowmentService) {
		this.adductionChannelRepo = adductionChannelRepo;
		this.grossEndowmentService = grossEndowmentService;
	}

	public List<AdductionChannel> findAll(){
		return adductionChannelRepo.findAll();
	}

	public Response findById(Long idAdductionChannel){
		
		Response response = new Response();
		Optional<AdductionChannel> recoveredAdductionChannel = adductionChannelRepo.findById(idAdductionChannel);
		if (recoveredAdductionChannel.isEmpty()) {
			response.setMessage("There is no adduction canal with the provided ID or it cannot be found");
			response.setStatus(404);
			return response;
		}
		
		response.setMessage("OK");
		response.setStatus(200);
		response.setResult(recoveredAdductionChannel);
		
		return response;
	}

	public Response save(AdductionChannelDTO adductionChannel) {
		
		Response response = new Response();
		
		//Recover the data associated with the id provided in the path of the request
		long idAqueductAtacched = adductionChannel.getAqueductId();
		Optional<GrossEndowment> endowment = grossEndowmentService.findByAqueduct(idAqueductAtacched);
		
		//Verification of the existence of the data
		if (endowment.isEmpty()) {
			response.setMessage("There is not an endowment associated with the id of the aqueduct ");
		}
		float designflow = endowment.get().getAverageDailyFlow();
		
		
		//Calculations to find the adduction channel slope
		
		float upperBound= adductionChannel.getUpperBound();
		float lowerBound = adductionChannel.getLowerBound();
		float adductionChannelLengh = adductionChannel.getAdductionLength();
		
		float slope= formatNumber(((upperBound-lowerBound)/adductionChannelLengh)); //final slope
		
		//Calculations to determine the diameter corresponding to Maning's equation
		float roughnessCoefficient = adductionChannel.getRoughnessCoefficient();
		float pipeDiameter =  formatNumber(
				((1.548)*(Math.pow(
						((roughnessCoefficient*designflow)/(Math.pow(slope, 0.5))), 0.375)))
				); // Pipe Diameter 
		
		//For constructive purposes, convert meters to inches.
		
		float convertPipeDiameter = formatNumber((pipeDiameter * 39.37));
		float finalPipeDiameter = convertPipeDiameter;
		
		
		//The immediately larger pipe diameter is adopted
		if (convertPipeDiameter<=3) {
			finalPipeDiameter = 3;
		}else if (convertPipeDiameter>3 && convertPipeDiameter<=4) {
			finalPipeDiameter = 4;
		}else if (convertPipeDiameter>4 && convertPipeDiameter<=6) {
			finalPipeDiameter = 6;
		}else if (convertPipeDiameter>6 && convertPipeDiameter<=8) {
			finalPipeDiameter = 8;
		}else if (convertPipeDiameter>8 && convertPipeDiameter<=10) {
			finalPipeDiameter = 10;
		}else if (convertPipeDiameter>10 && convertPipeDiameter<=12) {
			finalPipeDiameter = 12;
		}else if (convertPipeDiameter>12 && convertPipeDiameter<=14) {
			finalPipeDiameter = 14;
		}else if (convertPipeDiameter>14 && convertPipeDiameter<=16) {
			finalPipeDiameter = 16;
		}
		
		//Establishing full pipe flow conditions}
		
		float flowFullPipe = formatNumber(
				
				((0.312*Math.pow(finalPipeDiameter, 2.666666667)*Math.pow(slope, 0.5))/roughnessCoefficient)
				
				);
		
		float velocityFullPipe;
				
				
		
		
		return null;
	}

	public Response delete(Long idAdductionChannel) {
		
		Response response = new Response();
		response.setMessage("OK");
		response.setStatus(200);
		adductionChannelRepo.deleteById(idAdductionChannel);
		
		return response;
	}
	
	public float formatNumber(double num) {
		DecimalFormatSymbols decimalSymbols = new DecimalFormatSymbols();
		decimalSymbols.setDecimalSeparator('.');
		DecimalFormat decimalFormat = new DecimalFormat("#.####", decimalSymbols);

		return Float.parseFloat(decimalFormat.format(num));
	}
}
