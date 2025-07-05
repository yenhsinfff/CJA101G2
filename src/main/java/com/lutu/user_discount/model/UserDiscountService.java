package com.lutu.user_discount.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lutu.discount_code.model.DiscountCodeRepository;
import com.lutu.discount_code.model.DiscountCodeVO;
import com.lutu.member.model.MemberRepository;
import com.lutu.member.model.MemberVO;

import jakarta.transaction.Transactional;

@Service("userDiscountService")
public class UserDiscountService {

	@Autowired
	private UserDiscountRepository repository;
	
	@Autowired
	private DiscountCodeRepository discountRepo;
	
	@Autowired
	private MemberRepository memberRepo;

//	@Autowired
//	private SessionFactory sessionFactory;

	// 新增
	public void addUserDiscount(UserDiscountVO userDiscountVO) {
		repository.save(userDiscountVO);
	}

	// 更新
	public void updateUserDiscount(UserDiscountVO userDiscountVO) {
		repository.save(userDiscountVO);

	}

	// 新增單一會員折價券，作為「管理員發送折價券給會員」
	@Transactional
	public UserDiscountDTO sendDiscountToUser(UserDiscountDTO_request dto) {

	    // 1. 檢查是否已發送過
	    Optional<UserDiscountVO> existing = repository.findByIdMemIdAndIdDiscountCodeId(dto.getMemId(), dto.getDiscountCodeId());
	    if (existing.isPresent()) {
	        throw new RuntimeException("該會員已領取此折價券");
	    }

	    // 2. 查找資料
	    DiscountCodeVO discount = discountRepo.findById(dto.getDiscountCodeId())
	            .orElseThrow(() -> new RuntimeException("查無折價券"));
	    MemberVO member = memberRepo.findById(dto.getMemId())
	            .orElseThrow(() -> new RuntimeException("查無會員"));

	    // 3.建立UserDiscountVO
	    UserDiscountVO userDiscount = new UserDiscountVO();
	    UserDiscountVO.CompositeDetail id = new UserDiscountVO.CompositeDetail();
	    id.setDiscountCodeId(dto.getDiscountCodeId());
	    id.setMemId(dto.getMemId());
	    userDiscount.setId(id);
	    userDiscount.setUsedAt(null);

	    repository.save(userDiscount);

	    // 將結果回傳 DTO 
	    UserDiscountDTO responseDto = new UserDiscountDTO();
	    responseDto.setMemId(member.getMemId());
	    responseDto.setDiscountCodeId(discount.getDiscountCodeId());
	    responseDto.setDiscountCode(discount.getDiscountCode());
	    responseDto.setMinOrderAmount(discount.getMinOrderAmount());
	    responseDto.setDiscountType(discount.getDiscountType());
	    responseDto.setDiscountValue(discount.getDiscountValue());
	    responseDto.setStartDate(discount.getStartDate());
	    responseDto.setEndDate(discount.getEndDate());
	    responseDto.setUsedAt(null); // 初始未使用

	    return responseDto;
	}
	
	//新增所有會員折價券
	@Transactional
	public void sendDiscountToAllMembers(String discountCodeId) {
	    DiscountCodeVO discount = discountRepo.findById(discountCodeId)
	        .orElseThrow(() -> new RuntimeException("查無折價券"));
	    
	    List<MemberVO> allMembers = memberRepo.findAll();

	    for (MemberVO member : allMembers) {
	        Optional<UserDiscountVO> existing = repository.findByIdMemIdAndIdDiscountCodeId(member.getMemId(), discountCodeId);
	        if (existing.isPresent()) {
	            continue; // 已有就跳過
	        }

	        UserDiscountVO userDiscount = new UserDiscountVO();
	        UserDiscountVO.CompositeDetail id = new UserDiscountVO.CompositeDetail();
	        id.setDiscountCodeId(discountCodeId);
	        id.setMemId(member.getMemId());
	        userDiscount.setId(id);
	        userDiscount.setUsedAt(null);

	        repository.save(userDiscount);
	    }
	}

	// 刪除（必須傳入複合主鍵）
//	public UserDiscountVO getOneUserDiscount(Integer memId) {
//		Optional<UserDiscountVO> optional = repository.findById(id);
//		return optional.orElse(null);	
//	}

	// 查詢特定會員擁有的折扣碼，傳入dto
	public List<UserDiscountDTO> getDiscountsByMemberId(Integer memId) {
		List<UserDiscountVO> voList = repository.findByIdMemId(memId);


		// 將 VO 轉為 DTO
		return voList.stream().map(vo -> {
			DiscountCodeVO discount = vo.getDiscountCodeVO(); // 從userDiscount抓出discount資料

			return new UserDiscountDTO(vo.getId().getMemId(), vo.getId().getDiscountCodeId(),
					discount.getDiscountCode(), discount.getMinOrderAmount(), discount.getDiscountType(),
					discount.getDiscountValue(), discount.getStartDate(), discount.getEndDate(), vo.getUsedAt());
		}).collect(Collectors.toList());

    }
  
	// 查詢會員未使用過的折扣
	public List<UserDiscountDTO> getNotUsedByMemberId(Integer memId) {
		List<UserDiscountVO> voList = repository.findByIdMemId(memId);
		
		 // 將 VO 轉為 DTO
		List<UserDiscountDTO> dtoList = new ArrayList<>();
		
		for (UserDiscountVO vo : voList) {
			if (vo.getUsedAt() == null) {
				UserDiscountDTO dto = new UserDiscountDTO();
				dto.setDiscountCode(vo.getDiscountCodeVO().getDiscountCode());
				dto.setDiscountCodeId(vo.getDiscountCodeVO().getDiscountCodeId());
				dto.setEndDate(vo.getDiscountCodeVO().getEndDate());
				dto.setMemId(memId);
				dto.setMinOrderAmount(vo.getDiscountCodeVO().getMinOrderAmount());
				dto.setStartDate(vo.getDiscountCodeVO().getStartDate());
				dto.setUsedAt(vo.getUsedAt());
				
				dtoList.add(dto);
			}
		}
		
		return dtoList;
	}

	// 查詢所有
	public List<UserDiscountVO> getAll() {
		return repository.findAll();
	}

	// 🔍 可擴充自訂查詢，例如查某會員所有折扣記錄：
	public List<UserDiscountVO> getByMemId(Integer memId) {
		return repository.findByIdMemId(memId);
	}

	public Optional<UserDiscountVO> getById(UserDiscountVO.CompositeDetail compositeDetail) {
		return repository.findById(compositeDetail);
	}

}
