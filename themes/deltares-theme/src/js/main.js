var FormStepper = {

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

            let nextButtons = this.element.querySelectorAll('.next-step');
            let prevButtons = this.element.querySelectorAll('.prev-step');

            nextButtons.forEach(function (nextButton) {
                nextButton.addEventListener('click',function () {
                    FormStepper._navigate({data: {plugin: FormStepper, action: 'next'}});
                });
            });
            prevButtons.forEach(function (prevButton) {
                prevButton.addEventListener('click',function () {
                    FormStepper._navigate({data: {plugin: FormStepper, action: 'prev'}})
                });
            })

            this._getSubmitButton().forEach(function (submitbutton) {
                submitbutton.addEventListener('click', function () {
                    if (FormStepper._isFormValid()) {
                        FormStepper.form.preSubmitAction();
                        FormStepper.form.form.submit();
                    }
                });
            })
        },

        _isFormValid: function () {
            if (this.form.formValidator) {
                this.form.formValidator.validate();
                return !this.form.formValidator.hasErrors();
            } else {
                return true;
            }
        },

        _navigate: function (event) {
            let plugin = event.data.plugin;
            let action = event.data.action;
            let isFormValid = plugin._isFormValid() && plugin.form.validateFirstStep();

            let element = plugin.element;
            let active = element.querySelector('li.active');
            let next;
            if (active.nextElementSibling) {
                next = active.nextElementSibling;
            } else {
                next = active;
            }
            while (next.classList.contains('disabled') && next.nextElementSibling != null) {
                next = next.nextElementSibling;
            }

            let prev;
            if( active.previousElementSibling){
                prev = active.previousElementSibling;
            } else {
                prev = active;
            }

            while (prev.classList.contains('disabled') && prev.previousElementSibling != null) {
                prev = prev.previousElementSibling;
            }

            let isLast = next.nextElementSibling === null;
            let isFirst = prev.previousElementSibling === null;

            if (isFormValid && 'next' === action && next) {
                plugin._hideStep(active);
                plugin._showStep(next);

                if (isLast) {
                    plugin._disableButton(action);
                    plugin._showSubmitButton();
                } else {
                    plugin._enableButton('prev');
                }
                active.classList.remove('icon-circle-blank');
                active.classList.add('icon-circle');
                active.classList.add('completed');
            } else if ('prev' === action && prev) {
                plugin._hideStep(active);
                plugin._showStep(prev);

                if (isFirst) {
                    plugin._disableButton(action);
                } else {
                    plugin._enableButton('next');
                    plugin._hideSubmitButton();
                }
            }
        },

        _showStep: function (element) {
            let anchor = element.querySelector('a');
            let pane = document.querySelector(anchor.getAttribute('href'));
            this._activateElement(element);
            this._activateElement(anchor);
            this._activateElement(pane);
        },

        _hideStep: function (element) {
            let anchor = element.querySelector('a');
            let pane = document.querySelector(anchor.getAttribute('href'));
            this._deactivateElement(element);
            this._deactivateElement(anchor);
            this._deactivateElement(pane);
        },

        _enableButton: function (button) {
            let buttons = document.querySelectorAll('.' + button + '-step');
            buttons.forEach(function (selector) {
                selector.classList.remove('disabled');
                selector.classList.add('enabled');
            })
        },

        _disableButton: function (button) {
            let buttons = document.querySelectorAll('.' + button + '-step');
            buttons.forEach(function (selector) {
                selector.classList.remove('enabled');
                selector.classList.add('disabled');
            })
        },

        _activateElement: function (element) {
            element.classList.add('active');
        },

        _deactivateElement: function (element) {
            element.classList.remove('active');
        },

        _showSubmitButton: function () {
            let submitButtons = this._getSubmitButton();
            let nextButtons = this._getNextButton();
            submitButtons.forEach(function (submitButton) {
                submitButton.classList.remove('d-none');
                submitButton.classList.add('d-inline');

            })
            nextButtons.forEach(function (nextButton) {
                nextButton.classList.remove('d-inline');
                nextButton.classList.add('d-none');

            })
        },

        _hideSubmitButton: function () {
            let submitButtons = this._getSubmitButton();
            let nextButtons = this._getNextButton();
            submitButtons.forEach(function (submitButton) {
                submitButton.classList.add('d-none');
                submitButton.classList.remove('d-inline');

            })
            nextButtons.forEach(function (nextButton) {
                nextButton.classList.add('d-inline');
                nextButton.classList.remove('d-none');
            })
        },

        _getSubmitButton: function () {
            return this.element.querySelectorAll("a.submit");
        },

        _getNextButton: function () {
            return this.element.querySelectorAll('.next-step');
        }
    }

var ShoppingCart = {

        pluginName : "shoppingCart",
        defaults: {
            'languageKeys': {
                'add-to-cart': 'Add to cart',
                'remove-from-cart': 'Remove from cart'
            },
            'registrationFormId' : 'dsd_RegistrationFormPortlet'
        },
        options : {},
        cart : {},

        init: function (options){
            this.config = Object.assign({}, this.defaults, options);
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

            let elements = document.querySelectorAll('.c-cart__checkout__cart');
            if (elements) {
                elements.forEach(function (element) {
                    element.addEventListener('click', function (e) {
                        e.preventDefault();
                        buildCheckoutURL();
                    });
                })

            }

            function registerClick(clazz, type) {
                let clazzElms = document.querySelectorAll(clazz);
                if (clazzElms){

                    clazzElms.forEach(function (clazzElm) {

                        clazzElm.addEventListener('click', function (e) {
                            e.preventDefault();
                            let id = this.dataset.articleId;

                            if (shoppingCart._contains(id, type)) {
                                shoppingCart._removeFromCart(id, type);
                            } else {
                                shoppingCart._addToCart(id, type);
                            }
                            shoppingCart._updateLabel(this, type);
                            shoppingCart.refreshCart();
                        });
                    })
                }

            }
        },

        refreshCart: function () {
            let elements = document.querySelectorAll('.c-cart__item__counter');
            if (elements) {
                elements.forEach(function (element) {
                    if (element) {
                        element.textContent = (ShoppingCart.cart.items.length + ShoppingCart.cart.downloads.length);
                    }
                })
            }


            document.querySelectorAll('.add-to-cart').forEach( function (value){
                ShoppingCart._updateLabel(value, 'registration');
            });
            document.querySelectorAll('.add-download-to-cart').forEach(function (value){
                ShoppingCart._updateLabel(value, 'download');
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
                        portletId  = ShoppingCart.config.registrationFormId ;
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
            let id = element.dataset.articleId;
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

function closeMenuButtons(menuPrefix){
    document.querySelector('.' + menuPrefix + '-navpanel').classList.remove('is-open');
    document.querySelectorAll('.' + menuPrefix + '-menu-btn').forEach((btn) => {
        btn.classList.remove('opened');
        btn.setAttribute('aria-expanded', 'false');
    });
    document.querySelectorAll('.' + menuPrefix + '-icon-menu').forEach((btn) => {btn.classList.remove('hidden');});
    document.querySelectorAll('.' + menuPrefix + '-icon-close').forEach((btn) => {btn.classList.add('hidden');});
}

function openMenuButtons(menuPrefix){
    document.querySelector('.' + menuPrefix + '-navpanel').classList.add('is-open');
    document.querySelectorAll('.' + menuPrefix + '-menu-btn').forEach((btn) => {
        btn.classList.add('opened');
        btn.setAttribute('aria-expanded', 'true');
    });
    document.querySelectorAll('.' + menuPrefix + '-icon-menu').forEach((btn) => {btn.classList.add('hidden');});
    document.querySelectorAll('.' + menuPrefix + '-icon-close').forEach((btn) => {btn.classList.remove('hidden');});
}

var navMenu = document.querySelector('.main-navbar .nav-menu');

    // Mobile menu
var mobileContainer = document.querySelector('.mobile-container');
if ( mobileContainer ) {
    let mobileButtons = mobileContainer.querySelectorAll('.mobile-btn');
    mobileButtons.forEach(function (mobileButton) {
        mobileButton.addEventListener('click', function () {
            if (this.classList.contains('opened')) {
                navMenu.querySelector('button').setAttribute('aria-expanded', 'false');
                navMenu.querySelector('button').classList.remove('opened'); // Reset all nav-menu buttons
                navMenu.querySelector('.nav-subpanel').classList.remove('is-open'); // Reset open nav-subpanels
                document.querySelector('body').classList.remove('overflow-hidden');
            } else {
                document.querySelector('body').classList.add('overflow-hidden');
            }
        });
    });

    let mobileMenuButtons = mobileContainer.querySelectorAll('.mobile-menu-btn');
    mobileMenuButtons.forEach(function (mobileMenuButton) {
        mobileMenuButton.addEventListener('click', function () {
            if (this.classList.contains('opened')) {
                closeMenuButtons('mobile');
            } else {
                openMenuButtons('mobile');
            }
            closeMenuButtons('sites');
        });
    })

    let mobileMainnav = mobileContainer.querySelector('.mobile-mainnav');
    let mobileMainnavs = mobileMainnav.querySelectorAll('button');
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
}

var sitesContainer = document.querySelector('.sites-container');
if ( sitesContainer ) {

    let sitesButtons = sitesContainer.querySelectorAll('.sites-btn');
    sitesButtons.forEach(function (sitesButton) {
        sitesButton.addEventListener('click', function () {
            if (this.classList.contains('opened')) {
                // document.querySelector('.sites-navpanel').classList.remove('is-open');
                navMenu.querySelector('button').setAttribute('aria-expanded', 'false');
                navMenu.querySelector('button').classList.remove('opened'); // Reset all nav-menu buttons
                navMenu.querySelector('.nav-subpanel').classList.remove('is-open'); // Reset open nav-subpanels
                document.querySelector('body').classList.remove('overflow-hidden');
            } else {
                document.querySelector('body').classList.add('overflow-hidden');
            }
        });
    });

    let sitesMenuButtons = document.querySelectorAll('.sites-menu-btn');
    sitesMenuButtons.forEach(function (sitesButton) {
        sitesButton.addEventListener('click', function () {
            if (this.classList.contains('opened')) {
                closeMenuButtons('sites');
            } else {
                openMenuButtons('sites');
            }
            closeMenuButtons('mobile');
        });
    });

}
// Main navigation (desktop)
if ( navMenu ) {
    let navMenuButtons = navMenu.querySelectorAll('button');

    navMenuButtons.forEach(function (navMenuButton) {
        navMenuButton.addEventListener('click', function () {
            if (this.classList.contains('opened')) {
                this.setAttribute('aria-expanded', 'false');
                this.classList.remove('opened');
                this.nextElementSibling.classList.remove('is-open');
                document.querySelector('body').classList.remove('overflow-hidden');
            } else {
                navMenu.querySelector('button').setAttribute('aria-expanded', 'false')
                navMenu.querySelector('button').classList.remove('opened'); // Reset open nav-subpanels
                this.setAttribute('aria-expanded', 'true');
                this.classList.add('opened');
                navMenu.querySelector('.nav-subpanel').classList.remove('is-open'); // Reset open nav-subpanels
                this.nextElementSibling.classList.add('is-open');
                document.querySelector('body').classList.add('overflow-hidden');
            }
        });
    });

}

function checkStep(form, requiredStep) {
    return (getCurrentStep(form) === requiredStep);
}

function getCurrentStep(form) {
    var activePane = $('form[name=' + form + ']').closest('.bs-stepper').find('.tab-pane.active').attr('id');
    var currentStep = activePane.charAt(activePane.length - 1);
    return Number(currentStep);
}