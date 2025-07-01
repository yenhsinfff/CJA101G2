// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/

package com.lutu.camptracklist.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CampTrackListRepository extends JpaRepository<CampTrackListVO, CampTrackListVO.CompositeDetail> {

	
	@Transactional //涉及資料庫更新
	@Modifying //表示這不是查詢（SELECT），而是更新/刪除語句
	@Query(value = "delete from camp_track_list where camp_id =?1 and mem_id =?2", nativeQuery = true)
	void deleteByCampIdAndMemId(int campId, int memId);
	
	List<CampTrackListVO> findByMember_MemId(Integer memId);

}