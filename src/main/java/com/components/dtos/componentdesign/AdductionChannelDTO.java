package com.components.dtos.componentdesign;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdductionChannelDTO {
	

	private Long idAdductionChannel;
	private float adductionLength;
	private float roughnessCoefficient;
	private float upperBound;
	private float lowerBound;
	private long aqueductId;
}
