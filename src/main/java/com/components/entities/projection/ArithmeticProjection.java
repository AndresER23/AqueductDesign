package com.components.entities.projection;

import java.io.Serializable;

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

@Entity
@Table(name="arithmetic_projection")
@Getter
@Setter
public class ArithmeticProjection implements Serializable{

	private static final long serialVersionUID = 7973146036067615210L;
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="id_proyection")
	private Long idProyection;
	
	@Column(name="p_fin")
	private int populationFinal;
	
	@Column(name="p_uc")
	private int populationLastCensus;
	
	@Column(name="p_ci")
	private int populationInitialCensus;
	
	@Column(name="t_uc")
	private int yearLastCensus;
	
	@Column(name="t_ci")
	private int yearInitialCensus;

	@Column(name="t_fin")
	private int finalTime;
	
	@Column(name="c_arith")
	private int growthRate;
	
	@OneToOne
	@JoinColumn(name = "id_aqueduct")
	AqueductDesign aqueduct;
}
