package com.lutu.campsite.model;

public class CampsiteDTO {

	private Integer campsitePeople;
	    private Integer campsiteId;
	    private Integer campId;
	    private Integer campsiteTypeId;
	    private String campsiteIdName;
	    private String camperName;
	    
		public CampsiteDTO(Integer campsitePeople,Integer campsiteId, Integer campId, Integer campsiteTypeId, String campsiteIdName,
				String camperName) {
			super();
			this.campsitePeople = campsitePeople;
			this.campsiteId = campsiteId;
			this.campId = campId;
			this.campsiteTypeId = campsiteTypeId;
			this.campsiteIdName = campsiteIdName;
			this.camperName = camperName;
		}

		public Integer getCampsitePeople() {
			return campsitePeople;
		}

		public void setCampsitePeople(Integer campsitePeople) {
			this.campsitePeople = campsitePeople;
		}

		public Integer getCampsiteId() {
			return campsiteId;
		}

		public void setCampsiteId(Integer campsiteId) {
			this.campsiteId = campsiteId;
		}

		public Integer getCampId() {
			return campId;
		}

		public void setCampId(Integer campId) {
			this.campId = campId;
		}

		public Integer getCampsiteTypeId() {
			return campsiteTypeId;
		}

		public void setCampsiteTypeId(Integer campsiteTypeId) {
			this.campsiteTypeId = campsiteTypeId;
		}

		public String getCampsiteIdName() {
			return campsiteIdName;
		}

		public void setCampsiteIdName(String campsiteIdName) {
			this.campsiteIdName = campsiteIdName;
		}

		public String getCamperName() {
			return camperName;
		}

		public void setCamperName(String camperName) {
			this.camperName = camperName;
		}


		
		
	}

