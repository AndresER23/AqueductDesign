package com.components.dtos.projections;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExponentialDTO {
	private Long idExponential;
	private int populationLastCensus;
	private int finalTime;
	private int previousCensusPopulation;
	private int laterCensusYear;
	private int previousCensusYear;
	private int finalPopulation;
	private double growthRate;
	private Long aqueductId;
}
