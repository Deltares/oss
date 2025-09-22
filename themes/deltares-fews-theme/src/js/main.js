// Mobile menu
var mobileContainer = document.querySelector('.mobile-container');
var mobileButtons = mobileContainer.querySelectorAll('.mobile-btn');
var mobileMenuButton = mobileContainer.querySelector('.mobile-menu-btn');
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
            this.setAttribute('aria-expanded', 'false');
            this.classList.remove('opened');
            menuOverlay.classList.add('is-open');
            document.querySelector('body').classList.add('overflow-hidden');
        }
    });
});

mobileMenuButton.addEventListener('click', function() {
    if (this.classList.contains('opened')) {
        this.setAttribute('aria-expanded', false);
        this.classList.remove('opened');
        this.querySelector('.mobile-icon-menu').classList.remove('hidden');
        this.querySelector('.mobile-icon-close').classList.add('hidden');
    } else {
        this.setAttribute('aria-expanded', true);
        this.classList.add('opened');
        this.querySelector('.mobile-icon-menu').classList.add('hidden');
        this.querySelector('.mobile-icon-close').classList.remove('hidden');
        mobileContainer.querySelector('.mobile-navpanel').classList.add('is-open');
        mobileContainer.querySelector('.language-panel').classList.remove('is-open');
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
    if (mobileButton.classList.contains('opened')) {
        mobileButton.setAttribute('aria-expanded', false);
        mobileButton.classList.remove('opened');
        mobileContainer.querySelector('.mobile-icon').classList.remove('hidden');
        mobileContainer.querySelector('.mobile-icon-close').classList.add('hidden');
    }

    this.classList.remove('is-open');
    mobileContainer.querySelector('.mobile-navpanel').classList.remove('is-open');
    mobileContainer.querySelector('.language-panel').classList.remove('is-open');
    navMenu.querySelectorAll('button').forEach(button => {
        button.setAttribute('aria-expanded', false);
        button.classList.remove('opened');
    });
    navMenu.querySelector('.nav-subpanel').forEach(subpanel => subpanel.classList.remove('is-open'));
    document.body.classList.remove('overflow-hidden');
});
