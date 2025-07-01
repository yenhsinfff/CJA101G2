package com.lutu.camptracklist.model;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lutu.camp.model.CampVO;
import com.lutu.member.model.MemberVO;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service("campTrackListService") // 標記為 Spring 的服務元件，會被自動註冊進 Spring 容器
public class CampTrackListService {

	@Autowired
	CampTrackListRepository repository;

	@Autowired
	private SessionFactory sessionFactory;

	public void addCampTrackList(CampTrackListDTO_insert dto) {
	    if (dto.getCampId() == null || dto.getMemId() == null) {
	        throw new IllegalArgumentException("campId 和 memId 不可為空");
	    }

	    CampTrackListVO entity = new CampTrackListVO();

	    // 1. 設定複合主鍵
	    CampTrackListVO.CompositeDetail id = new CampTrackListVO.CompositeDetail();
	    id.setCampId(dto.getCampId());
	    id.setMemId(dto.getMemId());
	    entity.setId(id);

	    // 2. 必須設定關聯物件 camp 與 member，讓 Hibernate 可以從中取出 ID
	    CampVO camp = new CampVO();
	    camp.setCampId(dto.getCampId());
	    entity.setCamp(camp);

	    MemberVO member = new MemberVO();
	    member.setMemId(dto.getMemId());
	    entity.setMember(member);

	    // 3. 設定收藏日期
	    entity.setMemTrackDate(dto.getMemTrackDate() != null ? dto.getMemTrackDate() : LocalDate.now());

	    // 4. 儲存
	    repository.save(entity);
	}

	// 收藏功能不寫修改功能
//	public void updateCampTrackList(CampTrackListVO campTrackListVO) {
//		repository.save(campTrackListVO);
//	}

	public void deleteCampTrackList(CampTrackListVO.CompositeDetail id) {
	    if (id == null || id.getCampId() == null || id.getMemId() == null) {
	        throw new IllegalArgumentException("campId 和 memId 不可為空");
	    }

	    if (repository.existsById(id)) {
	        repository.deleteById(id); 
	    } else {
	        throw new EntityNotFoundException("找不到對應的收藏紀錄");
	    }
	}
	

	@Transactional
	public CampTrackListVO getOneCampTrackList(Integer campId, Integer memId) {
		CampTrackListVO.CompositeDetail id = new CampTrackListVO.CompositeDetail();
		id.setCampId(campId);
		id.setMemId(memId);
		CampTrackListVO vo = repository.findById(id).orElse(null);

	    if (vo != null) {
	        vo.getCamp().getCampName(); // 保證在 Session 存在時初始化
	        vo.getCamp().getCampCity();
	    }
	    return vo;
	}

	public List<CampTrackListVO> getAll() {
		return repository.findAll();
	}
	
	public List<CampTrackListDTO> getCampTracksByMemberId(Integer memId) {
        List<CampTrackListVO> trackList = repository.findByMember_MemId(memId);

        //資料轉成dto
        return trackList.stream() 
            .map(track -> {
                CampVO camp = track.getCamp();// 從收藏紀錄抓出營地資料
                
                return new CampTrackListDTO(
                    track.getId().getCampId(),       // 從 track 抓營地 ID
                    track.getId().getMemId(),        // 從 track 抓會員 ID
                    camp.getCampName(),              // 從 camp 抓營地名稱
                    track.getMemTrackDate()          // 從 track 抓收藏時間
                );
            })
            .collect(Collectors.toList());
    }

}