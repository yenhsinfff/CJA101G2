package com.lutu.camptracklist.model;

import java.io.Serializable;
import java.sql.Date;

public class CampTrackListVO implements Serializable{
	private Integer campId; //營地編號
	private Integer memId; //露營者編號
	private Date memTrackDate; //露營者編號
	
	
	public Integer getCampId() {
		return campId;
	}
	public void setCampId(Integer campId) {
		this.campId = campId;
	}
	public Integer getMemId() {
		return memId;
	}
	public void setMemId(Integer memId) {
		this.memId = memId;
	}
	public Date getMemTrackDate() {
		return memTrackDate;
	}
	public void setMemTrackDate(Date memTrackDate) {
		this.memTrackDate = memTrackDate;
	}
	
	
}
