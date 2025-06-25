package com.lutu.campsite_order.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lutu.camp.model.CampVO;
import com.lutu.campsite_order_details.model.CampSiteOrderDetailsVO;
import com.lutu.member.model.MemberVO;

import jakarta.transaction.Transactional;

@Service("campsiteOrderService")
public class CampSiteOrderService {

	@Autowired
	CampSiteOrderRepository campSiteOrderRepository;

	@Autowired
	private SessionFactory sessionFactory;
	

	public List<CampSiteOrderVO> getAllCampsiteOrder() {

		return campSiteOrderRepository.findAll();

	}
	
	public String generateCampsiteOrderId() {
		List<CampSiteOrderVO> campSiteOrderList= getAllCampsiteOrder();
		
		// 1. 今天日期
        String today = new SimpleDateFormat("yyyyMMdd").format(new java.util.Date());
        String prefix = "ORD" + today;

        // 2. 找今天最大流水號
        int maxSeq = 0;
        for (CampSiteOrderVO vo : campSiteOrderList) {
            String orderId = vo.getCampsiteOrderId();
            if (orderId != null && orderId.startsWith(prefix)) {
                String seqStr = orderId.substring(prefix.length());
                if (seqStr.matches("\\d{3}")) {
                    int seq = Integer.parseInt(seqStr);
                    if (seq > maxSeq) maxSeq = seq;
                }
            }
        }

        // 3. 新流水號
        int newSeq = maxSeq + 1;
        String newOrderId = String.format("%s%03d", prefix, newSeq);

        // 4. 再次確認不重複（理論上不會重複，但可加保險）
        boolean exists = false;
        for (CampSiteOrderVO vo : campSiteOrderList) {
            if (newOrderId.equals(vo.getCampsiteOrderId())) {
                exists = true;
                break;
            }
        }
        while (exists) {
            newSeq++;
            newOrderId = String.format("%s%03d", prefix, newSeq);
            exists = false;
            for (CampSiteOrderVO vo : campSiteOrderList) {
                if (newOrderId.equals(vo.getCampsiteOrderId())) {
                    exists = true;
                    break;
                }
            }}
        System.out.println("newOrderId:"+newOrderId);
        return newOrderId;
	}
	
//	@Transactional
	public CampSiteOrderVO getOneCampsiteOrder(String campSiteOrderId) {

		CampSiteOrderVO campSiteOrderVO = campSiteOrderRepository.findByCampSiteOrderId(campSiteOrderId);
//		if (campSiteOrderVO != null) {
//			campSiteOrderVO.getCampSiteOrderDetails().size(); // 強制初始化
//		}
		return campSiteOrderVO;

	}
	
	public CampSiteOrderVO createOneCampOrder(CampSiteOrderVO campSiteOrderVO) {
		campSiteOrderRepository.save(campSiteOrderVO);
    	CampSiteOrderVO thiscampSiteOrderVO = getOneCampsiteOrder(campSiteOrderVO.getCampsiteOrderId());
    	return thiscampSiteOrderVO;
	}
	
	public CampSiteOrderVO createOneCampOrderJson(JSONObject orderJson) {
		CampSiteOrderVO vo = convertToVO(orderJson);
		campSiteOrderRepository.save(vo);
		System.out.println("成功上傳資料");
		String orderIdString= orderJson.getString("orderId");
    	CampSiteOrderVO thiscampSiteOrderVO = getOneCampsiteOrder(orderIdString);
    	return thiscampSiteOrderVO;
	}
	
	 // DTO 轉 VO 的方法
    private CampSiteOrderVO convertToVO(JSONObject orderJson) {
        CampSiteOrderVO vo = new CampSiteOrderVO();
        vo.setCampsiteOrderId(orderJson.getString("orderId"));
        vo.setOrderDate(Timestamp.from(Instant.parse(orderJson.getString("orderDate"))));
        vo.setCampsiteOrderStatus((byte) orderJson.getInt("orderStatus"));
        vo.setPayMethod((byte) orderJson.getInt("payMethod"));
        vo.setBundleAmount(orderJson.getInt("bundleAmount"));
        vo.setCampsiteAmount(orderJson.getInt("campsiteAmount"));
        vo.setBefAmount(orderJson.getInt("befAmount"));
        vo.setDisAmount(orderJson.getInt("disAmount"));
        vo.setAftAmount(orderJson.getInt("aftAmount"));
        vo.setCheckIn(Date.valueOf(orderJson.getString("checkIn")));
        vo.setCheckOut(Date.valueOf(orderJson.getString("checkOut")));
        vo.setCommentSatisfaction(orderJson.getInt("satisfaction"));
        vo.setCommentContent(orderJson.getString("content"));
        if (orderJson.getString("date") != null && !orderJson.getString("date").isEmpty()) {
            vo.setCommentDate(Timestamp.valueOf(orderJson.getString("date")));
        }
        // 關聯 CampVO
        CampVO camp = new CampVO();
        camp.setCampId(Integer.parseInt(orderJson.getString("campId")));
        camp.setCampName(orderJson.getString("campName"));
        vo.setCampVO(camp);
        
        // 關聯 MemberVO
        MemberVO member = new MemberVO();
        member.setMemId(orderJson.getInt("memId"));
        vo.setMemberVO(member);
        // 折價券
        vo.setDiscountCodeId(orderJson.getString("discountCodeId"));
        // 訂單明細
        Set<CampSiteOrderDetailsVO> details = new HashSet<>();
       

        JSONArray detailArray = orderJson.getJSONArray("details");
        for (int i = 0; i < detailArray.length(); i++) {
            JSONObject obj = detailArray.getJSONObject(i);
            CampSiteOrderDetailsVO detail = new CampSiteOrderDetailsVO();
            detail.setcampSiteOrderVO(vo);
            detail.setCampsiteAmount(obj.getInt("campsiteAmount"));
            detail.setCampsiteNum(obj.getInt("campsiteNum"));
            // 如果 campsiteTypeId 是字串，要轉成 int
            detail.setCampsiteTypeId(Integer.parseInt(obj.getString("campsiteTypeId")));
            System.out.println(obj.getInt("campsiteAmount")+"||"+obj.getInt("campsiteNum")+"||"+obj.getString("campsiteTypeId"));
            details.add(detail);
        }
        
        vo.setCampSiteOrderDetails(details);

        
        return vo;
    }
    
    public Boolean updatePaymentStatus(String orderId,Byte status) {
    	try {
    	CampSiteOrderVO vo = campSiteOrderRepository.findByCampSiteOrderId(orderId);
    	vo.setCampsiteOrderId(orderId);
    	vo.setCampsiteOrderStatus(status);
    	campSiteOrderRepository.save(vo);
    	System.out.println(orderId+"完成狀態更新:"+status);
    	return true;
    	}catch (Exception e) {
			System.out.println("updatePaymentStatus_err"+e);
			return false;
		}
	}

}
