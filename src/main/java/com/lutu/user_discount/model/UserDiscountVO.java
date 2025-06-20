package com.lutu.user_discount.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.lutu.discount_code.model.DiscountCodeVO;
import com.lutu.member.model.MemberVO;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;


@Entity
@Table(name = "user_discount")
public class UserDiscountVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
    @EmbeddedId
    private UserDiscountId id;
    
    
	@ManyToOne
    @JoinColumn(name = "mem_id", referencedColumnName = "mem_id", insertable = false, updatable = false)
	private MemberVO memberVO; //露營者編號
	
	
	@ManyToOne
    @JoinColumn(name = "discount_code_id", referencedColumnName = "discount_code_id", insertable = false, updatable = false)
	private DiscountCodeVO discountCodeVO; //折價券編號
	
	
	@Column(name = "discount_code_type")
	@Min(value = 0, message = "折扣訂單類型: 只能是 0（營地）或 1（商城）")
	@Max(value = 1, message = "折扣訂單類型: 只能是 0（營地）或 1（商城）")
	private byte discountCodeType; //折扣訂單類型 0營地 1商城
	
	
	@Column(name = "used_at")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime usedAt; //使用時間
	
	
	
	
	public UserDiscountVO() {
	}
	
    public UserDiscountId getId() {
        return id;
    }

    public void setId(UserDiscountId id) {
        this.id = id;
    }
	
	public MemberVO getMemberVO() {
		return memberVO;
	}

	public void setMemberVO(MemberVO memberVO) {
		this.memberVO = memberVO;
	}

	public DiscountCodeVO getDiscountCodeVO() {
		return discountCodeVO;
	}

	public void setDiscountCodeVO(DiscountCodeVO discountCodeVO) {
		this.discountCodeVO = discountCodeVO;
	}

	public byte getDiscountCodeType() {
		return discountCodeType;
	}

	public void setDiscountCodeType(byte discountCodeType) {
		this.discountCodeType = discountCodeType;
	}

	public LocalDateTime getUsedAt() {
		return usedAt;
	}

	public void setUsedAt(LocalDateTime usedAt) {
		this.usedAt = usedAt;
	}
	
	
}
