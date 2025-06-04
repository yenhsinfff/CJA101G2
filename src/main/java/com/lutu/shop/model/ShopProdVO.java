package com.lutu.shop.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

public class ShopProdVO implements Serializable {

	private Integer prodId; // PK
	private String prodName;
	private String prodIntro;
	private Timestamp prodReleaseDate;
	private BigDecimal prodDiscount; // 定價折扣
	private Timestamp prodDiscountStart;
	private Timestamp prodDiscountEnd;
	private Integer prodCommentCount;
	private Integer prodCommentSumScore;
	private Integer prodTypeId;
	private Byte prodStatus; // 0:未上架 1:上架
	private Byte prodColorOrNot; // 0: 單一顏色 1: 有不同顏色
	
	
	public ShopProdVO() {
		super();
	}

	public ShopProdVO(Integer prodId, String prodName, String prodIntro, Timestamp prodReleaseDate, BigDecimal prodDiscount,
			Timestamp prodDiscountStart, Timestamp prodDiscountEnd, Integer prodCommentCount, Integer prodCommentSumScore,
			Integer prodTypeId, Byte prodStatus, Byte prodColorOrNot) {
		super();
		this.prodId = prodId;
		this.prodName = prodName;
		this.prodIntro = prodIntro;
		this.prodReleaseDate = prodReleaseDate;
		this.prodDiscount = prodDiscount;
		this.prodDiscountStart = prodDiscountStart;
		this.prodDiscountEnd = prodDiscountEnd;
		this.prodCommentCount = prodCommentCount;
		this.prodCommentSumScore = prodCommentSumScore;
		this.prodTypeId = prodTypeId;
		this.prodStatus = prodStatus;
		this.prodColorOrNot = prodColorOrNot;
	}

	public Integer getProdId() {
		return prodId;
	}

	public void setProdId(Integer prodId) {
		this.prodId = prodId;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getProdIntro() {
		return prodIntro;
	}

	public void setProdIntro(String prodIntro) {
		this.prodIntro = prodIntro;
	}

	public Timestamp getProdReleaseDate() {
		return prodReleaseDate;
	}

	public void setProdReleaseDate(Timestamp prodReleaseDate) {
		this.prodReleaseDate = prodReleaseDate;
	}

	public BigDecimal getProdDiscount() {
		return prodDiscount;
	}

	public void setProdDiscount(BigDecimal prodDiscount) {
		this.prodDiscount = prodDiscount;
	}

	public Timestamp getProdDiscountStart() {
		return prodDiscountStart;
	}

	public void setProdDiscountStart(Timestamp prodDiscountStart) {
		this.prodDiscountStart = prodDiscountStart;
	}

	public Timestamp getProdDiscountEnd() {
		return prodDiscountEnd;
	}

	public void setProdDiscountEnd(Timestamp prodDiscountEnd) {
		this.prodDiscountEnd = prodDiscountEnd;
	}

	public Integer getProdCommentCount() {
		return prodCommentCount;
	}

	public void setProdCommentCount(Integer prodCommentCount) {
		this.prodCommentCount = prodCommentCount;
	}

	public Integer getProdCommentSumScore() {
		return prodCommentSumScore;
	}

	public void setProdCommentSumScore(Integer prodCommentSumScore) {
		this.prodCommentSumScore = prodCommentSumScore;
	}

	public Integer getProdTypeId() {
		return prodTypeId;
	}

	public void setProdTypeId(Integer prodTypeId) {
		this.prodTypeId = prodTypeId;
	}

	public Byte getProdStatus() {
		return prodStatus;
	}

	public void setProdStatus(Byte prodStatus) {
		this.prodStatus = prodStatus;
	}

	public Byte getProdColorOrNot() {
		return prodColorOrNot;
	}

	public void setProdColorOrNot(Byte prodColorOrNot) {
		this.prodColorOrNot = prodColorOrNot;
	}

	@Override
	public int hashCode() {
		return Objects.hash(prodId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShopProdVO other = (ShopProdVO) obj;
		return Objects.equals(prodId, other.prodId);
	}

	@Override
	public String toString() {
		return "ShopProd [商品編號=" + prodId + ", 商品名稱=" + prodName + ", 商品介紹=" + prodIntro + ", 商品上架日期="
				+ prodReleaseDate + ", 商品折扣=" + prodDiscount + ", 折扣開始日期=" + prodDiscountStart
				+ ", 折扣結束日期=" + prodDiscountEnd + ", 評價總數=" + prodCommentCount
				+ ", 評價總分數=" + prodCommentSumScore + ", 商品類型編號=" + prodTypeId + ", 商品狀態="
				+ prodStatus + ", 是否有不同顏色=" + prodColorOrNot + "]";
	}

	
	
	
}
