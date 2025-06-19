package com.lutu.campsite_order.model;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CampSiteOrderRepository extends JpaRepository<CampSiteOrderVO, String> {

//    @Transactional
//    @Modifying
//    @Query(value = "delete from emp3 where empno =?1", nativeQuery = true)
//    void deleteByEmpno(int empno);
//
    // ● (自訂)條件查詢
//    @Query(value = "from CampSiteOrderVO where campsite_order_id=?1")
    @Query("from CampSiteOrderVO where campsiteOrderId = ?1")
    CampSiteOrderVO findByCampSiteOrderId(String campSiteOrderId);
}
