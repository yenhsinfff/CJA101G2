package com.lutu.camptracklist.model;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("campTrackListService") // 標記為 Spring 的服務元件，會被自動註冊進 Spring 容器
public class CampTrackListService {

	@Autowired
	CampTrackListRepository repository;

	@Autowired
	private SessionFactory sessionFactory;

	public void  addCampTrackList(CampTrackListVO campTrackListVO) {
		repository.save(campTrackListVO);
		System.out.println("OKK");
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

	public CampTrackListVO getOneCampTrackList(Integer campId, Integer memId) {
		CampTrackListVO.CompositeDetail id = new CampTrackListVO.CompositeDetail();
		id.setCampId(campId);
		id.setMemId(memId);
		Optional<CampTrackListVO> optional = repository.findById(id);
//		return optional.get(); //如果沒值會丟例外，不建議使用
		return optional.orElse(null); // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}

	public List<CampTrackListVO> getAll() {
		return repository.findAll();
	}

}