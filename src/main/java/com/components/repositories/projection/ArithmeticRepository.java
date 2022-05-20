package com.components.repositories.projection;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.components.entities.projection.ArithmeticProjection;

@Repository
public interface ArithmeticRepository extends JpaRepository<ArithmeticProjection, Long> {
	
	@Query(value="SELECT a FROM ArithmeticProjection a WHERE a.aqueduct.idAqueduct=:id")
	Optional<ArithmeticProjection> findArithmeticProjectionByAqueductIdAcueduct(@Param("id") Long aqueductId);
}
