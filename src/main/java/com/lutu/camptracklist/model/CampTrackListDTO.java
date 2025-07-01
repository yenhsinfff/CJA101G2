package com.lutu.camptracklist.model;

import java.time.LocalDate;

public class CampTrackListDTO {

	
	private Integer campId;
	private Integer memId;
	private String campName;
	private String campPic1; //base64 字串格式
	private LocalDate memTrackDate;

	public CampTrackListDTO() {
		super();
	}

	

	public CampTrackListDTO(Integer campId, Integer memId, String campName, String campPic1, LocalDate memTrackDate) {
		super();
		this.campId = campId;
		this.memId = memId;
		this.campName = campName;
		this.campPic1 = campPic1;
		this.memTrackDate = memTrackDate;
	}



	public Integer getMemId() {
		return memId;
	}

	public void setMemId(Integer memId) {
		this.memId = memId;
	}

	public Integer getCampId() {
		return campId;
	}

	public void setCampId(Integer campId) {
		this.campId = campId;
	}

	public String getCampName() {
		return campName;
	}

	public void setCampName(String campName) {
		this.campName = campName;
	}

	public String getCampPic1() {
		return campPic1;
	}

	public void setCampPic1(String campPic1) {
		this.campPic1 = campPic1;
	}

	public LocalDate getMemTrackDate() {
		return memTrackDate;
	}

	public void setMemTrackDate(LocalDate memTrackDate) {
		this.memTrackDate = memTrackDate;
	}
	
	

}
