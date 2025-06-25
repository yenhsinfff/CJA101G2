package com.lutu.shop_order.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lutu.discount_code.model.DiscountCodeRepository;
import com.lutu.discount_code.model.DiscountCodeVO;
import com.lutu.member.model.MemberRepository;
import com.lutu.member.model.MemberVO;
import com.lutu.prodColorList.model.ProdColorListRepository;
import com.lutu.prodSpecList.model.ProdSpecListRepository;
import com.lutu.prodSpecList.model.ProdSpecListVO;
import com.lutu.shopProd.model.ShopProdRepository;
import com.lutu.shop_order_items_details.model.ShopOrderItemsDetailsDTO_insert_req;
import com.lutu.shop_order_items_details.model.ShopOrderItemsDetailsRepository;
import com.lutu.shop_order_items_details.model.ShopOrderItemsDetailsVO;

@Service
public class ShopOrderService {

	@Autowired
	ShopOrderRepository sor;

	@Autowired
	ProdColorListRepository pclr;

	@Autowired
	ProdSpecListRepository pslr;

	@Autowired
	ShopProdRepository spr;

	@Autowired
	ShopOrderItemsDetailsRepository soidr;

	@Autowired
	DiscountCodeRepository dcr;

	@Autowired
	MemberRepository mr;

	@Transactional
	public ShopOrderVO addShopOrder(ShopOrderDTO_insert dto) {

		// 1. 新增訂單
		ShopOrderVO sovo = new ShopOrderVO();

		// 查出完整會員VO
		MemberVO mvo = mr.findById(dto.getMemId()).orElseThrow(() -> new RuntimeException("查無該筆會員編號"));
		sovo.setMemId(mvo);

		sovo.setShopOrderShipment(dto.getShopOrderShipment());
		sovo.setShopOrderShipFee(dto.getShopOrderShipFee());

		// 於明細建立完成後取得折扣前總金額，先預設為BigDecimal 0，方便後續計算
		BigDecimal beforeDiscountAmount = BigDecimal.ZERO;
		sovo.setBeforeDiscountAmount(beforeDiscountAmount.intValue());

		// 將實付金額預設為 BigDecimal 0，方便後續計算
		BigDecimal afterDiscountAmount = BigDecimal.ZERO;
		sovo.setAfterDiscountAmount(afterDiscountAmount.intValue());

		sovo.setShopOrderPayment(dto.getShopOrderPayment());
		sovo.setOrderName(dto.getOrderName());
		sovo.setOrderEmail(dto.getOrderEmail());
		sovo.setOrderPhone(dto.getOrderPhone());
		sovo.setOrderShippingAddress(dto.getOrderShippingAddress());
		sovo.setShopOrderNote(dto.getShopOrderNote());
		sovo.setShopOrderShipDate(dto.getShopOrderShipDate());
		sovo.setShopOrderStatus(dto.getShopOrderStatus() == null ? (byte) 0 : dto.getShopOrderStatus());
		sovo.setShopReturnApply(dto.getShopReturnApply() == null ? (byte) 0 : dto.getShopReturnApply());

		sor.save(sovo); // 存主表，產生訂單ID

		sovo = sor.findById(sovo.getShopOrderId()).orElseThrow(() -> new RuntimeException("查無訂單"));

		// 2. 新增所有明細
		List<ShopOrderItemsDetailsDTO_insert_req> dtoDetails = dto.getDetailsDto();

		if (dtoDetails == null || dtoDetails.isEmpty()) {
			throw new IllegalArgumentException("訂單明細不得為空");
		} else {

			for (ShopOrderItemsDetailsDTO_insert_req detailsDTO : dtoDetails) {

				ShopOrderItemsDetailsVO soidVO = new ShopOrderItemsDetailsVO();

				soidVO.setShopOrderId(sovo.getShopOrderId());
				soidVO.setProdId(detailsDTO.getProdId());

				// 取得數量，先轉為BigDecimal方便後續計算
				BigDecimal qty = new BigDecimal(detailsDTO.getShopOrderQty());
				soidVO.setShopOrderQty(qty.intValue());

				// 取得價格，先轉為BigDecimal方便計算
				ProdSpecListVO.CompositeDetail2 key = new ProdSpecListVO.CompositeDetail2(detailsDTO.getProdId(),
						detailsDTO.getProdSpecId());
				ProdSpecListVO spec = pslr.findById(key).orElseThrow(() -> new RuntimeException("查無該種規格"));
				BigDecimal price = new BigDecimal(spec.getProdSpecPrice());

				// 將價格存入訂單明細
				soidVO.setProdOrderPrice(price.intValue());

				// 新增明細時不會有滿意度、評價內容、評價日期
				soidVO.setProdColorId(detailsDTO.getProdColorId());
				soidVO.setProdSpecId(detailsDTO.getProdSpecId());

				// 依照明細計算折扣前金額
				beforeDiscountAmount = beforeDiscountAmount.add(price.multiply(qty));

				// 將明細存入明細資料庫
				soidr.save(soidVO);

			}
		}

		// 設定折扣前總金額
		sovo.setBeforeDiscountAmount(beforeDiscountAmount.intValue());

		// 加入運費，預設是60
		BigDecimal shipFee = dto.getShopOrderShipFee() != null ? new BigDecimal(dto.getShopOrderShipFee())
				: new BigDecimal(60);

		// 如果有折扣碼需要扣除折扣碼金額來計算總金額
		if (dto.getDiscountCodeId() != null) {

			// 存入DiscountCodeId
			DiscountCodeVO dcVO = dcr.findById(dto.getDiscountCodeId())
					.orElseThrow(() -> new RuntimeException("查無該種折扣碼"));
			sovo.setDiscountCodeId(dcVO);

			// 取得discountCodeType
			if (sovo.getDiscountCodeId() != null) {
				Byte type = sovo.getDiscountCodeId().getDiscountType();

				// 取得discountValue
				BigDecimal value = dcVO.getDiscountValue();

				if (type == 0) {

					sovo.setDiscountAmount(value.intValue()); // 將value轉換為Integer

					// 計算折扣後金額
					BigDecimal totalAmount = beforeDiscountAmount.subtract(value);

					// 加入運費計算實付金額
					afterDiscountAmount = totalAmount.add(shipFee);

					// 將afterDiscountAmount存入商品訂單
					sovo.setAfterDiscountAmount(afterDiscountAmount.intValue());
				} else if (type == 1) {
					// 折扣前金額*(1 - discountValue(%))並四捨五入
					BigDecimal discountAmount = beforeDiscountAmount.multiply(BigDecimal.ONE.subtract(value))
							.setScale(0, RoundingMode.HALF_UP);

					// 計算折扣後金額，先以BigDecimal計算
					BigDecimal totalAmount = beforeDiscountAmount.subtract(discountAmount);

					// 加入運費計算實付金額
					afterDiscountAmount = totalAmount.add(shipFee);

					sovo.setDiscountAmount(discountAmount.intValue()); // 存入折扣金額
					sovo.setAfterDiscountAmount(afterDiscountAmount.intValue()); // 存入實付金額

				}
			}

		} else {

			// 沒有折扣碼，直接加運費
			afterDiscountAmount = beforeDiscountAmount.add(shipFee);
			System.out.println("afterDiscountAmount:" + afterDiscountAmount);
			sovo.setDiscountCodeId(null);
			sovo.setDiscountAmount(null);
			sovo.setAfterDiscountAmount(afterDiscountAmount.intValue());

		}

		sor.save(sovo); // 再存一次，更新金額
		ShopOrderVO sovo2 = getOneShopOrder(sovo.getShopOrderId());
		return sovo2;
	}

	@Transactional
	public ShopOrderVO updateShopOrder(ShopOrderDTO_update_req dtoUpdate) {

		ShopOrderVO sovo;
		// 1. 先查出原本的VO
		if (sor.existsById(dtoUpdate.getShopOrderId())) {
			sovo = getOneShopOrder(dtoUpdate.getShopOrderId());
		} else {
			throw new RuntimeException("找不到id");
		}

		// 2. 只更新有傳值的欄位（可用if判斷，避免null覆蓋）

		// memId不可變動
		sovo.setMemId(sovo.getMemId());

		if (dtoUpdate.getShopOrderShipment() != null) {
			sovo.setShopOrderShipment(dtoUpdate.getShopOrderShipment());
		}

		if (dtoUpdate.getShopOrderShipFee() != null) {
			sovo.setShopOrderShipFee(dtoUpdate.getShopOrderShipFee());
		}

//		sovo.setBeforeDiscountAmount(sovo.getBeforeDiscountAmount());

		// 先預設為BigDecimal 0，方便後續計算
		BigDecimal beforeDiscountAmount = new BigDecimal(sovo.getBeforeDiscountAmount());

		// 將實付金額預設為 BigDecimal 0，方便後續計算
		BigDecimal afterDiscountAmount = new BigDecimal(sovo.getAfterDiscountAmount());

		// 運費轉為BigDecimal
		BigDecimal shipFee = new BigDecimal(sovo.getShopOrderShipFee());

		// 檢查是否傳入 discountCodeId 欄位
		if (dtoUpdate.hasDiscountCodeId()) {
			if (dtoUpdate.getDiscountCodeId() != null) {
				// 新增或更新折扣碼
				DiscountCodeVO dcVO = dcr.findById(dtoUpdate.getDiscountCodeId())
						.orElseThrow(() -> new RuntimeException("查無折扣碼"));
				sovo.setDiscountCodeId(dcVO);

				// 取得discountCodeType
				Byte type = dcVO.getDiscountType();
				// 取得discountValue
				BigDecimal value = dcVO.getDiscountValue();

				if (type == 0) { // 固定金額折扣
					sovo.setDiscountAmount(value.intValue());
					// 計算實付金額 = 折扣前金額 - 折扣金額 + 運費 
					afterDiscountAmount = beforeDiscountAmount.subtract(value).add(shipFee);
				} else if (type == 1) { // 百分比折扣
					// 折扣前金額*(1 - discountValue(%))並四捨五入
					BigDecimal discountAmount = beforeDiscountAmount.multiply(value)
							.setScale(0, RoundingMode.HALF_UP);
					sovo.setDiscountAmount(discountAmount.intValue());
					// 計算實付金額 = 折扣前金額 - 折扣金額 + 運費 
					afterDiscountAmount = beforeDiscountAmount.subtract(discountAmount).add(shipFee);
				} else {
					throw new RuntimeException("無效的折扣類型");
				}
				sovo.setAfterDiscountAmount(afterDiscountAmount.intValue());
			} else {
				// 刪除折扣碼
				sovo.setDiscountCodeId(null);
				sovo.setDiscountAmount(null);
				afterDiscountAmount = beforeDiscountAmount.add(shipFee);
				sovo.setAfterDiscountAmount(afterDiscountAmount.intValue());
			}
		} else {
			// 未傳入 discountCodeId，保持原有折扣碼不變
			if (sovo.getDiscountCodeId() != null) {
				DiscountCodeVO dcVO = sovo.getDiscountCodeId();
				Byte type = dcVO.getDiscountType();
				BigDecimal value = dcVO.getDiscountValue();

				if (type == 0) {
					sovo.setDiscountAmount(value.intValue());
					afterDiscountAmount = beforeDiscountAmount.subtract(value).add(shipFee);
				} else if (type == 1) {
					BigDecimal discountAmount = beforeDiscountAmount.multiply(BigDecimal.ONE.subtract(value))
							.setScale(0, RoundingMode.HALF_UP);
					sovo.setDiscountAmount(discountAmount.intValue());
					afterDiscountAmount = beforeDiscountAmount.subtract(discountAmount).add(shipFee);
				}
				sovo.setAfterDiscountAmount(afterDiscountAmount.intValue());
			} else {
				sovo.setDiscountAmount(null);
				afterDiscountAmount = beforeDiscountAmount.add(shipFee);
				sovo.setAfterDiscountAmount(afterDiscountAmount.intValue());
			}
		}

		if (dtoUpdate.getShopOrderPayment() != null) {
			sovo.setShopOrderPayment(dtoUpdate.getShopOrderPayment());
		}

		if (dtoUpdate.getOrderName() != null) {
			sovo.setOrderName(dtoUpdate.getOrderName());
		}

		if (dtoUpdate.getOrderEmail() != null) {
			sovo.setOrderEmail(dtoUpdate.getOrderEmail());
		}

		if (dtoUpdate.getOrderPhone() != null) {
			sovo.setOrderPhone(dtoUpdate.getOrderPhone());
		}

		if (dtoUpdate.getOrderShippingAddress() != null) {
			sovo.setOrderShippingAddress(dtoUpdate.getOrderShippingAddress());
		}

		if (dtoUpdate.getShopOrderNote() != null) {
			sovo.setShopOrderNote(dtoUpdate.getShopOrderNote());
		}

		if (dtoUpdate.getShopOrderShipDate() != null) {
			sovo.setShopOrderShipDate(dtoUpdate.getShopOrderShipDate());
		}

		if (dtoUpdate.getShopOrderStatus() != null) {
			// 如果ShopReturnApply不是未申請退貨(0)就不能進行修改
			if (sovo.getShopReturnApply() == 0) {
				// 判斷ShopOrderStatus輸入範圍
				if (dtoUpdate.getShopOrderStatus() >= 0 && dtoUpdate.getShopOrderStatus() <= 6) {
					sovo.setShopOrderStatus(dtoUpdate.getShopOrderStatus());
				} else {
					throw new IllegalArgumentException("0: 等待付款中 1: 已取消 2: 等待賣家確認中 3: 準備出貨中 4: 已出貨 5: 未取貨，退回賣家 ");
				}
			} else {
				throw new IllegalArgumentException("商品訂單已進行退貨申請流程");
			}
		}

		if (dtoUpdate.getShopReturnApply() != null) {
			// ShopOrderStatus要在5(已取貨)才有資格申請退貨
			if (sovo.getShopOrderStatus() != null && sovo.getShopOrderStatus() == 5) {

				// ReturnApply介於0~3之間
				if (dtoUpdate.getShopReturnApply() >= 0 && dtoUpdate.getShopReturnApply() <= 3) {
					sovo.setShopReturnApply(dtoUpdate.getShopReturnApply());
				} else {
					throw new IllegalArgumentException("0: 未申請退貨 1: 申請退貨 2: 退貨成功 3: 退貨失敗");
				}
			} else {
				throw new IllegalArgumentException("確認商品訂單是否為已出貨狀態");
			}
		}

		sor.save(sovo);
		ShopOrderVO sovo2 = getOneShopOrder(sovo.getShopOrderId());
		return sovo2;
	}

	public ShopOrderVO getOneShopOrder(Integer shopOrderId) {
		return sor.findById(shopOrderId).orElseThrow(() -> new RuntimeException("查無此筆訂單資料"));
		// public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}

	public List<ShopOrderVO> getAll() {
		return sor.findAll();
	}

	public List<ShopOrderVO> getAll(MemberVO memId) {
		return sor.findByMember(memId);
	}

}
