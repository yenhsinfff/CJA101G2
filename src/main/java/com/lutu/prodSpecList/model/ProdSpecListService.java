package com.lutu.prodSpecList.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lutu.prodSpecList.model.ProdSpecListVO.CompositeDetail2;
import com.lutu.specList.model.SpecListDTO;
import com.lutu.specList.model.SpecListRepository;
import com.lutu.specList.model.SpecListVO;

@Service
@Transactional
public class ProdSpecListService {

    @Autowired
    private ProdSpecListRepository repository;
    
    @Autowired
    private SpecListRepository specListRepository;
    
    @Autowired
    private ProdSpecListRepository prodSpecListRepository;

    /* 查所有規格 */
    public List<ProdSpecListDTO> getAllProdSpecs() {
        List<ProdSpecListVO> voList = repository.findAll();
        List<ProdSpecListDTO> dtoList = new ArrayList<>();

        for (ProdSpecListVO vo : voList) {
            dtoList.add(toDTO(vo));
        }
        return dtoList;
    }

    /* 查某商品所有商品規格 */
    public List<ProdSpecListDTO> getProdSpecsByProdId(Integer prodId) {
        List<ProdSpecListVO> voList = repository.findAllByProdId(prodId);
        List<ProdSpecListDTO> dtoList = new ArrayList<>();

        for (ProdSpecListVO vo : voList) {
            dtoList.add(toDTO(vo));
        }
        return dtoList;
    }

    /* 查單筆商品規格 */
    public ProdSpecListDTO getOne(Integer prodId, Integer specId) {
        Optional<ProdSpecListVO> opt = repository.findById(new CompositeDetail2(prodId, specId));
        if (opt.isPresent()) {
            return toDTO(opt.get());
        } else {
            return null;
        }
    }

    //取得規格名稱
    public List<SpecListDTO> getAllSpecNames() {
        List<SpecListVO> list = specListRepository.findAll();
        List<SpecListDTO> result = new ArrayList<>();

        for (SpecListVO vo : list) {
            SpecListDTO dto = new SpecListDTO();
            dto.setSpecId(vo.getSpecId());
            dto.setSpecName(vo.getSpecName());
            result.add(dto);
        }
        return result;
    }
    
    // 只查上架的商品規格
    public List<ProdSpecListDTO> getActiveProdSpecsByProdId(Integer prodId) {
        List<ProdSpecListVO> voList = repository.findActiveSpecsByProdId(prodId);
        List<ProdSpecListDTO> dtoList = new ArrayList<>();

        for (ProdSpecListVO vo : voList) {
            dtoList.add(toDTO(vo));
        }
        return dtoList;
    }

    
    // *更新商品規格狀態
    public void updateProdSpecs(Integer prodId, List<ProdSpecListDTO> newSpecDTOs) {
        // 取得目前商品所有規格（含上下架）
        List<ProdSpecListVO> currentSpecs = repository.findAllByProdId(prodId);

        // 建立目前已有的規格 ID 集合
        Set<Integer> currentSpecIds = new HashSet<>();
        for (ProdSpecListVO vo : currentSpecs) {
            currentSpecIds.add(vo.getCompositeKey().getProdSpecId());
        }

        // 將這次傳入的有效規格建立清單
        Set<Integer> newSpecIds = new HashSet<>();
        List<ProdSpecListDTO> validSpecs = new ArrayList<>();

        // 檢查是否選擇了單一規格（假設 ID = 1）
        boolean hasSingleSpec = newSpecDTOs.stream()
            .anyMatch(dto -> dto.getProdSpecId() != null && dto.getProdSpecId() == 1);

        for (ProdSpecListDTO dto : newSpecDTOs) {
            Integer specId = dto.getProdSpecId();
            if (hasSingleSpec && specId != 1) {
                continue; // 有單一規格就跳過其他
            }

            // 記錄本次出現的 ID
            newSpecIds.add(specId);
            validSpecs.add(dto);
        }

        // 處理每個新的或更新的規格
        for (ProdSpecListDTO dto : validSpecs) {
            Integer specId = dto.getProdSpecId();
            ProdSpecListVO existing = repository.findByCompositeKey(prodId, specId);

            if (existing == null) {
                // 新增（只新增主鍵、價格、狀態）
                ProdSpecListVO newVO = new ProdSpecListVO();
                newVO.setCompositeKey(new CompositeDetail2(prodId, specId));
                newVO.setProdSpecPrice(dto.getProdSpecPrice());
                newVO.setProdSpecStatus((byte) 1);
                repository.save(newVO);
            } else {
                // 已存在 → 更新價格 & 設為上架
                existing.setProdSpecPrice(dto.getProdSpecPrice());
                existing.setProdSpecStatus((byte) 1);
                repository.save(existing);
            }
        }

        // 將舊有但未被保留的 → 設為下架
        for (ProdSpecListVO vo : currentSpecs) {
            Integer oldSpecId = vo.getCompositeKey().getProdSpecId();
            if (!newSpecIds.contains(oldSpecId)) {
                vo.setProdSpecStatus((byte) 0); // 下架，不刪除
                repository.save(vo);
            }
        }
    }




    // 刪除 by ProdId
 	public void deleteByProdId(Integer prodId) {
 	    List<ProdSpecListVO> list = repository.findAllByProdId(prodId);
 	    for (ProdSpecListVO vo : list) {
 	        repository.deleteById(new CompositeDetail2(vo.getProdId(), vo.getProdSpecId()));
 	    }
 	}
 	

    
    /* ======== 轉換工具 ======== */
    // VO -> DTO
    private ProdSpecListDTO toDTO(ProdSpecListVO vo) {
        ProdSpecListDTO dto = new ProdSpecListDTO();
        dto.setProdId(vo.getProdId());
        dto.setProdSpecId(vo.getProdSpecId());
        dto.setProdSpecPrice(vo.getProdSpecPrice());
        dto.setProdSpecStatus(vo.getProdSpecStatus());

        // 顯示規格名稱
        if (vo.getSpecListVO() != null) {
            dto.setProdSpecName(vo.getSpecListVO().getSpecName());
        }

        return dto;
    }


    // DTO -> VO
    private ProdSpecListVO toVO(ProdSpecListDTO dto) {
        ProdSpecListVO vo = new ProdSpecListVO();
        vo.setProdId(dto.getProdId());
        vo.setProdSpecId(dto.getProdSpecId());
        vo.setProdSpecPrice(dto.getProdSpecPrice());
        vo.setProdSpecStatus(dto.getProdSpecStatus());
        return vo;
    }
}
