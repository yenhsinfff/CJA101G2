package com.lutu.user_discount.model;


public class UserDiscountDTO_updateUsedTime {

	private Integer memId; // 露營者編號
	private String discountCodeId; // 折價券編號
	
	public UserDiscountDTO_updateUsedTime() {
		super();
	}

	public UserDiscountDTO_updateUsedTime(Integer memId, String discountCodeId) {
		super();
		this.memId = memId;
		this.discountCodeId = discountCodeId;
	}

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

}
