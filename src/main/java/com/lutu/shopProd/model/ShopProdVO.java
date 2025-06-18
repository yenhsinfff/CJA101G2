package com.lutu.shopProd.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;

import com.lutu.product_type.model.ProdTypeVO;
import com.lutu.shop.model.ProdColorListVO;
import com.lutu.shop.model.ProdFavListVO;
import com.lutu.shop.model.ProdPicVO;
import com.lutu.shop.model.ProdSpecListVO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "shop_prod")
public class ShopProdVO implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //AI
	@Column(name = "prod_id", updatable = false)
	private Integer prodId; 		// PK 商品編號
	
	@Column(name = "prod_name")
	@NotEmpty(message="商品名稱: 請勿空白")
	@Pattern(regexp = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,50}$", message = "商品名稱: 只能是中、英文字母、數字和_ , 且長度必需在2到50之間")
	private String prodName;		//商品名稱
	
	@Column(name = "prod_intro")
	private String prodIntro;		//商品介紹
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "prod_release_date", insertable = false, updatable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private Timestamp prodReleaseDate;//上架日期
	
	@Column(name = "prod_discount")
	private BigDecimal prodDiscount; // 定價折扣
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "prod_discount_start")
	private Timestamp prodDiscountStart; //折扣開始時間
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "prod_discount_end")
	private Timestamp prodDiscountEnd; //折扣結束時間
	
	@Column(name = "prod_comment_count")
	private Integer prodCommentCount; //評價總數
	
	@Column(name = "prod_comment_sum_score")
	private Integer prodCommentSumScore; //評價總分數
	
	@ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name = "prod_type_id", referencedColumnName = "prod_type_id")
	private ProdTypeVO prodTypeVO; //商品類型編號(物件)
	
	@Column(name = "prod_status")
	private Byte prodStatus; // 0:未上架 1:上架
	
	@Column(name = "prod_color_or_not")
	private Byte prodColorOrNot; // 0: 單一顏色 1: 有不同顏色
	
	@OneToMany(mappedBy = "shopProdVO", cascade = CascadeType.ALL)
	@OrderBy("prodId asc")
	private Set<ProdColorListVO> prodColorList;
	
	@OneToMany(mappedBy = "shopProdVO", cascade = CascadeType.ALL)
	@OrderBy("prodId asc")
	private Set<ProdSpecListVO> prodSpecList;
	
	@OneToMany(mappedBy = "shopProdVO", cascade = CascadeType.ALL)
	@OrderBy("prodPicId asc")
	private Set<ProdPicVO> prodPicList;
	
	@OneToMany(mappedBy = "shopProdVO", cascade = CascadeType.ALL)
	private Set<ProdFavListVO> prodFavList;
	
	public ShopProdVO() {
		super();
	}

	public ShopProdVO(Integer prodId, String prodName, String prodIntro, Timestamp prodReleaseDate, BigDecimal prodDiscount,
			Timestamp prodDiscountStart, Timestamp prodDiscountEnd, Integer prodCommentCount, Integer prodCommentSumScore,
			ProdTypeVO prodTypeVO, Byte prodStatus, Byte prodColorOrNot) {
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
		this.prodTypeVO = prodTypeVO;
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

	public ProdTypeVO getProdTypeVO() {
		return prodTypeVO;
	}

	public void setProdTypeVO(ProdTypeVO prodTypeVO) {
		this.prodTypeVO = prodTypeVO;
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

	public Set<ProdColorListVO> getProdColorList() {
		return prodColorList;
	}

	public void setProdColorList(Set<ProdColorListVO> prodColorList) {
		this.prodColorList = prodColorList;
	}

	public Set<ProdSpecListVO> getProdSpecList() {
		return prodSpecList;
	}

	public void setProdSpecList(Set<ProdSpecListVO> prodSpecList) {
		this.prodSpecList = prodSpecList;
	}

	public Set<ProdPicVO> getProdPicList() {
		return prodPicList;
	}

	public void setProdPicList(Set<ProdPicVO> prodPicList) {
		this.prodPicList = prodPicList;
	}

	public Set<ProdFavListVO> getProdFavList() {
		return prodFavList;
	}

	public void setProdFavList(Set<ProdFavListVO> prodFavList) {
		this.prodFavList = prodFavList;
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
		return "Prodoucts [商品編號=" + prodId + ", 商品名稱=" + prodName + ", 商品介紹=" + prodIntro + ", 商品上架日期="
				+ prodReleaseDate + ", 商品折扣=" + prodDiscount + ", 折扣開始日期=" + prodDiscountStart
				+ ", 折扣結束日期=" + prodDiscountEnd + ", 評價總數=" + prodCommentCount
				+ ", 評價總分數=" + prodCommentSumScore  + ", 商品狀態="
				+ prodStatus + ", 是否有不同顏色=" + prodColorOrNot 
				+ ", 商品類型(物件)=" + prodTypeVO 
				+ "]";
	}

	
}
