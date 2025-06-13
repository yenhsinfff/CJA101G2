package com.lutu.campsite_order_details.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CampsiteOrderDetailsRepository extends JpaRepository<CampSiteOrderDetailsVO, Integer>{

}
