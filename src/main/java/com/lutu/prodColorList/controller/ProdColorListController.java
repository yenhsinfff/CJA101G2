package com.lutu.prodColorList.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lutu.ApiResponse;
import com.lutu.prodColorList.model.ProdColorListDTO;
import com.lutu.prodColorList.model.ProdColorListService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/prod-colors")
public class ProdColorListController {

    @Autowired
    private ProdColorListService service;

    /** 取得所有規格 
     * http://localhost:8081/CJA101G02/api/prod-colors
     * */
    @GetMapping("")
    public ApiResponse<List<ProdColorListDTO>> getAllProdColors() {
        List<ProdColorListDTO> list = service.getAllProdColors();
        return new ApiResponse<>("success", list, "查詢成功");
    }
    
    // 查詢某商品的所有顏色規格
    // GET http://localhost:8081/CJA101G02/api/prod-colors/101
    @GetMapping("/{prodId}")
    public ApiResponse<List<ProdColorListDTO>> getProdColorsByProdId(@PathVariable Integer prodId) {
        List<ProdColorListDTO> colorList = service.getProdColorsByProdId(prodId);
        return new ApiResponse<>("success", colorList, "查詢成功");
    }

    // 查詢單一顏色規格
    // GET http://localhost:8081/CJA101G02/api/prod-colors/101/3
    @GetMapping("/{prodId}/{colorId}")
    public ApiResponse<ProdColorListDTO> getOne(@PathVariable Integer prodId, @PathVariable Integer colorId) {
        ProdColorListDTO dto = service.getOne(prodId, colorId);
        if (dto != null) {
            return new ApiResponse<>("success", dto, "查詢成功");
        } else {
            return new ApiResponse<>("fail", null, "查無此顏色規格");
        }
    }

    // 新增或更新一筆顏色資料
    // POST http://localhost:8081/CJA101G02/api/prod-colors
    @PostMapping("")
    public ApiResponse<ProdColorListDTO> saveOrUpdate(@RequestBody ProdColorListDTO dto) {
        ProdColorListDTO saved = service.saveOrUpdate(dto);
        return new ApiResponse<>("success", saved, "儲存成功");
    }
    
    // 取得顏色圖片
    @GetMapping("/colorpic/{prodId}/{colorId}")
    public void getProdColorPic(@PathVariable Integer prodId, @PathVariable Integer colorId, HttpServletResponse response) throws IOException {
        byte[] img = service.getPicByCompositeKey(prodId, colorId);
        if (img != null && img.length > 0) {
            response.setContentType("image/jpeg"); // 或依需要改為 image/png
            response.getOutputStream().write(img);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "找不到顏色圖片");
        }
    }

    //新增商品顏色圖片
    @PostMapping("/colorpic/{prodId}/{colorId}")
    public ApiResponse<String> uploadColorPic(
            @PathVariable Integer prodId,
            @PathVariable Integer colorId,
            @RequestParam("file") MultipartFile file) {

        try {
        	if (file == null || file.isEmpty()) {
                return new ApiResponse<>("fail", null, "未選擇圖片檔案");
            }
        	
            boolean success = service.updateColorPic(prodId, colorId, file);
            if (success) {
                return new ApiResponse<>("success", "圖片上傳成功", "顏色圖片已更新");
            } else {
                return new ApiResponse<>("fail", null, "圖片上傳失敗，找不到對應資料");
            }
        } catch (IOException e) {
            return new ApiResponse<>("fail", null, "圖片處理錯誤：" + e.getMessage());
        }
    }
    
    // 刪除一筆顏色資料
    // DELETE http://localhost:8081/CJA101G02/api/prod-colors/101/3
//    @DeleteMapping("/{prodId}/{colorId}")
//    public ApiResponse<Void> delete(@PathVariable Integer prodId, @PathVariable Integer colorId) {
//        service.delete(prodId, colorId);
//        return new ApiResponse<>("success", null, "刪除成功");
//    }
}
