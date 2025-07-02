package com.lutu.shopProd.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lutu.colorList.model.ColorListDTO;
import com.lutu.colorList.model.ColorListService;
import com.lutu.prodColorList.model.ProdColorListDTO;
import com.lutu.prodColorList.model.ProdColorListService;
import com.lutu.prodPic.model.ProdPicService;
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
    
    @Autowired
    private ProdPicService prodPicService;
    
    @Autowired
    private ColorListService colorListService;

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

    //價格由低到高排序
    public List<ShopProdDTO> getProductsByPriceAsc() {
        List<ShopProdVO> voList = repository.findAllByPriceAsc();
        List<ShopProdDTO> dtoList = new ArrayList<>();

        for (ShopProdVO vo : voList) {
            ShopProdDTO dto = convertToDTO(vo);
            dtoList.add(dto);
        }

        return dtoList;
    }

    //價格由高到低排序
    public List<ShopProdDTO> getProductsByPriceDesc() {
        List<ShopProdVO> voList = repository.findAllByPriceDesc();
        List<ShopProdDTO> dtoList = new ArrayList<>();

        for (ShopProdVO vo : voList) {
            ShopProdDTO dto = convertToDTO(vo);
            dtoList.add(dto);
        }

        return dtoList;
    }
    
    //價格區間查詢
    public List<ShopProdDTO> getByPriceRange(String range) {
        List<ShopProdVO> voList = new ArrayList<>();

        switch (range) {
            case "0-1000":
                voList = repository.findByPriceBetween(0, 1000);
                break;
            case "1000-3000":
                voList = repository.findByPriceBetween(1000, 3000);
                break;
            case "3000-5000":
                voList = repository.findByPriceBetween(3000, 5000);
                break;
            case "5000+":
                voList = repository.findByPriceMoreThan(5000);
                break;
            default:
                voList = repository.findAll(); // 不限價格
                break;
        }

        List<ShopProdDTO> dtoList = new ArrayList<>();
        for (ShopProdVO vo : voList) {
            dtoList.add(convertToDTO(vo)); 
        }
        return dtoList;
    }

    // 新增商品
    public ShopProdDTO addProd(ShopProdDTO dto) {
        ShopProdVO vo = convertToVO(dto);
        repository.save(vo);

        // 新增每一筆商品規格資料
        if (dto.getProdSpecList() != null) {
            for (ProdSpecListDTO specDTO : dto.getProdSpecList()) {
                specDTO.setProdId(vo.getProdId());
                prodSpecListService.saveOrUpdate(specDTO);
            }
        }

        // 新增每一筆商品顏色資料
        if (dto.getProdColorList() != null) {
            for (ProdColorListDTO colorDTO : dto.getProdColorList()) {

                // 若 colorId 為 null 或 0，表示為新顏色，先存 color_list
                if (colorDTO.getProdColorId() == null || colorDTO.getProdColorId() == 0) {
	            	// 傳入前端送來的新顏色名稱
	            	ColorListDTO newColorDTO = new ColorListDTO();
	            	newColorDTO.setColorName(colorDTO.getColorName()); //從DTO取名稱
	
	            	// 新增顏色到 color_list 表
	            	ColorListDTO savedColor = colorListService.saveOrUpdate(newColorDTO);
	
	            	// 回填 colorId 給後續 prodColorList 用
	            	colorDTO.setProdColorId(savedColor.getColorId());
	            	colorDTO.setColorName(savedColor.getColorName());
                }
                
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
/*
        // 加入規格與顏色
        List<ProdSpecListDTO> specs = prodSpecListService.getProdSpecsByProdId(vo.getProdId());
        dto.setProdSpecList(specs);
        List<ProdColorListDTO> colors = prodColorListService.getProdColorsByProdId(vo.getProdId());
        dto.setProdColorList(colors);
        // 加入圖片清單
        List<ProdPicDTO> pics = prodPicService.getByProdId(vo.getProdId());
        dto.setProdPicList(pics);
*/
        dto.setProdSpecList(prodSpecListService.getProdSpecsByProdId(vo.getProdId()));
        dto.setProdColorList(prodColorListService.getProdColorsByProdId(vo.getProdId()));
        //取得規格顏色名稱
        dto.setSpecList(prodSpecListService.getAllSpecNames());
        dto.setColorList(prodColorListService.getAllColorNames());
        //取得商品圖片
        dto.setProdPicList(prodPicService.getByProdId(vo.getProdId()));


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
