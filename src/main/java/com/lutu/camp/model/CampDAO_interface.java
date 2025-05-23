package com.lutu.camp.model;

import java.util.*;

public interface CampDAO_interface {
	public void insert(CampVO campVO);

	 public void update(CampVO campVO);

	 public void delete(Integer campId);
	 
	 public CampVO getOneCamp(Integer campId);

	// public CampVO findByPrimaryKey(Integer campno);

	public List<CampVO> getAll();


}
