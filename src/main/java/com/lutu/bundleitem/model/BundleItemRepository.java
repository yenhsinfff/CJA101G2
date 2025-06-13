package com.lutu.bundleitem.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BundleItemRepository extends JpaRepository<BundleItemVO, Integer>{

}
