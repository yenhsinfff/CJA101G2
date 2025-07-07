package com.lutu.prodPic.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lutu.ApiResponse;
import com.lutu.prodPic.model.ProdPicDTO;
import com.lutu.prodPic.model.ProdPicService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/prodpics")
public class ProdPicController {

	@Autowired
    private ProdPicService prodPicService;

	
	
    public ProdPicController(ProdPicService prodPicService) {
        this.prodPicService = prodPicService;
    }
    
    /**
     * 取得商品圖片 (依照 prodPicId)
     * GET /api/prodpics/{prodPicId}
     */
    @GetMapping("/{prodPicId}")
    public void getProductImage(@PathVariable Integer prodPicId, HttpServletResponse response) throws IOException {
        byte[] img = prodPicService.getProdPicById(prodPicId); // 從 service 拿 byte[]
        if (img != null && img.length > 0) {
            try (InputStream is = new ByteArrayInputStream(img)) {
                String mimeType = URLConnection.guessContentTypeFromStream(is);
                if (mimeType == null) {
                    mimeType = "application/octet-stream";
                }
                response.setContentType(mimeType);
                response.getOutputStream().write(img);
            }
        }
    }
    
    // 依照prodId取得其商品圖片
    @GetMapping("/byProd/{prodId}")
    public List<ProdPicDTO> getProdPicsByProdId(@PathVariable Integer prodId) {
        return prodPicService.getByProdId(prodId);
    }

    
    /**
     * 上傳商品圖片（指定商品 ID）
     * POST /api/prodpics/upload/{prodId}
     */
    @PostMapping("/upload/{prodId}")
    public ApiResponse<String> uploadProductImage(
            @PathVariable Integer prodId,
            @RequestParam("file") MultipartFile file) {

        try {
        	if (file == null || file.isEmpty()) {
                return new ApiResponse<>("fail", null, "圖片為空，請重新上傳");
            }
        	
            boolean success = prodPicService.saveProductImage(prodId, file);
            if (success) {
                return new ApiResponse<>("success", "圖片上傳成功", "商品圖片已儲存");
            } else {
                return new ApiResponse<>("fail", null, "圖片儲存失敗");
            }
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "上傳發生錯誤：" + e.getMessage());
        }
    }
    
    
    /**
     * 覆蓋更新指定商品圖片（依商品 ID 與第幾張圖）用 index 取出第幾張圖。
     * POST /api/prodpics/upload/{prodId}/{index}
     */
    @PostMapping("/upload/{prodId}/{index}")
    public ApiResponse<String> uploadProductImageByIndex(
        @PathVariable Integer prodId,
        @PathVariable Integer index,
        @RequestParam("file") MultipartFile file) {

        if (file == null || file.isEmpty()) {
            return new ApiResponse<>("fail", null, "圖片為空，請重新上傳");
        }

        boolean success = prodPicService.updateProductImageByIndex(prodId, index, file);
        if (success) {
            return new ApiResponse<>("success", "圖片上傳成功", "商品圖片已更新");
        } else {
            return new ApiResponse<>("fail", null, "圖片更新失敗");
        }
    }

    // 移除商品圖片
    @PatchMapping("/clear/{prodId}/{index}")
    public ApiResponse<String> clearImage(@PathVariable Integer prodId, @PathVariable Integer index) {
        boolean success = prodPicService.clearImageByIndex(prodId, index);
        return success
            ? new ApiResponse<>("success", "圖片已清除", null)
            : new ApiResponse<>("fail", null, "圖片清除失敗");
    }

    
}
