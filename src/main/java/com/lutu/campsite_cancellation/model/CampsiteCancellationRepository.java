package com.lutu.campsite_cancellation.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampsiteCancellationRepository extends JpaRepository<CampsiteCancellationVO, String>{

}
