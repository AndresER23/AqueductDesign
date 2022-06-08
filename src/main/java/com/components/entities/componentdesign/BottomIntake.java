package com.components.entities.componentdesign;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.components.entities.aqueduct.AqueductDesign;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="bottom_intake")
public class BottomIntake {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_bottom.intake")
	private Long idBottomIntake;
	
	@Column(name="min_river_flow")
	private float minRiverFlow;
	
	@Column(name="mean_river_flow")
	private float meanRiverFlow;
	
	@Column(name="max_river_flow")
	private float maxRiverFlow;
	
	@Column(name="river_width")
	private float riverWidth;
	
	@Column(name="dam_width")
	private float damWidth;
	
	@Column(name="lateral_contractions")
	private float lateralContractions;
	
	@Column(name="bars_diameter")
	private float barsDiameter;
	
	@Column(name="spacing_btw_bars")
	private float spacingBetweenBars;
	
	@Column(name="speed_btw_bars")
	private float speedBetweenBars;
	
	@Column(name="height_water_sheet")
	private float heigthOfWaterSheet;
	
	@Column(name="flow_multiplier")
	private float flowMultiplier;
	
	@Column(name="design_flow")
	private float designFlow;
	
	@Column(name="channel_slope")
	private float channelSlope;
	
	@Column(name="wall_thickness")
	private float wallThickness;
	
	@Column(name="free_edge")
	private float freeEdge;
	
	@OneToOne
	@JoinColumn(name="id_aqueduct")
	AqueductDesign aqueduct;
}
