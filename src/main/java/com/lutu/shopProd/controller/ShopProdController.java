package com.lutu.shopProd.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lutu.ApiResponse;
import com.lutu.prodPic.model.ProdPicService;
import com.lutu.productType.model.ProdTypeRepository;
import com.lutu.shopProd.model.ShopProdDTO;
import com.lutu.shopProd.model.ShopProdService;

import jakarta.servlet.http.HttpServletResponse;

//api格式 「http://web/CJA101G02/api/campsite_orders」

@RestController
@CrossOrigin(origins = "*") // 允許所有網域跨域存取
public class ShopProdController {

    @Autowired
    ShopProdService shopProdService;

    @Autowired
    ProdPicService prodPicService;
    
    @Autowired
    ProdTypeRepository prodTypeRepository;

    /**
     * 查詢所有商品（上下架商品）
     * @return 所有商品資料列表（含規格、顏色等）
     * GET http://localhost:8081/CJA101G02/admin/products
     */
    @GetMapping("/admin/products")
    public ApiResponse<List<ShopProdDTO>> getAllProds() {
        List<ShopProdDTO> dtoList = shopProdService.getAllProds();
        return new ApiResponse<>("success", dtoList, "查詢成功");
    }
    
    // 只查詢上架商品
    @GetMapping("/api/products")
    public ApiResponse<List<ShopProdDTO>> getAllAvailableProds() {
        List<ShopProdDTO> dtoList = shopProdService.getAvailableProds(); // 只查上架商品
        return new ApiResponse<>("success", dtoList, "查詢成功");
    }


    /**
     * 查詢單一商品by 商品 ID
     * @param id 商品主鍵 ID
     * @return 對應商品資料（含詳細資訊）
     * GET http://localhost:8081/CJA101G02/api/products/{id}
     */
    @GetMapping("/api/products/{id}")
    public ApiResponse<ShopProdDTO> getProdById(@PathVariable Integer id) {
        ShopProdDTO dto = shopProdService.getProdById(id);
        return (dto != null)
            ? new ApiResponse<>("success", dto, "查詢成功")
            : new ApiResponse<>("fail", null, "查無此商品");
    }

    /**
     * 關鍵字查詢商品（名稱模糊比對）
     * @param keyword 查詢關鍵字
     * @return 符合關鍵字的商品列表
     * GET http://localhost:8081/CJA101G02/api/products/keyword?keyword=XXX
     */
    @GetMapping("/api/products/keyword")
    public ApiResponse<List<ShopProdDTO>> getByKeyword(@RequestParam String keyword) {
        return new ApiResponse<>("success", shopProdService.getByKeyword(keyword), "查詢成功");
    }

    /**
     * 查詢指定類別的所有商品
     * @param typeId 類別 ID
     * @return 該類別下的所有商品列表
     * GET http://localhost:8081/CJA101G02/api/products/type/{typeId}
     */
    @GetMapping("/api/products/type/{typeId}")
    public ApiResponse<List<ShopProdDTO>> getByType(@PathVariable Integer typeId) {
        return new ApiResponse<>("success", shopProdService.getByType(typeId), "查詢成功");
    }


    /**
     * 查詢最新上架商品（依日期排序）
     * @return 最新商品列表
     * GET http://localhost:8081/CJA101G02/api/products/latest
     */
    @GetMapping("/api/products/latest")
    public ApiResponse<List<ShopProdDTO>> getLatest(@RequestParam(defaultValue = "6") int limit) {
        return new ApiResponse<>("success", shopProdService.getLatestProds(limit), "查詢成功");
    }

    /**
     * 查詢有折扣的商品（prodDiscount > 0）
     * @return 折扣商品清單
     * GET http://localhost:8081/CJA101G02/api/products/discount
     */
    @GetMapping("/api/products/discount")
    public ApiResponse<List<ShopProdDTO>> getDiscounted() {
        return new ApiResponse<>("success", shopProdService.getDiscountProds(), "查詢成功");
    }

    /**
     * 查詢隨機商品推薦
     * @param limit 限制筆數，預設為5筆
     * @return 隨機商品清單
     * @RequestParam(defaultValue = "5") int limit : 代表 URL 可以帶入參數 limit
     * /api/products/random?limit=3 → 取得 3 筆商品
     * GET http://localhost:8081/CJA101G02/api/products/random → 取得 6 筆商品（使用預設值）
     */
    @GetMapping("/api/products/random")
    public ApiResponse<List<ShopProdDTO>> getRandom(@RequestParam(defaultValue = "5") int limit) {
        return new ApiResponse<>("success", shopProdService.getRandomProds(limit), "推薦成功");
    }
    //價格由低到高
    @GetMapping("/api/products/price-asc")
    public ApiResponse<List<ShopProdDTO>> getProductsByPriceAsc() {
        List<ShopProdDTO> products = shopProdService.getProductsByPriceAsc();
        return new ApiResponse<>("success", products, "查詢成功（價格由低到高）");
    }
    //價格由高到低
    @GetMapping("/api/products/price-desc")
    public ApiResponse<List<ShopProdDTO>> getProductsByPriceDesc() {
        List<ShopProdDTO> products = shopProdService.getProductsByPriceDesc();
        return new ApiResponse<>("success", products, "查詢成功（價格由高到低）");
    }
    //價格區間查詢
    @GetMapping("/api/products/price-range")
    public ApiResponse<List<ShopProdDTO>> getByPriceRange(@RequestParam String range) {
        List<ShopProdDTO> list = shopProdService.getByPriceRange(range);
        return new ApiResponse<>("success", list, "價格區間查詢成功");
    }


    /**
     * 新增商品（含規格與顏色資料）
     * @param dto 商品 DTO 資料
     * @return 新增成功後的商品 DTO
     * POST http://localhost:8081/CJA101G02/api/addprod
     */
    @PostMapping("/api/addprod")
    public ApiResponse<ShopProdDTO> addProd(@RequestBody ShopProdDTO dto) {
        try {
            ShopProdDTO saved = shopProdService.addProd(dto);
            return new ApiResponse<>("success", saved, "新增成功");
        } catch (Exception e) {
            return new ApiResponse<>("fail", null, "新增失敗：" + e.getMessage());
        }
    }

    /**
     * 修改商品（含更新對應規格資料）
     * @param dto 商品 DTO（含規格、顏色）
     * @return 修改後的商品資料
     * PUT http://localhost:8081/CJA101G02/api/updateprod
     */
    @PutMapping("/api/updateprod")
    public ApiResponse<ShopProdDTO> updateProd(@RequestBody ShopProdDTO dto) {
        ShopProdDTO updated = shopProdService.updateProd(dto);
        return (updated != null)
            ? new ApiResponse<>("success", updated, "修改成功")
            : new ApiResponse<>("fail", null, "查無此商品，無法修改");
    }
    
    /**
     * 切換商品上架狀態（0=下架，1=上架）
     * @param id 商品 ID
     * @param status 商品狀態（0 or 1）
     * @return 修改結果訊息
     * PATCH http://localhost:8081/CJA101G02/api/products/{id}/status?status=1
     */
    @PatchMapping("/api/products/{id}/status") //patch部分更新（只改指定欄位）
    public ApiResponse<String> updateStatus(@PathVariable Integer id, @RequestParam Byte status) {
        ShopProdDTO dto = shopProdService.getProdById(id);

        if (dto != null) {
            dto.setProdStatus(status);
            shopProdService.updateProd(dto);
            return new ApiResponse<>("success", "狀態修改成功", (status == 1) ? "已上架" : "已下架");
        } else {
            return new ApiResponse<>("fail", null, "查無此商品");
        }
    }
	
    
    
//	// 抓取資料庫的營地圖片，提供給前端
//	@GetMapping("/api/camps/{campId}/{num}")
//	public void getCampPic3(@PathVariable Integer campId, @PathVariable Integer num, HttpServletResponse response)
//			throws IOException {
//		byte[] img = null;
//		try {
//			switch (num) {
//			case 1:
//
//				img = (campService.getOneCamp(campId)).getCampPic1();
//				break;
//
//			case 2:
//
//				img = (campService.getOneCamp(campId)).getCampPic2();
//				break;
//
//			case 3:
//
//				img = (campService.getOneCamp(campId)).getCampPic3();
//				break;
//
//			case 4:
//
//				img = (campService.getOneCamp(campId)).getCampPic4();
//				break;
//
//			default:
//				throw new IllegalArgumentException("Unexpected value: " + num);
//			}
//			response.setContentType("image/jpeg");
//			response.getOutputStream().write(img);
//		} catch (Exception e) {
//			System.out.println("營地編號：" + campId + "||第" + num + "張圖片無法讀取");// TODO: handle exception
//			img = (campService.getOneCamp(campId)).getCampPic1();
//			response.setContentType("image/jpeg");
//			response.getOutputStream().write(img);
//		}
//	}
}
