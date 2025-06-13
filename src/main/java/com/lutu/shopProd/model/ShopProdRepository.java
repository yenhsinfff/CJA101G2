// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/

package com.lutu.shopProd.model;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ShopProdRepository extends JpaRepository<ShopProdVO, Integer> { //(VO,PK型別)

	@Transactional
	@Modifying
	@Query(value = "delete from shop_prod where prod_id =?1", nativeQuery = true)
	void deleteByProdId(int prodId);

	//● (自訂)條件查詢
	@Query(value = "from ShopProdVO where prodId=?1 and prodName like?2 and prodReleaseDate=?3 order by prodId")
	List<ShopProdVO> findByOthers(Integer prodId , String prodName , Timestamp prodReleaseDate);
	
//	//● (自訂)條件查詢
//	@Query(value = "from EmpVO where empno=?1 order by empno")
//	List<EmpVO> findByOthers(int empno);
//	
//	//● (自訂)條件查詢 依部門編號查詢
//	@Query(value = "from DeptVO where deptno=?1 order by empno")
//	List<EmpVO> findByOthers(DeptVO deptVO);
}