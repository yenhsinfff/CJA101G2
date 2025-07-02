package com.lutu.campsite_available.model;

import com.lutu.campsitetype.model.CampsiteTypeRepository;
import com.lutu.campsitetype.model.CampsiteTypeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("campsiteAvailableService")
public class CampsiteAvailableService {

    @Autowired
    private CampsiteAvailableRepository availRepo;

    @Autowired
    private CampsiteTypeRepository typeRepo;
    
    
    @Transactional
    public List<CampsiteTypeAvailableDTO> ensureAndQueryRemaing(
            List<Integer> campIds,
            Integer people,
            Date checkIn,
            Date checkOut) {
    	
    	// 1) 獲取所有目標房型（多個營地）
        List<CampsiteTypeVO> targetTypes = new ArrayList<>();
        
        if (campIds == null || campIds.isEmpty()) {
            // 如果沒有指定營地，獲取所有房型
            targetTypes = typeRepo.findAll();
        } else {
            // 獲取指定多個營地的房型
            for (Integer campId : campIds) {
                List<CampsiteTypeVO> typesForCamp = typeRepo.findByIdCampId(campId);
                targetTypes.addAll(typesForCamp);
            }
        }

        // 2) 過濾符合入住人數的房型
//        List<CampsiteTypeVO> filteredTypes = targetTypes.stream()
//            .filter(t -> t.getCampsitePeople() >= people) // 入住人數條件
//            .collect(Collectors.toList());

        // 3) 逐日逐房型檢查房況
        for (CampsiteTypeVO t : targetTypes) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(checkIn);

            while (cal.getTime().before(checkOut)) {
                Date d = new Date(cal.getTimeInMillis());
                CampsiteAvailableVO.CACompositeDetail id =
                    new CampsiteAvailableVO.CACompositeDetail(t.getId().getCampsiteTypeId(), d);

                Optional<CampsiteAvailableVO> exists = availRepo.findById(id);
                if (!exists.isPresent()) {
                    CampsiteAvailableVO ra = new CampsiteAvailableVO();
                    ra.setId(id);
                    ra.setCampId(t.getId().getCampId());
                    ra.setRemaining(t.getCampsiteNum());
                    availRepo.save(ra);
                }
                cal.add(Calendar.DATE, 1);
            }
        }

        List<CampsiteTypeAvailableDTO> result = new ArrayList<>();
        if (campIds == null || campIds.isEmpty()) {
            result = availRepo.findAvailableRoomTypesWithRemaining(
                null, people, checkIn, checkOut
            );
        } else {
            for (Integer campId : campIds) {
                result.addAll(availRepo.findAvailableRoomTypesWithRemaining(
                    campId, people, checkIn, checkOut
                ));
            }
        }
        return result;
    }

    
    @Transactional
    public List<CampsiteTypeVO> ensureAndQuery(List<Integer> campIds, 
                                               Integer people, // 新增入住人數參數
                                               Date checkIn,
                                               Date checkOut) {
    	
    	System.out.println(campIds);

        // 1) 獲取所有目標房型（多個營地）
        List<CampsiteTypeVO> targetTypes = new ArrayList<>();
        
        if (campIds == null || campIds.isEmpty()) {
            // 如果沒有指定營地，獲取所有房型
            targetTypes = typeRepo.findAll();
        } else {
            // 獲取指定多個營地的房型
            for (Integer campId : campIds) {
                List<CampsiteTypeVO> typesForCamp = typeRepo.findByIdCampId(campId);
                targetTypes.addAll(typesForCamp);
            }
        }

        // 2) 過濾符合入住人數的房型
//        List<CampsiteTypeVO> filteredTypes = targetTypes.stream()
//            .filter(t -> t.getCampsitePeople() >= people) // 入住人數條件
//            .collect(Collectors.toList());

        // 3) 逐日逐房型檢查房況
        for (CampsiteTypeVO t : targetTypes) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(checkIn);

            while (cal.getTime().before(checkOut)) {
                Date d = new Date(cal.getTimeInMillis());
                CampsiteAvailableVO.CACompositeDetail id =
                    new CampsiteAvailableVO.CACompositeDetail(t.getId().getCampsiteTypeId(), d);

                Optional<CampsiteAvailableVO> exists = availRepo.findById(id);
                if (!exists.isPresent()) {
                    CampsiteAvailableVO ra = new CampsiteAvailableVO();
                    ra.setId(id);
                    ra.setCampId(t.getId().getCampId());
                    ra.setRemaining(t.getCampsiteNum());
                    availRepo.save(ra);
                }
                cal.add(Calendar.DATE, 1);
            }
        }

        // 4) 查出所有符合條件的房型
        List<CampsiteTypeVO> result = new ArrayList<>();
        for (Integer campId : campIds) {
        	System.out.println("campId:"+campId);
            List<CampsiteTypeVO> availableForCamp = availRepo.findAvailableRoomTypes(
                campId, 
                people, // 傳入住人數
                checkIn, 
                checkOut
            );
            result.addAll(availableForCamp);
        }
//      	System.out.println("result:"+result);
        
        // 如果沒有指定營地，查詢所有營地
        if (campIds == null || campIds.isEmpty()) {
            result = availRepo.findAvailableRoomTypes(
                null, 
                people, // 傳入住人數
                checkIn, 
                checkOut
            );
        }

        return result;
    }

    /**
     * 1. 指定日期 + 營地，建立當日所有房型的初始庫存
     */
    @Transactional
    public void initByCamp(Date date, Integer campId) {

        List<CampsiteTypeVO> types = typeRepo.findByIdCampId(campId);
        if (types == null || types.isEmpty()) {
            throw new IllegalArgumentException("查無此營地或無房型");
        }

        for (CampsiteTypeVO t : types) {
        	CampsiteAvailableVO.CACompositeDetail id = new CampsiteAvailableVO.CACompositeDetail(t.getId().getCampsiteTypeId(), date);

            Optional<CampsiteAvailableVO> existing = availRepo.findById(id);
            if (!existing.isPresent()) {
            	CampsiteAvailableVO entity = new CampsiteAvailableVO();
                entity.setId(id);
                entity.setRemaining(t.getCampsiteNum());
                availRepo.save(entity);
            }
        }
    }

    /**
     * 2. 只給日期，為所有房型建立當日庫存
     */
    @Transactional
    public void initByDate(Date date) {

        List<CampsiteTypeVO> types = typeRepo.findAll();

        for (CampsiteTypeVO t : types) {
           	CampsiteAvailableVO.CACompositeDetail id = new CampsiteAvailableVO.CACompositeDetail(t.getId().getCampsiteTypeId(), date);

            Optional<CampsiteAvailableVO> existing = availRepo.findById(id);
            if (!existing.isPresent()) {
            	CampsiteAvailableVO entity = new CampsiteAvailableVO();
                entity.setId(id);
                entity.setRemaining(t.getCampsiteNum());
                availRepo.save(entity);
            }
        }
    }
}
