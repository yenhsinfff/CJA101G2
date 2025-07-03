package com.lutu;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lutu.camp.model.CampService;
import com.lutu.camp.model.CampVO;
import com.lutu.campsite_available.model.CampsiteAvailableService;
import com.lutu.campsite_order.model.CampSiteOrderService;
import com.lutu.campsite_order.model.CampSiteOrderVO;
import com.lutu.campsite_order.model.CampsiteOrderDTO;
import com.lutu.shop_order.model.ShopOrderService;
import com.lutu.campsite_order.model.CampSiteOrderService;
import com.lutu.campsite_order.model.CampSiteOrderVO;
import com.lutu.shop_order.model.ShopOrderDTO_insert;
import com.lutu.shop_order.model.ShopOrderService;
import com.lutu.shop_order.model.ShopOrderVO;

import com.lutu.util.HmacUtil;

import jakarta.servlet.http.HttpServletResponse;
import javassist.runtime.DotClass;


import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.UUID;
//api格式 「http://localhost:8081/CJA101G02/api/campsite_orders」

@RestController
@CrossOrigin(origins = "*")
public class ApiController {

	@Autowired
	CampSiteOrderService campsiteOrdSvc;

	@Autowired
	CampService campService;
	
	@Autowired
	ShopOrderService shopOrderSvc;

	
	@Autowired
	CampsiteAvailableService caService;


	@Transactional
	@PostMapping("/api/linepay/{isCamp}")
	public ApiResponse<String> doLinePay(@PathVariable Boolean isCamp, @RequestBody String jsonBody,
			HttpServletResponse response) throws IOException, JSONException {
		final String channelId = "1656895462";
		final String CHANNEL_SECRET = "fd01e635b9ea97323acbe8d5c6b2fb71";
		final String API_URL = "https://sandbox-api-pay.line.me/v3/payments/request";

		// 解析原本的 JSON
		JSONObject body = new JSONObject(jsonBody);
		JSONObject linepayBody = body.optJSONObject("linepayBody");
		JSONObject linepayOrder = body.optJSONObject("linepayOrder");
		System.out.println("jsonBody:" + body);
		System.out.println("linepayBody:" + linepayBody);
		System.out.println("linepayOrder:" + linepayOrder);


		// 先儲存訂單，生成 shopOrderId
		String orderId = null;
		if (!isCamp) { // 屬於商城訂單
			System.out.println("Shop");
			ShopOrderDTO_insert dto = shopOrderSvc.jsonToDTO(linepayOrder);
			ShopOrderVO shopOrder = shopOrderSvc.addShopOrder(dto);
			orderId = "SHOP" + shopOrder.getShopOrderId(); // 將 Integer 轉為 String
			// 驗證金額
			if (linepayBody.getInt("amount") != shopOrder.getAfterDiscountAmount()) {
				shopOrderSvc.updatePaymentStatus(shopOrder.getShopOrderId(), (byte) 6); // 6: 付款失敗
				 return new ApiResponse<>("fail", null, "金額不一致");
			}
			
			// 更新 linepayBody 的 orderId 和 confirmUrl
			linepayBody.put("orderId", orderId);
			linepayBody.getJSONObject("redirectUrls").put("confirmUrl", 
					linepayBody.getJSONObject("redirectUrls")
					.getString("confirmUrl").replace("{orderId}", orderId));
		}
		

		// 產生 HMAC 簽章
		String nonce = UUID.randomUUID().toString();
		String uri = "/v3/payments/request";
		String signatureRaw = CHANNEL_SECRET + uri + linepayBody.toString() + nonce;
		String signature = HmacUtil.hmacSHA256Base64(CHANNEL_SECRET, signatureRaw);

		// 發送 HTTP POST 請求到 LINE Pay
		HttpURLConnection conn = (HttpURLConnection) new URL(API_URL).openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("X-LINE-ChannelId", channelId);
		conn.setRequestProperty("X-LINE-Authorization", signature);
		conn.setRequestProperty("X-LINE-Authorization-Nonce", nonce);
		conn.setDoOutput(true);
		try (OutputStream os = conn.getOutputStream()) {
			os.write(linepayBody.toString().getBytes("UTF-8"));
		}
		// 取得回應
		StringBuilder responseLinePay = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
			String respLine;
			while ((respLine = br.readLine()) != null)
				responseLinePay.append(respLine);
		}
		JSONObject resJson = new JSONObject(responseLinePay.toString());
		System.out.println("resJson:" + resJson);
		JSONObject info = resJson.optJSONObject("info");
		String returnCode = resJson.getString("returnCode");

		if (returnCode.equals("0000")) {
			// 付款網址請求成功，將訂單資料(未付款)塞入DB
			if (isCamp) {
				System.out.println("Camp");
				campsiteOrdSvc.createOneCampOrderJson(linepayOrder);
			} else {
				System.out.println("Shop");

			}

			System.out.println("aaaaaa");
			String paymentUrl = (info != null && info.has("paymentUrl"))
					? info.getJSONObject("paymentUrl").getString("web")
					: null;

			// 回傳 paymentUrl 給前端
			return new ApiResponse<>("success", paymentUrl, "查詢成功");
		} else {
			
			if (!isCamp) {//針對商品訂單
                shopOrderSvc.updatePaymentStatus(Integer.parseInt(orderId.replace("SHOP", "")), (byte) 6); //6: 付款失敗
            }
			// 交易失敗
			return new ApiResponse<>("fail", "fail", "查詢失敗");
		}

	}

	// 確認LINEPAY付款狀態
	@GetMapping("api/confirmpayment/{orderId}/{isCamp}")
	public void checkLinePayStatus(@PathVariable String orderId, @PathVariable Boolean isCamp,
			HttpServletResponse responseServlet) throws IOException, URISyntaxException, JSONException {
		final String channelId = "1656895462";
		final String CHANNEL_SECRET = "fd01e635b9ea97323acbe8d5c6b2fb71";
		final String API_URL = "https://sandbox-api-pay.line.me/v2/payments/orders/" + orderId + "/check";

		// 發送 HTTP POST 請求到 LINE Pay
		HttpURLConnection conn = (HttpURLConnection) new URL(API_URL).openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("X-LINE-ChannelId", channelId);
		conn.setRequestProperty("X-LINE-ChannelSecret", CHANNEL_SECRET);

		// 發送request
		int responseCode = conn.getResponseCode();
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		String inputLine;
		StringBuilder response = new StringBuilder();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// 解析 JSON 字串
		JSONObject json = new JSONObject(response.toString());
		String returnCode = json.getString("returnCode");
		String returnMessage = json.getString("returnMessage");
		System.out.println("LINEPAY交易(" + orderId + "): " + returnCode + "||" + returnMessage);
		if (returnCode.equals("0000")) {
			// 交易成功，將訂單狀態改成已付款塞入DB
			if (isCamp) {
				System.out.println("Camp");

				campsiteOrdSvc.updatePaymentStatus(orderId, (byte)1);
				CampsiteOrderDTO dto = campsiteOrdSvc.getOneDTOCampsiteOrder(orderId);
				Boolean response1 = caService.deductRoomsByDateRange(dto.getCheckIn(),dto.getCheckOut(), dto.getOrderDetails());
				System.out.println("linpay_response:"+response1);
				
				responseServlet.sendRedirect("http://127.0.0.1:5501/linepay-success.html?orderId=" + orderId + "&isCamp=" + isCamp);

			} else {
				System.out.println("Shop");
				Integer shopOrderId = Integer.parseInt(orderId.replace("SHOP", ""));
				shopOrderSvc.updatePaymentStatus(shopOrderId, (byte) 1);;
				responseServlet.sendRedirect(
						"http://127.0.0.1:5503/linepay-success-shop.html?orderId=" + orderId + "&isCamp=" + isCamp);
			}

		} else {
			// 交易失敗
			if (isCamp) {
			responseServlet
					.sendRedirect("http://127.0.0.1:5501/linepay-cancel.html?orderId=" + orderId + "&isCamp=" + isCamp);
			} else {
				responseServlet
				.sendRedirect("http://127.0.0.1:5503/linepay-cancel-shop.html?orderId=" + orderId + "&isCamp=" + isCamp);
			}
		}
	}

	// 取得所有營地訂單，回傳 JSON
//	@GetMapping("/api/getallcamps")
//	public ApiResponse<List<CampVO>> getAllCamps() {
//		List<CampVO> camps = campService.getAllCamp();
//		return new ApiResponse<>("success", camps, "查詢成功");
//	}

//	@PostMapping("/api/createonecamp")
//	public ApiResponse<Boolean> createOneCamp(@RequestParam("ownerId") Integer ownerId,
//			@RequestParam("campName") String campName, @RequestParam("campContent") String campContent,
//			@RequestParam("campCity") String campCity, @RequestParam("campDist") String campDist,
//			@RequestParam("campAddr") String campAddr, @RequestParam("campReleaseStatus") Byte campReleaseStatus,
//			@RequestParam("campCommentNumberCount") Integer campCommentNumberCount,
//			@RequestParam("campCommentSumScore") Integer campCommentSumScore,
//			@RequestParam("campRegDate") String campRegDate, // yyyy-MM-dd
//			@RequestPart("campPic1") MultipartFile campPic1, @RequestPart("campPic2") MultipartFile campPic2,
//			@RequestPart(value = "campPic3", required = false) MultipartFile campPic3,
//			@RequestPart(value = "campPic4", required = false) MultipartFile campPic4) {
//		CampVO camp = new CampVO();
//		try {
//			camp.setOwnerId(ownerId);
//			camp.setCampName(campName);
//			camp.setCampContent(campContent);
//			camp.setCampCity(campCity);
//			camp.setCampDist(campDist);
//			camp.setCampAddr(campAddr);
//			camp.setCampReleaseStatus(campReleaseStatus);
//			camp.setCampCommentNumberCount(campCommentNumberCount);
//			camp.setCampCommentSumScore(campCommentSumScore);
//			camp.setCampRegDate(java.sql.Date.valueOf(campRegDate));
//			camp.setCampPic1(campPic1.getBytes());
//			camp.setCampPic2(campPic2.getBytes());
//			if (campPic3 != null)
//				camp.setCampPic3(campPic3.getBytes());
//			if (campPic4 != null)
//				camp.setCampPic4(campPic4.getBytes());
//
//			CampVO newCampVO = campService.createOneCamp(camp);
//			return new ApiResponse<>("success", true, "查詢成功");
//		} catch (Exception e) {
//			System.out.println("createOneCamp_err:"+e);
//			return new ApiResponse<>("fail", false, "查詢失敗");
//		}
//
//	}

	// 取得所有營地訂單，回傳 JSON
	@GetMapping("/api/campsite_orders")
	public ApiResponse<List<CampSiteOrderVO>> getAllCampsiteOrders() {
		List<CampSiteOrderVO> orders = campsiteOrdSvc.getAllCampsiteOrder();
		return new ApiResponse<>("success", orders, "查詢成功");
	}

	@GetMapping("/api/camps/{campId}/pic1")
	public void getCampPic1(@PathVariable Integer campId, HttpServletResponse response) throws IOException {

		byte[] img = (campService.getOneCamp(campId)).getCampPic1(); // 從資料庫取得

		response.setContentType("image/jpeg");
		response.getOutputStream().write(img);
	}


}
