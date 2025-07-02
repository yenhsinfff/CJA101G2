package com.lutu.shopProd.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lutu.colorList.model.ColorListDTO;
import com.lutu.prodColorList.model.ProdColorListDTO;
import com.lutu.prodPic.model.ProdPicDTO;
import com.lutu.prodSpecList.model.ProdSpecListDTO;
import com.lutu.specList.model.SpecListDTO;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ShopProdDTO {

	private Integer prodId; // PK 商品編號

	@NotEmpty(message = "商品名稱: 請勿空白")
	@Size(min = 1, max = 50, message = "商品名稱長度必須介於1到50之間")
//	@Pattern(regexp = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,50}$", message = "商品名稱: 只能是中、英文字母、數字和_ , 且長度必需在2到50之間")
	private String prodName; // 商品名稱

	private String prodIntro; // 商品介紹

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")
	private Timestamp prodReleaseDate;// 上架日期

	@DecimalMin(value = "0.0", inclusive = true, message = "定價折扣不能小於 0")
	@DecimalMax(value = "1.0", inclusive = true, message = "定價折扣不能大於 1") // inclusive = true：包含邊界值（0 和 1 都可接受）
	private BigDecimal prodDiscount; // 定價折扣

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")
	private Timestamp prodDiscountStart; // 折扣開始時間

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")
	private Timestamp prodDiscountEnd; // 折扣結束時間

	@Min(value = 0, message = "評價總數不能為負數")
	private Integer prodCommentCount; // 評價總數

	@Min(value = 0, message = "評價總分數不能為負數")
	private Integer prodCommentSumScore; // 評價總分數

	@NotNull(message = "商品類型不得為空")
	@Min(value = 0, message = "商品類型編號不能為負數")
	private Integer prodTypeId; // 商品類型編號

	private String prodTypeName; // 商品類別

	@NotNull(message = "商品狀態: 請勿空白")
	@Min(value = 0, message = "商品狀態只能是 0 或 1")
	@Max(value = 1, message = "商品狀態只能是 0 或 1")
	private Byte prodStatus; 	// 0:未上架 1:上架

	@NotNull(message = "商品顏色與否: 請勿空白")
	@Min(value = 0, message = "商品顏色與否只能是 0 或 1")
	@Max(value = 1, message = "商品顏色與否只能是 0 或 1")
	private Byte prodColorOrNot; // 0: 單一顏色 1: 有不同顏色

	private List<ProdSpecListDTO> prodSpecList; 	// 商品規格
	private List<ProdColorListDTO> prodColorList; 	// 商品顏色
	private List<SpecListDTO> specList; 			// 規格
	private List<ColorListDTO> colorList; 			// 顏色
	
	private List<ProdPicDTO> prodPicList; 			//商品圖片

	public ShopProdDTO() {
		super();
	}

	public Integer getProdId() {
		return prodId;
	}

	public void setProdId(Integer prodId) {
		this.prodId = prodId;
	}

	public String getProdTypeName() {
		return prodTypeName;
	}

	public void setProdTypeName(String prodTypeName) {
		this.prodTypeName = prodTypeName;
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

	public List<ProdSpecListDTO> getProdSpecList() {
		return prodSpecList;
	}

	public void setProdSpecList(List<ProdSpecListDTO> prodSpecList) {
		this.prodSpecList = prodSpecList;
	}

	public List<ProdColorListDTO> getProdColorList() {
		return prodColorList;
	}

	public void setProdColorList(List<ProdColorListDTO> prodColorList) {
		this.prodColorList = prodColorList;
	}

	public List<SpecListDTO> getSpecList() {
		return specList;
	}

	public void setSpecList(List<SpecListDTO> specList) {
		this.specList = specList;
	}

	public List<ColorListDTO> getColorList() {
		return colorList;
	}

	public void setColorList(List<ColorListDTO> colorList) {
		this.colorList = colorList;
	}

	public List<ProdPicDTO> getProdPicList() {
		return prodPicList;
	}

	public void setProdPicList(List<ProdPicDTO> prodPicList) {
		this.prodPicList = prodPicList;
	}

	
}
