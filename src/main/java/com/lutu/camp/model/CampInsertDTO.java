package com.lutu.camp.model;

import java.sql.Date;



public class CampInsertDTO {
    private Integer campId;
    private Integer ownerId;
    private String campName;
    private String campContent;
    private String campCity;
    private String campDist;
    private String campAddr;
    private Byte campReleaseStatus;
    private Integer campCommentNumberCount;
    private Integer campCommentSumScore;
    private Date campRegDate;
    private byte[] campPic1; // 營地照片1
    private byte[] campPic2; // 營地照片2
	private byte[] campPic3; // 營地照片3
    private byte[] campPic4; // 營地照片4

    // 轉換方法：從 VO 轉為 DTO（移除所有關聯物件）
//    public  CampInsertDTO(CampVO entity) {
//        CampInsertDTO dto = new CampInsertDTO();
//        dto.setCampId(entity.getCampId());
//        dto.setOwnerId(entity.getOwnerId());
//        dto.setCampName(entity.getCampName());
//        dto.setCampContent(entity.getCampContent());
//        dto.setCampCity(entity.getCampCity());
//        dto.setCampDist(entity.getCampDist());
//        dto.setCampAddr(entity.getCampAddr());
//        dto.setCampReleaseStatus(entity.getCampReleaseStatus());
//        dto.setCampCommentNumberCount(entity.getCampCommentNumberCount());
//        dto.setCampCommentSumScore(entity.getCampCommentSumScore());
//        dto.setCampRegDate(entity.getCampRegDate());
//        dto.setCampPic1(entity.getCampPic1());
//        dto.setCampPic2(entity.getCampPic2());
//        dto.setCampPic3(entity.getCampPic3());
//        dto.setCampPic4(entity.getCampPic4());
//        return dto;
//    }
    
    public CampInsertDTO() {
    	
    }

    public CampInsertDTO(CampVO entity) {
		super();
		this.campId = entity.getCampId();
		this.ownerId = entity.getOwnerId();
		this.campName = entity.getCampName();
		this.campContent = entity.getCampContent();
		this.campCity = entity.getCampCity();
		this.campDist = entity.getCampDist();
		this.campAddr = entity.getCampAddr();
		this.campReleaseStatus = entity.getCampReleaseStatus();
		this.campCommentNumberCount = entity.getCampCommentNumberCount();
		this.campCommentSumScore = entity.getCampCommentSumScore();
		this.campRegDate = entity.getCampRegDate();
		this.campPic1 = entity.getCampPic1();
		this.campPic2 = entity.getCampPic2();
		this.campPic3 = entity.getCampPic3();
		this.campPic4 = entity.getCampPic4();
	}

	// --- Getters and Setters ---
    public Integer getCampId() { return campId; }
    public void setCampId(Integer campId) { this.campId = campId; }

    public Integer getOwnerId() { return ownerId; }
    public void setOwnerId(Integer ownerId) { this.ownerId = ownerId; }

    public String getCampName() { return campName; }
    public void setCampName(String campName) { this.campName = campName; }

    public String getCampContent() { return campContent; }
    public void setCampContent(String campContent) { this.campContent = campContent; }

    public String getCampCity() { return campCity; }
    public void setCampCity(String campCity) { this.campCity = campCity; }

    public String getCampDist() { return campDist; }
    public void setCampDist(String campDist) { this.campDist = campDist; }

    public String getCampAddr() { return campAddr; }
    public void setCampAddr(String campAddr) { this.campAddr = campAddr; }

    public Byte getCampReleaseStatus() { return campReleaseStatus; }
    public void setCampReleaseStatus(Byte campReleaseStatus) { this.campReleaseStatus = campReleaseStatus; }

    public Integer getCampCommentNumberCount() { return campCommentNumberCount; }
    public void setCampCommentNumberCount(Integer campCommentNumberCount) { this.campCommentNumberCount = campCommentNumberCount; }

    public Integer getCampCommentSumScore() { return campCommentSumScore; }
    public void setCampCommentSumScore(Integer campCommentSumScore) { this.campCommentSumScore = campCommentSumScore; }

    public Date getCampRegDate() { return campRegDate; }
    public void setCampRegDate(Date campRegDate) { this.campRegDate = campRegDate; }
    

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
}
