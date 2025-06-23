package com.lutu.colorList.model;

import java.util.List;

import com.lutu.prodColorList.model.ProdColorListDTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class ColorListDTO {

    private Integer colorId; // 顏色編號
    
    @NotEmpty(message="顏色名稱: 請勿空白")
	@Pattern(regexp = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,50}$", message = "顏色名稱: 只能是中、英文字母、數字和_ , 且長度必需在2到50之間")
    private String colorName; // 顏色名稱

    private List<ProdColorListDTO> prodColors;

    public ColorListDTO() {
        super();
    }

    public ColorListDTO(Integer colorId, String colorName, List<ProdColorListDTO> prodColors) {
        this.colorId = colorId;
        this.colorName = colorName;
        this.prodColors = prodColors;
    }

    public Integer getColorId() {
        return colorId;
    }

    public void setColorId(Integer colorId) {
        this.colorId = colorId;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public List<ProdColorListDTO> getProdColors() {
        return prodColors;
    }

    public void setProdColors(List<ProdColorListDTO> prodColors) {
        this.prodColors = prodColors;
    }

    @Override
    public String toString() {
        return "ColorListDTO [colorId=" + colorId + ", colorName=" + colorName + ", prodColors=" + prodColors + "]";
    }
}
