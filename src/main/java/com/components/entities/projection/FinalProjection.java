package com.components.entities.projection;

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
@Getter
@Setter
@Table(name="final_projection")
public class FinalProjection {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_final_p")
	private Long idFinalProjection;
	
	@Column(name="desgin_flow")
	private float finalProjection;
	
	@OneToOne
	@JoinColumn(name="id_aqueduct")
	AqueductDesign aqueduct;
	
	@Column(name="divider")
	private int divider;
	
}
