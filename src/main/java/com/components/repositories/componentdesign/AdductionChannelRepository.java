package com.components.repositories.componentdesign;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.components.entities.componentdesign.AdductionChannel;

@Repository
public interface AdductionChannelRepository extends JpaRepository<AdductionChannel, Long>{

}
