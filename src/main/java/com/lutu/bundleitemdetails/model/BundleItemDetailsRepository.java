package com.lutu.bundleitemdetails.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lutu.bundleitem.model.BundleItemVO;

@Repository
public interface BundleItemDetailsRepository extends JpaRepository<BundleItemDetailsVO, Integer>{

}
