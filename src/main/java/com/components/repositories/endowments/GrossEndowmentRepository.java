package com.components.repositories.endowments;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.components.entities.endowment.GrossEndowment;
@Repository
public interface GrossEndowmentRepository extends JpaRepository<GrossEndowment, Long>{
	
	@Query(value="SELECT e FROM GrossEndowment e WHERE e.aqueduct.idAqueduct=:id")
	Optional<GrossEndowment> findEndowmentByAqueduct(@Param("id") Long endowmentId);
	
}
