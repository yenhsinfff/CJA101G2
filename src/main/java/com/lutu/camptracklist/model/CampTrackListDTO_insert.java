package com.lutu.camptracklist.model;

import java.time.LocalDate;

public class CampTrackListDTO_insert {

	
	
	private Integer campId;
	private Integer memId;
	private LocalDate memTrackDate;

	public CampTrackListDTO_insert() {
		super();
	}

	public CampTrackListDTO_insert(Integer campId, Integer memId, LocalDate memTrackDate) {
		super();
		this.campId = campId;
		this.memId = memId;
		this.memTrackDate = memTrackDate;
	}

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

	public LocalDate getMemTrackDate() {
		return memTrackDate;
	}

	public void setMemTrackDate(LocalDate memTrackDate) {
		this.memTrackDate = memTrackDate;
	}

	


}
