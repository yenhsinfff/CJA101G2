package com.lutu;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lutu.camp.model.CampService;
import com.lutu.camp.model.CampVO;
import com.lutu.campsite_order.model.CampSiteOrderService;
import com.lutu.campsite_order.model.CampSiteOrderVO;
import com.lutu.util.HmacUtil;

import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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

	@GetMapping("/api/camps/linepay")
	public void doLinePay( HttpServletResponse response) throws IOException {
		final String channelId = "1656895462";
		final String CHANNEL_SECRET = "fd01e635b9ea97323acbe8d5c6b2fb71";
		final String API_URL = "https://sandbox-api-pay.line.me/v3/payments/request";
		final String CONFIRM_URL = "http://127.0.0.1:5501/linepay-success.html";
		final String CANCEL_URL = "http://127.0.0.1:5501/linepay-cancel.html";
		
		// 2. 組裝 LINE Pay 請求內容
        JSONObject body = new JSONObject();
        body.put("amount", 2000);
        body.put("currency", "TWD");
        body.put("orderId", UUID.randomUUID().toString());
        JSONArray packages = new JSONArray();
        JSONObject pkg = new JSONObject();
        pkg.put("id", "pkg-001");
        pkg.put("amount", 2000);
        JSONArray products = new JSONArray();
        JSONObject product = new JSONObject();
        product.put("name", "皮皮商品");
        product.put("quantity", 2);
        product.put("price", 1000);
        products.put(product);
        pkg.put("products", products);
        packages.put(pkg);
        body.put("packages", packages);

        JSONObject redirectUrls = new JSONObject();
        redirectUrls.put("confirmUrl", CONFIRM_URL);
        redirectUrls.put("cancelUrl", CANCEL_URL);
        body.put("redirectUrls", redirectUrls);

        // 3. 產生 HMAC 簽章
        String nonce = UUID.randomUUID().toString();
        String uri = "/v3/payments/request";
        String signatureRaw = CHANNEL_SECRET + uri + body.toString() + nonce;
        String signature = HmacUtil.hmacSHA256Base64(CHANNEL_SECRET, signatureRaw);
        System.out.println("nonce:"+nonce);
        System.out.println("signature:"+signature);

        // 4. 發送 HTTP POST 請求到 LINE Pay
        HttpURLConnection conn = (HttpURLConnection) new URL(API_URL).openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("X-LINE-ChannelId", channelId);
        conn.setRequestProperty("X-LINE-Authorization", signature);
        conn.setRequestProperty("X-LINE-Authorization-Nonce", nonce);
        conn.setDoOutput(true);
        try(OutputStream os = conn.getOutputStream()) {
            os.write(body.toString().getBytes("UTF-8"));
        }
     // 5. 取得回應
        StringBuilder responseLinePay = new StringBuilder();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String respLine;
            while((respLine = br.readLine()) != null) responseLinePay.append(respLine);
        }
        System.out.println("responseLinePay"+responseLinePay);
        JSONObject resJson = new JSONObject(responseLinePay.toString());
        JSONObject info = resJson.optJSONObject("info");
        String paymentUrl = (info != null && info.has("paymentUrl")) ? info.getJSONObject("paymentUrl").getString("web") : null;
        
        // 6. 回傳 paymentUrl 給前端
        response.setContentType("application/json");
        response.getWriter().write("{\"paymentUrl\":\"" + paymentUrl + "\"}");
	}

	// 取得所有營地訂單，回傳 JSON
	@GetMapping("/api/getallcamps")
	public ApiResponse<List<CampVO>> getAllCamps() {
		List<CampVO> camps = campService.getAllCamp();
		return new ApiResponse<>("success", camps, "查詢成功");
	}

	@PostMapping("/api/createonecamp")
	public ApiResponse<CampVO> createOneCamp(@RequestParam("ownerId") Integer ownerId,
			@RequestParam("campName") String campName, @RequestParam("campContent") String campContent,
			@RequestParam("campCity") String campCity, @RequestParam("campDist") String campDist,
			@RequestParam("campAddr") String campAddr, @RequestParam("campReleaseStatus") Byte campReleaseStatus,
			@RequestParam("campCommentNumberCount") Integer campCommentNumberCount,
			@RequestParam("campCommentSumScore") Integer campCommentSumScore,
			@RequestParam("campRegDate") String campRegDate, // yyyy-MM-dd
			@RequestPart("campPic1") MultipartFile campPic1, @RequestPart("campPic2") MultipartFile campPic2,
			@RequestPart(value = "campPic3", required = false) MultipartFile campPic3,
			@RequestPart(value = "campPic4", required = false) MultipartFile campPic4) {
		CampVO camp = new CampVO();
		try {
			camp.setOwnerId(ownerId);
			camp.setCampName(campName);
			camp.setCampContent(campContent);
			camp.setCampCity(campCity);
			camp.setCampDist(campDist);
			camp.setCampAddr(campAddr);
			camp.setCampReleaseStatus(campReleaseStatus);
			camp.setCampCommentNumberCount(campCommentNumberCount);
			camp.setCampCommentSumScore(campCommentSumScore);
			camp.setCampRegDate(java.sql.Date.valueOf(campRegDate));
			camp.setCampPic1(campPic1.getBytes());
			camp.setCampPic2(campPic2.getBytes());
			if (campPic3 != null)
				camp.setCampPic3(campPic3.getBytes());
			if (campPic4 != null)
				camp.setCampPic4(campPic4.getBytes());

			CampVO newCampVO = campService.createOneCamp(camp);
			return new ApiResponse<>("success", newCampVO, "查詢成功");
		} catch (Exception e) {
			return new ApiResponse<>("fail", camp, "查詢失敗");
		}

	}

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

	// 抓取資料庫的營地圖片，提供給前端
	@GetMapping("/api/camps/{campId}/{num}")
	public void getCampPic3(@PathVariable Integer campId, @PathVariable Integer num, HttpServletResponse response)
			throws IOException {
		byte[] img = null;
		try {
			switch (num) {
			case 1:

				img = (campService.getOneCamp(campId)).getCampPic1();
				break;

			case 2:

				img = (campService.getOneCamp(campId)).getCampPic2();
				break;

			case 3:

				img = (campService.getOneCamp(campId)).getCampPic3();
				break;

			case 4:

				img = (campService.getOneCamp(campId)).getCampPic4();
				break;

			default:
				throw new IllegalArgumentException("Unexpected value: " + num);
			}
			response.setContentType("image/jpeg");
			response.getOutputStream().write(img);
		} catch (Exception e) {
			System.out.println("營地編號：" + campId + "||第" + num + "張圖片無法讀取");// TODO: handle exception
			img = (campService.getOneCamp(campId)).getCampPic1();
			response.setContentType("image/jpeg");
			response.getOutputStream().write(img);
		}
	}

}
