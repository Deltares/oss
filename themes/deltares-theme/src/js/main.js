FormStepper = {

    pluginName : "formStepper",
    form : {},
    element: {},

    init: function (element, form){
        this.element = element;
        this.form = form;
        this.registerEvents();

        return this;
    },

    registerEvents: function () {

        let nextButton = this.element.querySelector('.next-step');
        let prevButton = this.element.querySelector('.prev-step');

        nextButton.addEventListener('click', {plugin: this, action: 'next'}, this._navigate);
        prevButton.addEventListener('click', {plugin: this, action: 'prev'}, this._navigate);

        this._getSubmitButton().addEventListener('click', function () {
            if (this._isFormValid()) {
                this.form.preSubmitAction();
                this.form.submit();
            }
        });
    },

    _isFormValid: function () {
        this.form.validate();
        return !this.form.hasErrors();
    },

    _navigate: function (event) {
        let plugin = event.data.plugin;
        let action = event.data.action;
        let isFormValid = plugin._isFormValid() && plugin.form.validateFirstStep();

        let element = plugin.element;
        let active = element.querySelector('li.active');
        let next = active.nextElementSibling();
        while (next.classList.contains('disabled') && next.nextElementSibling().length > 0) {
            next = next.nextElementSibling();
        }
        let prev = active.previousElementSibling();
        while (prev.classList.contains('disabled') && prev.previousElementSibling.length > 0) {
            prev = prev.previousElementSibling();
        }

        let isLast = (next.nextElementSibling().length === 0);
        let isFirst = (prev.previousElementSibling().length === 0);

        if (isFormValid || 'prev' === action) {
            if ('next' === action && next.length) {
                plugin._hideStep(active);
                plugin._showStep(next);

                if (isLast) {
                    plugin._disableButton(action);
                    plugin._showSubmitButton();
                } else {
                    plugin._enableButton('prev');
                }
                active.removeClass('icon-circle-blank');
                active.addClass('icon-circle completed');
            } else if ('prev' === action && prev.length) {
                plugin._hideStep(active);
                plugin._showStep(prev);

                if (isFirst) {
                    plugin._disableButton(action);
                } else {
                    plugin._enableButton('next');
                    plugin._hideSubmitButton();
                }
            }
        }
    },

    _showStep: function (element) {
        let anchor = element.querySelector('a');
        let pane = anchor.getAttribute('href');
        this._activateElement(element);
        this._activateElement(anchor);
        this._activateElement(pane);
    },

    _hideStep: function (element) {
        let anchor = element.querySelector('a');
        let pane = anchor.getAttribute('href');
        this._deactivateElement(element);
        this._deactivateElement(anchor);
        this._deactivateElement(pane);
    },

    _enableButton: function (button) {
        let selector = document.querySelector('.' + button + '-step');
        selector.removeClass('disabled');
        selector.addClass('enabled');
    },

    _disableButton: function (button) {
        let selector = document.querySelector('.' + button + '-step');
        selector.removeClass('enabled');
        selector.addClass('disabled');
    },

    _activateElement: function (element) {
        element.addClass('active');
    },

    _deactivateElement: function (element) {
        element.removeClass('active');
    },

    _showSubmitButton: function () {
        let submitButton = this._getSubmitButton();
        let nextButton = this._getNextButton();
        submitButton.removeClass('d-none');
        submitButton.addClass('d-inline');
        nextButton.addClass('d-none');
        nextButton.removeClass('d-inline');
    },

    _hideSubmitButton: function () {
        let submitButton = this._getSubmitButton();
        let nextButton = this._getNextButton();
        submitButton.addClass('d-none');
        submitButton.removeClass('d-inline');
        nextButton.addClass('d-inline');
        nextButton.removeClass('d-none');
    },

    _getSubmitButton: function () {
        return this.element.querySelector("a.submit");
    },

    _getNextButton: function () {
        return this.element.querySelector('.next-step');
    }
}

ShoppingCart = {

    pluginName : "shoppingCart",
    defaults: {
            'languageKeys': {
                'add-to-cart': 'Add to cart',
                'remove-from-cart': 'Remove from cart'
            }
        },
    options : {},
    cart : {},

    init: function (options){
        this.options = options
        this.initCart();
        this.registerEvents();

        return this;
    },
    initCart: function () {
        this._loadCart();

        if (this.cart === null) {
            this.cart = {
                'userId': this._getUserId(),
                'siteId' : this._getSiteId(),
                'items': [],
                'downloads': []
            };

            this._saveCart();
        } else if (! this.cart.hasOwnProperty('downloads') ){
            this.cart['downloads'] = []
            this._saveCart();
        }

        this.refreshCart();

        this._registerCheckoutURLBuilder();
    },

    registerEvents: function () {
        registerClick('.add-to-cart', 'registration');
        registerClick('.add-download-to-cart', 'download');

        let element = document.querySelector('.c-cart__checkout__cart');
        if (element) {
            element.addEventListener('click', function (e) {
                e.preventDefault();
                buildCheckoutURL();
            });
        }

        function registerClick(clazz, type) {
            let clazzElm = document.querySelector(clazz);
            if (clazzElm){
                clazzElm.addEventListener('click', function (e) {
                    e.preventDefault();
                    let id = this.dataset.articleId;

                    if (this._contains(id, type)) {
                        this._removeFromCart(id, type);
                    } else {
                        this._addToCart(id, type);
                    }
                    this._updateLabel(this, type);
                    this.refreshCart();
                });
            }

        }
    },

    refreshCart: function () {
        let element = document.querySelector('.c-cart__item__counter');
        if (element){
            element.textContent = (this.cart.items.length + this.cart.downloads.length);
        }

        document.querySelectorAll('.add-to-cart').forEach(value => function (){
            this._updateLabel(value, 'registration');
        });
        document.querySelectorAll('.add-download-to-cart').forEach(value => function (){
            this._updateLabel(value, 'download');
        });
    },

    clearCart: function () {
        this.cart.items = [];
        this._saveCart();
        this.refreshCart();
    },

    clearDownloadsCart: function (){
        this.cart.downloads = [];
        this._saveCart();
        this.refreshCart();
    },

    _registerCheckoutURLBuilder: function () {
        let plugin = this;
        Liferay.provide(
            window,
            'buildCheckoutURL',
            function () {
                let cartUrl;
                let action;
                let ids;
                let portletId;
                if (plugin.cart.downloads.length > 0){
                    cartUrl = downloadCartURL;
                    action = 'download';
                    ids = plugin.cart.downloads.join(',');
                    portletId = 'DownloadFormPortlet';
                } else {
                    cartUrl = checkoutCartURL;
                    action = 'register';
                    ids = plugin.cart.items.join(',');
                    portletId  = 'RegistrationFormPortlet';
                }
                let portletURL = Liferay.Util.PortletURL.createPortletURL(cartUrl,
                    {
                        'p_p_id' : portletId,
                        'p_p_mode' : 'view',
                        'p_p_state' : 'normal',
                        'p_p_Lifecycle' : 0,
                        'action' : action,
                        'ids' : ids,
                        'callerURL' : window.location.href
                    });

                if (undefined !== portletURL) {
                    window.location = portletURL.toString();
                }
            },
            ['liferay-portlet-url']
        );
    },

    _updateLabel: function (element, type) {
        let plugin = this;
        let id = element.data('articleId');
        if (plugin._contains(id, type)) {
            element.textContent = (plugin._getLanguageKey('remove-from-cart'));
        } else {
            element.textContent = (plugin._getLanguageKey('add-to-cart'));
        }
    },

    _getLanguageKey: function (key) {
        return this.config.languageKeys[key];
    },

    _getUserId: function () {
        return Liferay.ThemeDisplay.getUserId();
    },

    _getSiteId: function () {
        return Liferay.ThemeDisplay.getSiteGroupId();
    },

    _addToCart: function (id, type) {

        if (!this._contains(id, type)) {
            if (type === 'registration'){
                this.cart.items.push(id);
            } else if (type === 'download'){
                this.cart.downloads.push(id)
            }
            this._saveCart();
        }
    },

    _removeFromCart: function (id, type) {
        if (this._contains(id, type)) {
            if (type === 'registration'){
                this.cart.items = this.cart.items.filter(item => item !== id);
            } else if (type === 'download' ) {
                this.cart.downloads = this.cart.downloads.filter(item => item !== id);
            }
            this._saveCart();
        }
    },

    _contains: function (id, type) {

        const contains = (newItem, list) => list.some(item => newItem === item);
        if ( type === 'registration'){
            return contains(id, this.cart.items);
        } else if(type === 'download'){
            return contains(id, this.cart.downloads);
        } else {
            return false;
        }
    },

    _loadCart: function () {
        this.cart = JSON.parse(localStorage.getItem(this._getSiteId() + '/shoppingCart'));
    },

    _saveCart: function () {
        localStorage.setItem(this._getSiteId() + '/shoppingCart', JSON.stringify(this.cart));
    }
}

    // Mobile menu
    var mobileContainer = document.querySelector('.mobile-container');
    var mobileButtons = mobileContainer.querySelectorAll('.mobile-btn');
    var mobileMenuButton = mobileContainer.querySelector('.mobile-menu-btn');
    var mobileLangButton = mobileContainer.querySelector('.mobile-lang-btn');
    var mobileMainnav = mobileContainer.querySelector('.mobile-mainnav');

    mobileButtons.forEach(function (mobileButton){
        mobileButton.addEventListener('click', function() {
            if (this.classList.contains('opened')) {
                menuOverlay.classList.remove('is-open');
                mobileContainer.querySelector('.mobile-navpanel').classList.remove('is-open');
                mobileContainer.querySelector('.language-panel').classList.remove('is-open');
                navMenu.querySelector('button').setAttribute('aria-expanded', 'false');
                navMenu.querySelector('button').classList.remove('opened'); // Reset all nav-menu buttons
                navMenu.querySelector('.nav-subpanel').classList.remove('is-open'); // Reset open nav-subpanels
                document.querySelector('body').classList.remove('overflow-hidden');
            } else {
                mobileContainer.querySelector('.mobile-icon').classList.remove('hidden');
                mobileContainer.querySelector('.mobile-icon-close').classList.add('hidden');
                mobileButton.setAttribute('aria-expanded', 'false');
                mobileButton.classList.remove('opened');
                menuOverlay.classList.add('is-open');
                document.querySelector('body').classList.add('overflow-hidden');
            }
        });
    });

    mobileMenuButton.addEventListener('click', function() {
        if (this.classList.contains('opened')) {
            this.setAttribute('aria-expanded', 'false');
            this.classList.remove('opened');
            this.querySelector('.mobile-icon-menu').classList.remove('hidden');
            this.querySelector('.mobile-icon-close').classList.add('hidden');
        } else {
            this.setAttribute('aria-expanded', 'true');
            this.classList.add('opened');
            this.querySelector('.mobile-icon-menu').classList.add('hidden');
            this.querySelector('.mobile-icon-close').classList.remove('hidden');
            mobileContainer.querySelector('.mobile-navpanel').classList.add('is-open');
            mobileContainer.querySelector('.language-panel').classList.remove('is-open');
        }
    });

    mobileLangButton.addEventListener('click', function() {
        if (this.classList.contains('opened')) {
            this.setAttribute('aria-expanded', 'false');
            this.classList.remove('opened');
            this.querySelector('.mobile-icon-lang').classList.remove('hidden');
            this.querySelector('.mobile-icon-close').classList.add('hidden');
        } else {
            this.setAttribute('aria-expanded', 'true');
            this.classList.add('opened');
            this.querySelector('.mobile-icon-lang').classList.add('hidden');
            this.querySelector('.mobile-icon-close').classList.remove('hidden');
            mobileContainer.querySelector('.mobile-navpanel').classList.remove('is-open');
            mobileContainer.querySelector('.language-panel').classList.add('is-open');
        }
    });

    var mobileMainnavs = mobileMainnav.querySelectorAll('button');
    mobileMainnavs.forEach(function (mobileMainnavButton) {

        mobileMainnavButton.addEventListener('click', function () {
            let mobileMainnavSubpanel = this.nextElementSibling;
            let svgElement = this.querySelector('svg');
            if (this.classList.contains('opened')) {
                this.setAttribute('aria-expanded', 'false');
                this.classList.remove('opened');
                svgElement.classList.remove('-rotate-180');
                svgElement.classList.add('rotate-0');
                if (mobileMainnavSubpanel) {
                    mobileMainnavSubpanel.classList.remove('is-open');
                }
            } else {
                this.setAttribute('aria-expanded', 'true');
                this.classList.add('opened');
                svgElement.classList.remove('rotate-0')
                svgElement.classList.add('-rotate-180');
                if (mobileMainnavSubpanel) {
                    mobileMainnavSubpanel.classList.add('is-open');
                }
            }
        });
    });
    // Main navigation (desktop)
    var navMenu = document.querySelector('.main-navbar .nav-menu');
    var menuOverlay = document.querySelector('.menu-overlay');
    var navMenuButtons = navMenu.querySelectorAll('button');

    navMenuButtons.forEach(function (navMenuButton){
        navMenuButton.addEventListener('click', function() {
            if (this.classList.contains('opened')) {
                this.setAttribute('aria-expanded', 'false');
                this.classList.remove('opened');
                menuOverlay.classList.remove('is-open');
                this.nextElementSibling.classList.remove('is-open');
                document.querySelector('body').classList.remove('overflow-hidden');
            } else {
                navMenu.querySelector('button').setAttribute('aria-expanded', 'false')
                navMenu.querySelector('button').classList.remove('opened'); // Reset open nav-subpanels
                this.setAttribute('aria-expanded', 'true');
                this.classList.add('opened');
                navMenu.querySelector('.nav-subpanel').classList.remove('is-open'); // Reset open nav-subpanels
                this.nextElementSibling.classList.add('is-open');
                menuOverlay.classList.add('is-open');
                document.querySelector('body').classList.add('overflow-hidden');
            }
        });
    });

    menuOverlay.addEventListener('click', function() {
        // When opened, reset mobile menu
        mobileButtons.forEach(function (mobileButton) {
            if (mobileButton.classList.contains('opened')) {
                mobileButton.setAttribute('aria-expanded', 'false');
                mobileButton.classList.remove('opened');
                mobileContainer.querySelector('.mobile-icon').classList.remove('hidden');
                mobileContainer.querySelector('.mobile-icon-close').classList.add('hidden');
            }
        })
        this.classList.remove('is-open');
        mobileContainer.querySelector('.mobile-navpanel').classList.remove('is-open');
        mobileContainer.querySelector('.language-panel').classList.remove('is-open');
        navMenu.querySelectorAll('button').forEach(button => {
            button.setAttribute('aria-expanded', false);
            button.classList.remove('opened');
        });
        navMenu.querySelector('.nav-subpanel').classList.remove('is-open');
        document.querySelector('body').classList.remove('overflow-hidden');
    });
