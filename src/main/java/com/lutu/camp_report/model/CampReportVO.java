package com.lutu.camp_report.model;

import java.sql.Timestamp;

public class CampReportVO {

    private Integer campsiteReportId; // 檢舉編號
    private String campsiteOrderId; // 營地訂單編號
    private Integer memId; // 露營者編號
    private Integer adminId; // 管理員編號
    private String campsiteReportContent; // 檢舉內容
    private String campsiteReportReply; // 檢舉回覆
    private Timestamp campsiteReportDate; // 檢舉日期
    private byte campsiteReportStatus; // 處理狀態

    public Integer getCampsiteReportId() {
        return campsiteReportId;
    }

    public void setCampsiteReportId(Integer campsiteReportId) {
        this.campsiteReportId = campsiteReportId;
    }

    public String getCampsiteOrderId() {
        return campsiteOrderId;
    }

    public void setCampsiteOrderId(String campsiteOrderId) {
        this.campsiteOrderId = campsiteOrderId;
    }

    public Integer getMemId() {
        return memId;
    }

    public void setMemId(Integer memId) {
        this.memId = memId;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
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
