package com.lutu.administrator.model;



import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AdministratorRepository extends JpaRepository<AdministratorVO, Integer>{
	
	@Transactional
	@Modifying
	@Query(value = "delete from administrator where adminId =?1", nativeQuery = true)
	void deleteByAdminId(int adminId);

	//● (自訂)條件查詢
	@Query(value = "from AdministratorVO where adminId=?1 and adminName like?2 order by adminId")
	List<AdministratorVO> findByOthers(int adminId , String adminName );
}
