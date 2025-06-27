package com.lutu.user_discount.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import com.lutu.camptracklist.model.CampTrackListVO.CompositeDetail;
import com.lutu.discount_code.model.DiscountCodeVO;
import com.lutu.member.model.MemberVO;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "user_discount")
public class UserDiscountVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CompositeDetail id;

	@ManyToOne
	@JoinColumn(name = "mem_id", referencedColumnName = "mem_id", insertable = false, updatable = false)
	private MemberVO memberVO; // 露營者編號

	@ManyToOne
	@JoinColumn(name = "discount_code_id", referencedColumnName = "discount_code_id", insertable = false, updatable = false)
	private DiscountCodeVO discountCodeVO; // 折價券編號

//    @Column(name = "mem_id")
//    private Integer memId;
//    
//    @Column(name = "discount_code_id")
//    private Integer discountCodeId;

	@Column(name = "discount_code_type")
	@Min(value = 0, message = "折扣訂單類型: 只能是 0（營地）或 1（商城）")
	@Max(value = 1, message = "折扣訂單類型: 只能是 0（營地）或 1（商城）")
	private byte discountCodeType; // 折扣訂單類型 0營地 1商城

	@Column(name = "used_at")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime usedAt; // 使用時間

	public UserDiscountVO() {
	}

	public CompositeDetail getId() {
		return id;
	}

	public void setId(CompositeDetail id) {
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

	// 需要宣告一個有包含複合主鍵屬性的類別，並一定要實作 java.io.Serializable 介面
	@Embeddable
	public static class CompositeDetail implements Serializable {
		private static final long serialVersionUID = 1L;

		@Column(name = "discount_code_id")
		private String discountCodeId; // 營地編號

		@Column(name = "mem_id")
		private Integer memId; // 露營者編號

		// 一定要有無參數建構子
		public CompositeDetail() {
			super();
		}

		public String getDiscountCodeId() {
			return discountCodeId;
		}

		public void setDiscountCodeId(String discountCodeId) {
			this.discountCodeId = discountCodeId;
		}

		public Integer getMemId() {
			return memId;
		}

		public void setMemId(Integer memId) {
			this.memId = memId;
		}

		// 一定要 override 此類別的 hashCode() 與 equals() 方法
		@Override
		public int hashCode() {
			return Objects.hash(discountCodeId, memId);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			CompositeDetail other = (CompositeDetail) obj;
			return Objects.equals(discountCodeId, other.discountCodeId) && Objects.equals(memId, other.memId);
		}

		@Override
		public String toString() {
			return "CompositeDetail [discountCodeId=" + discountCodeId + ", memId=" + memId + "]";
		}

	}

}
