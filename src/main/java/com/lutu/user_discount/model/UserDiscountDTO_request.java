package com.lutu.user_discount.model;


//接收發送會員折價券(傳入)
public class UserDiscountDTO_request{


	private Integer memId; // 露營者編號
	private String discountCodeId; // 折價券編號


	public UserDiscountDTO_request() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public UserDiscountDTO_request(Integer memId, String discountCodeId) {
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
