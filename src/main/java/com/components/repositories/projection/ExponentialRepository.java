package com.components.repositories.projection;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.components.entities.projection.ExponentialProjection;


@Repository
public interface ExponentialRepository extends JpaRepository<ExponentialProjection, Long>{
	
	@Query(value="SELECT e FROM ExponentialProjection e WHERE e.aqueduct.idAqueduct=:id")
	Optional<ExponentialProjection> findProjectionByAqueduct(@Param("id")Long aqueductId);
}
