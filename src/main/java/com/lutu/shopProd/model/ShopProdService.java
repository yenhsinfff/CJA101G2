package com.lutu.shopProd.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lutu.prodColorList.model.ProdColorListDTO;
import com.lutu.prodColorList.model.ProdColorListService;
import com.lutu.prodSpecList.model.ProdSpecListDTO;
import com.lutu.prodSpecList.model.ProdSpecListService;
import com.lutu.productType.model.ProdTypeVO;

@Transactional
@Service
public class ShopProdService {

    @Autowired
    ShopProdRepository repository;

    @Autowired
    private ProdSpecListService prodSpecListService;

    @Autowired
    private ProdColorListService prodColorListService;

    // 查詢所有商品
    public List<ShopProdDTO> getAllProds() {
        List<ShopProdVO> voList = repository.findAll();
        List<ShopProdDTO> dtoList = new ArrayList<>();
        for (ShopProdVO vo : voList) {
            dtoList.add(convertToDTO(vo));
        }
        return dtoList;
    }

    // 查詢單筆產品
    public ShopProdDTO getProdById(Integer prodId) {
        Optional<ShopProdVO> optional = repository.findById(prodId);
        if (optional.isPresent()) {
            ShopProdVO vo = optional.get();
            return convertToDTO(vo);
        } else {
            return null;
        }
    }

    // 關鍵字查詢
    public List<ShopProdDTO> getByKeyword(String keyword) {
        List<ShopProdVO> voList = repository.findByKeyword(keyword);
        List<ShopProdDTO> dtoList = new ArrayList<>();
        for (ShopProdVO vo : voList) {
            dtoList.add(convertToDTO(vo));
        }
        return dtoList;
    }

    // 類別查詢
    public List<ShopProdDTO> getByType(Integer typeId) {
        List<ShopProdVO> voList = repository.findByProdType(typeId);
        List<ShopProdDTO> dtoList = new ArrayList<>();
        for (ShopProdVO vo : voList) {
            dtoList.add(convertToDTO(vo));
        }
        return dtoList;
    }

    // 最新上架
    public List<ShopProdDTO> getLatestProds() {
        List<ShopProdVO> voList = repository.findByReleaseDateDesc();
        List<ShopProdDTO> dtoList = new ArrayList<>();
        for (ShopProdVO vo : voList) {
            dtoList.add(convertToDTO(vo));
        }
        return dtoList;
    }

    // 折扣商品
    public List<ShopProdDTO> getDiscountProds() {
        List<ShopProdVO> voList = repository.findByDiscounted();
        List<ShopProdDTO> dtoList = new ArrayList<>();
        for (ShopProdVO vo : voList) {
            dtoList.add(convertToDTO(vo));
        }
        return dtoList;
    }

    // 隨機推薦
    public List<ShopProdDTO> getRandomProds(int limit) {
        List<ShopProdVO> voList = repository.findRandom(limit);
        List<ShopProdDTO> dtoList = new ArrayList<>();
        for (ShopProdVO vo : voList) {
            dtoList.add(convertToDTO(vo));
        }
        return dtoList;
    }

    // 新增 
    public ShopProdDTO addProd(ShopProdDTO dto) {
        ShopProdVO vo = convertToVO(dto);
        repository.save(vo);

        // 🔁 新增每一筆商品規格資料
        if (dto.getProdSpecList() != null) {
            for (ProdSpecListDTO specDTO : dto.getProdSpecList()) {
                specDTO.setProdId(vo.getProdId());
                prodSpecListService.saveOrUpdate(specDTO);
            }
        }

        // 🔁 新增每一筆商品顏色資料
        if (dto.getProdColorList() != null) {
            for (ProdColorListDTO colorDTO : dto.getProdColorList()) {
                colorDTO.setProdId(vo.getProdId());
                prodColorListService.saveOrUpdate(colorDTO);
            }
        }

        return convertToDTO(vo);
    }

    // 修改
    public ShopProdDTO updateProd(ShopProdDTO dto) {
        Optional<ShopProdVO> opt = repository.findById(dto.getProdId());
        if (opt.isPresent()) {
            ShopProdVO vo = opt.get();
            vo.setProdName(dto.getProdName());
            vo.setProdIntro(dto.getProdIntro());
            vo.setProdDiscount(dto.getProdDiscount());
            vo.setProdDiscountStart(dto.getProdDiscountStart());
            vo.setProdDiscountEnd(dto.getProdDiscountEnd());
            vo.setProdCommentCount(dto.getProdCommentCount());
            vo.setProdCommentSumScore(dto.getProdCommentSumScore());
            vo.setProdStatus(dto.getProdStatus());
            vo.setProdColorOrNot(dto.getProdColorOrNot());

            if (dto.getProdTypeId() != null) {
                ProdTypeVO type = new ProdTypeVO();
                type.setProdTypeId(dto.getProdTypeId());
                vo.setProdTypeVO(type);
            }

            if (dto.getProdSpecList() != null) {
                for (ProdSpecListDTO specDTO : dto.getProdSpecList()) {
                    specDTO.setProdId(vo.getProdId());
                    prodSpecListService.saveOrUpdate(specDTO);
                }
            }

            if (dto.getProdColorList() != null) {
                for (ProdColorListDTO colorDTO : dto.getProdColorList()) {
                    colorDTO.setProdId(vo.getProdId());
                    prodColorListService.saveOrUpdate(colorDTO);
                }
            }

            repository.save(vo);
            return convertToDTO(vo);
        } else {
            return null;
        }
    }

    // VO ➜ DTO 轉換
    private ShopProdDTO convertToDTO(ShopProdVO vo) {
        ShopProdDTO dto = new ShopProdDTO();
        dto.setProdId(vo.getProdId());
        dto.setProdName(vo.getProdName());
        dto.setProdIntro(vo.getProdIntro());
        dto.setProdReleaseDate(vo.getProdReleaseDate());
        dto.setProdDiscount(vo.getProdDiscount());
        dto.setProdDiscountStart(vo.getProdDiscountStart());
        dto.setProdDiscountEnd(vo.getProdDiscountEnd());
        dto.setProdCommentCount(vo.getProdCommentCount());
        dto.setProdCommentSumScore(vo.getProdCommentSumScore());

        if (vo.getProdTypeVO() != null) {
            dto.setProdTypeId(vo.getProdTypeVO().getProdTypeId());
            dto.setProdTypeName(vo.getProdTypeVO().getProdTypeName());
        }

        dto.setProdStatus(vo.getProdStatus());
        dto.setProdColorOrNot(vo.getProdColorOrNot());

        List<ProdSpecListDTO> specs = prodSpecListService.getProdSpecsByProdId(vo.getProdId());
        dto.setProdSpecList(specs);

        List<ProdColorListDTO> colors = prodColorListService.getProdColorsByProdId(vo.getProdId());
        dto.setProdColorList(colors);

        return dto;
    }

    // DTO ➜ VO 轉換
    private ShopProdVO convertToVO(ShopProdDTO dto) {
        ShopProdVO vo = new ShopProdVO();
        vo.setProdId(dto.getProdId());
        vo.setProdName(dto.getProdName());
        vo.setProdIntro(dto.getProdIntro());
        vo.setProdReleaseDate(dto.getProdReleaseDate());
        vo.setProdDiscount(dto.getProdDiscount());
        vo.setProdDiscountStart(dto.getProdDiscountStart());
        vo.setProdDiscountEnd(dto.getProdDiscountEnd());
        vo.setProdCommentCount(dto.getProdCommentCount());
        vo.setProdCommentSumScore(dto.getProdCommentSumScore());
        vo.setProdStatus(dto.getProdStatus());
        vo.setProdColorOrNot(dto.getProdColorOrNot());

        if (dto.getProdTypeId() != null) {
            ProdTypeVO type = new ProdTypeVO();
            type.setProdTypeId(dto.getProdTypeId());
            vo.setProdTypeVO(type);
        }

        return vo;
    }
} 
