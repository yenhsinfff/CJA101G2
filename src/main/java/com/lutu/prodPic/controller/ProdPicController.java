package com.lutu.prodPic.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
@CrossOrigin(origins = "*") // 允許所有網域跨域存取
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
}
