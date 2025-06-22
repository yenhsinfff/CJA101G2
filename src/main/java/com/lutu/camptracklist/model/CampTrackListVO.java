package com.lutu.camptracklist.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.lutu.camp.model.CampVO;
import com.lutu.member.model.MemberVO;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "camp_track_list") // 營地收藏
public class CampTrackListVO{

	// 直接宣告複合識別類別的屬性，並加上 @EmbeddedId 標註
	@EmbeddedId
	private CompositeDetail id;
	
	@Column(name = "mem_track_date")
	private LocalDate memTrackDate; // 收藏時間
	
	public CampTrackListVO(){
		
	}
	
	//=======================Association==================================	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("campId")
	@JoinColumn(name = "camp_id", referencedColumnName = "camp_id")
	private CampVO camp;
	
	public CampVO getCamp() {
		return camp;
	}

	public void setCamp(CampVO camp) {
		this.camp = camp;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("memId")
	@JoinColumn(name = "mem_id", referencedColumnName = "mem_id")
	private MemberVO member;
	

	public MemberVO getMember() {
		return member;
	}

	public void setMember(MemberVO member) {
		this.member = member;
	}	
	
	//====================================================================
	// 特別加上對複合主鍵物件的 getter / setter
	public CompositeDetail getId() {
		return id;
	}

	public void setId(CompositeDetail compositeKey) {
		this.id = id;
	}

	public LocalDate getMemTrackDate() {
		return memTrackDate;
	}

	public void setMemTrackDate(LocalDate memTrackDate) {
		this.memTrackDate = memTrackDate;
	}
	
	

	@Override
	public String toString() {
		return "CampTrackListVO [id=" + id + ", memTrackDate=" + memTrackDate + ", camp=" + camp + ", member=" + member
				+ "]";
	}



	// 需要宣告一個有包含複合主鍵屬性的類別，並一定要實作 java.io.Serializable 介面
	@Embeddable
	public static class CompositeDetail implements Serializable {
		private static final long serialVersionUID = 1L;

		@Column(name = "camp_id")
		private Integer campId; // 營地編號

		@Column(name = "mem_id")
		private Integer memId; // 露營者編號

		// 一定要有無參數建構子
		public CompositeDetail() {
			super();
		}

		public Integer getCampId() {
			return campId;
		}

		public void setCampId(Integer campId) {
			this.campId = campId;
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
			return Objects.hash(campId, memId);
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
			return Objects.equals(campId, other.campId) && Objects.equals(memId, other.memId);
		}

		@Override
		public String toString() {
			return "CompositeDetail [campId=" + campId + ", memId=" + memId + "]";
		}

	}

}
