package com.components.repositories.componentdesign;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.components.entities.componentdesign.SandTrap;

@Repository
public interface SandTrapRepository extends JpaRepository<SandTrap, Long>{

}
