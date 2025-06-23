package com.lutu.shop_order_items_details.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lutu.shop_order.model.ShopOrderService;

@Service
public class ShopOrderItemsDetailsService {

	@Autowired
	ShopOrderItemsDetailsRepository soidr;

	@Autowired
	ShopOrderService sos;

	// 商品訂單新增時同步新增訂單明細，故寫在ShopOrderService

	// 全部查詢
	public List<ShopOrderItemsDetailsVO> getAll() {
		return soidr.findAll();
	}

	// 依訂單編號查詢
	public List<ShopOrderItemsDetailsVO> getAll(Integer shopOrderId) {
		return soidr.findByShopOrderId(shopOrderId);
	}

	// 單筆查詢
	public ShopOrderItemsDetailsVO getOneDetail(ShopOrderItemsDetailsVO.CompositeDetail key1) {

		return soidr.findById(key1).orElseThrow(() -> new RuntimeException("查無此筆明細資料"));
	}

	// 修改評論
	public ShopOrderItemsDetailsVO updateDetails(ShopOrderItemsDetailsDTO_update dtoUpdate) {

		// 1. 先查出原本的VO
		// 找到PK
		ShopOrderItemsDetailsVO.CompositeDetail key = new ShopOrderItemsDetailsVO.CompositeDetail(
				dtoUpdate.getShopOrderId(), dtoUpdate.getProdId(), dtoUpdate.getProdColorId(),
				dtoUpdate.getProdSpecId());

		// 2. 找到該筆訂單vo
		ShopOrderItemsDetailsVO detailsVO = soidr.findById(key).orElseThrow(() -> new RuntimeException("查無此筆明細資料"));

		// 3. 用dto_update的其他欄位更新vo
		if (dtoUpdate.getCommentSatis() != null && dtoUpdate.getCommentSatis() >= 0
				&& dtoUpdate.getCommentSatis() <= 5) {

			detailsVO.setCommentSatis(dtoUpdate.getCommentSatis());
		} 
		else {
			throw new RuntimeException ("評分超過系統機制");
		}

		if (dtoUpdate.getCommentContent() != null) { // 評論有新增或異動

			detailsVO.setCommentContent(dtoUpdate.getCommentContent());
			detailsVO.setCommentDate(LocalDateTime.now());
		} // 刪除原有評論
		else if (detailsVO.getCommentContent() != null && dtoUpdate.getCommentContent() == null) {

			detailsVO.setCommentContent(null);
		}

		soidr.save(detailsVO);
		ShopOrderItemsDetailsVO detailsVO2 = getOneDetail(detailsVO.getCompositeKey());

		return detailsVO2;
	}

}
