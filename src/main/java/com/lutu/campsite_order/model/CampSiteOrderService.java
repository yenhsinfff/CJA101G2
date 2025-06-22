package com.lutu.campsite_order.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lutu.camp.model.CampVO;

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
        String today = new SimpleDateFormat("yyyyMMdd").format(new Date());
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

}
