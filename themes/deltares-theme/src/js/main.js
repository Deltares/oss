(function ($) {
    var pluginName = "formStepper";

    function Plugin(element, form) {
        this.element = element;
        this.form = form;
        this._name = pluginName;

        this.init();
    }

    Plugin.prototype = {
        init: function () {
            this.registerEvents();
        },

        registerEvents: function () {
            let plugin = this;
            let element = this._getElement();
            let nextButton = element.find('.next-step');
            let prevButton = element.find('.prev-step');

            nextButton.on('click', {plugin: this, action: 'next'}, this._navigate);
            prevButton.on('click', {plugin: this, action: 'prev'}, this._navigate);

            this._getSubmitButton().on('click', function () {
                if (plugin._isFormValid()) {
                    plugin.form.preSubmitAction();
                    plugin._getForm().submit();
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
            let isFormValid = plugin._isFormValid();

            let element = $(plugin.element);
            let active = element.find('li.active');
            let next = active.next();
            while (next.hasClass('disabled') && next.next().length > 0) {
                next = next.next();
            }
            let prev = active.prev();
            while (prev.hasClass('disabled') && prev.prev().length > 0) {
                prev = prev.prev();
            }

            let isLast = (next.next().length === 0);
            let isFirst = (prev.prev().length === 0);

            if (isFormValid || 'prev' === action) {
                if ('next' === action && next.length) {
                    plugin._hideStep(active);
                    plugin._showStep(next);
                    if (typeof plugin.form.activateStep === "function"){
                        plugin.form.activateStep(active, next);
                    }
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
                    if (typeof plugin.form.activateStep === "function"){
                        plugin.form.activateStep(active, prev);
                    }
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
            let anchor = element.find('a');
            let pane = $(anchor.attr('href'));
            this._activateElement(element);
            this._activateElement(anchor);
            this._activateElement(pane);
        },

        _hideStep: function (element) {
            let anchor = element.find('a');
            let pane = $(anchor.attr('href'));
            this._deactivateElement(element);
            this._deactivateElement(anchor);
            this._deactivateElement(pane);
        },

        _enableButton: function (button) {
            let selector = $('.' + button + '-step');
            selector.removeClass('disabled');
            selector.addClass('enabled');
        },

        _disableButton: function (button) {
            let selector = $('.' + button + '-step');
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

        _getElement: function () {
            return $(this.element);
        },

        _getForm: function () {
            return this._getElement().find('form');
        },

        _getSubmitButton: function () {
            return this._getElement().find("a.submit");
        },

        _getNextButton: function () {
            return this._getElement().find('.next-step');
        }
    };

    $.fn[pluginName] = function (form) {
        return this.each(function () {
            if (!$.data(this, "plugin_" + pluginName)) {
                $.data(this, "plugin_" + pluginName,
                    new Plugin(this, form));
            }
        })
    }
})(jQuery);

function checkStep(form, requiredStep) {
    return (getCurrentStep(form) === requiredStep);
}

function getCurrentStep(form) {
    var activePane = $('form[name=' + form + ']').closest('.bs-stepper').find('.tab-pane.active').attr('id');
    var currentStep = activePane.charAt(activePane.length - 1);
    return Number(currentStep);
}

(function ($) {

    var pluginName = "shoppingCart";

    var Plugin = function Plugin(options) {
        this.element = this;
        this.options = options;
        this._name = pluginName;

        this.init();
    }

    Plugin.prototype = {
        defaults: {
            'languageKeys': {
                'add-to-cart': 'Add to cart',
                'remove-from-cart': 'Remove from cart'
            }
        },
        init: function () {
            this.config = $.extend({}, this.defaults, this.options);

            this.initCart();
            this.registerEvents();
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
            let plugin = this;

            registerClick('.add-to-cart', 'registration');
            registerClick('.add-download-to-cart', 'download');

            $('.c-cart__checkout__cart').on('click', function (e) {
                e.preventDefault();
                buildCheckoutURL();
            });

            function registerClick(clazz, type) {
                $(clazz).on('click', function (e) {
                    e.preventDefault();
                    let id = $(this).data('articleId');

                    if (plugin._contains(id, type)) {
                        plugin._removeFromCart(id, type);
                    } else {
                        plugin._addToCart(id, type);
                    }
                    plugin._updateLabel($(this), type);
                    plugin.refreshCart();
                });
            }
        },

        refreshCart: function () {
            let plugin = this;
            $('.c-cart__item__counter').text(plugin.cart.items.length + plugin.cart.downloads.length);
            $('.add-to-cart').each(function (){
                plugin._updateLabel($(this), 'registration');
            });
            $('.add-download-to-cart').each(function (){
                plugin._updateLabel($(this), 'download');
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
                        portletId  = 'dsd_RegistrationFormPortlet';
                    }
                    let portletURL = Liferay.PortletURL.createURL(cartUrl);

                    portletURL.setLifecycle(Liferay.PortletURL.RENDER_PHASE);
                    portletURL.setWindowState('normal');
                    portletURL.setPortletMode('view');
                    portletURL.setParameter('action', action);
                    portletURL.setParameter('ids', ids);
                    portletURL.setParameter('redirect', window.location.href);
                    portletURL.setPortletId(portletId);

                    if (undefined !== portletURL.toString()) {
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
                element.text(plugin._getLanguageKey('remove-from-cart'));
            } else {
                element.text(plugin._getLanguageKey('add-to-cart'));
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
    };

    Plugin.defaults = Plugin.prototype.defaults;

    $.fn[pluginName] = function (options) {
        return this.each(function () {
            if (!$.data(this, "plugin_" + pluginName)) {
                $.data(this, "plugin_" + pluginName,
                    new Plugin(options));
            }
        })
    };

    window.ShoppingCart = Plugin;

}(jQuery));

(function ($) {
    // Scroll service bar (desktop)
    var header = $('#header');

    // window.addEventListener('scroll', function() {
    //     if ($('body').hasClass('signed-in')) {
    //         if (document.documentElement.scrollTop > 94) {
    //             header.addClass('hidden-servicebar logged-in');
    //         } else {
    //             header.removeClass('hidden-servicebar logged-in');
    //         }
    //     } else {
    //         if (document.documentElement.scrollTop > 38) {
    //             header.addClass('hidden-servicebar');
    //         } else {
    //             header.removeClass('hidden-servicebar');
    //         }
    //     }
    // });

    // Mobile menu
    var mobileContainer = $('.mobile-container');
    var mobileButton = mobileContainer.find('.mobile-btn');
    var mobileMenuButton = mobileContainer.find('.mobile-menu-btn');
    var mobileLangButton = mobileContainer.find('.mobile-lang-btn');
    var mobileMainnav = mobileContainer.find('.mobile-mainnav');

    mobileButton.on('click', function() {
        if ($(this).hasClass('opened')) {
            menuOverlay.removeClass('is-open');
            mobileContainer.find('.mobile-navpanel').removeClass('is-open');
            mobileContainer.find('.language-panel').removeClass('is-open');
            navMenu.find('button').attr('aria-expanded', false).removeClass('opened'); // Reset all nav-menu buttons
            navMenu.find('.nav-subpanel').removeClass('is-open'); // Reset open nav-subpanels
            $('body').removeClass('overflow-hidden');
        } else {
            mobileContainer.find('.mobile-icon').removeClass('hidden');
            mobileContainer.find('.mobile-icon-close').addClass('hidden');
            mobileButton.attr('aria-expanded', false).removeClass('opened');
            menuOverlay.addClass('is-open');
            $('body').addClass('overflow-hidden');
        }
    });

    mobileMenuButton.on('click', function() {
        if ($(this).hasClass('opened')) {
            $(this).attr('aria-expanded', false).removeClass('opened');
            $(this).find('.mobile-icon-menu').removeClass('hidden');
            $(this).find('.mobile-icon-close').addClass('hidden');
        } else {
            $(this).attr('aria-expanded', true).addClass('opened');
            $(this).find('.mobile-icon-menu').addClass('hidden');
            $(this).find('.mobile-icon-close').removeClass('hidden');
            mobileContainer.find('.mobile-navpanel').addClass('is-open');
            mobileContainer.find('.language-panel').removeClass('is-open');
        }
    });

    mobileLangButton.on('click', function() {
        if ($(this).hasClass('opened')) {
            $(this).attr('aria-expanded', false).removeClass('opened');
            $(this).find('.mobile-icon-lang').removeClass('hidden');
            $(this).find('.mobile-icon-close').addClass('hidden');
        } else {
            $(this).attr('aria-expanded', true).addClass('opened');
            $(this).find('.mobile-icon-lang').addClass('hidden');
            $(this).find('.mobile-icon-close').removeClass('hidden');
            mobileContainer.find('.mobile-navpanel').removeClass('is-open');
            mobileContainer.find('.language-panel').addClass('is-open');
        }
    });

    mobileMainnav.find('button').on('click', function() {
        if ($(this).hasClass('opened')) {
            $(this).attr('aria-expanded', false).removeClass('opened');
            $(this).find('svg').removeClass('-rotate-180').addClass('rotate-0');
            $(this).next('.v-mainnav_subpanel--mobile').removeClass('is-open');
        } else {
            $(this).attr('aria-expanded', true).addClass('opened');
            $(this).find('svg').removeClass('rotate-0').addClass('-rotate-180');
            $(this).next('.v-mainnav_subpanel--mobile').addClass('is-open');
        }
    });

    // Main navigation (desktop)
    var navMenu = $('.main-navbar .nav-menu');
    var menuOverlay = $('.menu-overlay');

    navMenu.find('button').on('click', function() {
        if ($(this).hasClass('opened')) {
            $(this).attr('aria-expanded', false).removeClass('opened');
            menuOverlay.removeClass('is-open');
            navMenu.find('.nav-subpanel').removeClass('is-open');
            $('body').removeClass('overflow-hidden');
        } else {
            navMenu.find('button').attr('aria-expanded', false).removeClass('opened'); // Reset all nav-menu buttons
            $(this).attr('aria-expanded', true).addClass('opened');
            navMenu.find('.nav-subpanel').removeClass('is-open'); // Reset open nav-subpanels
            $(this).next('.nav-subpanel').addClass('is-open');
            menuOverlay.addClass('is-open');
            $('body').addClass('overflow-hidden');
        }
    });

    menuOverlay.on('click', function() {
        // When opened, reset mobile menu
        if (mobileButton.hasClass('opened')) {
            mobileButton.attr('aria-expanded', false).removeClass('opened');
            mobileContainer.find('.mobile-icon').removeClass('hidden');
            mobileContainer.find('.mobile-icon-close').addClass('hidden');
        }

        $(this).removeClass('is-open');
        mobileContainer.find('.mobile-navpanel').removeClass('is-open');
        mobileContainer.find('.language-panel').removeClass('is-open');
        navMenu.find('button').attr('aria-expanded', false).removeClass('opened');
        navMenu.find('.nav-subpanel').removeClass('is-open');
        $('body').removeClass('overflow-hidden');
    });
}(jQuery));
