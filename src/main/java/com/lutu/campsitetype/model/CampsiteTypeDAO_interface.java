package com.lutu.campsitetype.model;

import java.util.*;

public interface CampsiteTypeDAO_interface {
          public void insert(CampsiteTypeVO CampsiteTypeVO);
          public void update(CampsiteTypeVO CampsiteTypeVO);
          public void delete(Integer campsiteTypeId);
          public CampsiteTypeVO findByPK(Integer campsiteTypeId);
          public List<CampsiteTypeVO> getAll();

}
