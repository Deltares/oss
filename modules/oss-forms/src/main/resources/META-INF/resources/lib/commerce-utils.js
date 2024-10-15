CommerceUtils = {
    addToCart: function (actionUrl, skuId, callback) {
        let order = Liferay.CommerceContext.order;
        if (order) {
            this.callAddCartItem(actionUrl, order.orderId, skuId, callback);
        }
    },

    removeFromCart: function (cartItemId, callback) {
        let order = Liferay.CommerceContext.order;
        if (order) {
            this.callDeleteCartItem(cartItemId, callback);
        }
    },

    getButtonByDataProductId: function (buttons, searchId) {
        let foundButton;
        [...buttons].forEach(function (button) {
            let skuId = button.getAttribute('data-product-id')
            if (Number(skuId) === searchId) {
                foundButton = button;
            }
        });
        return foundButton;
    },

    setButtonTexts: function (buttons, callback) {
        let order = Liferay.CommerceContext.order;
        if (order) {
            var orderId = order.orderId;
            this.callGetCartItems(orderId, function (json) {
                let cartItems = json.cartItems;
                if (cartItems) {
                    for (const cartItem of cartItems) {
                        var foundButton = CommerceUtils.getButtonByDataProductId(buttons, cartItem.skuId);
                        if (foundButton) {
                            callback(foundButton)
                        }
                    }
                }
            });
        }
    },

    callAddCartItem: function (actionUrl, orderId, skuId, callback) {

        let data = {
            "cartItems": [{
                "quantity": 1,
                "skuId": skuId
            }]
        }
        let request = new XMLHttpRequest();
        request.open('POST', actionUrl + "?cmd=add&cpInstanceId=" + skuId);
        request.setRequestHeader("Content-Type", "application/json");
        // request.setRequestHeader("x-csrf-token", Liferay.authToken);
        request.onreadystatechange = function () {
            if (request.readyState === 3) {
                callback(request.response);
            }
        }
        request.send(JSON.stringify(data));


    },

    callUpdateCartItem: function (cartItemId, quantity, callback) {

        let data = {"quantity": quantity}
        let request = new XMLHttpRequest();
        request.open('PATCH', "/o/headless-commerce-delivery-cart/v1.0/cart-items/" + cartItemId);
        request.setRequestHeader("Content-Type", "application/json");
        request.setRequestHeader("x-csrf-token", Liferay.authToken);
        request.onreadystatechange = function () {
            if (request.readyState === 3) {
                callback(JSON.parse(request.response));
            }
        }
        request.send(JSON.stringify(data));


    },

    callDeleteCartItem: function (cartItemId, callback) {

        let request = new XMLHttpRequest();
        request.open('DELETE', "/o/headless-commerce-delivery-cart/v1.0/cart-items/" + cartItemId);
        request.setRequestHeader("x-csrf-token", Liferay.authToken);
        request.onreadystatechange = function () {
            if (request.readyState === 4) {
                callback(request.status === 204);
            }
        }
        request.send();
    },

    callGetCartItems: function (orderId, callback) {

        let request = new XMLHttpRequest();
        request.open('GET', "/o/headless-commerce-delivery-cart/v1.0/carts/" + orderId + "?nestedFields=cartItems");
        request.setRequestHeader("x-csrf-token", Liferay.authToken);
        request.onreadystatechange = function () {
            if (request.status === 200 && request.readyState === 3) {
                callback(JSON.parse(request.response));
            }

        }
        request.send();

    }
}
