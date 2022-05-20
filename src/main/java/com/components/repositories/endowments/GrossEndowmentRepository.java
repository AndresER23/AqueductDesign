package com.components.repositories.endowments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.components.entities.endowment.GrossEndowment;
@Repository
public interface GrossEndowmentRepository extends JpaRepository<GrossEndowment, Long>{
}
