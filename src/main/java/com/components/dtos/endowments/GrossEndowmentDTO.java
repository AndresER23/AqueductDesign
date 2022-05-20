package com.components.dtos.endowments;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrossEndowmentDTO {
	private float netEndowment;
	private float waterLosses;
	private float totalGrossEndowment;
	private float averageDailyFlow;
	private float maximumDailyFlow;
	private float maximumHourlyFlow;
	private float consumptionCoefficient1;
	private float consumptionCoefficient2;
	private Long  aqueductId;
}
