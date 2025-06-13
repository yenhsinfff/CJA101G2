// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/

package com.lutu.shopProd.model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopProdRepository extends JpaRepository<ShopProdVO, Integer> {

//	@Transactional
//	@Modifying
//	@Query(value = "delete from shop_prod where prod_id =?1", nativeQuery = true)
//	void deleteByProdId(int prodId);

	//● (自訂)條件查詢
//	@Query(value = "from EmpVO where empno=?1 and ename like?2 and hiredate=?3 order by empno")
//	List<ShopProdVO> findByOthers(Integer prodId , String prodName , java.sql.Date hiredate);
	
//	//● (自訂)條件查詢
//	@Query(value = "from EmpVO where empno=?1 order by empno")
//	List<EmpVO> findByOthers(int empno);
//	
//	//● (自訂)條件查詢 依部門編號查詢
//	@Query(value = "from DeptVO where deptno=?1 order by empno")
//	List<EmpVO> findByOthers(DeptVO deptVO);
}