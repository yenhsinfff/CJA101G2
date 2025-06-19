package com.lutu.campsite_cancellation.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("campsiteCancellationService")
public class CampsiteCancellationService {
	
	@Autowired
	CampsiteCancellationRepository campsiteCancellationRepository;

	public List<CampsiteCancellationVO> getAllCampsiteCancellation() {
		return campsiteCancellationRepository.findAll();
	}
	
	
}
