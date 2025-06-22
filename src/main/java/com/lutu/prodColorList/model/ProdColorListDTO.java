package com.lutu.prodColorList.model;

public class ProdColorListDTO {

    private Integer prodId;         // 商品編號（外鍵，也是 composite key）
    private Integer prodColorId;    // 顏色 ID（外鍵，也是 composite key）
    private byte[] prodColorPic;    // 顏色對應的圖片（可為預覽圖或顏色小圖）

    private String colorName;       // 額外欄位：顏色名稱（ColorListVO.colorName）

    public ProdColorListDTO() {}

    public Integer getProdId() {
        return prodId;
    }

    public void setProdId(Integer prodId) {
        this.prodId = prodId;
    }

    public Integer getProdColorId() {
        return prodColorId;
    }

    public void setProdColorId(Integer prodColorId) {
        this.prodColorId = prodColorId;
    }

    public byte[] getProdColorPic() {
        return prodColorPic;
    }

    public void setProdColorPic(byte[] prodColorPic) {
        this.prodColorPic = prodColorPic;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }
}
