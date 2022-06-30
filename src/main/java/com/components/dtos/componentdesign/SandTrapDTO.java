package com.components.dtos.componentdesign;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SandTrapDTO {
	
	private float modules;
	private float particleDiameter;
	private int removalRate;
	private int averageTownTemperature;
	private double depth;
	private int relationWidthHeight;
	private int sandTrapGrade;
	private Long aquedutId;
}
