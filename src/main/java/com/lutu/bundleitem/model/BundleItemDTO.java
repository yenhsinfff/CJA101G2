package com.lutu.bundleitem.model;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.lutu.bundleitemdetails.model.BundleItemDetailsVO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class BundleItemDTO {


	private Integer bundleId; // 加購項目編號


	@NotNull(message = "營地編號: 請勿空白")
	private Integer campId; // 營地編號


	@NotEmpty(message = "加購項目名稱: 請勿空白")
	private String bundleName; // 加購項目名稱


	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate bundleAddDate; // 建立日期


	@NotNull(message = "加購項目價格: 請勿空白")
	private Integer bundlePrice; // 加購項目價格

	
    public BundleItemDTO(){
		
	}
    
    

	public BundleItemDTO(Integer bundleId, @NotNull(message = "營地編號: 請勿空白") Integer campId,
			@NotEmpty(message = "加購項目名稱: 請勿空白") String bundleName, LocalDate bundleAddDate,
			@NotNull(message = "加購項目價格: 請勿空白") Integer bundlePrice) {
		super();
		this.bundleId = bundleId;
		this.campId = campId;
		this.bundleName = bundleName;
		this.bundleAddDate = bundleAddDate;
		this.bundlePrice = bundlePrice;
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

}
