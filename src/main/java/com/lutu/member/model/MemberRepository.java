package com.lutu.member.model;



import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface MemberRepository extends JpaRepository<MemberVO, Integer> {
	
	@Transactional
	@Modifying
	@Query(value = "delete from member where memId =?1", nativeQuery = true)
	void deleteByMemId(int memId);

	//● (自訂)條件查詢
<<<<<<< Upstream, based on branch 'master' of https://github.com/yenhsinfff/CJA101G2.git
	@Query(value = "from MemberVO where memId=?1 and memName like?2 order by memId")
=======
	@Query(value = "from MemberVO where memId=?1 and memName like ?2 order by memId")
>>>>>>> f6e446f Modify Member Model And Member Controller
	List<MemberVO> findByOthers(int memId , String memName );
}
