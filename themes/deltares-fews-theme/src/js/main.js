(function ($) {
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
