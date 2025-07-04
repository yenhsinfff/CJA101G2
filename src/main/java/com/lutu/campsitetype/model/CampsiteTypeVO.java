package com.lutu.campsitetype.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

import com.lutu.campsite.model.CampsiteVO;
import com.lutu.campsite_available.model.CampsiteAvailableVO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "campsite_type")
public class CampsiteTypeVO implements Serializable {

	// 直接宣告複合識別類別的屬性，並加上 @EmbeddedId 標註
	@EmbeddedId
	private CompositeDetail id;

	@Column(name = "campsite_name")
	@NotEmpty(message = "營地房型名稱: 請勿空白")
	private String campsiteName; // 營地房型名稱

	@Column(name = "campsite_people")
	@NotNull(message = "可入住人數: 請勿空白")
	private Integer campsitePeople; // 可入住人數

	@Column(name = "campsite_num")
	@NotNull(message = "房間數量: 請勿空白")
	private Integer campsiteNum; // 房間數量

	@Column(name = "campsite_price")
	@NotNull(message = "房間價格: 請勿空白")
	private Integer campsitePrice; // 房間價格

	@Lob
	@Column(name = "campsite_pic1")
	@NotNull(message = "房間照片: 至少上傳1張")
	private byte[] campsitePic1; // 房間照片1

	@Lob
	@Column(name = "campsite_pic2")
	private byte[] campsitePic2; // 房間照片2

	@Lob
	@Column(name = "campsite_pic3")
	private byte[] campsitePic3; // 房間照片3

	@Lob
	@Column(name = "campsite_pic4")
	private byte[] campsitePic4; // 房間照片4

	public CampsiteTypeVO() {

	}

//=======================Association==================================	

//	@ManyToOne(fetch = FetchType.LAZY)
//	@MapsId("campId") // 映射為外鍵
//	@JoinColumn(name = "camp_id", referencedColumnName = "camp_id")
//	private CampVO camp;
//
//	public CampVO getCamp() {
//		return camp;
//	}
//
//	public void setCamp(CampVO camp) {
//		this.camp = camp;
//	}
//
//	@OneToMany(mappedBy = "campsiteType", cascade =CascadeType.ALL)
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumns({ @JoinColumn(name = "campsite_type_id", referencedColumnName = "campsite_type_id"),
			@JoinColumn(name = "camp_id", referencedColumnName = "camp_id"),

	})
//	 @JoinColumn(name = "campsite_type_id", referencedColumnName = "campsite_type_id")
	private Set<CampsiteVO> campsites;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumns({
	    @JoinColumn(name = "camp_id", referencedColumnName = "camp_id"),
	    @JoinColumn(name = "campsite_type_id", referencedColumnName = "campsite_type_id")
	})
//	@JoinColumn(name = "campsite_type_id", referencedColumnName = "campsite_type_id")
	private Set<CampsiteAvailableVO> availabilities;

	public Set<CampsiteAvailableVO> getAvailabilities() {
		return availabilities;
	}

	public void setAvailabilities(Set<CampsiteAvailableVO> availabilities) {
		this.availabilities = availabilities;
	}

	public Set<CampsiteVO> getCampsites() {
		return campsites;
	}

	public void setCampsites(Set<CampsiteVO> campsites) {
		this.campsites = campsites;
	}

	// =======================複合主鍵設定==================================
	// 特別加上對複合主鍵物件的 getter / setter
	public CompositeDetail getId() {
		return id;
	}

	public void setId(CompositeDetail id) {
		this.id = id;
	}

//==================================================================

	public String getCampsiteName() {
		return campsiteName;
	}

	public void setCampsiteName(String campsiteName) {
		this.campsiteName = campsiteName;
	}

	public Integer getCampsitePeople() {
		return campsitePeople;
	}

	public void setCampsitePeople(Integer campsitePeople) {
		this.campsitePeople = campsitePeople;
	}

	public Integer getCampsiteNum() {
		return campsiteNum;
	}

	public void setCampsiteNum(Integer campsiteNum) {
		this.campsiteNum = campsiteNum;
	}

	public Integer getCampsitePrice() {
		return campsitePrice;
	}

	public void setCampsitePrice(Integer campsitePrice) {
		this.campsitePrice = campsitePrice;
	}

	public byte[] getCampsitePic1() {
		return campsitePic1;
	}

	public void setCampsitePic1(byte[] campsitePic1) {
		this.campsitePic1 = campsitePic1;
	}

	public byte[] getCampsitePic2() {
		return campsitePic2;
	}

	public void setCampsitePic2(byte[] campsitePic2) {
		this.campsitePic2 = campsitePic2;
	}

	public byte[] getCampsitePic3() {
		return campsitePic3;
	}

	public void setCampsitePic3(byte[] campsitePic3) {
		this.campsitePic3 = campsitePic3;
	}

	public byte[] getCampsitePic4() {
		return campsitePic4;
	}

	public void setCampsitePic4(byte[] campsitePic4) {
		this.campsitePic4 = campsitePic4;
	}

	@Override
	public String toString() {
		return "CampsiteTypeVO [id=" + id + ", campsiteName=" + campsiteName + ", campsitePeople=" + campsitePeople
				+ ", campsiteNum=" + campsiteNum + ", campsitePrice=" + campsitePrice + ", campsitePic1="
				+ Arrays.toString(campsitePic1);
	}

//=======================複合主鍵設定==================================	

	// 需要宣告一個有包含複合主鍵屬性的類別，並一定要實作 java.io.Serializable 介面
	@Embeddable
	public static class CompositeDetail implements Serializable {
		private static final long serialVersionUID = 1L;

		@Column(name = "campsite_type_id")
		@Min(value = 2001, message = "房型編號起始值為2001")
		private Integer campsiteTypeId; // 營地房型編號

		@Column(name = "camp_id")
		@Min(value = 1001, message = "營地編號起始值為1001")
		private Integer campId; // 營地編號

		// 一定要有無參數建構子
		public CompositeDetail() {
			super();
		}

		public CompositeDetail(Integer campsiteTypeId, Integer campId) {
			super();
			this.campsiteTypeId = campsiteTypeId;
			this.campId = campId;
		}

		public Integer getCampsiteTypeId() {
			return campsiteTypeId;
		}

		public void setCampsiteTypeId(Integer campsiteTypeId) {
			this.campsiteTypeId = campsiteTypeId;
		}

		public Integer getCampId() {
			return campId;
		}

		public void setCampId(Integer campId) {
			this.campId = campId;
		}

		// 一定要 override 此類別的 hashCode() 與 equals() 方法
		@Override
		public int hashCode() {
			return Objects.hash(campId, campsiteTypeId);
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
			return Objects.equals(campId, other.campId) && Objects.equals(campsiteTypeId, other.campsiteTypeId);
		}

		@Override
		public String toString() {
			return "CompositeDetail [campsiteTypeId=" + campsiteTypeId + ", campId=" + campId + "]";
		}

	}

}
