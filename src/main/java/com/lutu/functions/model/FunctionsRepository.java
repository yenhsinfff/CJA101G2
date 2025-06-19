package com.lutu.functions.model;



import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface FunctionsRepository extends JpaRepository<FunctionsVO, Integer> {
	
	@Transactional
	@Modifying
	@Query(value = "delete from functions where funcId =?1", nativeQuery = true)
	void deleteByFuncId(int funcId);

	//● (自訂)條件查詢
	@Query(value = "from FunctionsVO where funcId=?1 and funcName like?2 order by funcId")
	List<FunctionsVO> findByOthers(int funcId , String funcName );

}
