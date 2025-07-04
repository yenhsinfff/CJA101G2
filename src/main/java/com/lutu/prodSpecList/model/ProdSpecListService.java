package com.lutu.prodSpecList.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        List<ProdSpecListVO> voList = repository.findByProdId(prodId);
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
    
    /* 新增或修改商品規格*/
    public ProdSpecListDTO saveOrUpdate(ProdSpecListDTO dto) {
        // 如果原始的 specId 存在，且與目前要儲存的不一樣
        if (dto.getProdId() != null && dto.getOriginalSpecId() != null &&
            !dto.getOriginalSpecId().equals(dto.getProdSpecId())) {
            
            // 主鍵要變動 → 先刪舊的
            repository.deleteById(new CompositeDetail2(dto.getProdId(), dto.getOriginalSpecId()));
            
            // 建立新實體再存
            return toDTO(repository.save(toVO(dto)));
        }

        // 若是原始的 key（主鍵）沒變動，就是單純更新價格
        Optional<ProdSpecListVO> existing = repository.findById(new CompositeDetail2(dto.getProdId(), dto.getProdSpecId()));
        if (existing.isPresent()) {
            ProdSpecListVO vo = existing.get();
            vo.setProdSpecPrice(dto.getProdSpecPrice());
            return toDTO(repository.save(vo));
        }

        // 若是新增的，直接存
        return toDTO(repository.save(toVO(dto)));
    }



    // 刪除 by ProdId
 	public void deleteByProdId(Integer prodId) {
 	    List<ProdSpecListVO> list = repository.findByProdId(prodId);
 	    for (ProdSpecListVO vo : list) {
 	        repository.deleteById(new CompositeDetail2(vo.getProdId(), vo.getProdSpecId()));
 	    }
 	}
 	
 	
    /* 刪除 */
    public void delete(Integer prodId, Integer specId) {
        repository.deleteById(new CompositeDetail2(prodId, specId));
    }

    
    /* ======== 轉換工具 ======== */
    // VO -> DTO
    private ProdSpecListDTO toDTO(ProdSpecListVO vo) {
        ProdSpecListDTO dto = new ProdSpecListDTO();
        dto.setProdId(vo.getProdId());
        dto.setProdSpecId(vo.getProdSpecId());
        dto.setProdSpecPrice(vo.getProdSpecPrice());

        // 顯示規格名稱
        if (vo.getSpecListVO() != null) {
            dto.setProdSpecName(vo.getSpecListVO().getSpecName());
        }

        // 記錄原本的 prodSpecId，供修改時辨識用
        dto.setOriginalSpecId(vo.getProdSpecId());

        return dto;
    }


    // DTO -> VO
    private ProdSpecListVO toVO(ProdSpecListDTO dto) {
        ProdSpecListVO vo = new ProdSpecListVO();
        vo.setProdId(dto.getProdId());
        vo.setProdSpecId(dto.getProdSpecId());
        vo.setProdSpecPrice(dto.getProdSpecPrice());
        return vo;
    }
}
