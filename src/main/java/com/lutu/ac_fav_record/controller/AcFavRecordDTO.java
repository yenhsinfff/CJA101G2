package com.lutu.ac_fav_record.controller;


import com.lutu.ac_fav_record.model.AcFavRecordVO;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class AcFavRecordDTO {

    @NotNull(message = "文章ID: 不能為空")
    private Integer acId;

    @NotNull(message = "會員ID: 不能為空")
    private Integer memId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime acFavTime;

    // 預設建構子
    public AcFavRecordDTO() {
    }

    // 從 VO 建立 DTO
    public AcFavRecordDTO(AcFavRecordVO acFavRecordVO) {
        this.acId = acFavRecordVO.getAcId();
        this.memId = acFavRecordVO.getMemId();
        this.acFavTime = acFavRecordVO.getAcFavTime();
    }

    // 有參數建構子
    public AcFavRecordDTO(Integer acId, Integer memId) {
        this.acId = acId;
        this.memId = memId;
        this.acFavTime = LocalDateTime.now();
    }

    // Getter 和 Setter
    public Integer getAcId() {
        return acId;
    }

    public void setAcId(Integer acId) {
        this.acId = acId;
    }

    public Integer getMemId() {
        return memId;
    }

    public void setMemId(Integer memId) {
        this.memId = memId;
    }

    public LocalDateTime getAcFavTime() {
        return acFavTime;
    }

    public void setAcFavTime(LocalDateTime acFavTime) {
        this.acFavTime = acFavTime;
    }

    @Override
    public String toString() {
        return "AcFavRecordDTO [acId=" + acId + ", memId=" + memId + ", acFavTime=" + acFavTime + "]";
    }
}