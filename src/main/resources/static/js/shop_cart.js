// 價格快取 Map
let priceMap = new Map();

// 取得所有商品規格
async function fetchAllProductSpecs() {
  try {
    const response = await fetch(`${window.api_prefix}/api/prod-specs`);
    if (!response.ok) throw new Error('無法取得商品規格');
    const data = await response.json();
    return data.status === 'success' ? data.data : [];
  } catch (error) {
    console.error('取得商品規格失敗:', error);
    return [];
  }
}

// 從 API 取得商品單價並建立 Map
async function fetchPriceMap() {
  try {
    const response = await fetch(`${window.api_prefix}/api/products`);
    if (!response.ok) throw new Error('無法取得商品價格');
    const data = await response.json();
    if (data.status === 'success' && data.data) {
      priceMap = new Map();
      data.data.forEach(product => {
        const prodId = product.prodId;
        const discountRate = product.prodDiscount || 1;
        
        // 遍歷每個商品的顏色列表
        (product.prodColorList || []).forEach(color => {
          // 遍歷每個顏色的規格列表
          (product.prodSpecList || []).forEach(spec => {
            const key = `${prodId}-${color.prodColorId || 0}-${spec.prodSpecId}`;
            const basePrice = spec.prodSpecPrice || 0;
            priceMap.set(key, {
              basePrice: basePrice,
              discountRate: discountRate,
              discountedPrice: Math.round(basePrice * discountRate)
            });
          });
        });
      
      });
    }
  } catch (error) {
    console.error('取得價格資料失敗:', error);
  }
  // console.log('priceMap 完成後:', priceMap);
}

(async () => {
  await fetchPriceMap();
})();

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
  },
  updateItem: function(prodId, prodColorId, prodSpecId, qty) {
    let cart = this.getCart();
    const existIndex = cart.findIndex(c => c.prodId === prodId);
    if (existIndex !== -1) {
      cart[existIndex].prodColorId = prodColorId;
      cart[existIndex].prodSpecId = prodSpecId;
      cart[existIndex].cartProdQty = qty;
      this.saveCart(cart);
    }
  },
  removeItem: function(prodId, prodColorId, prodSpecId) {
    let cart = this.getCart();
    cart = cart.filter(c =>
      !(c.prodId === prodId && c.prodColorId === prodColorId && c.prodSpecId === prodSpecId)
    );
    this.saveCart(cart);
  },
  clearCart: function() {
    sessionStorage.removeItem('sessionCart');
  }
};

// 商品購物車頁面功能
document.addEventListener('DOMContentLoaded', function() {
  // 初始化購物車頁面
  initCartPage();

  // 綁定清空購物車按鈕事件
  const clearCartBtn = document.getElementById('clear-cart-btn');
  if (clearCartBtn) {
    clearCartBtn.addEventListener('click', clearCart);
  }

  // 綁定結帳按鈕事件
  const checkoutBtn = document.getElementById('checkout-btn');
  if (checkoutBtn) {
    checkoutBtn.addEventListener('click', proceedToCheckout);
  }
});

// 初始化購物車頁面
async function initCartPage() {
  showLoadingOverlay();
  try {
    // 確保價格快取已經載入
    if (priceMap.size === 0) {
      console.log('價格快取為空，重新載入價格資料...');
      await fetchPriceMap();
    }
    
    // 從API獲取購物車數據
    const cartData = await fetchCartData();
    
    // 渲染購物車項目
    await renderCartItems(cartData.items);
    
    // 更新購物車摘要
    updateCartSummary(cartData.summary);
    
    // 更新購物車計數
    // updateCartCount(cartData.totalItems);
    
    // 檢查購物車是否為空
    checkEmptyCart(cartData.items.length);
    
    hideLoadingOverlay();
  } catch (error) {
    console.error('初始化購物車頁面失敗:', error);
    showErrorMessage('載入購物車資料失敗，請稍後再試');
    hideLoadingOverlay();
  }
}

// 從API或sessionStorage獲取購物車數據
async function fetchCartData() {
  try {
    // 取得會員ID
    const memberInfo = sessionStorage.getItem('currentMember');
    const member = memberInfo ? JSON.parse(memberInfo) : null;
    const memId = member ? member.memId : null;
    console.log('取得會員ID:', memId);

    if (memId) {
      // 已登入使用者，從 API 獲取資料
      const response = await fetch(`${window.api_prefix}/api/getCart?memId=${memId}`);
      if (!response.ok) {
        throw new Error(`網路回應不正常: ${response.status} ${response.statusText}`);
      }
      const data = await response.json();
      if (data.status === 'success' && data.data) {
        const cartItems = data.data;
        const totalItems = cartItems.reduce((sum, item) => sum + item.cartProdQty, 0);
        const subtotal = cartItems.reduce((sum, item) => sum + (item.prodPrice * item.cartProdQty), 0);
        // console.log('送出API資料', { memId, data });
        return {
          items: cartItems,
          totalItems: totalItems,
          summary: {
            subtotal: subtotal,
            shipping: 0,
            discount: 0,
            total: subtotal
          }
        };
      } else {
        // 沒有資料，返回空的購物車
        return {
          items: [],
          totalItems: 0,
          summary: {
            subtotal: 0,
            shipping: 0,
            discount: 0,
            total: 0
          }
        };
      }
    } else {
      // 未登入使用者，從 sessionStorage 獲取資料，並補全商品資訊
      const cartItems = sessionCartManager.getCart();
      // 依序補全每個商品的詳細資訊
      const enrichedItems = await Promise.all(cartItems.map(async (item) => {
        try {
          const response = await fetch(`${window.api_prefix}/api/products/${item.prodId}`);
          if (response.ok) {
            const data = await response.json();
            if (data.status === 'success' && data.data) {
              const product = data.data;
              // 找到對應的顏色與規格
              const colorInfo = (product.prodColorList || []).find(c => c.prodColorId === item.prodColorId);
              const specInfo = (product.prodSpecList || []).find(s => s.prodSpecId === item.prodSpecId);
              // 折扣計算
              const discountRate = product.prodDiscount != null ? product.prodDiscount : 1;
              const basePrice = specInfo ? specInfo.prodSpecPrice : product.prodPrice;
              const discountedPrice = Math.round(basePrice * discountRate);
              return {
                ...item,
                prodName: product.prodName,
                prodPrice: discountedPrice, // 售價用折扣後價格
                basePrice: basePrice,      // 原價
                discountRate: discountRate,
                colorName: colorInfo ? colorInfo.colorName : '',
                specName: specInfo ? specInfo.prodSpecName : '',
                prodImage: product.prodImage
              };
            }
          }
        } catch (e) {}
        // 若查詢失敗，回傳原本資料
        return { ...item, prodName: '', prodPrice: 0, colorName: '', specName: '', prodImage: '' };
      }));
      const totalItems = enrichedItems.reduce((sum, item) => sum + item.cartProdQty, 0);
      const subtotal = enrichedItems.reduce((sum, item) => sum + (item.prodPrice ? item.prodPrice * item.cartProdQty : 0), 0);
      // console.log('送出API資料', { memId: null, enrichedItems });
      return {
        items: enrichedItems,
        totalItems: totalItems,
        summary: {
          subtotal: subtotal,
          shipping: 0,
          discount: 0,
          total: subtotal
        }
      };
    }
  } catch (error) {
    console.error('獲取購物車數據失敗:', error);
    throw error;
  }
}

// 渲染購物車項目（支援動態取得顏色/規格選項）
async function renderCartItems(items) {
  const cartItemsContainer = document.getElementById('cart-items-container');
  if (!cartItemsContainer) return;
  cartItemsContainer.innerHTML = '';
  if (!items || items.length === 0) return;

  for (const item of items) {
    // 動態取得 color/spec options
    let colorOptions = [];
    let specOptions = [];
    try {
      const res = await fetch(`${window.api_prefix}/api/products/${item.prodId}`);
      if (res.ok) {
        const data = await res.json();
        if (data.status === 'success' && data.data) {
          colorOptions = (data.data.prodColorList || []).map(c => ({ id: c.prodColorId, name: c.colorName }));
          specOptions = (data.data.prodSpecList || []).map(s => ({ id: s.prodSpecId, name: s.prodSpecName }));
        }
      }
    } catch (e) {
      // 若失敗則 options 為空
    }
    const itemElement = createCartItemElement(item, colorOptions, specOptions);
    cartItemsContainer.appendChild(itemElement);
  }
  // 綁定事件
  bindCartItemEvents();
}

// 創建購物車項目元素（支援 select）
function createCartItemElement(item, colorOptions, specOptions) {
  const itemDiv = document.createElement('div');
  itemDiv.className = 'cart-item';
  itemDiv.dataset.id = item.prodId;
  itemDiv.dataset.colorId = item.prodColorId;
  itemDiv.dataset.specId = item.prodSpecId;
  // 新增：記錄原本的顏色/規格
  itemDiv.dataset.oldColorId = item.prodColorId;
  itemDiv.dataset.oldSpecId = item.prodSpecId;

  // 顏色選單
  let colorSelect = '<select class="color-select">';
  if (colorOptions && colorOptions.length > 0) {
    colorOptions.forEach(opt => {
      colorSelect += `<option value="${opt.id}" ${opt.id === item.prodColorId ? 'selected' : ''}>${opt.name}</option>`;
    });
  } else {
    colorSelect += `<option value="${item.prodColorId}" selected>${item.colorName || '載入中...'}</option>`;
  }
  colorSelect += '</select>';

  // 規格選單
  let specSelect = '<select class="spec-select">';
  if (specOptions && specOptions.length > 0) {
    specOptions.forEach(opt => {
      specSelect += `<option value="${opt.id}" ${opt.id === item.prodSpecId ? 'selected' : ''}>${opt.name}</option>`;
    });
  } else {
    specSelect += `<option value="${item.prodSpecId}" selected>${item.specName || '載入中...'}</option>`;
  }
  specSelect += '</select>';

  // 從價格快取取得價格資訊
  const priceKey = `${item.prodId}-${item.prodColorId || 0}-${item.prodSpecId}`;
  const priceInfo = priceMap.get(priceKey) || { basePrice: item.prodPrice || 0, discountRate: item.discountRate || 1, discountedPrice: item.prodPrice || 0 };
  
  // console.log('createCartItemElement 價格資訊:', {
  //   prodId: item.prodId,
  //   prodColorId: item.prodColorId,
  //   prodSpecId: item.prodSpecId,
  //   priceKey,
  //   priceInfo,
  //   priceMapSize: priceMap.size,
  //   fallbackPrice: item.prodPrice
  // });
  
  // 商品價格顯示
  let priceHtml = `<span class="current-price" data-base-price="${priceInfo.basePrice}" data-discount-rate="${priceInfo.discountRate}">NT$ ${priceInfo.discountedPrice.toLocaleString()}</span>`;
  if (priceInfo.discountRate && priceInfo.discountRate < 1 && priceInfo.basePrice) {
    priceHtml += ` <span class="original-price">NT$ ${priceInfo.basePrice.toLocaleString()}</span>`;
  }

  itemDiv.innerHTML = `
    <div class="product-info">
      <h3>${item.prodName}</h3>
      <div class="product-color">顏色: ${colorSelect}</div>
      <div class="product-spec">規格: ${specSelect}</div>
      <div class="product-price">
        ${priceHtml}
      </div>
    </div>
    <div class="quantity-selector">
      <button class="btn-decrease">-</button>
      <input type="number" value="${item.cartProdQty}" min="1" max="10">
      <button class="btn-increase">+</button>
    </div>
    <div class="product-subtotal">
      <span>NT$ ${(priceInfo.discountedPrice * item.cartProdQty).toLocaleString()}</span>
    </div>
    <button class="btn-remove">
      <i class="fas fa-trash-alt"></i>
    </button>
  `;

  // 綁定 select/數量事件 - 避免重新載入頁面
  itemDiv.querySelector('.color-select').addEventListener('change', function() {
    updateCartItemPrice(itemDiv);
    updateCartItem(itemDiv);
    // 更新原本的顏色id
    itemDiv.dataset.oldColorId = itemDiv.querySelector('.color-select').value;
    // 同時更新當前的顏色id
    itemDiv.dataset.colorId = itemDiv.querySelector('.color-select').value;
  });
  itemDiv.querySelector('.spec-select').addEventListener('change', function() {
    updateCartItemPrice(itemDiv);
    updateCartItem(itemDiv);
    // 更新原本的規格id
    itemDiv.dataset.oldSpecId = itemDiv.querySelector('.spec-select').value;
    // 同時更新當前的規格id
    itemDiv.dataset.specId = itemDiv.querySelector('.spec-select').value;
  });
  // input change 事件只呼叫一次 updateCartItem
  itemDiv.querySelector('.quantity-selector input').addEventListener('change', function() {
    updateCartItemPrice(itemDiv);
    updateCartItem(itemDiv);
  });

  // + - 按鈕只改變 input.value 並 dispatch change 事件
  itemDiv.querySelector('.btn-increase').addEventListener('click', function() {
    const input = itemDiv.querySelector('.quantity-selector input');
    let value = parseInt(input.value) || 1;
    if (value < parseInt(input.max)) {
      input.value = value + 1;
      input.dispatchEvent(new Event('change'));
    }
  });
  itemDiv.querySelector('.btn-decrease').addEventListener('click', function() {
    const input = itemDiv.querySelector('.quantity-selector input');
    let value = parseInt(input.value) || 1;
    if (value > parseInt(input.min)) {
      input.value = value - 1;
      input.dispatchEvent(new Event('change'));
    }
  });

  // 綁定移除按鈕事件
  itemDiv.querySelector('.btn-remove').addEventListener('click', function() {
    removeCartItem(itemDiv);
  });

  return itemDiv;
}

// 綁定購物車項目事件
function bindCartItemEvents() {
  // 移除按鈕事件已經在 createCartItemElement 中綁定
  // 數量變更事件也已經在 createCartItemElement 中綁定
  // 此函數保留以備未來擴展使用
}

// 新增：從價格快取更新商品價格和總計
function updateCartItemPrice(cartItem) {
  const prodId = parseInt(cartItem.dataset.id);
  const prodColorId = parseInt(cartItem.querySelector('.color-select').value);
  const prodSpecId = parseInt(cartItem.querySelector('.spec-select').value);
  const quantity = parseInt(cartItem.querySelector('.quantity-selector input').value);
  
  // 從價格快取取得價格資訊
  const priceKey = `${prodId}-${prodColorId || 0}-${prodSpecId}`;
  const priceInfo = priceMap.get(priceKey);
  
  // console.log('updateCartItemPrice 除錯資訊:', {
  //   prodId,
  //   prodColorId,
  //   prodSpecId,
  //   quantity,
  //   priceKey,
  //   priceInfo,
  //   priceMapSize: priceMap.size
  // });
  
  if (priceInfo) {
    // 更新價格顯示
    const currentPriceElement = cartItem.querySelector('.current-price');
    if (currentPriceElement) {
      currentPriceElement.textContent = `NT$ ${priceInfo.discountedPrice.toLocaleString()}`;
      currentPriceElement.dataset.basePrice = priceInfo.basePrice;
      currentPriceElement.dataset.discountRate = priceInfo.discountRate;
      // console.log('價格更新成功:', priceInfo.discountedPrice);
    }
    
    // 更新原價顯示
    const originalPriceElement = cartItem.querySelector('.original-price');
    if (priceInfo.discountRate && priceInfo.discountRate < 1 && priceInfo.basePrice) {
      if (originalPriceElement) {
        originalPriceElement.textContent = `NT$ ${priceInfo.basePrice.toLocaleString()}`;
      } else {
        // 如果沒有原價元素，則新增
        currentPriceElement.insertAdjacentHTML('afterend', ` <span class="original-price">NT$ ${priceInfo.basePrice.toLocaleString()}</span>`);
      }
    } else if (originalPriceElement) {
      // 如果沒有折扣，移除原價元素
      originalPriceElement.remove();
    }
    
    // 更新商品小計
    const subtotalElement = cartItem.querySelector('.product-subtotal span');
    if (subtotalElement) {
      const newSubtotal = priceInfo.discountedPrice * quantity;
      subtotalElement.textContent = `NT$ ${newSubtotal.toLocaleString()}`;
      // console.log('小計更新成功:', newSubtotal);
    }
    
    // 更新購物車摘要
    recalculateCartSummary();
  } else {
    console.warn('找不到價格資訊:', priceKey);
    console.log('可用的價格快取 keys:', Array.from(priceMap.keys()));
  }
}

// 修改 updateCartItem，未登入時用 sessionCartManager
async function updateCartItem(cartItem) {
  const prodId = parseInt(cartItem.dataset.id);
  const oldProdColorId = parseInt(cartItem.dataset.oldColorId);
  const oldProdSpecId = parseInt(cartItem.dataset.oldSpecId);
  const prodColorId = parseInt(cartItem.querySelector('.color-select').value);
  const prodSpecId = parseInt(cartItem.querySelector('.spec-select').value);
  const quantity = parseInt(cartItem.querySelector('.quantity-selector input').value);
  const memberInfo = sessionStorage.getItem('currentMember');
  const member = memberInfo ? JSON.parse(memberInfo) : null;
  const memId = member ? member.memId : null;
  console.log('取得會員ID:', memId);

  if (memId) {
    // 已登入使用者，呼叫 API 更新購物車
    try {
      const response = await fetch(`${window.api_prefix}/api/updateCart`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          memId, prodId,
          oldProdColorId, oldProdSpecId,
          prodColorId, prodSpecId,
          cartProdQty: quantity
        })
      });
      if (!response.ok) throw new Error('網路回應不正常');
      const data = await response.json();
      if (data.status === 'success') {
        // 更新成功，不需要重新載入頁面
        console.log('購物車更新成功');
      } else {
        alert('更新失敗');
      }
    } catch (error) {
      alert('更新失敗，請稍後再試');
    }
  } else {
    // 未登入，直接操作 sessionStorage，傳入新選擇的顏色/規格
    sessionCartManager.updateItem(prodId, prodColorId, prodSpecId, quantity);
    console.log('Session 購物車更新成功');
  }
}

// 修改 removeCartItem，未登入時用 sessionCartManager
async function removeCartItem(cartItem) {
  if (!confirm('確定要移除這個商品嗎？')) {
    return;
  }
  const prodId = parseInt(cartItem.dataset.id);
  const prodColorId = parseInt(cartItem.querySelector('.color-select').value);
  const prodSpecId = parseInt(cartItem.querySelector('.spec-select').value);
  const memberInfo = sessionStorage.getItem('currentMember');
  const member = memberInfo ? JSON.parse(memberInfo) : null;
  const memId = member ? member.memId : null;
  console.log('取得會員ID:', memId);

  if (memId) {
    // 已登入使用者，呼叫 API 移除商品
    try {
      const response = await fetch(`${window.api_prefix}/api/removeCart`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          memId: memId,
          prodId: prodId,
          prodColorId: prodColorId,
          prodSpecId: prodSpecId
        })
      });
      if (!response.ok) {
        throw new Error('網路回應不正常');
      }
      const data = await response.json();
      if (data.status === 'success') {
        // 移除成功，直接從 DOM 移除元素
        cartItem.remove();
        recalculateCartSummary();
        checkEmptyCart(document.querySelectorAll('.cart-item').length);
        console.log('購物車商品移除成功');
      } else {
        alert('移除商品失敗');
      }
    } catch (error) {
      alert('移除商品失敗，請稍後再試');
    }
  } else {
    // 未登入，直接操作 sessionStorage
    sessionCartManager.removeItem(prodId, prodColorId, prodSpecId);
    // 直接從 DOM 移除元素
    cartItem.remove();
    recalculateCartSummary();
    checkEmptyCart(document.querySelectorAll('.cart-item').length);
    console.log('Session 購物車商品移除成功');
  }
}

// 修改 clearCart，未登入時用 sessionCartManager
async function clearCart() {
  if (!confirm('確定要清空購物車嗎？此操作無法復原。')) {
    return;
  }
  const memberInfo = sessionStorage.getItem('currentMember');
  const member = memberInfo ? JSON.parse(memberInfo) : null;
  const memId = member ? member.memId : null;
  console.log('取得會員ID:', memId);
  if (memId) {
    // 已登入使用者，呼叫 API 清空購物車
    try {
      const response = await fetch(`${window.api_prefix}/api/clearCart?memId=${memId || ''}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        }
      });
      if (!response.ok) {
        throw new Error('網路回應不正常');
      }
      const data = await response.json();
      if (data.status === 'success') {
        // 清空成功，直接清空 DOM 元素
        const cartItemsContainer = document.getElementById('cart-items-container');
        if (cartItemsContainer) {
          cartItemsContainer.innerHTML = '';
        }
        recalculateCartSummary();
        checkEmptyCart(0);
        console.log('購物車清空成功');
      } else {
        alert('清空購物車失敗');
      }
    } catch (error) {
      alert('清空購物車失敗，請稍後再試');
    }
  } else {
    // 未登入，直接操作 sessionStorage
    sessionCartManager.clearCart();
    // 直接清空 DOM 元素
    const cartItemsContainer = document.getElementById('cart-items-container');
    if (cartItemsContainer) {
      cartItemsContainer.innerHTML = '';
    }
    recalculateCartSummary();
    checkEmptyCart(0);
    console.log('Session 購物車清空成功');
  }
}

// 設定運費常數
const SHIPPING_FEE = 60;

// 更新購物車摘要
function updateCartSummary(summary) {
  // 商品總計 = 商品費用 * 數量的加總
  const subtotalElement = document.getElementById('cart-subtotal');
  if (subtotalElement) {
    subtotalElement.textContent = `NT$ ${Number(summary.subtotal).toLocaleString()}`;
  }

  // 運費 - 固定為 SHIPPING_FEE
  const shippingElement = document.querySelector('.shipping-fee');
  if (shippingElement) {
    shippingElement.textContent = `NT$ ${Number(SHIPPING_FEE).toLocaleString()}`;
  }

  // 計算總計（商品總計 + 運費）
  const finalTotal = Number(summary.subtotal) + Number(SHIPPING_FEE);
  const totalElement = document.getElementById('cart-total');
  if (totalElement) {
    totalElement.textContent = `NT$ ${finalTotal.toLocaleString()}`;
  }

  // 啟用/禁用結帳按鈕
  const checkoutButton = document.getElementById('checkout-btn');
  if (checkoutButton) {
    checkoutButton.disabled = finalTotal <= 0;
  }
}


// 檢查購物車是否為空
function checkEmptyCart(itemCount) {
  const emptyCart = document.getElementById('cart-empty');
  const cartItemsContainer = document.getElementById('cart-items-container');
  const orderSummary = document.getElementById('order-summary');
  
  if (emptyCart && cartItemsContainer && orderSummary) {
    if (itemCount <= 0) {
      emptyCart.style.display = 'flex';
      cartItemsContainer.style.display = 'none';
      orderSummary.style.display = 'none';
    } else {
      emptyCart.style.display = 'none';
      cartItemsContainer.style.display = 'block';
      orderSummary.style.display = 'block';
    }
  }
}

// 前往結帳
function proceedToCheckout() {
  window.location.href = 'shop-checkout.html';
}

// 顯示載入畫面
function showLoadingOverlay() {
  // 檢查是否已存在載入畫面
  if (document.querySelector('.loading-overlay')) return;
  
  const overlay = document.createElement('div');
  overlay.className = 'loading-overlay';
  overlay.innerHTML = `
    <div class="loading-spinner"></div>
    <p>載入中...</p>
  `;
  
  document.body.appendChild(overlay);
}

// 隱藏載入畫面
function hideLoadingOverlay() {
  const overlay = document.querySelector('.loading-overlay');
  if (overlay) {
    overlay.remove();
  }
}

// 顯示錯誤訊息
function showErrorMessage(message) {
  // 創建錯誤訊息元素
  const errorDiv = document.createElement('div');
  errorDiv.className = 'error-message';
  errorDiv.innerHTML = `
    <i class="fas fa-exclamation-circle"></i>
    <span>${message}</span>
  `;
  
  document.body.appendChild(errorDiv);
  
  // 3秒後移除
  setTimeout(() => {
    errorDiv.className = 'error-message fadeOut';
    setTimeout(() => {
      if (errorDiv.parentNode) {
        errorDiv.parentNode.removeChild(errorDiv);
      }
    }, 300);
  }, 3000);
}

// 新增：即時計算商品總計、總計
function recalculateCartSummary() {
  let subtotal = 0;
  console.log('開始重新計算購物車摘要');
  
  document.querySelectorAll('.cart-item').forEach((item, index) => {
    const prodId = parseInt(item.dataset.id);
    const prodColorId = parseInt(item.querySelector('.color-select').value);
    const prodSpecId = parseInt(item.querySelector('.spec-select').value);
    const qty = parseInt(item.querySelector('.quantity-selector input').value) || 1;
    
    // 從價格快取取得價格
    const priceKey = `${prodId}-${prodColorId || 0}-${prodSpecId}`;
    const priceInfo = priceMap.get(priceKey);
    const price = priceInfo ? priceInfo.discountedPrice : 0;
    
    const itemSubtotal = price * qty;
    subtotal += itemSubtotal;
    
    // console.log(`商品 ${index + 1} 計算:`, {
    //   prodId,
    //   prodColorId,
    //   prodSpecId,
    //   qty,
    //   priceKey,
    //   price,
    //   itemSubtotal
    // });
    
    // 更新每個商品小計
    const subtotalElement = item.querySelector('.product-subtotal span');
    if (subtotalElement) {
      subtotalElement.textContent = `NT$ ${itemSubtotal.toLocaleString()}`;
    }
  });
  
  // console.log('購物車總計:', subtotal);
  
  // 商品總計
  const subtotalElement = document.getElementById('cart-subtotal');
  if (subtotalElement) {
    subtotalElement.textContent = `NT$ ${subtotal.toLocaleString()}`;
  }
  
  // 運費
  const shipping = Number(SHIPPING_FEE);
  const shippingElement = document.querySelector('.shipping-fee');
  if (shippingElement) {
    shippingElement.textContent = `NT$ ${shipping.toLocaleString()}`;
  }
  
  // 總計
  const total = subtotal + shipping;
  const totalElement = document.getElementById('cart-total');
  if (totalElement) {
    totalElement.textContent = `NT$ ${total.toLocaleString()}`;
  }
  
  console.log('購物車摘要更新完成:', { subtotal, shipping, total });
}