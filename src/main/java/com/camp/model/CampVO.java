package com.camp.model;

import java.sql.Date;

public class CampVO implements java.io.Serializable {

	private Integer campId; // 營地編號
	private Integer ownerId; // 營地主編號
	private String campName; // 營地名稱
	private String campContent; // 營地說明
	private String campCity; // 營地地址_縣市
	private String campDist; // 營地地址_鄉鎮區
	private String campAddr; // 營地地址_詳細地址
	private byte campReleaseStatus; // 上下架狀態：0=下架，1=上架
	private byte[] campPic1; // 營地照片1
	private byte[] campPic2; // 營地照片2
	private byte[] campPic3; // 營地照片3
	private byte[] campPic4; // 營地照片4
	private Integer campCommentNumberCount; // 評價總人數
	private Integer campCommentSumScore; // 評價總分
	private Date campRegDate; // 加入日期

	// --- Getters and Setters ---

	public Integer getCampId() {
		return campId;
	}

	public void setCampId(Integer campId) {
		this.campId = campId;
	}

	public Integer getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	public String getCampName() {
		return campName;
	}

	public void setCampName(String campName) {
		this.campName = campName;
	}

	public String getCampContent() {
		return campContent;
	}

	public void setCampContent(String campContent) {
		this.campContent = campContent;
	}

	public String getCampCity() {
		return campCity;
	}

	public void setCampCity(String campCity) {
		this.campCity = campCity;
	}

	public String getCampDist() {
		return campDist;
	}

	public void setCampDist(String campDist) {
		this.campDist = campDist;
	}

	public String getCampAddr() {
		return campAddr;
	}

	public void setCampAddr(String campAddr) {
		this.campAddr = campAddr;
	}

	public Byte getCampReleaseStatus() {
		return campReleaseStatus;
	}

	public void setCampReleaseStatus(Byte campReleaseStatus) {
		this.campReleaseStatus = campReleaseStatus;
	}

	public byte[] getCampPic1() {
		return campPic1;
	}

	public void setCampPic1(byte[] campPic1) {
		this.campPic1 = campPic1;
	}

	public byte[] getCampPic2() {
		return campPic2;
	}

	public void setCampPic2(byte[] campPic2) {
		this.campPic2 = campPic2;
	}

	public byte[] getCampPic3() {
		return campPic3;
	}

	public void setCampPic3(byte[] campPic3) {
		this.campPic3 = campPic3;
	}

	public byte[] getCampPic4() {
		return campPic4;
	}

	public void setCampPic4(byte[] campPic4) {
		this.campPic4 = campPic4;
	}

	public Integer getCampCommentNumberCount() {
		return campCommentNumberCount;
	}

	public void setCampCommentNumberCount(Integer campCommentNumberCount) {
		this.campCommentNumberCount = campCommentNumberCount;
	}

	public Integer getCampCommentSumScore() {
		return campCommentSumScore;
	}

	public void setCampCommentSumScore(Integer campCommentSumScore) {
		this.campCommentSumScore = campCommentSumScore;
	}

	public Date getCampRegDate() {
		return campRegDate;
	}

	public void setCampRegDate(Date campRegDate) {
		this.campRegDate = campRegDate;
	}
}
