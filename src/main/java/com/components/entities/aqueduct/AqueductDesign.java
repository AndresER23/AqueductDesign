package com.components.entities.aqueduct;

import javax.persistence.*;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="aqueduct")
public class AqueductDesign {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name= "id_aqueduct")
	private Long idAqueduct;
	
	@Column(name="aqu_name")
	private String aqueductName;
}
