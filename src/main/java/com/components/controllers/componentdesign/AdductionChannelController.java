package com.components.controllers.componentdesign;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.components.dtos.componentdesign.AdductionChannelDTO;
import com.components.entities.componentdesign.AdductionChannel;
import com.components.response.Response;
import com.components.services.interfaces.componentdesign.AdductionChannelService;

@RestController
@RequestMapping("/AdductionChannel")
public class AdductionChannelController {
	
	private AdductionChannelService aducctionChannelService;
	
	public AdductionChannelController(AdductionChannelService aducctionChannelService) {
		this.aducctionChannelService = aducctionChannelService;
	}

	@PostMapping
	public Response save(@RequestBody AdductionChannelDTO adductionChannel) {
		return aducctionChannelService.save(adductionChannel);
	}
	
	@GetMapping
	public ResponseEntity<List<AdductionChannel>> read() {
		return ResponseEntity.ok(aducctionChannelService.findAll());
	}
	
	@GetMapping("/{id}")
	public Response readById(@PathVariable(name="id") Long id) {
		return aducctionChannelService.findById(id);
	}
	
	@DeleteMapping("/{id}")
	public Response delete(@PathVariable(name="id") Long id ) {
		return aducctionChannelService.delete(id);
	}
	

}
