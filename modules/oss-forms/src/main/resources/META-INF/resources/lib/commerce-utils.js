CommerceUtils = {

    refreshShoppingCartIcon: function (itemsCount){
        let miniCarts = document.getElementsByClassName("mini-cart");
        [...miniCarts].forEach(function( miniCart ){
            let badgeHolders = miniCart.querySelectorAll('.has-badge');
            [...badgeHolders].forEach(function ( badgeHolder ){

                if (itemsCount === 0){
                    badgeHolder.classList.remove('has-badge');
                } else {
                    badgeHolder.dataset.badgeCount = itemsCount;
                }
            })
        });
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
