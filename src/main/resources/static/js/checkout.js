//LINE PAY:
// Channel ID: 1656895462
// Channel Secret Key:fd01e635b9ea97323acbe8d5c6b2fb71

// 結帳頁面管理
class CheckoutManager {
  constructor() {
    // 檢查CartManager是否已初始化
    if (cartManager.isInitialized()) {
      this.init();
    } else {
      console.log("等待CartManager初始化完成...");
      // 監聽CartManager初始化完成事件
      document.addEventListener("cartManagerInitialized", () => {
        console.log("CartManager初始化完成，開始初始化CheckoutManager");
        this.init();
      });
    }
  }

  init() {
    this.cartItems = cartManager.getCartItems();
    // 使用 cartManager.getBundleItems() 獲取加購商品
    this.bundleItems = cartManager.getBundleItems();
    this.totalPrice = cartManager.getTotalPrice();
    this.selectedPaymentMethod = "line-pay"; // 預設付款方式
    this.isProcessing = false; // 付款處理中標誌

    // 如果購物車為空，重定向到購物車頁面
    if (this.cartItems.length === 0 && this.bundleItems.length === 0) {
      window.location.href = "camp_cart.html";
      return;
    }

    this.renderOrderSummary();
    this.loadMemberData();
    this.bindEvents();
  }

  // 載入會員資料
  loadMemberData() {
    try {
      // 從 localStorage 獲取會員資料
      const memberData = JSON.parse(localStorage.getItem("memberData"));

      if (memberData) {
        // 自動填入會員資料到表單
        const customerNameInput = document.getElementById("customer-name");
        const customerPhoneInput = document.getElementById("customer-phone");
        const customerEmailInput = document.getElementById("customer-email");
        const customerAddressInput =
          document.getElementById("customer-address");

        if (customerNameInput && memberData.name) {
          customerNameInput.value = memberData.name;
        }
        if (customerPhoneInput && memberData.phone) {
          customerPhoneInput.value = memberData.phone;
        }
        if (customerEmailInput && memberData.email) {
          customerEmailInput.value = memberData.email;
        }
        if (customerAddressInput && memberData.address) {
          customerAddressInput.value = memberData.address;
        }

        console.log("會員資料已自動載入");
      } else {
        console.log("未找到會員資料");
      }
    } catch (error) {
      console.error("載入會員資料時發生錯誤:", error);
    }
  }

  // 渲染訂單摘要
  renderOrderSummary() {
    const orderItemsContainer = document.getElementById("order-items");
    const orderSubtotalElement = document.getElementById("order-subtotal");
    const orderTotalElement = document.getElementById("order-total");

    // 清空容器
    orderItemsContainer.innerHTML = "";
    console.log("清空容器:", this.cartItems);
    // 渲染訂單項目
    this.cartItems.forEach((item) => {
      const orderItemElement = document.createElement("div");
      orderItemElement.className = "order-item";

      if (item.isBundle) {
        // 加購商品
        orderItemElement.innerHTML = `
          <div class="order-item-details">
            <div class="order-item-title">${item.bundleName}</div>
            <div class="order-item-info">數量: ${item.quantity || 1}</div>
            <div class="order-item-price">NT$ ${(
              item.bundlePrice * (item.quantity || 1)
            ).toLocaleString()}</div>
          </div>
        `;
      } else {
        // 營地項目
        console.log("campsite_type_id_check_out:" + item.campsiteTypeId);

        const campsiteType = cartManager.getCampsiteTypeById(
          item.campsiteTypeId
        );
        const nights = cartManager.calculateNights(item.checkIn, item.checkOut);
        const tentPrice =
          item.tentType && item.tentType.includes("rent")
            ? cartManager.getTentPrice(item.tentType)
            : 0;
        const totalPrice = (campsiteType.campsitePrice + tentPrice) * nights;

        orderItemElement.innerHTML = `
          <div class="order-item-details">
            <div class="order-item-title">${campsiteType.campsiteName}</div>
            <div class="order-item-info">${this.formatDate(
              item.checkIn
            )} - ${this.formatDate(item.checkOut)} (${nights}晚)</div>
            <div class="order-item-price">NT$ ${totalPrice.toLocaleString()}</div>
          </div>
        `;
      }

      orderItemsContainer.appendChild(orderItemElement);
    });

    // 渲染從localStorage獲取的加購商品
    this.bundleItems.forEach((item) => {
      const orderItemElement = document.createElement("div");
      orderItemElement.className = "order-item bundle-item";

      orderItemElement.innerHTML = `
        <div class="order-item-details">
          <div class="order-item-title">${item.bundleName}</div>
          <div class="order-item-info">數量: ${item.quantity || 1}</div>
          <div class="order-item-price">NT$ ${(
            item.bundlePrice * (item.quantity || 1)
          ).toLocaleString()}</div>
        </div>
      `;

      orderItemsContainer.appendChild(orderItemElement);
    });

    // 計算總價（包括加購商品）
    const totalPrice = cartManager.getTotalPrice();

    // 更新總計
    orderSubtotalElement.textContent = `NT$ ${totalPrice.toLocaleString()}`;
    orderTotalElement.textContent = `NT$ ${totalPrice.toLocaleString()}`;
  }

  // 綁定事件
  bindEvents() {
    // 付款方式選擇
    const paymentMethods = document.querySelectorAll(".payment-method-header");
    paymentMethods.forEach((method) => {
      method.addEventListener("click", (e) => {
        const methodElement = method.closest(".payment-method");
        const methodId = methodElement.dataset.method;
        this.selectPaymentMethod(methodId);
      });
    });



    // 提交付款按鈕
    const submitPaymentBtn = document.getElementById("submit-payment");
    if (submitPaymentBtn) {
      submitPaymentBtn.addEventListener("click", () => this.processPayment());
    }

    // 付款結果彈窗按鈕
    const viewOrderBtn = document.getElementById("view-order");
    if (viewOrderBtn) {
      viewOrderBtn.addEventListener("click", () => {
        window.location.href = "campsite_order.html";
      });
    }

    const retryPaymentBtn = document.getElementById("retry-payment");
    if (retryPaymentBtn) {
      retryPaymentBtn.addEventListener("click", () => {
        this.hidePaymentResultModal();
        this.processPayment();
      });
    }

    const backToCheckoutBtn = document.getElementById("back-to-checkout");
    if (backToCheckoutBtn) {
      backToCheckoutBtn.addEventListener("click", () => {
        this.hidePaymentResultModal();
      });
    }

    // 新增：同會員資訊按鈕
    const fillMemberBtn = document.getElementById("fill-member-info");
    if (fillMemberBtn) {
      fillMemberBtn.addEventListener("click", () => {
        let memberInfo =
          localStorage.getItem("currentMember") ||
          sessionStorage.getItem("currentMember");
        if (memberInfo) {
          try {
            const member = JSON.parse(memberInfo);
            document.getElementById("customer-name").value =
              member.memName || "";
            // 手機號碼格式化
            let mobile = member.memMobile || "";
            mobile = mobile.replace(/\D/g, ""); // 移除所有非數字
            document.getElementById("customer-phone").value = mobile;
            document.getElementById("customer-email").value =
              member.memEmail || "";
            document.getElementById("customer-address").value =
              member.memAddr || "";
          } catch (e) {
            alert("會員資料解析失敗");
          }
        } else {
          alert("找不到會員資料");
        }
      });
    }
  }

  // 選擇付款方式
  selectPaymentMethod(methodId) {
    this.selectedPaymentMethod = methodId;

    // 更新UI
    const paymentMethods = document.querySelectorAll(".payment-method");
    paymentMethods.forEach((method) => {
      if (method.dataset.method === methodId) {
        method.classList.add("active");
        const radio = method.querySelector("input[type='radio']");
        if (radio) radio.checked = true;
      } else {
        method.classList.remove("active");
        const radio = method.querySelector("input[type='radio']");
        if (radio) radio.checked = false;
      }
    });
  }

  // 驗證表單
  validateForm() {
    // 驗證客戶資訊
    const customerName = document.getElementById("customer-name").value.trim();
    const customerPhone = document
      .getElementById("customer-phone")
      .value.trim();
    const customerEmail = document
      .getElementById("customer-email")
      .value.trim();
    const customerAddress = document
      .getElementById("customer-address")
      .value.trim();

    if (!customerName || !customerPhone || !customerEmail || !customerAddress) {
      alert("請填寫完整的訂購人資訊");
      return false;
    }

    // 驗證電子郵件格式
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(customerEmail)) {
      alert("請輸入有效的電子郵件地址");
      return false;
    }

    // 如果選擇信用卡付款，驗證信用卡資訊
    if (this.selectedPaymentMethod === "credit-card") {
      const cardNumber = document
        .getElementById("card-number")
        .value.trim()
        .replace(/\s/g, "");
      const cardExpiry = document.getElementById("card-expiry").value.trim();
      const cardCvc = document.getElementById("card-cvc").value.trim();
      const cardName = document.getElementById("card-name").value.trim();

      if (!cardNumber || !cardExpiry || !cardCvc || !cardName) {
        alert("請填寫完整的信用卡資訊");
        return false;
      }

      // 驗證信用卡號格式
      if (!/^\d{16}$/.test(cardNumber)) {
        alert("請輸入有效的16位信用卡號");
        return false;
      }

      // 驗證有效期格式
      if (!/^(0[1-9]|1[0-2])\/\d{2}$/.test(cardExpiry)) {
        alert("請輸入有效的到期日期 (MM/YY)");
        return false;
      }

      // 驗證安全碼格式
      if (!/^\d{3}$/.test(cardCvc)) {
        alert("請輸入有效的3位安全碼");
        return false;
      }
    }

    return true;
  }

  // 處理付款
  processPayment() {
    if (this.isProcessing) return;

    // 驗證表單
    if (!this.validateForm()) return;

    this.isProcessing = true;
    this.showPaymentProcessingModal();

    // 收集訂單資訊
    const orderData = this.collectOrderData();

    // 只處理 LINE Pay 付款
    if (this.selectedPaymentMethod === "line-pay") {
      this.processServerPayment(orderData);
    }
  }

  // 收集訂單資訊
  collectOrderData() {
    // 使用 cartManager.getTotalPrice() 獲取總價
    const totalPrice = cartManager.getTotalPrice();

    // 合併購物車項目和加購商品
    const allItems = [...this.cartItems, ...this.bundleItems];

    return {
      customer: {
        name: document.getElementById("customer-name").value.trim(),
        phone: document.getElementById("customer-phone").value.trim(),
        email: document.getElementById("customer-email").value.trim(),
        address: document.getElementById("customer-address").value.trim(),
        note: document.getElementById("customer-note").value.trim(),
      },
      items: allItems,
      totalPrice: totalPrice,
      paymentMethod: this.selectedPaymentMethod,
      paymentDetails: {},
    };
  }



  // 處理伺服器付款
  async processServerPayment(orderData) {
    console.log("處理伺服器付款", orderData);

    try {
      // 從伺服器獲取訂單ID
      const orderId = await this.getOrderIdFromServer();
      if (!orderId) {
        throw new Error("無法獲取訂單ID");
      }

      // 準備商品資訊
      const products = [];

      // 添加營地項目
      this.cartItems.forEach((item) => {
        if (!item.isBundle) {
          const campsiteType = cartManager.getCampsiteTypeById(
            item.campsiteTypeId
          );
          const nights = cartManager.calculateNights(
            item.checkIn,
            item.checkOut
          );
          const tentPrice =
            item.tentType && item.tentType.includes("rent")
              ? cartManager.getTentPrice(item.tentType)
              : 0;
          const totalPrice = (campsiteType.campsitePrice + tentPrice) * nights;

          products.push({
            name: `${campsiteType.campsiteName} (${this.formatDate(
              item.checkIn
            )} - ${this.formatDate(item.checkOut)})`,
            quantity: 1,
            price: totalPrice,
          });
        }
      });

      // 添加加購商品
      this.bundleItems.forEach((item) => {
        products.push({
          name: item.bundleName,
          quantity: item.quantity || 1,
          price: item.bundlePrice,
        });
      });

      // 計算總金額
      const totalAmount = cartManager.getTotalPrice();

      //confirmURL
      const confirm_url = "http://127.0.0.1:5503/linepay-success.html";

      // 構建付款請求參數
      const linepay_body = {
        amount: totalAmount,
        currency: "TWD",
        orderId: orderId,
        packages: [
          {
            id: "pkg-001",
            amount: totalAmount,
            products: products,
          },
        ],

        redirectUrls: {
          confirmUrl: `${window.api_prefix}/api/confirmpayment/${orderId}/true`,
          cancelUrl: "http://127.0.0.1:5503/linepay-cancel.html",
        },
      };

      // 計算各種金額
      const campsiteAmount = this.cartItems.reduce((total, item) => {
        const campsiteType = cartManager.getCampsiteTypeById(
          item.campsiteTypeId
        );
        const nights = cartManager.calculateNights(item.checkIn, item.checkOut);
        const tentPrice =
          item.tentType && item.tentType.includes("rent")
            ? cartManager.getTentPrice(item.tentType)
            : 0;
        return total + (campsiteType.campsitePrice + tentPrice) * nights;
      }, 0);

      const bundleAmount = this.bundleItems.reduce((total, item) => {
        return total + item.bundlePrice * (item.quantity || 1);
      }, 0);

      const befAmount = campsiteAmount + bundleAmount;

      // 折扣金額，這裡可以根據實際情況計算
      const disAmount = 0; // 預設為0，如果有折扣碼可以計算實際折扣

      const aftAmount = befAmount - disAmount;

      // 獲取第一個訂單項目的營地資訊（如果有）
      const firstItem = this.cartItems[0] || {};
      const campInfo = firstItem.campInfo || {
        campId: firstItem.campId || 0,
        campName: firstItem.name || "未知營地",
      };

      // 獲取當前日期時間作為訂單日期
      const now = new Date();
      const orderDate = now.toISOString();

      // 從localStorage獲取會員ID
	  const memberData =
	          localStorage.getItem("currentMember") ||
	          sessionStorage.getItem("currentMember");
	        const memberDataJson = JSON.parse(memberData);
	        console.log("linepay_memberData:", memberData);

	        // 從localStorage獲取會員ID
	        const memId = memberDataJson.memId; // 預設會員ID
	        console.log("linepay_id:", memId); // 預設會員ID

      const linepay_order_body = {
        orderId: orderId,
        orderDate: orderDate,
        orderStatus: 0, // 0: 待付款
        payMethod: this.selectedPaymentMethod === "credit-card" ? 1 : 2, // 1: 信用卡, 2: LINE Pay

        bundleAmount: bundleAmount,
        campsiteAmount: campsiteAmount,
        befAmount: befAmount,
        disAmount: disAmount,
        aftAmount: aftAmount,

        checkIn: firstItem.checkIn || "",
        checkOut: firstItem.checkOut || "",

        satisfaction: 0, // 初始評分
        content: "", // 初始評論
        date: "", // 評論日期

        campId: campInfo.campId,
        campName: campInfo.campName,

        memId: memId,

        discountCodeId: "", // 如果有折扣碼可以設置
        details: this.cartItems.map((item) => {
          // 獲取房型資料
          const campsiteType = cartManager.getCampsiteTypeById(
            item.campsiteTypeId
          );
          // 計算住宿天數
          const nights = cartManager.calculateNights(
            item.checkIn,
            item.checkOut
          );
          // 計算帳篷租借費用
          const tentPrice =
            item.tentType && item.tentType.includes("rent")
              ? cartManager.getTentPrice(item.tentType)
              : 0;
          // 計算總金額
          const totalAmount = (campsiteType.campsitePrice + tentPrice) * nights;

          return {
            campsiteTypeId: item.campsiteTypeId,
            campsiteNum: 1, // 預設為1，如果需要可以從item中獲取
            campsiteAmount: totalAmount,
          };
        }),
        bundleItemDetails: this.bundleItems.map((item) => {
          // 計算加購商品總金額
          const totalAmount = item.bundlePrice * (item.quantity || 1);

          return {
            campsiteOrderId: orderId, // 使用當前訂單ID
            bundleBuyAmount: totalAmount,
            bundleBuyNum: item.quantity || 1,
            bundleId: item.bundleId,
          };
        }),
      };

      const requestBody = {
        linepayBody: linepay_body,
        linepayOrder: linepay_order_body,
      };

      console.log("付款請求參數:", requestBody);
      console.log("order_details:", linepay_order_body);

      // 發送請求到伺服器
      const paymentUrl = await this.sendPaymentRequest(requestBody);
      console.log("paymentUrl:" + paymentUrl);

      if (paymentUrl) {
        // 準備帶有房型名稱的購物車項目
        const cartItemsWithTypeName = this.cartItems.map((item) => {
          if (!item.isBundle) {
            const campsiteType = cartManager.getCampsiteTypeById(
              item.campsiteTypeId
            );
            return {
              ...item,
              campsiteTypeName: campsiteType.campsiteName,
              campsiteTypePrice: campsiteType.campsitePrice,
            };
          }
          return item;
        });

        // 儲存訂單資訊到 localStorage，以便在付款成功後處理
        localStorage.setItem(
          "pendingOrder",
          JSON.stringify({
            orderId: orderId,
            items: [...cartItemsWithTypeName, ...this.bundleItems],
            totalPrice: totalAmount,
            customer: orderData.customer,
          })
        );

        // 跳轉到付款頁面
        window.location.href = paymentUrl;
      } else {
        throw new Error("無法獲取付款網址");
      }
    } catch (error) {
      console.error("付款請求失敗:", error);
      this.hidePaymentProcessingModal();
      this.handlePaymentFailure({
        errorCode: "API_ERROR",
        errorMessage:
          error.message || "無法連接到付款服務，請檢查網路連接或稍後再試。",
      });
    }
  }

  // 從伺服器獲取訂單ID
  async getOrderIdFromServer() {
    try {
      const response = await fetch(
        `${window.api_prefix}/api/campsite/newordernumber`
      );
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      const json_data = await response.json();
      const new_order_num = json_data.data;
      console.log("newOrderNumber:" + new_order_num);

      return new_order_num;
    } catch (error) {
      console.error("獲取訂單ID失敗:", error);
      return null;
    }
  }

  // 發送付款請求到伺服器
  async sendPaymentRequest(requestBody) {
    try {
      const response = await fetch(`${window.api_prefix}/api/linepay/true`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(requestBody),
      });

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const data = await response.json();
      console.log("付款回應:", data);

      // 返回付款網址
      return data.data;
    } catch (error) {
      console.error("發送付款請求失敗:", error);
      return null;
    }
  }

  // 處理付款成功
  handlePaymentSuccess(result) {
    console.log("付款成功", result);
    this.isProcessing = false;
    this.hidePaymentProcessingModal();

    // 設置訂單編號
    document.getElementById("order-number").textContent = result.orderId;

    // 顯示成功訊息
    const successElement = document.getElementById("payment-success");
    const failureElement = document.getElementById("payment-failure");
    successElement.classList.add("active");
    failureElement.classList.remove("active");

    // 顯示結果彈窗
    this.showPaymentResultModal();

    // 清空購物車和加購商品
    cartManager.clearAll();

    // 創建訂單 (在實際應用中，這裡會發送API請求到伺服器)
    this.createOrder(result);
  }

  // 處理付款失敗
  handlePaymentFailure(error) {
    console.error("付款失敗", error);
    this.isProcessing = false;
    this.hidePaymentProcessingModal();

    // 設置錯誤原因
    document.getElementById("failure-reason").textContent = error.errorMessage;

    // 顯示失敗訊息
    const successElement = document.getElementById("payment-success");
    const failureElement = document.getElementById("payment-failure");
    successElement.classList.remove("active");
    failureElement.classList.add("active");

    // 顯示結果彈窗
    this.showPaymentResultModal();
  }

  // 創建訂單
  createOrder(paymentResult) {
    // 在實際應用中，這裡會發送API請求到伺服器
    console.log("創建訂單", {
      orderId: paymentResult.orderId,
      transactionId: paymentResult.transactionId,
      paymentMethod: paymentResult.paymentMethod,
      items: this.cartItems,
      bundleItems: this.bundleItems,
      totalPrice: cartManager.getTotalPrice(),
      customer: {
        name: document.getElementById("customer-name").value.trim(),
        phone: document.getElementById("customer-phone").value.trim(),
        email: document.getElementById("customer-email").value.trim(),
        address: document.getElementById("customer-address").value.trim(),
        note: document.getElementById("customer-note").value.trim(),
      },
      createdAt: new Date().toISOString(),
    });
  }

  // 顯示付款處理中彈窗
  showPaymentProcessingModal() {
    const modal = document.getElementById("payment-processing-modal");
    modal.classList.add("active");
  }

  // 隱藏付款處理中彈窗
  hidePaymentProcessingModal() {
    const modal = document.getElementById("payment-processing-modal");
    modal.classList.remove("active");
  }

  // 顯示付款結果彈窗
  showPaymentResultModal() {
    const modal = document.getElementById("payment-result-modal");
    modal.classList.add("active");
  }

  // 隱藏付款結果彈窗
  hidePaymentResultModal() {
    const modal = document.getElementById("payment-result-modal");
    modal.classList.remove("active");
  }

  // 生成訂單ID
  generateOrderId() {
    const timestamp = new Date().getTime();
    const random = Math.floor(Math.random() * 10000);
    return `ORD-${timestamp}-${random}`;
  }

  // 生成交易ID
  generateTransactionId() {
    const timestamp = new Date().getTime();
    const random = Math.floor(Math.random() * 10000);
    return `TXN-${timestamp}-${random}`;
  }

  // 格式化日期
  formatDate(dateString) {
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, "0");
    const day = date.getDate().toString().padStart(2, "0");
    return `${year}/${month}/${day}`;
  }
}

// 頁面載入完成後初始化結帳頁面
document.addEventListener("DOMContentLoaded", () => {
  new CheckoutManager();
});
