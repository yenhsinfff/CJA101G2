package com.lutu.camptracklist.model;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}