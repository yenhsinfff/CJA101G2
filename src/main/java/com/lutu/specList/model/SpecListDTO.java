package com.lutu.specList.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class SpecListDTO {
	
    private Integer specId;
    
    @NotEmpty(message="規格名稱: 請勿空白")
	@Pattern(regexp = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,50}$", message = "規格名稱: 只能是中、英文字母、數字和_ , 且長度必需在2到50之間")
    private String specName;

    public SpecListDTO() {}

    public SpecListDTO(Integer specId, String specName) {
        this.specId = specId;
        this.specName = specName;
    }

    public Integer getSpecId() {
        return specId;
    }

    public void setSpecId(Integer specId) {
        this.specId = specId;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }
}
