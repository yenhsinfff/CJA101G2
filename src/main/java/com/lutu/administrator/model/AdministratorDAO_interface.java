package com.lutu.administrator.model;

import java.util.*;

public interface AdministratorDAO_interface {

	public void insert(AdministratorVO administratorVO);
    public void update(AdministratorVO administratorVO);
    public void delete(Integer adminId);
    public AdministratorVO findByPrimaryKey(Integer adminId);
    public List<AdministratorVO> getAll();
}
