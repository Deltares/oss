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
        },

        _isFormValid: function () {
            this.form.validate();
            return !this.form.hasErrors();
        },

        _navigate: function (event) {
            let plugin = event.data.plugin;
            let action = event.data.action;
            let isFormValid = plugin._isFormValid() && validateFirstStep();

            let element = $(plugin.element);
            let active = element.find('li.active');
            let next = active.next();
            let prev = active.prev();

            let isLast = (next.next().length === 0);
            let isFirst = (prev.prev().length === 0);

            console.log("navigate to");
            console.log(action);
            console.log(isFormValid);

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