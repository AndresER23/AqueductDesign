package com.components.dtos.projections;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArithmeticDTO {
	
	private Long idProyection;
	private int populationFinal;
	private int populationLastCensus;
	private int populationInitialCensus;
	private int yearLastCensus;
	private int yearInitialCensus;
	private int finalTime;
	private int growthRate;
	private long aqueductId;
}
