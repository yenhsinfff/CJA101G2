package com.lutu.camp_report.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.sql.Timestamp;

import com.lutu.administrator.model.AdministratorVO;
import com.lutu.campsite_order.model.CampSiteOrderVO;
import com.lutu.member.model.MemberVO;

@Entity
@Table(name = "camp_report")
public class CampReportVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "campsite_report_id")
    private Integer campsiteReportId; // 檢舉編號

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campsite_order_id", nullable = false)
    private CampSiteOrderVO campsiteOrder; // 營地訂單（建議建立對應Entity）

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_id", nullable = false)
    private MemberVO memberVO; // 露營者（建議建立對應Entity）

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private AdministratorVO administratorVO; // 管理員（可為NULL，建議建立對應Entity）

    @Column(name = "campsite_report_content", length = 500, nullable = false)
    private String campsiteReportContent; // 檢舉內容

    @Column(name = "campsite_report_reply", length = 500)
    private String campsiteReportReply; // 檢舉回覆

    @Column(name = "campsite_report_date", nullable = false)
    private Timestamp campsiteReportDate; // 檢舉日期

    @Column(name = "campsite_report_status", nullable = false)
    private byte campsiteReportStatus; // 處理狀態

    // Getter & Setter

    public Integer getCampsiteReportId() {
        return campsiteReportId;
    }

    public void setCampsiteReportId(Integer campsiteReportId) {
        this.campsiteReportId = campsiteReportId;
    }

    public CampSiteOrderVO getCampsiteOrder() {
        return campsiteOrder;
    }

    public void setCampsiteOrder(CampSiteOrderVO campsiteOrder) {
        this.campsiteOrder = campsiteOrder;
    }

    public MemberVO getMemberVO() {
        return memberVO;
    }

    public void setMemberVO(MemberVO memberVO) {
        this.memberVO = memberVO;
    }

    public AdministratorVO getAdministratorVO() {
        return administratorVO;
    }

    public void setAdministratorVO(AdministratorVO administratorVO) {
        this.administratorVO = administratorVO;
    }

    public String getCampsiteReportContent() {
        return campsiteReportContent;
    }

    public void setCampsiteReportContent(String campsiteReportContent) {
        this.campsiteReportContent = campsiteReportContent;
    }

    public String getCampsiteReportReply() {
        return campsiteReportReply;
    }

    public void setCampsiteReportReply(String campsiteReportReply) {
        this.campsiteReportReply = campsiteReportReply;
    }

    public Timestamp getCampsiteReportDate() {
        return campsiteReportDate;
    }

    public void setCampsiteReportDate(Timestamp campsiteReportDate) {
        this.campsiteReportDate = campsiteReportDate;
    }

    public byte getCampsiteReportStatus() {
        return campsiteReportStatus;
    }

    public void setCampsiteReportStatus(byte campsiteReportStatus) {
        this.campsiteReportStatus = campsiteReportStatus;
    }
}
