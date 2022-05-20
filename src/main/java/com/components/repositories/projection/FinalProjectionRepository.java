package com.components.repositories.projection;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.components.entities.projection.FinalProjection;
@Repository
public interface FinalProjectionRepository extends JpaRepository<FinalProjection, Long>{
	
	@Query(value="SELECT f FROM FinalProjection f WHERE f.aqueduct.idAqueduct=:id")
	Optional<FinalProjection> findFinProjectionByAqueduct(@Param("id")Long id);
}
