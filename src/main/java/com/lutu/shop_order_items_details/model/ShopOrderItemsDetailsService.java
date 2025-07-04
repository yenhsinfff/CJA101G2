package com.lutu.shop_order_items_details.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.lutu.colorList.model.ColorListRepository;
import com.lutu.colorList.model.ColorListVO;
import com.lutu.prodSpecList.model.ProdSpecListRepository;
import com.lutu.prodSpecList.model.ProdSpecListVO;
import com.lutu.shopProd.model.ShopProdRepository;
import com.lutu.shopProd.model.ShopProdVO;
import com.lutu.shop_order.model.ShopOrderService;
import com.lutu.shop_order.model.ShopOrderVO;
import com.lutu.specList.model.SpecListRepository;
import com.lutu.specList.model.SpecListVO;

@Service
public class ShopOrderItemsDetailsService {

	@Autowired
	ShopOrderItemsDetailsRepository soidr;

	@Autowired
	ShopOrderService sos;

	@Autowired
	ShopProdRepository spr;

	@Autowired
	ColorListRepository clr;

	@Autowired
	SpecListRepository slr;

	@Autowired
	ProdSpecListRepository psr;

	// 商品訂單新增時同步新增訂單明細，故寫在ShopOrderService

	// vo轉dto
	public ShopOrderItemsDetailsDTO_res voToDto(ShopOrderItemsDetailsVO vo) {
		ShopOrderItemsDetailsDTO_res dto = new ShopOrderItemsDetailsDTO_res();

		dto.setShopOrderId(vo.getShopOrderId());
		dto.setProdId(vo.getProdId());
		dto.setProdColorId(vo.getProdColorId());
		dto.setProdSpecId(vo.getProdSpecId());
		dto.setShopOrderQty(vo.getShopOrderQty());
		dto.setCommentSatis(vo.getCommentSatis());
		dto.setCommentContent(vo.getCommentContent());
		dto.setCommentDate(vo.getCommentDate());

		// 名稱
		dto.setProdName(spr.findById(vo.getProdId()).map(ShopProdVO::getProdName).orElse("未知的商品"));
		dto.setProdColorName(clr.findById(vo.getProdColorId()).map(ColorListVO::getColorName).orElse("未知的顏色"));
		dto.setProdSpecName(slr.findById(vo.getProdSpecId()).map(SpecListVO::getSpecName).orElse("未知的規格"));

		ProdSpecListVO.CompositeDetail2 key = new ProdSpecListVO.CompositeDetail2(vo.getProdId(), vo.getProdSpecId());
		// 價格
		dto.setProdOrderPrice(psr.findById(key).map(ProdSpecListVO::getProdSpecPrice).orElse(0));

		return dto;
	}

	// 全部查詢
	public List<ShopOrderItemsDetailsDTO_res> getAll() {
		List<ShopOrderItemsDetailsVO> listVO = soidr.findAll();
		List<ShopOrderItemsDetailsDTO_res> listDTO = new ArrayList<>();

		for (ShopOrderItemsDetailsVO vo : listVO) {
			ShopOrderItemsDetailsDTO_res dto = voToDto(vo);
			listDTO.add(dto);
		}

		return listDTO;
	}

	// 依訂單編號查詢
	public List<ShopOrderItemsDetailsDTO_res> getAll(Integer shopOrderId) {
		List<ShopOrderItemsDetailsVO> listVO = soidr.findByShopOrderId(shopOrderId);

		List<ShopOrderItemsDetailsDTO_res> listDTO = new ArrayList<>();

		for (ShopOrderItemsDetailsVO vo : listVO) {
			ShopOrderItemsDetailsDTO_res dto = voToDto(vo);
			listDTO.add(dto);
		}

		return listDTO;
	}

	// 單筆查詢
	public ShopOrderItemsDetailsDTO_res getOneDetail(ShopOrderItemsDetailsVO.CompositeDetail key1) {

		ShopOrderItemsDetailsVO vo = soidr.findById(key1).orElseThrow(() -> new RuntimeException("查無此筆明細資料"));

		ShopOrderItemsDetailsDTO_res dto = voToDto(vo);

		return dto;
	}

	// 修改評論
	public ShopOrderItemsDetailsDTO_res updateDetails(ShopOrderItemsDetailsDTO_update dtoUpdate) {

		// 1. 先查出原本的明細VO
		// 找到PK
		ShopOrderItemsDetailsVO.CompositeDetail key = new ShopOrderItemsDetailsVO.CompositeDetail(
				dtoUpdate.getShopOrderId(), dtoUpdate.getProdId(), dtoUpdate.getProdColorId(),
				dtoUpdate.getProdSpecId());

		// 2. 找到該筆訂單明細vo
		ShopOrderItemsDetailsVO detailsVO = soidr.findById(key).orElseThrow(() -> new RuntimeException("查無此筆明細資料"));

		// 3. 判斷訂單狀態
		ShopOrderVO order = sos.getOneShopOrder(dtoUpdate.getShopOrderId());

		final int ORDER_STATUS_COMPLETED = 3; // 設定訂單完成的狀態為常數，3: 已取貨，完成訂單

		if (order.getShopOrderStatus() != ORDER_STATUS_COMPLETED) { // 訂單狀態還沒取貨無法修改
			throw new RuntimeException("訂單未完成，無法評論或修改評價");
		}

		// 4. 用dto_update的其他欄位更新vo
		if (dtoUpdate.getCommentSatis() != null && dtoUpdate.getCommentSatis() >= 0
				&& dtoUpdate.getCommentSatis() <= 5) {

			detailsVO.setCommentSatis(dtoUpdate.getCommentSatis());
		} else {
			throw new RuntimeException("評分超過系統機制");
		}

		if (dtoUpdate.getCommentContent() != null) { // 評論有新增或異動

			detailsVO.setCommentContent(dtoUpdate.getCommentContent());
			detailsVO.setCommentDate(LocalDateTime.now());
		} // 刪除原有評論
		else if (detailsVO.getCommentContent() != null && dtoUpdate.getCommentContent() == null) {

			detailsVO.setCommentContent(null);
		}

		soidr.save(detailsVO);

		// vo轉dto
		return voToDto(detailsVO);

	}

	// 查看商品評分評價
	public List<ShopOrderItemsDetailsDTO_comment> getProdComments(@RequestParam Integer prodId) {
		List<ShopOrderItemsDetailsVO> listVO = soidr.findByprodId(prodId);

		List<ShopOrderItemsDetailsDTO_comment> listDTO = new ArrayList<>();

		for (ShopOrderItemsDetailsVO vo : listVO) {
			
			// 只留下有評分或有評論的資料
			boolean hasScore = vo.getCommentSatis() != 0;
			boolean hasComments = vo.getCommentContent() != null && !vo.getCommentContent().trim().isEmpty();
			
			if (!hasScore && !hasComments) {
		        continue; // 兩者都沒填就跳過
		    }
			
			ShopOrderItemsDetailsDTO_comment dto = new ShopOrderItemsDetailsDTO_comment();

			dto.setProdId(vo.getProdId());

			// 取得會員編號
			ShopOrderVO orderVO = sos.getOneShopOrder(vo.getShopOrderId());
			dto.setMemId(orderVO.getMemId().getMemId());

			if (hasScore) {
		        dto.setCommentSatis(vo.getCommentSatis());
		    }
		    if (hasComments) {
		        dto.setCommentContent(vo.getCommentContent());
		    }

			listDTO.add(dto);

		}

		return listDTO;
	}

}
