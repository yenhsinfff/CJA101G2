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
import com.lutu.specList.model.SpecListDTO;
import com.lutu.specList.model.SpecListService;

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
    private SpecListService specListService;
    
    @Autowired
    private ColorListService colorListService;

    @Autowired
    private ProdPicService prodPicService;
    
    // 查詢所有商品✅
    public List<ShopProdDTO> getAllProds() {
        List<ShopProdVO> voList = repository.findAll(); // 撈上下架商品
        List<ShopProdDTO> dtoList = new ArrayList<>();
        for (ShopProdVO vo : voList) {
            dtoList.add(convertToDTO(vo));
        }
        return dtoList;
    }
    // 給前台使用，僅查上架商品✅
    public List<ShopProdDTO> getAvailableProds() {
        List<ShopProdVO> voList = repository.findByStatus(1); // 只撈上架商品
        List<ShopProdDTO> dtoList = new ArrayList<>();
        for (ShopProdVO vo : voList) {
            dtoList.add(convertToDTOByStatus1(vo)); // 用只撈上架規格/顏色的方法
        }
        return dtoList;
    }


    // 查詢單筆產品✅ for product-detail.html
    public ShopProdDTO getProdById(Integer prodId) {
        Optional<ShopProdVO> optional = repository.findById(prodId);
        if (optional.isPresent()) {
            ShopProdVO vo = optional.get();
            return convertToDTOByStatus1(vo); // 用只撈上架規格/顏色的方法
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

    // 類別查詢✅
    public List<ShopProdDTO> getByType(Integer typeId) {
        List<ShopProdVO> voList = repository.findByProdType(typeId);
        List<ShopProdDTO> dtoList = new ArrayList<>();
        for (ShopProdVO vo : voList) {
            dtoList.add(convertToDTOByStatus1(vo));
        }
        return dtoList;
    }

    // 最新上架✅
    public List<ShopProdDTO> getLatestProds(int limit) {
        List<ShopProdVO> voList = repository.findByReleaseDateDesc(limit);
        List<ShopProdDTO> dtoList = new ArrayList<>();
        for (ShopProdVO vo : voList) {
            dtoList.add(convertToDTOByStatus1(vo));
        }
        return dtoList;
    }

    // 折扣商品✅
    public List<ShopProdDTO> getDiscountProds() {
        List<ShopProdVO> voList = repository.findByDiscounted();
        List<ShopProdDTO> dtoList = new ArrayList<>();
        for (ShopProdVO vo : voList) {
            dtoList.add(convertToDTOByStatus1(vo));
        }
        return dtoList;
    }

    // 隨機推薦✅
    public List<ShopProdDTO> getRandomProds(int limit) {
        List<ShopProdVO> voList = repository.findRandom(limit);
        List<ShopProdDTO> dtoList = new ArrayList<>();
        for (ShopProdVO vo : voList) {
            dtoList.add(convertToDTOByStatus1(vo));
        }
        return dtoList;
    }

    //價格由低到高排序✅
    public List<ShopProdDTO> getProductsByPriceAsc() {
        List<ShopProdVO> voList = repository.findAllByPriceAsc();
        List<ShopProdDTO> dtoList = new ArrayList<>();

        for (ShopProdVO vo : voList) {
            ShopProdDTO dto = convertToDTOByStatus1(vo);
            dtoList.add(dto);
        }

        return dtoList;
    }

    //價格由高到低排序✅
    public List<ShopProdDTO> getProductsByPriceDesc() {
        List<ShopProdVO> voList = repository.findAllByPriceDesc();
        List<ShopProdDTO> dtoList = new ArrayList<>();

        for (ShopProdVO vo : voList) {
            ShopProdDTO dto = convertToDTOByStatus1(vo);
            dtoList.add(dto);
        }

        return dtoList;
    }
    
    //價格區間查詢 (5000+)
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
//                voList = repository.findAll(); // 不限價格
                voList = repository.findByStatus(1); // 僅查上架商品
                break;
        }

        List<ShopProdDTO> dtoList = new ArrayList<>();
        for (ShopProdVO vo : voList) {
            dtoList.add(convertToDTOByStatus1(vo)); 
        }
        return dtoList;
    }

    // 新增商品（新增規格與顏色，上架狀態控制）
    public ShopProdDTO addProd(ShopProdDTO dto) {
        ShopProdVO vo = convertToVO(dto);
        repository.save(vo); 	

        Integer prodId = vo.getProdId(); // 先存商品主表以取得 prodId
        
        // 商品圖片初始化（建立 4 張空白圖）
        prodPicService.initProductImages(prodId);
        
        // 商品規格統一由 updateProdSpecs() 處理（會新增 + 設為上架）
        if (dto.getProdSpecList() != null && !dto.getProdSpecList().isEmpty()) {
            for (ProdSpecListDTO specDTO : dto.getProdSpecList()) {
            	 // 若為新規格（specId 為 null 或 0），先新增至 spec_list
                if (specDTO.getProdSpecId() == null || specDTO.getProdSpecId() == 0) {
                    SpecListDTO newSpec = new SpecListDTO();
                    newSpec.setSpecName(specDTO.getProdSpecName()); // 從 DTO 取新名稱
                    SpecListDTO savedSpec = specListService.saveOrUpdate(newSpec); // 儲存到 spec_list
                    specDTO.setProdSpecId(savedSpec.getSpecId()); // 回填 ID
                    specDTO.setProdSpecName(savedSpec.getSpecName());
                }
                
                specDTO.setProdId(prodId);
            }
            prodSpecListService.updateProdSpecs(prodId, dto.getProdSpecList());
        }

        // 商品顏色處理（含新增 colorList）
        if (dto.getProdColorList() != null && !dto.getProdColorList().isEmpty()) {
            for (ProdColorListDTO colorDTO : dto.getProdColorList()) {
            	 // 若為新顏色（colorId 為 null 或 0），先新增至 color_list
                if (colorDTO.getProdColorId() == null || colorDTO.getProdColorId() == 0) {
                    ColorListDTO newColor = new ColorListDTO();
                    newColor.setColorName(colorDTO.getColorName());
                    ColorListDTO savedColor = colorListService.saveOrUpdate(newColor);
                    colorDTO.setProdColorId(savedColor.getColorId());
                    colorDTO.setColorName(savedColor.getColorName());
                }
                colorDTO.setProdId(prodId);
            }
            prodColorListService.updateProdColors(prodId, dto.getProdColorList());
        }
        
        return convertToDTO(vo);
    }

    
    // 修改編輯商品（含更新規格與顏色狀態）
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

            // 更新商品類別
            if (dto.getProdTypeId() != null) {
                ProdTypeVO type = new ProdTypeVO();
                type.setProdTypeId(dto.getProdTypeId());
                vo.setProdTypeVO(type);
            }

            Integer prodId = vo.getProdId();
            // 單一規格處理
            if (dto.getProdSpecList() != null && dto.getProdSpecList().size() == 1 
            		&& dto.getProdSpecList().get(0).getProdSpecId() == 1) {
//                prodSpecListService.deleteByProdId(prodId); // 刪除全部舊的
                prodSpecListService.updateProdSpecs(prodId, new ArrayList<>()); // 傳空陣列代表全部下架

                ProdSpecListDTO singleSpec = dto.getProdSpecList().get(0); // 加入單一規格
                
                // 若是新規格名稱，先新增 spec_list
                if (singleSpec.getProdSpecId() == null || singleSpec.getProdSpecId() == 0) {
                    SpecListDTO newSpec = new SpecListDTO();
                    newSpec.setSpecName(singleSpec.getProdSpecName());
                    SpecListDTO savedSpec = specListService.saveOrUpdate(newSpec);
                    singleSpec.setProdSpecId(savedSpec.getSpecId());
                    singleSpec.setProdSpecName(savedSpec.getSpecName());
                }
                
                singleSpec.setProdId(prodId);
                singleSpec.setProdSpecStatus((byte) 1);
                prodSpecListService.updateProdSpecs(prodId, List.of(singleSpec));
            } else {
            	// 多規格更新由 prodSpecListService 判斷新增 / 上架 / 下架
                for (ProdSpecListDTO spec : dto.getProdSpecList()) {
                	
                	// 檢查是否為新規格，需先新增至 spec_list
                    if (spec.getProdSpecId() == null || spec.getProdSpecId() == 0) {
                        SpecListDTO newSpec = new SpecListDTO();
                        newSpec.setSpecName(spec.getProdSpecName());
                        SpecListDTO savedSpec = specListService.saveOrUpdate(newSpec);
                        spec.setProdSpecId(savedSpec.getSpecId());
                        spec.setProdSpecName(savedSpec.getSpecName());
                    }
                    
                    spec.setProdId(prodId);
                    spec.setOriginalSpecId(spec.getOriginalSpecId());
                }
                prodSpecListService.updateProdSpecs(prodId, dto.getProdSpecList());
            }

            // 單一顏色處理
            if (vo.getProdColorOrNot() == 0) {
//                prodColorListService.deleteByProdId(prodId);
                prodColorListService.updateProdColors(prodId, new ArrayList<>()); // 傳空陣列代表全部下架
                ProdColorListDTO singleColor = new ProdColorListDTO(); // 加入單一顏色
                singleColor.setProdId(prodId);
                singleColor.setProdColorId(1);
                singleColor.setColorName("單一顏色");
                singleColor.setProdColorStatus((byte) 1);
                prodColorListService.updateProdColors(prodId, List.of(singleColor));
            } else {
            	// 多顏色處理：新增新顏色名稱並更新關聯資料
                for (ProdColorListDTO color : dto.getProdColorList()) {
                	
                    if (color.getProdColorId() == null || color.getProdColorId() == 0) {
                        ColorListDTO newColor = new ColorListDTO();
                        newColor.setColorName(color.getColorName());
                        ColorListDTO savedColor = colorListService.saveOrUpdate(newColor);
                        color.setProdColorId(savedColor.getColorId());
                        color.setColorName(savedColor.getColorName());
                    }
                    
                    color.setProdId(prodId);
                    color.setOriginalColorId(color.getOriginalColorId());
                }
                prodColorListService.updateProdColors(prodId, dto.getProdColorList());
            }
            
            repository.save(vo);
            return convertToDTO(vo);
        } else {
            return null;
        }
    }

    // VO ➜ DTO 轉換 (撈取全部上下架的 商品規格顏色)
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
        
//        dto.setProdSpecList(prodSpecListService.getProdSpecsByProdId(vo.getProdId()));
//        dto.setProdColorList(prodColorListService.getProdColorsByProdId(vo.getProdId()));
        // 只撈上架的規格與顏色
        dto.setProdSpecList(prodSpecListService.getActiveProdSpecsByProdId(vo.getProdId()));
        dto.setProdColorList(prodColorListService.getActiveProdColorsByProdId(vo.getProdId()));
        //取得規格顏色名稱
        dto.setSpecList(prodSpecListService.getAllSpecNames());
        dto.setColorList(prodColorListService.getAllColorNames());
        //取得商品圖片
        dto.setProdPicList(prodPicService.getByProdId(vo.getProdId()));


        return dto;
    }
    // 只給前台上架的 商品 規格 顏色 
    private ShopProdDTO convertToDTOByStatus1(ShopProdVO vo) {
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

        // 只撈上架的規格與顏色
        dto.setProdSpecList(prodSpecListService.getActiveProdSpecsByProdId(vo.getProdId()));
        dto.setProdColorList(prodColorListService.getActiveProdColorsByProdId(vo.getProdId()));

        // 顏色名稱 / 規格名稱 / 圖片
        dto.setSpecList(prodSpecListService.getAllSpecNames());
        dto.setColorList(prodColorListService.getAllColorNames());
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
