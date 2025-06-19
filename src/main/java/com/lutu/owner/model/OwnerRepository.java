package com.lutu.owner.model;



import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface OwnerRepository extends JpaRepository<OwnerVO, Integer>{

	@Transactional
	@Modifying
	@Query(value = "delete from owner where ownerId =?1", nativeQuery = true)
	void deleteByOwnerId(int ownerId);

	//● (自訂)條件查詢
	@Query(value = "from OwnerVO where ownerId=?1 and ownerName like?2 order by ownerId")
	List<OwnerVO> findByOthers(int ownerId , String ownerName );
}
	

