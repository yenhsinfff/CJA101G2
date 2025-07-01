package com.lutu.camptracklist.model;

import java.time.LocalDate;

public class CampTrackListDTO {

	
	private Integer campId;
	private Integer memId;
	private String campName;
	private LocalDate memTrackDate;

	public CampTrackListDTO() {
		super();
	}

	

	public CampTrackListDTO(Integer campId, Integer memId, String campName, LocalDate memTrackDate) {
		super();
		this.campId = campId;
		this.memId = memId;
		this.campName = campName;
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


	public LocalDate getMemTrackDate() {
		return memTrackDate;
	}

	public void setMemTrackDate(LocalDate memTrackDate) {
		this.memTrackDate = memTrackDate;
	}
	
	

}
