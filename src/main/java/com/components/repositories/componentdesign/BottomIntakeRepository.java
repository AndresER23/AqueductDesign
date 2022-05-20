package com.components.repositories.componentdesign;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.components.entities.componentdesign.BottomIntake;

@Repository
public interface BottomIntakeRepository extends JpaRepository<BottomIntake, Long>{
}
