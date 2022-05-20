package com.components.dtos.projections;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeometricDTO {
	
	private Long idProyection;
	private int populationFinal;
	private int populationLastCensus;
	private int populationInitialCensus;
	private int yearLastCensus;
	private int yearInitialCensus;
	private int finalTime;
	private double annualGrowthRate;
	private Long aqueductId;
}
