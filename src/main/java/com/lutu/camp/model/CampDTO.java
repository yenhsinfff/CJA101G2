package com.lutu.camp.model;


import java.sql.Date;

public class CampDTO {
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

    // 轉換方法：從 VO 轉為 DTO
    public static CampDTO fromEntity(CampVO entity) {
        CampDTO dto = new CampDTO();
        dto.setCampId(entity.getCampId());
        dto.setOwnerId(entity.getOwnerId());
        dto.setCampName(entity.getCampName());
        dto.setCampContent(entity.getCampContent());
        dto.setCampCity(entity.getCampCity());
        dto.setCampDist(entity.getCampDist());
        dto.setCampAddr(entity.getCampAddr());
        dto.setCampReleaseStatus(entity.getCampReleaseStatus());
        dto.setCampCommentNumberCount(entity.getCampCommentNumberCount());
        dto.setCampCommentSumScore(entity.getCampCommentSumScore());
        dto.setCampRegDate(entity.getCampRegDate());
        return dto;
    }

    // Getters and Setters
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
}

