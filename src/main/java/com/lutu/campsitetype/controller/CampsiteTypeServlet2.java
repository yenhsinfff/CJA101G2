package com.lutu.campsitetype.controller;

import java.util.LinkedList;
import java.util.List;

import com.lutu.campsitetype.model.CampsiteTypeService;
import com.lutu.campsitetype.model.CampsiteTypeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/back-end/campsiteType")
public class CampsiteTypeServlet2 {

    @Autowired
    private CampsiteTypeService campsiteTypeSvc;
    
    @GetMapping("/select_page")
    public String index(Model model) {
    	return "back-end/campsiteType/select_page";
    }
    
    // 查詢單筆資料
    @GetMapping("/getOne")
    public String getOneForDisplay(@RequestParam("campsiteTypeId") String str, Model model) {
        List<String> errorMsgs = new LinkedList<>();

        if (str == null || str.trim().isEmpty()) {
            errorMsgs.add("請輸入房型編號");
            model.addAttribute("errorMsgs", errorMsgs);
            return "back-end/campsiteType/select_page";
        }

        Integer campsiteTypeId = null;
        try {
            campsiteTypeId = Integer.valueOf(str.trim());
        } catch (NumberFormatException e) {
            errorMsgs.add("營地房型編號格式不正確");
            model.addAttribute("errorMsgs", errorMsgs);
            return "back-end/campsiteType/select_page";
        }

        CampsiteTypeVO campsiteTypeVO = campsiteTypeSvc.getOneCampsiteType(campsiteTypeId);
        if (campsiteTypeVO == null) {
            errorMsgs.add("查無資料");
            model.addAttribute("errorMsgs", errorMsgs);
            return "back-end/campsiteType/select_page";
        }

        model.addAttribute("campsiteTypeVO", campsiteTypeVO);
        return "back-end/campsiteType/listOneCampsiteType";
    }

    // 前往更新頁面
    @GetMapping("/updatePage")
    public String getOneForUpdate(@RequestParam("campsiteTypeId") Integer campsiteTypeId, Model model) {
        CampsiteTypeVO campsiteTypeVO = campsiteTypeSvc.getOneCampsiteType(campsiteTypeId);
        model.addAttribute("campsiteTypeVO", campsiteTypeVO);
        return "back-end/campsiteType/update_campsiteType_input";
    }

    // 更新資料
    @PostMapping("/update")
    public String update(@ModelAttribute CampsiteTypeVO form, Model model) {
    	System.out.println("update:"+form.getCampsiteName());
    	List<String> errorMsgs = validateForm(form, true);
        if (!errorMsgs.isEmpty()) {
            model.addAttribute("campsiteTypeVO", form);
            model.addAttribute("errorMsgs", errorMsgs);
            return "back-end/campsiteType/update_campsiteType_input";
        }

        byte[] dummyPic = "fake image bytes".getBytes();
        form.setCampsitePic1(dummyPic);
        form.setCampsitePic2(dummyPic);
        form.setCampsitePic3(dummyPic);
        form.setCampsitePic4(dummyPic);

        campsiteTypeSvc.updateCampsiteType(
                form.getCampsiteTypeId(), form.getCampId(), form.getCampsiteName(),
                form.getCampsitePeople(), form.getCampsiteNum(), form.getCampsitePrice(),
                dummyPic, dummyPic, dummyPic, dummyPic
        );

        CampsiteTypeVO updatedVO = campsiteTypeSvc.getOneCampsiteType(form.getCampsiteTypeId());
        model.addAttribute("campsiteTypeVO", updatedVO);
        return "back-end/campsiteType/listOneCampsiteType";
    }

    // 新增資料
    @PostMapping("/insert")
    public String insert(@ModelAttribute CampsiteTypeVO form, Model model) {
    	System.out.println("bbbbb:"+form.getCampsiteName());
        List<String> errorMsgs = validateForm(form, false);
        if (!errorMsgs.isEmpty()) {
            model.addAttribute("campsiteTypeVO", form);
            model.addAttribute("errorMsgs", errorMsgs);
            return "back-end/campsiteType/addCampsiteType";
        }

        byte[] dummyPic = "fake image bytes".getBytes();
        form.setCampsitePic1(dummyPic);
        form.setCampsitePic2(dummyPic);
        form.setCampsitePic3(dummyPic);
        form.setCampsitePic4(dummyPic);

        campsiteTypeSvc.addCampsiteType(
                form.getCampId(), form.getCampsiteName(),
                form.getCampsitePeople(), form.getCampsiteNum(), form.getCampsitePrice(),
                dummyPic, dummyPic, dummyPic, dummyPic
        );

        return "back-end/campsiteType/listAllcampsiteType";
    }

    // 刪除資料
    @PostMapping("/delete")
    public String delete(@RequestParam("campsiteTypeId") Integer campsiteTypeId) {
        campsiteTypeSvc.deleteCampsiteType(campsiteTypeId);
        return "back-end/campsiteType/listAllCampsiteType";
    }

    // 共用驗證邏輯
    private List<String> validateForm(CampsiteTypeVO form, boolean isUpdate) {
        List<String> errorMsgs = new LinkedList<>();

        String name = form.getCampsiteName();
        String reg = "^[\u4e00-\u9fa5a-zA-Z0-9_]{3,20}$";
        if (name == null || name.trim().isEmpty()) {
            errorMsgs.add("營地房型名稱不得為空");
        } else if (!name.matches(reg)) {
            errorMsgs.add("營地房型名稱: 只能是中、英文字母、數字和_ , 且長度必需在3到20之間");
        }

        if (form.getCampsitePeople() == null || form.getCampsitePeople() <= 0) {
            errorMsgs.add("可入住人數須大於0人");
        }

        if (form.getCampsiteNum() == null || form.getCampsiteNum() < 0) {
            errorMsgs.add("房間數量不得為空，需為正整數且最少為0間");
        }

        if (form.getCampsitePrice() == null || form.getCampsitePrice() <= 0) {
            errorMsgs.add("房型價格不得為空，且為正整數");
        }

        return errorMsgs;
    }
}
