<%@ include file="/META-INF/resources/init.jsp" %>
<%
    String startDate = (String)renderRequest.getAttribute("startDate") ;
    String endDate = (String)renderRequest.getAttribute("endDate");

%>

<liferay-portlet:actionURL
        var="submitURL"
        name="submitForm"
/>

<span id="<portlet:namespace/>dateRange-message-block"></span>
<aui:form method="post" name="dateRangeFacetForm" action="<%=submitURL%>">
    <label ><liferay-ui:message key="facet.date-range.label"/></label>
    <div class="form-group-autofit">
        <aui:input name="startDate" label="" type="text" wrapperCssClass="form-group-item"
                   value='<%=startDate == null ? "" : startDate%>'/>
        <aui:input name="endDate" label="" type="text" wrapperCssClass="form-group-item"
                   value='<%=endDate == null ? "" : endDate%>'/>
    </div>
</aui:form>


<aui:script use="deltares-search-facet-util">

    const updateValue = function (fieldName, startFieldValue, newFieldValue) {
        if (!validateSelection()) return
        let val = '';
        if (newFieldValue != null){
            val = newFieldValue.getInputFormatted()
        }
        if (val !== startFieldValue){
            var form = document.querySelector('form[name="<portlet:namespace />dateRangeFacetForm"]')
            form.submit();
            // Liferay.Deltares.FacetUtil.updateQueryString("<portlet:namespace/>", fieldName);
        }
    }

    const validateSelection = function (){
        let valid = Liferay.Deltares.FacetUtil.validateDateField("<portlet:namespace/>");
        Liferay.Deltares.FacetUtil.clearError("<portlet:namespace/>", "dateRange" );
        if (!valid){
           Liferay.Deltares.FacetUtil.writeError("<portlet:namespace/>", "dateRange", "Invalid date range! Start date must be before end date." );
        }
        return valid;
    }

    let startDateInput = document.getElementById('<portlet:namespace/>startDate');
    const startDatePicker = new TheDatepicker.Datepicker(startDateInput);
    startDatePicker.options.setInputFormat('d-m-Y');
    startDatePicker.options.setShowDeselectButton(true)
    startDatePicker.options.setShowResetButton(true)
    startDatePicker.options.setShowCloseButton(true)
    startDatePicker.options.setTitle("Select start date:")
    startDatePicker.options.onSelect(function (event, day, previousDay){
        if (day !== previousDay){
            updateValue("startDate", "<%=startDate%>", day);
        }
    })
    startDatePicker.render();

    let endDateInput = document.getElementById('<portlet:namespace/>endDate');
    const endDatePicker = new TheDatepicker.Datepicker(endDateInput);
    endDatePicker.options.setInputFormat('d-m-Y');
    endDatePicker.options.setShowDeselectButton(true)
    endDatePicker.options.setShowResetButton(true)
    endDatePicker.options.setShowCloseButton(true)
    endDatePicker.options.setTitle("Select end date:")
    endDatePicker.options.onSelect(function (event, day, previousDay){
        if (day !== previousDay){
            updateValue("endDate", "<%=endDate%>", day);
        }
    })
    endDatePicker.render();


</aui:script>