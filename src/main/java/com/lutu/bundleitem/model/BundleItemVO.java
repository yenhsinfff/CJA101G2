package com.lutu.bundleitem.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.lutu.bundleitemdetails.model.BundleItemDetailsVO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "bundle_item")
public class BundleItemVO implements Serializable {

	
	@Id
	@Column(name = "bundle_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer bundleId; // 加購項目編號

	@Column(name = "camp_id")
	@NotNull(message = "營地編號: 請勿空白")
	private Integer campId; // 營地編號

	@Column(name = "bundle_name")
	@NotEmpty(message = "加購項目名稱: 請勿空白")
	private String bundleName; // 加購項目名稱

	@Column(name = "bundle_add_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate bundleAddDate; // 建立日期

	@Column(name = "bundle_price", nullable = false)
	@NotNull(message = "加購項目價格: 請勿空白")
	private Integer bundlePrice; // 加購項目價格

//=======================================================	
	
	@OneToMany(mappedBy = "bundleItem", cascade = CascadeType.ALL)
	private Set<BundleItemDetailsVO> bundleItemDetails;

   public Set<BundleItemDetailsVO> getBundleItemDetails() {
		return bundleItemDetails;
	}

	public void setBundleItemDetails(Set<BundleItemDetailsVO> bundleItemDetails) {
		this.bundleItemDetails = bundleItemDetails;
	}

//=======================================================	
	
    public BundleItemVO(){
		
	}

	public Integer getBundleId() {
		return bundleId;
	}

	public void setBundleId(Integer bundleId) {
		this.bundleId = bundleId;
	}

	public Integer getCampId() {
		return campId;
	}

	public void setCampId(Integer campId) {
		this.campId = campId;
	}

	public String getBundleName() {
		return bundleName;
	}

	public void setBundleName(String bundleName) {
		this.bundleName = bundleName;
	}

	public LocalDate getBundleAddDate() {
		return bundleAddDate;
	}

	public void setBundleAddDate(LocalDate bundleAddDate) {
		this.bundleAddDate = bundleAddDate;
	}

	public Integer getBundlePrice() {
		return bundlePrice;
	}

	public void setBundlePrice(Integer bundlePrice) {
		this.bundlePrice = bundlePrice;
	}

	@Override
	public String toString() {
		return "BundleItemVO [bundleId=" + bundleId + ", CampId=" + campId + ", bundleName=" + bundleName
				+ ", bundleAddDate=" + bundleAddDate + ", bundlePrice=" + bundlePrice + "]";
	}

}
