package com.components.entities.projection;

import java.io.Serializable;

import javax.persistence.*;

import com.components.entities.aqueduct.AqueductDesign;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="exponential_projection")
public class ExponentialProjection implements Serializable {

	private static final long serialVersionUID = -2512674432422125542L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_exponential")
	private Long idExponential;
	
	@Column(name="p_uc")
	private int populationLastCensus;
	
	@Column(name="t_fin")
	private int finalTime;
	
	@Column(name="t_uc")
	private int yearLastCensus;
	
	@Column(name="p_cp")
	private int lastCensusPopulation;
	
	@Column(name="p_ca")
	private int previousCensusPopulation;
	
	@Column(name="t_cp")
	private int laterCensusYear;
	
	@Column(name="t_ci")
	private int previousCensusYear;
	
	@Column(name="p_fin")
	private int finalPopulation;
	
	@Column(name="k_annual")
	private double growthRate;
	
	@OneToOne
	@JoinColumn(name = "id_aqueduct")
	AqueductDesign aqueduct;
	}
