package com.components.entities.componentdesign;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SandTrap {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_sand_trap")
	private Long idSandTrap;
	
	@Column(name="modules")
	private float modules;
	
	@Column(name="particle_diameter")
	private float particleDiameter;
	
	@Column(name="removal_rate")
	private int removalRate;
	
	@Column(name="average_town_temperature")
	private int averageTownTemperature;
	
	@Column(name="depth")
	private int depth;
	
	@Column(name="relationWidthHeight")
	private int relationWidthHeight;
	
	@Column(name="sand_trap_grade")
	private int sandTrapGrade;
}
