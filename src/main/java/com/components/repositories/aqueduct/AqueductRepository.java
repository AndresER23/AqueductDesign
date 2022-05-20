package com.components.repositories.aqueduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.components.entities.aqueduct.AqueductDesign;
@Repository
public interface AqueductRepository extends JpaRepository<AqueductDesign, Long>{
}
