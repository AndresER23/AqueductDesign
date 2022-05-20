package com.components.repositories.projection;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.components.entities.projection.GeometricProjection;

@Repository
public interface GeometricRepository extends JpaRepository<GeometricProjection, Long> {
	
	@Query(value="SELECT g FROM GeometricProjection g WHERE g.aqueduct.idAqueduct=:id")
	Optional<GeometricProjection> findProjectionByIdAqueduct(@Param("id") Long aqueductId);
}

