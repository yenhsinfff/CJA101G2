package com.lutu.campsite_available.model;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lutu.campsite_available.model.CampsiteAvailableVO.CACompositeDetail;
import com.lutu.campsitetype.model.CampsiteTypeVO;

import io.lettuce.core.dynamic.annotation.Param;

public interface CampsiteAvailableRepository extends JpaRepository<CampsiteAvailableVO, CACompositeDetail> {

	/* 依房型 + 日期區間查剩餘量（for 搜尋） */
//	@Query("""
//			    SELECT ra
//			      FROM RoomAvailability ra
//			     WHERE ra.id.roomTypeId = :roomTypeId
//			       AND ra.id.date BETWEEN :start AND :end
//			""")
//	List<CampsiteAvailableVO> findRange(@Param("roomTypeId") Long typeId, @Param("start") Date start,
//			@Param("end") Date end);
//
//	/* 一次扣一天（Optimistic）；回傳更新筆數 0=失敗 */
	@Modifying
	@Query("""
	    UPDATE CampsiteAvailableVO ra
	       SET ra.remaining = ra.remaining - :qty
	     WHERE ra.id.campsiteTypeId = :campsiteTypeId
	       AND ra.id.date = :date
	       AND ra.remaining >= :qty
	""")
	int deductOne(@Param("campsiteTypeId") Integer campsiteTypeId,
	              @Param("date") Date date,
	              @Param("qty") int qty);

	@Modifying
	@Query("""
	    UPDATE CampsiteAvailableVO ra
	       SET ra.remaining = ra.remaining + :qty
	     WHERE ra.id.campsiteTypeId = :campsiteTypeId
	       AND ra.id.date = :date
	""")
	int refundOne(@Param("campsiteTypeId") Integer campsiteTypeId,
	              @Param("date") Date date,
	              @Param("qty") int qty);

//	查詢某房型某日期的可用量
//	@Query("""
//		    SELECT ra
//		      FROM CampsiteAvailableVO ra
//		     WHERE ra.id.campsiteTypeId = :typeId
//		       AND ra.id.date BETWEEN :start AND :end
//		""")
//		List<CampsiteAvailableVO> findRange(
//		    @Param("typeId") Integer typeId,
//		    @Param("start") Date start,
//		    @Param("end") Date end
//		);

	/**
	 * 查詢「指定營地／全部營地」在區間內仍有房的房型
	 *
	 * JPQL 說明： 1) 先把房型 (ct) 與房況 (ra) join 起來 2) 條件：日期介於 checkIn ~ checkOut-1 3)
	 * GROUP BY 房型 4) HAVING MIN(ra.remaining) > 0 ⇒ 區間內所有天都還有剩
	 */
	@Query("""
			  SELECT ct
			   FROM CampsiteTypeVO ct
			   JOIN ct.availabilities ra
			  WHERE (:campId IS NULL OR ct.id.campId = :campId)
			  	AND ct.campsitePeople >= :people
			    AND ra.id.date >= :checkIn
			    AND ra.id.date <  :checkOut
			  GROUP BY ct.id.campsiteTypeId
			  HAVING MIN(ra.remaining) > 0
			""")
	List<CampsiteTypeVO> findAvailableRoomTypes(@Param("campId") Integer campId, @Param("people") Integer people,
			@Param("checkIn") Date checkIn, @Param("checkOut") Date checkOut);

	@Query("""
			    SELECT new com.lutu.campsite_available.model.CampsiteTypeAvailableDTO(
			      ct.id.campId,
			      ct.id.campsiteTypeId,
			      ct.campsiteName,
			      ct.campsitePeople,
			      ct.campsiteNum,
			      ct.campsitePrice,
			      MIN(ra.remaining)
			  )
			  FROM CampsiteTypeVO ct
			  JOIN ct.availabilities ra
			  WHERE (:campId IS NULL OR ct.id.campId = :campId)
			    AND ct.campsitePeople >= :people
			    AND ra.id.date >= :checkIn
			    AND ra.id.date <  :checkOut
			  GROUP BY ct.id.campId, ct.id.campsiteTypeId, ct.campsiteName, ct.campsitePeople, ct.campsiteNum, ct.campsitePrice
			  HAVING MIN(ra.remaining) > 0
			""")
	List<CampsiteTypeAvailableDTO> findAvailableRoomTypesWithRemaining(@Param("campId") Integer campId,
			@Param("people") Integer people, @Param("checkIn") Date checkIn, @Param("checkOut") Date checkOut);

	//為刪除房型，先刪除可用房間內的資料列
	@Modifying
	@Query("DELETE FROM CampsiteAvailableVO c WHERE c.campId = :campId AND c.id.campsiteTypeId = :campsiteTypeId")
	void deleteByCampIdAndCampsiteTypeId(@Param("campId") Integer campId, @Param("campsiteTypeId") Integer campsiteTypeId);
	
}
