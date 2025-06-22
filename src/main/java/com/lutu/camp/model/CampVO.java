package com.lutu.camp.model;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lutu.campsite_order.model.CampSiteOrderVO;
import com.lutu.campsitetype.model.CampsiteTypeVO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "camp")
@JsonFilter("campFilter")
public class CampVO implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "camp_id")
    private Integer campId; // 營地編號

    @Column(name = "owner_id", nullable = false)
    private Integer ownerId; // 營地主編號

    @Column(name = "camp_name", length = 20, nullable = false)
    private String campName; // 營地名稱

    @Column(name = "camp_content", length = 1000, nullable = false)
    private String campContent; // 營地說明

    @Column(name = "camp_city", length = 5, nullable = false)
    private String campCity; // 營地地址_縣市

    @Column(name = "camp_dist", length = 5, nullable = false)
    private String campDist; // 營地地址_鄉鎮區

    @Column(name = "camp_addr", length = 30, nullable = false)
    private String campAddr; // 營地地址_詳細地址

    @Column(name = "camp_release_status", nullable = false)
    private Byte campReleaseStatus; // 上下架狀態

    @Column(name = "camp_pic1", nullable = false, columnDefinition = "longblob")
    private byte[] campPic1; // 營地照片1

    @Column(name = "camp_pic2", nullable = false, columnDefinition = "longblob")
    private byte[] campPic2; // 營地照片2

    @Column(name = "camp_pic3", columnDefinition = "longblob")
    private byte[] campPic3; // 營地照片3

    @Column(name = "camp_pic4", columnDefinition = "longblob")
    private byte[] campPic4; // 營地照片4

    @Column(name = "camp_comment_number_count", nullable = false)
    private Integer campCommentNumberCount; // 評價總人數

    @Column(name = "camp_comment_sum_score", nullable = false)
    private Integer campCommentSumScore; // 評價總分

    @Column(name = "camp_reg_date", nullable = false)
    private Date campRegDate; // 加入日期
    
    @OneToMany(mappedBy = "campVO", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<CampSiteOrderVO> campsiteOrders = new HashSet<>();  

    @OneToMany(mappedBy = "camp", cascade = CascadeType.ALL)
    private Set<CampsiteTypeVO> campsiteTypes;
    

    // --- Getters and Setters ---
    
    public Set<CampsiteTypeVO> getCampsiteTypes() {
		return campsiteTypes;
	}

	public void setCampsiteTypes(Set<CampsiteTypeVO> campsiteTypes) {
		this.campsiteTypes = campsiteTypes;
	}

	public Set<CampSiteOrderVO> getCampsiteOrders() {
        return campsiteOrders;
    }

    public void setCampsiteOrders(Set<CampSiteOrderVO> campsiteOrders) {
        this.campsiteOrders = campsiteOrders;
    }
    
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
