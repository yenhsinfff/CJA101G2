// 使用全域購物車管理器
// 移除重複的ShopCartManager類別定義

// Session Cart 管理工具
const sessionCartManager = {
  getCart: function() {
    const cart = sessionStorage.getItem('sessionCart');
    return cart ? JSON.parse(cart) : [];
  },
  saveCart: function(cart) {
    sessionStorage.setItem('sessionCart', JSON.stringify(cart));
  },
  addToCart: function(item) {
    const cart = this.getCart();
    const exist = cart.find(c =>
      c.prodId === item.prodId &&
      c.prodColorId === item.prodColorId &&
      c.prodSpecId === item.prodSpecId
    );
    if (exist) {
      exist.cartProdQty += item.cartProdQty;
    } else {
      cart.push(item);
    }
    this.saveCart(cart);
  }
};

document.addEventListener("DOMContentLoaded", function () {
  const container = document.querySelector(".product-grid");
  const categorySelect = document.getElementById("category");
  const sortSelect = document.getElementById("sort");
  const form = document.querySelector(".filter-form");
  const loadingOverlay = document.querySelector(".loading-overlay");

  // 初始化商品dropdown分類選單
  const urlParams = new URLSearchParams(window.location.search);
  const urlSort = urlParams.get("sort");
  if (urlSort && sortSelect) {
    sortSelect.value = urlSort;
  }

  // 初始化松果載入動畫
  initLoadingAnimation();
  // 顯示載入畫面
  showLoadingOverlay();

  // 載入分類後，再載入商品（確保分類選單有渲染完）
  loadCategories().then(() => {
    fetchProducts();
  });

  // 表單送出時依條件重新查詢
  form.addEventListener("submit", function (e) {
    e.preventDefault();
    showLoadingOverlay();
    fetchProducts();
  });

  // 顯示載入畫面
  function showLoadingOverlay() {
    if (loadingOverlay) {
      loadingOverlay.classList.add("active");
    }
  }

  // 隱藏載入畫面
  function hideLoadingOverlay() {
    if (loadingOverlay) {
      loadingOverlay.classList.remove("active");
    }
  }

  // 松果載入動畫
  function initLoadingAnimation() {
    const pineCone = document.querySelector(".pine-cone");
    if (!pineCone) return;

    // 動態生成松果鱗片
    const scales = 8; // 鱗片數量
    const radius = 25; // 松果半徑

    for (let i = 0; i < scales; i++) {
      const angle = (i / scales) * Math.PI * 2;
      const scale = document.createElementNS(
        "http://www.w3.org/2000/svg",
        "path"
      );

      // 計算鱗片位置
      const x1 = 30 + Math.cos(angle) * radius * 0.5;
      const y1 = 30 + Math.sin(angle) * radius * 0.5;
      const x2 = 30 + Math.cos(angle) * radius;
      const y2 = 30 + Math.sin(angle) * radius;

      scale.setAttribute("d", `M30,30 L${x1},${y1} L${x2},${y2} Z`);
      scale.setAttribute("fill", "#A68A64");
      scale.setAttribute("class", "pine-cone-scale");
      scale.style.transformOrigin = "30px 30px";
      scale.style.animation = `pulse 1.5s infinite ease-in-out ${i * 0.2}s`;

      pineCone.appendChild(scale);
    }

    // 添加鱗片脈動動畫
    if (!document.querySelector('style[data-pine-cone-animation]')) {
      const style = document.createElement("style");
      style.setAttribute('data-pine-cone-animation', 'true');
      style.textContent = `
        @keyframes pulse {
            0%, 100% { transform: scale(1); }
            50% { transform: scale(0.8); }
        }
      `;
      document.head.appendChild(style);
    }
  }

  // ✅ 動態載入商品分類選單
  function loadCategories() {
    return fetch(`${window.api_prefix}/api/product-types`)
      .then(res => res.json())
      .then(response => {
        // 檢查是否有嵌套結構，例如 { data: [...] } 或 { results: [...] }
        const data = response.data || response.results || response;
        console.log("處理後的商品類別數據：", data);
        
        categorySelect.innerHTML = `<option value="">全部分類</option>`;
        
        if (Array.isArray(data) && data.length > 0) {
          data.forEach(type => {
            const option = document.createElement("option");
            option.value = type.prodTypeId || type.id || "";
            option.textContent = type.prodTypeName || type.name || "未知類別";
            categorySelect.appendChild(option);
          });
        } else {
          console.error("API 返回的數據結構不符合預期");
          throw new Error("無效的數據格式");
        }
      })
      .catch(err => {
        console.error("載入商品分類失敗：", err);
        categorySelect.innerHTML = `<option value="">無法載入分類</option>`;
      });
  }

  // ✅ 根據篩選條件載入商品
  // 在fetchProducts函數中添加更新section-title的代碼
  function fetchProducts() {
      const categorySelect = document.getElementById("category");
      const category = categorySelect.value;
      const sort = sortSelect.value;
      const priceRange = document.getElementById("price")?.value;
      
      // 更新section-title內容
      updateSectionTitle(category, sort, priceRange);
  
      let url = `${window.api_prefix}/api/products`;

      if (category) {
        url = `${window.api_prefix}/api/products/type/${category}`;
      } else if (priceRange){
        url = `${window.api_prefix}/api/products/price-range?range=${priceRange}`;
      }else if (sort === "latest") {
        url = `${window.api_prefix}/api/products/latest?limit=5`;
      } else if (sort === "popular") {
        url = `${window.api_prefix}/api/products`;
      } else if (sort === "discount") {
        url = `${window.api_prefix}/api/products/discount`;
      } else if (sort === "price-asc") {
        url = `${window.api_prefix}/api/products/price-asc`;
      } else if (sort === "price-desc") {
        url = `${window.api_prefix}/api/products/price-desc`;
      }

      // 修改商品載入邏輯（約第175-310行）
      // 在 fetch 成功的回調中（約第175-310行）
      fetch(url || `${window.api_prefix}/api/getProducts?${queryString}`)
        .then((res) => res.json())
        .then((data) => {
          const products = data.data || [];
          container.innerHTML = ''; // 清空容器
          
          let allProductsHtml = ''; // 收集所有商品的 HTML
          const productIds = []; // 收集所有商品 ID
          
          products.forEach((prod) => {
            // 確保只有真正有折扣的商品才標記為促銷
            const hasDiscount = prod.prodDiscount !== null && prod.prodDiscount !== undefined && prod.prodDiscount < 1 && prod.prodDiscount > 0;
            const featuresHtml = generateProductFeatures(prod);
            const colorMap = new Map((prod.colorList || []).map(c => [c.colorId, c.colorName]));
            const specMap = new Map((prod.specList || []).map(s => [s.specId, s.specName]));
            const colors = prod.prodColorList || [];
            let colorSelectHtml = '';
      
          // 生成顏色選擇按鈕;若商品有多種顏色，才顯示顏色選擇區塊
          if (colors.length > 0) {
            colorSelectHtml += `<div class="product-color-select"><span class="label"></span>`;
          
          colors.forEach((color, index) => {
            const colorId = color.prodColorId;
            const colorName = colorMap.get(colorId) || `顏色 ${colorId}`;
            const hasImage = color.hasPic === true; // 確認是否有圖片
        
            // 預設圖片內容為空
            let imageHtml = '';
        
            // 如果有圖片才組圖片 HTML
            if (hasImage) {
              const imgUrl = `${window.api_prefix}/api/prod-colors/colorpic/${prod.prodId}/${colorId}`;
              imageHtml = `<img src="${imgUrl}" alt="${colorName}" class="color-thumbnail" onerror="this.src='images/default-color.png'" />`;
            }
        
            // 整合顏色按鈕
            colorSelectHtml += `
              <button class="color-box${index === 0 ? ' active' : ''}" data-color-id="${colorId}">
                  ${imageHtml}
                  <span>${colorName}</span>
                </button>`;
          });
        
          colorSelectHtml += `</div>`;
        }
      
          const specs = prod.prodSpecList || [];
          let specSelectHtml = '';
          let prodSpecPrice = null;
          // 生成規格選擇下拉式選單
          if (specs.length > 0) {
            // 獲取第一個規格的價格作為 prodSpecPrice
            prodSpecPrice = specs[0].prodSpecPrice;
            
            specSelectHtml += `<select class="prod-spec-select" data-prod-id="${prod.prodId}">`;
            specs.forEach(spec => {
              // 修改这里，使用规格ID作为value，显示规格名称
              const specId = spec.prodSpecId;
              const specName = specMap.get(specId) || `規格 ${specId}`; 
              specSelectHtml += `<option value="${spec.prodSpecId}" data-price="${spec.prodSpecPrice}">${specName || `規格 ${spec.prodSpecId}`}</option>`;
            });
            specSelectHtml += `</select>`;
          }
          
          // 計算折扣率（如果有折扣） 使用 prodSpecPrice 或 prodPrice 作為原始價格
          const originalPrice = prodSpecPrice || prod.prodPrice;

          // prod.prodDiscount 是折扣倍率（例如 0.8 表示 8 折）
          let discountRate = (hasDiscount && prod.prodDiscount > 0 && prod.prodDiscount < 1) ? prod.prodDiscount : 1;
          // 計算折扣後價格
          const discountedPrice = Math.round(originalPrice * discountRate);
          
          // 動態取得商品圖片（取第一張為主圖）
          let productImageHtml = '';
          if (prod.prodPicList && prod.prodPicList.length > 0) {
            const firstPicId = prod.prodPicList[0].prodPicId;
            // 添加錯誤處理
            productImageHtml = `<img src="${window.api_prefix}/api/prodpics/${firstPicId}" alt="${prod.prodName}" onerror="this.onerror=null; this.src='images/default-product.jpg';" />`;
          } else {
            productImageHtml = `<img src="images/default-product.jpg" alt="無圖片" />`;
          }
          
          // 添加調試信息
          console.log("商品圖片信息:", {
            prodId: prod.prodId,
            prodName: prod.prodName,
            hasPicList: !!prod.prodPicList,
            picListLength: prod.prodPicList ? prod.prodPicList.length : 0,
            firstPicId: prod.prodPicList && prod.prodPicList.length > 0 ? prod.prodPicList[0].prodPicId : null
          });
          const html = `
            <div class="product-card">
              <div class="product-image">
                ${productImageHtml}
                <span class="${hasDiscount ? 'product-tag' : 'product-tag2'}">${hasDiscount ? '促銷' : (prod.prodTag || '熱銷')}</span>
              </div>
              <div class="product-info">
                <h3><a href="product-detail.html?id=${prod.prodId}">${prod.prodName}</a></h3>
                <p class="product-category">
                  <i class="fas fa-tag"></i> ${prod.prodTypeName}
                </p>

                <!--商品評論生成部分-->
                <div class="product-rating" data-product-id="${prod.prodId}" style="display: none;">
                  <span class="stars"></span>
                  <span class="rating-count"></span>
                </div>
                <div class="product-features">${featuresHtml}</div>

                ${colorSelectHtml} <!-- 顏色區 -->
                ${specSelectHtml}  <!-- 規格區 -->
                <div class="product-price">
                  <span class="current-price" data-base-price="${discountedPrice}" data-discount-rate="${discountRate}">NT$ ${discountedPrice}</span>
                  ${hasDiscount && discountRate < 1 ? `<span class="original-price">NT$ ${originalPrice}</span>` : ''}
                </div>             

                <div class="product-actions">
                  <button class="btn-favorite" data-id="${prod.prodId}"><i class="far fa-heart"></i> 加入收藏</button>
                  <button class="btn-add-cart" data-id="${prod.prodId}"><i class="fas fa-shopping-cart"></i> 加入購物車</button>
                </div>
              </div>
            </div>
          `;
          
          allProductsHtml += html;
          productIds.push(prod.prodId);
        });
        
        // 一次性設置所有商品的 HTML
        container.innerHTML = allProductsHtml;
        
        // 確保 DOM 完全渲染後再載入評分
        setTimeout(() => {
          productIds.forEach(prodId => {
            loadProductRatingForCard(prodId);
          });
        }, 100); // 延遲 100ms 確保 DOM 渲染完成
        
        bindButtons();
        hideLoadingOverlay();
      })
      .catch((err) => {
        console.error("商品載入失敗：", err);
        hideLoadingOverlay();
      });
  }
  
  // 評分載入函數
  async function loadProductRatingForCard(prodId) {
    try {
      const res = await fetch(`${window.api_prefix}/api/getProdComments?prodId=${prodId}`);
      const data = await res.json();
      
      const ratingElement = document.querySelector(`[data-product-id="${prodId}"]`);
      if (!ratingElement) return;
  
      const starsElement = ratingElement.querySelector('.stars');
      const countElement = ratingElement.querySelector('.rating-count');
  
      if (data.status === 'success' && Array.isArray(data.data)) {
        const comments = data.data;
        const count = comments.length;
  
        if (count === 0) {
          ratingElement.style.display = 'none';
          return;
        }
  
        const sum = comments.reduce((acc, c) => acc + (c.commentSatis || 0), 0);
        const average = sum / count;
  
        // 生成星星 HTML
        let starsHtml = '';
        for (let i = 1; i <= 5; i++) {
          if (i <= Math.floor(average)) {
            starsHtml += '<i class="fas fa-star"></i>';
          } else if (i === Math.ceil(average) && average % 1 >= 0.5) {
            starsHtml += '<i class="fas fa-star-half-alt"></i>';
          } else {
            starsHtml += '<i class="far fa-star"></i>';
          }
        }
  
        if (starsElement) starsElement.innerHTML = starsHtml;
        if (countElement) countElement.textContent = `（${count} 則評價）`;
        ratingElement.style.display = 'block';
      } else {
        ratingElement.style.display = 'none';
      }
    } catch (error) {
      console.error(`載入商品 ${prodId} 評分失敗:`, error);
      const ratingElement = document.querySelector(`[data-product-id="${prodId}"]`);
      if (ratingElement) ratingElement.style.display = 'none';
    }
  }
  
  
  
  function generateProductFeatures() {
    // 這裡可以根據產品類型或特性生成不同的標籤
    const features = [];

    // 添加一些預設標籤
    features.push('<span><i class="fas fa-shield-alt"></i> 品質保證</span>');

    if (features.length < 3) {
      features.push('<span><i class="fas fa-truck"></i> 快速配送</span>');
    }

    return features.join(' '); 
  }

  function bindButtons() {
    // 顏色按鈕點選事件
    document.querySelectorAll(".product-color-select").forEach((colorGroup) => {
      const buttons = colorGroup.querySelectorAll(".color-box");
      buttons.forEach((btn) => {
        btn.addEventListener("click", function () {
          // 取消所有 .active
          buttons.forEach((b) => b.classList.remove("active"));
          // 設定被點選的為 .active
          this.classList.add("active");
        });
      });
    });

    // 規格選擇下拉式選單
    document.querySelectorAll(".prod-spec-select").forEach((select) => {

      select.addEventListener("change", function () {
        const selectedOption = this.options[this.selectedIndex];
        const selectedPrice = parseFloat(selectedOption.dataset.price) || 0;
        const priceContainer = this.closest(".product-info").querySelector(".product-price");
        const priceSpan = priceContainer.querySelector(".current-price");
        let originalPriceSpan = priceContainer.querySelector(".original-price");
      
        // 取得折扣率
        const discountRate = parseFloat(priceSpan.dataset.discountRate) || 1;
      
        // 若沒有 .original-price 元素，則手動建立
        if (!originalPriceSpan && discountRate < 1) {
          originalPriceSpan = document.createElement("span");
          originalPriceSpan.classList.add("original-price");
          priceSpan.after(originalPriceSpan);
        }
      
        // 更新原始價格與折扣價格
        const discountedPrice = Math.round(selectedPrice * discountRate);
        priceSpan.textContent = `NT$ ${discountedPrice}`;
      
        if (originalPriceSpan) {
          originalPriceSpan.textContent = `NT$ ${selectedPrice}`;
        }
      });
      
    });

    // 加入收藏按鈕點選事件
    document.querySelectorAll(".btn-favorite").forEach((btn) => {
      const prodId = parseInt(btn.dataset.id);
      // 檢查收藏狀態
      checkFavoriteStatus(prodId, btn);
      
      btn.addEventListener("click", function () {
        const prodId = parseInt(this.dataset.id);
        toggleWishlist(prodId, this);
      });
    });

// 加入購物車按鈕點選事件
document.querySelectorAll(".btn-add-cart").forEach((btn) => {
  btn.addEventListener("click", function () {
    const prodId = parseInt(this.dataset.id);
    const productCard = this.closest('.product-card');
    const selectedColorBtn = productCard.querySelector('.product-color-select .color-box.active');
    const prodColorId = selectedColorBtn ? parseInt(selectedColorBtn.dataset.colorId) : 1;
    const selectedSpecSelect = productCard.querySelector('.prod-spec-select');
    const prodSpecId = selectedSpecSelect ? parseInt(selectedSpecSelect.value) : 1;

    const cartData = {
      prodId: prodId,
      prodColorId: prodColorId,
      prodSpecId: prodSpecId,
      cartProdQty: 1
    };

    // 判斷登入
    const memberInfo = sessionStorage.getItem('currentMember');
    const member = memberInfo ? JSON.parse(memberInfo) : null;
    const memId = member ? member.memId : null;
    console.log('取得會員ID:', memId);

    if (memId) {
      // 已登入，呼叫 API 寫入資料庫
      cartData.memId = memId;
      fetch(`${window.api_prefix}/api/addCart`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(cartData)
      })
      .then(response => response.json())
      .then(data => {
        if (data.status === 'success') {
          if (window.globalCartManager) window.globalCartManager.updateCartCount();
        }
        showAddToCartMessage();
      })
      .catch(error => {
        alert('加入購物車失敗，請稍後再試');
      });
    } else {
      // 未登入，寫入 sessionStorage
      sessionCartManager.addToCart(cartData);
      if (window.globalCartManager) window.globalCartManager.updateCartCount();

      console.log('加入購物車數據:', cartData);

      // 使用 API 模擬寫入（可省略或未來接上）
      fetch(`${window.api_prefix}/api/addCart`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(cartData)
      })
      .then(response => {
        if (!response.ok) throw new Error('網路回應不正常');
        return response.json();
      })
      .then(data => {
        console.log('加入購物車成功:', data);
        if (data.status === 'success') {
          if (window.globalCartManager) window.globalCartManager.updateCartCount();
        }
        showAddToCartMessage();
      })
      .catch(err => {
        console.error('加入購物車失敗:', err);
      });
    }
  });
});

    
    // 顯示加入購物車成功訊息
    function showAddToCartMessage() {
      // 創建提示消息
      const message = document.createElement('div');
      message.className = 'cart-message';
      message.innerHTML = `
        <i class="fas fa-check-circle"></i>
        <span>已添加到購物車</span>
        <a href="shop_cart.html" class="view-cart-btn">查看購物車</a>
      `;

      // 添加樣式
      message.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        background-color: #3A5A40;
        color: white;
        padding: 12px 20px;
        border-radius: 8px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
        z-index: 10000;
        display: flex;
        align-items: center;
        gap: 12px;
        font-family: 'Noto Sans TC', sans-serif;
        animation: slideInRight 0.3s ease;
      `;
      
      // 添加查看購物車按鈕樣式
      const viewCartBtn = message.querySelector('.view-cart-btn');
      if (viewCartBtn) {
        viewCartBtn.style.cssText = `
          background-color: white;
          color: #3A5A40;
          padding: 4px 10px;
          border-radius: 4px;
          text-decoration: none;
          font-size: 14px;
          font-weight: 500;
          transition: background-color 0.2s;
        `;
        
        // 添加懸停效果
        viewCartBtn.addEventListener('mouseover', function() {
          this.style.backgroundColor = '#f0f0f0';
        });
        
        viewCartBtn.addEventListener('mouseout', function() {
          this.style.backgroundColor = 'white';
        });
      }

      // 添加動畫樣式
      if (!document.querySelector('style[data-cart-animation]')) {
        const style = document.createElement('style');
        style.setAttribute('data-cart-animation', 'true');
        style.textContent = `
          @keyframes slideInRight {
            from { transform: translateX(100%); opacity: 0; }
            to { transform: translateX(0); opacity: 1; }
          }
          @keyframes fadeOut {
            from { opacity: 1; }
            to { opacity: 0; }
          }
        `;
        document.head.appendChild(style);
      }

      // 添加到頁面
      document.body.appendChild(message);

      // 5秒後移除（增加時間讓用戶有更多時間點擊查看購物車）
      setTimeout(() => {
        message.style.animation = 'fadeOut 0.3s ease';
        setTimeout(() => {
          if (message.parentNode) {
            message.parentNode.removeChild(message);
          }
        }, 300);
      }, 5000);
    }
  }
});

// 添加新函數用於更新section-title
function updateSectionTitle(category, sort, priceRange) {
    const sectionTitle = document.querySelector('.shop-products .section-title');
    if (!sectionTitle) return;
    
    let titleText = '熱門商品';
    let filterInfo = '';

    const categorySelect = document.getElementById('category');

    // 根據篩選條件更新標題
    if (category) {
        const categoryOption = categorySelect.options[categorySelect.selectedIndex];
        const categoryName = categoryOption ? categoryOption.textContent : '';
        titleText = categoryName;
    } else if (sort === 'latest') {
        titleText = '最新上架商品';
    } else if (sort === 'discount') {
        titleText = '折扣商品';
    } else if (sort === 'price-asc') {
        titleText = '價格由低至高商品';
    } else if (sort === 'price-desc') {
        titleText = '價格由高至低商品';
    }
    
    // 如果有價格範圍，添加到篩選信息
    if (priceRange) {
        const priceOption = document.getElementById('price').options[document.getElementById('price').selectedIndex];
        const priceText = priceOption ? priceOption.textContent : '';
        filterInfo = `<span class="filter-info">${priceText}</span>`;
    }
    
    // 更新section-title的內容
    sectionTitle.innerHTML = titleText + (filterInfo ? ` - ${filterInfo}` : '');
}

// 獲取當前登入會員的 ID
function getCurrentMemberId() {
  const memberData = localStorage.getItem('currentMember') || sessionStorage.getItem('currentMember');
  if (memberData) {
    try {
      return JSON.parse(memberData).memId;
    } catch (e) {
      console.error('解析會員資料失敗', e);
    }
  }
  return null;
}

// 檢查收藏狀態
function checkFavoriteStatus(prodId, favoriteBtn) {
  const memId = getCurrentMemberId();
  if (!memId) return; // 未登入不檢查
  
  fetch(`${window.api_prefix}/api/prodfavorites/isFavoriteOrNot/${memId}/${prodId}`)
    .then(response => response.json())
    .then(data => {
      if (data.status === 'success' && data.data) {
        // 如果已收藏，更新按鈕狀態
        if (favoriteBtn) {
          favoriteBtn.innerHTML = '<i class="fas fa-heart"></i> 已收藏';
          favoriteBtn.classList.add('active');
          favoriteBtn.dataset.isFavorite = 'true';
        }
      }
    })
    .catch(error => {
      console.error('檢查收藏狀態失敗:', error);
    });
}

// 加入收藏函數
function addToWishlist(prodId, favoriteBtn) {
  const memId = getCurrentMemberId();
  if (!memId) {
    alert('請先登入會員');
    window.location.href = 'login.html?redirect=' + encodeURIComponent(window.location.href);
    return;
  }

  fetch(`${window.api_prefix}/api/prodfavorites/${memId}/${prodId}`, {
    method: 'POST'
  })
    .then(response => {
      if (!response.ok) throw new Error('網路回應不正常');
      return response.json();
    })
    .then(data => {
      console.log('加入收藏成功:', data);
      if (favoriteBtn) {
        favoriteBtn.innerHTML = '<i class="fas fa-heart"></i> 已收藏';
        favoriteBtn.classList.add('active');
        favoriteBtn.dataset.isFavorite = 'true';
      }
      showWishlistMessage('已添加到收藏');
    })
    .catch(error => {
      console.error('加入收藏失敗:', error);
      alert('加入收藏失敗，請稍後再試');
    });
}

// 取消收藏函數
function removeFromWishlist(prodId, favoriteBtn) {
  const memId = getCurrentMemberId();
  if (!memId) return;

  fetch(`${window.api_prefix}/api/prodfavorites/${memId}/${prodId}`, {
    method: 'DELETE'
  })
    .then(response => {
      if (!response.ok) throw new Error('網路回應不正常');
      return response.json();
    })
    .then(data => {
      console.log('取消收藏成功:', data);
      if (favoriteBtn) {
        favoriteBtn.innerHTML = '<i class="far fa-heart"></i> 加入收藏';
        favoriteBtn.classList.remove('active');
        favoriteBtn.dataset.isFavorite = 'false';
      }
      showWishlistMessage('已取消收藏');
    })
    .catch(error => {
      console.error('取消收藏失敗:', error);
      alert('取消收藏失敗，請稍後再試');
    });
}

// 切換收藏狀態
function toggleWishlist(prodId, favoriteBtn) {
  const memId = getCurrentMemberId();
  
  if (!memId) {
    alert('請先登入會員');
    window.location.href = 'login.html?redirect=' + encodeURIComponent(window.location.href);
    return;
  }
  
  const isFavorite = favoriteBtn && favoriteBtn.dataset.isFavorite === 'true';
  
  if (isFavorite) {
    // 取消收藏
    removeFromWishlist(prodId, favoriteBtn);
  } else {
    // 加入收藏
    addToWishlist(prodId, favoriteBtn);
  }
}

// 顯示收藏訊息
function showWishlistMessage(messageText = '已添加到收藏') {
  // 創建提示消息
  const message = document.createElement('div');
  message.className = 'wishlist-message';
  message.innerHTML = `
    <i class="fas fa-heart"></i>
    <span>${messageText}</span>
    <a href="user-profile.html?tab=wishlist" class="view-wishlist-btn">查看收藏</a>
  `;
  
  // 添加樣式
  message.style.cssText = `
    position: fixed;
    top: 20px;
    right: 20px;
    background-color: #E76F51;
    color: white;
    padding: 12px 20px;
    border-radius: 8px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
    z-index: 10000;
    display: flex;
    align-items: center;
    gap: 12px;
    font-family: 'Noto Sans TC', sans-serif;
    animation: slideInRight 0.3s ease;
  `;
  
  // 添加查看收藏按鈕樣式
  const viewWishlistBtn = message.querySelector('.view-wishlist-btn');
  if (viewWishlistBtn) {
    viewWishlistBtn.style.cssText = `
      background-color: white;
      color: #E76F51;
      padding: 4px 10px;
      border-radius: 4px;
      text-decoration: none;
      font-size: 14px;
      font-weight: 500;
      transition: background-color 0.2s;
    `;
    
    // 添加懸停效果
    viewWishlistBtn.addEventListener('mouseover', function() {
      this.style.backgroundColor = '#f0f0f0';
    });
    
    viewWishlistBtn.addEventListener('mouseout', function() {
      this.style.backgroundColor = 'white';
    });
  }

  // 添加動畫樣式
  if (!document.querySelector('style[data-wishlist-animation]')) {
    const style = document.createElement('style');
    style.setAttribute('data-wishlist-animation', 'true');
    style.textContent = `
      @keyframes slideInRight {
        from { transform: translateX(100%); opacity: 0; }
        to { transform: translateX(0); opacity: 1; }
      }
      @keyframes fadeOut {
        from { opacity: 1; }
        to { opacity: 0; }
      }
    `;
    document.head.appendChild(style);
  }

  // 添加到頁面
  document.body.appendChild(message);

  // 5秒後移除
  setTimeout(() => {
    message.style.animation = 'fadeOut 0.3s ease';
    setTimeout(() => {
      if (message.parentNode) {
        message.parentNode.removeChild(message);
      }
    }, 300);
  }, 5000);
}
async function loadProductRatingForCard(prodId) {
    try {
        console.log(`開始載入商品 ${prodId} 的評分`);
        
        const response = await fetch(`${window.api_prefix}/api/getProdComments?prodId=${prodId}`);
        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
        
        const data = await response.json();
        const reviews = data.data || [];
        
        const ratingElement = document.querySelector(`[data-product-id="${prodId}"]`);
        console.log(`找到評分元素:`, ratingElement);
        
        if (ratingElement) {
            const starsElement = ratingElement.querySelector('.stars');
            const countElement = ratingElement.querySelector('.rating-count');
            console.log(`星星元素:`, starsElement, `計數元素:`, countElement);
            
            const validRatings = reviews.filter(review => review.prodRating && review.prodRating > 0);
            console.log('所有評論:', reviews.length, '有效評分:', validRatings.length);
            
            if (starsElement && countElement) {
                if (validRatings.length > 0) {
                    // 有有效評分
                    const totalRating = validRatings.reduce((sum, review) => sum + review.prodRating, 0);
                    const averageRating = totalRating / validRatings.length;
                    console.log(`商品 ${prodId} 平均評分:`, averageRating);
                    
                    const starsHtml = generateStarsHtml(averageRating);
                    starsElement.innerHTML = starsHtml;
                    countElement.textContent = `(${reviews.length})`;
                } else if (reviews.length > 0) {
                    // 有評論但無評分，顯示空星星
                    console.log(`商品 ${prodId} 有評論但無評分`);
                    const starsHtml = generateStarsHtml(0);
                    starsElement.innerHTML = starsHtml;
                    countElement.textContent = `(${reviews.length})`;
                } else {
                    // 無評論無評分
                    console.log(`商品 ${prodId} 無評論無評分`);
                    ratingElement.style.display = 'none';
                    return;
                }
                
                // 顯示評分區塊
                ratingElement.style.display = 'block';
                console.log(`商品 ${prodId} 評分顯示成功 - 總評論: ${reviews.length}, 有評分: ${validRatings.length}`);
            }
        }
    } catch (error) {
        console.error(`載入商品 ${prodId} 評分失敗:`, error);
        const ratingElement = document.querySelector(`[data-product-id="${prodId}"]`);
        if (ratingElement) {
            ratingElement.style.display = 'none';
        }
    }
}
