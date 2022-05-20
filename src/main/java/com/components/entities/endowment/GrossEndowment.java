package com.components.entities.endowment;

import javax.persistence.*;

import com.components.entities.aqueduct.AqueductDesign;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="gross_endowment")
public class GrossEndowment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_endowment")
	private Long idEndowment;
	
	@Column(name="net_endowment")
	private float netEndowment;
	
	@Column(name="w_loss")
	private float waterLosses;
	
	@Column(name="t_gross_endowment")
	private float totalGrossEndowment;
	
	@Column(name="av_dai_flow")
	private float averageDailyFlow;
	
	@Column(name="max_dai_flow")
	private float maximumDailyFlow;
	
	@Column(name="max_h_flow")
	private float maximumHourlyFlow;
	
	@Column(name="consum_coeff1")
	private float consumptionCoefficient1;
	
	@Column(name="consum_coeff2")
	private float consumptionCoefficient2;
	
	@OneToOne
	@JoinColumn(name = "id_aqueduct")
	AqueductDesign aqueduct;
}
