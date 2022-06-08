package com.components.dtos.componentdesign;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BottomIntakeDTO {
	private Long idBottomIntake;
	private float minRiverFlow;
	private float meanRiverFlow;
	private float maxRiverFlow;
	private float riverWidth;
	private float damWidth;
	private float lateralContractions;
	private float barsDiameter;
	private float spacingBetweenBars;
	private float speedBetweenBars;
	private float heigthOfWaterSheet;
	private float flowMultiplier;
	private float designFlow;
	private float wallThickness;
	private float channelSlope;
	private float freeEdge;
	private Long idAttachedAqueduct;
}
