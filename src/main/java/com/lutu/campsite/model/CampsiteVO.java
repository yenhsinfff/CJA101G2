package com.lutu.campsite.model;

import java.io.Serializable;

public class CampsiteVO implements Serializable{
 private Integer campsiteId; //營地房間編號
 private Integer campId; //營地編號
 private Integer campsiteTypeId; //營地房型編號
 private String campsiteIdName; //房間名稱
 private String camperName; //露營者姓名

 public Integer getCampsiteId() {
	return campsiteId;
}
public void setCampsiteId(Integer campsiteId) {
	this.campsiteId = campsiteId;
}
public Integer getCampId() {
	return campId;
}
public void setCampId(Integer campId) {
	this.campId = campId;
}
public Integer getCampsiteTypeId() {
	return campsiteTypeId;
}
public void setCampsiteTypeId(Integer campsiteTypeId) {
	this.campsiteTypeId = campsiteTypeId;
}
public String getCampsiteIdName() {
	return campsiteIdName;
}
public void setCampsiteIdName(String campsiteIdName) {
	this.campsiteIdName = campsiteIdName;
}
public String getCamperName() {
	return camperName;
}
public void setCamperName(String camperName) {
	this.camperName = camperName;
}
 
 
}
