package com.lutu.user_discount.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class UserDiscountId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "mem_id")
    private Integer memId;

    @Column(name = "discount_code_id")
    private String discountCodeId;

    // 空建構子（必需）
    public UserDiscountId() {
    }

    // 建構子（方便初始化）
    public UserDiscountId(Integer memId, String discountCodeId) {
        this.memId = memId;
        this.discountCodeId = discountCodeId;
    }

    // Getter / Setter
    public Integer getMemId() {
        return memId;
    }

    public void setMemId(Integer memId) {
        this.memId = memId;
    }

    public String getDiscountCodeId() {
        return discountCodeId;
    }

    public void setDiscountCodeId(String discountCodeId) {
        this.discountCodeId = discountCodeId;
    }

    // 必須覆寫 equals() 和 hashCode() 才能正常作為複合主鍵使用
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDiscountId)) return false;
        UserDiscountId that = (UserDiscountId) o;
        return Objects.equals(memId, that.memId) &&
               Objects.equals(discountCodeId, that.discountCodeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memId, discountCodeId);
    }
}
