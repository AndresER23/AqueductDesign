package com.components.services.interfaces.componentdesign;

import java.util.List;

import com.components.dtos.componentdesign.AdductionChannelDTO;
import com.components.entities.componentdesign.AdductionChannel;
import com.components.response.Response;

public interface AdductionChannelService {
	public List<AdductionChannel> findAll();

	public Response findById(Long idAdductionChannel);

	public Response save(AdductionChannelDTO adductionChannelDTO);

	public Response delete(Long idAdductionChannel);
}
