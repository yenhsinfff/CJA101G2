package com.lutu.camptracklist.model;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lutu.camp.model.CampVO;

import jakarta.transaction.Transactional;

@Service("campTrackListService") // 標記為 Spring 的服務元件，會被自動註冊進 Spring 容器
public class CampTrackListService {

	@Autowired
	CampTrackListRepository repository;

	@Autowired
	private SessionFactory sessionFactory;

	public void  addCampTrackList(CampTrackListVO campTrackListVO) {
		repository.save(campTrackListVO);

	}

	// 收藏功能不寫修改功能
//	public void updateCampTrackList(CampTrackListVO campTrackListVO) {
//		repository.save(campTrackListVO);
//	}

	public void deleteCampTrackList(Integer campId, Integer memId) {
		CampTrackListVO.CompositeDetail id = new CampTrackListVO.CompositeDetail();
		id.setCampId(campId);
		id.setMemId(memId);
		if (repository.existsById(id))
			repository.deleteByCampIdAndMemId(campId, memId);
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
                
                // ✅ 這裡轉換圖片為 Base64 字串
                String base64Pic = null;
                byte[] imageBytes = camp.getCampPic1(); // 假設是 byte[] 圖片欄位
                if (imageBytes != null) {
                    base64Pic = Base64.getEncoder().encodeToString(imageBytes);
                }
                
                return new CampTrackListDTO(
                    track.getId().getCampId(),       // 從 track 抓營地 ID
                    track.getId().getMemId(),        // 從 track 抓會員 ID
                    camp.getCampName(),              // 從 camp 抓營地名稱
                    base64Pic,                       // 放進DTO
                    track.getMemTrackDate()          // 從 track 抓收藏時間
                );
            })
            .collect(Collectors.toList());
    }

}