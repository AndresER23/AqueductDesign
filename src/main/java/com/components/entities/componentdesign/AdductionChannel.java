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
@Table(name="adduction_channel")
public class AdductionChannel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_adducction_channel")
	private Long idAdductionChannel;
	
	@Column(name="adduction_length")	
	private float adductionLength;
	
	@Column(name="roughness_coefficient")
	private float roughnessCoefficient;
	
	@Column(name="upper_bound")
	private float upperBound;
	
	@Column(name="lower_bound")
	private float lowerBound;
	
	@OneToOne
	@JoinColumn(name="id_aqueduct")
	AqueductDesign aqueduct; 
	
	
}

