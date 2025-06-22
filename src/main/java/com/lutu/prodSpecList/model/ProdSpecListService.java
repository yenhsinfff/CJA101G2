package com.lutu.prodSpecList.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lutu.prodSpecList.model.ProdSpecListVO.CompositeDetail2;

@Service
@Transactional
public class ProdSpecListService {

    @Autowired
    private ProdSpecListRepository repository;

    /* 新增 / 修改（同一支） */
    public ProdSpecDTO saveOrUpdate(ProdSpecDTO dto) {
        ProdSpecListVO vo = toVO(dto);
        ProdSpecListVO saved = repository.save(vo);
        return toDTO(saved);
    }

    /* 刪除 */
//    public void delete(Integer prodId, Integer specId) {
//    	repository.deleteById(new CompositeDetail2(prodId, specId));
//    }

    /* 查單筆 */
    public ProdSpecDTO getOne(Integer prodId, Integer specId) {
        Optional<ProdSpecListVO> opt = repository.findById(new CompositeDetail2(prodId, specId));
        if (opt.isPresent()) {
            return toDTO(opt.get());
        } else {
            return null;
        }
    }

    /* 查該商品全部規格 */
    public List<ProdSpecDTO> findByProd(Integer prodId) {
        List<ProdSpecListVO> list = repository.findByProdId(prodId);
        List<ProdSpecDTO> dtoList = new ArrayList<>();

        for (ProdSpecListVO vo : list) {
            dtoList.add(toDTO(vo));
        }

        return dtoList;
    }

    /* ======== 轉換工具 ======== */
    private ProdSpecDTO toDTO(ProdSpecListVO vo) {
        ProdSpecDTO dto = new ProdSpecDTO();
        dto.setProdId(vo.getProdId());
        dto.setProdSpecId(vo.getProdSpecId());
        dto.setProdSpecPrice(vo.getProdSpecPrice());
        return dto;
    }

    private ProdSpecListVO toVO(ProdSpecDTO dto) {
        ProdSpecListVO vo = new ProdSpecListVO();
        vo.setProdId(dto.getProdId());
        vo.setProdSpecId(dto.getProdSpecId());
        vo.setProdSpecPrice(dto.getProdSpecPrice());
        return vo;
    }
}
