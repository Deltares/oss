
<%@ taglib uri="http://liferay.com/tld/clay" prefix="clay" %>
<%@ include file="/META-INF/resources/init.jsp" %>
<%
    String startDate = "";
    Object val = renderRequest.getAttribute("startDate");
    if (val != null) {
        startDate = (String) val;
    }
    String endDate = "";
    val = renderRequest.getAttribute("endDate");
    if (val != null) {
        endDate = (String) val;
    }


%>
<span id="<portlet:namespace/>dateRange-message-block"></span>
<aui:form method="post" name="dateRangeFacetForm">
    <label for="dates"><liferay-ui:message key="facet.date-range.label"/></label>
    <div id="dates" >
        <aui:row>
            <aui:col>
                <aui:input type="date" name="startDate" label=""
                           inputCssClass="date-picker input-date" />
            </aui:col>
            <aui:col>
                <aui:input type="date" name="endDate" label=""
                           inputCssClass="date-picker input-date" />
            </aui:col>
        </aui:row>
    </div>

</aui:form>


<aui:script use="deltares-search-facet-util">
    Liferay.Deltares.FacetUtil.initializeDates("<portlet:namespace/>", "<%=startDate%>", "<%=endDate%>");

    var myChangeFunction;
    const changeFunction = function (name){

        if (myChangeFunction){
            clearTimeout(myChangeFunction)
        }
        myChangeFunction = setTimeout(function (){
            if (Liferay.Deltares.FacetUtil.validateDateField("<portlet:namespace/>")){
                Liferay.Deltares.FacetUtil.clearError("<portlet:namespace/>", "dateRange" );
            } else {
                Liferay.Deltares.FacetUtil.writeError("<portlet:namespace/>", "dateRange", "Invalid date range! Start date must be before end date." );
            }
            Liferay.Deltares.FacetUtil.updateQueryString("<portlet:namespace/>", name);
        }, 4000);
    };
    let startDateInput = document.getElementById('<portlet:namespace/>startDate');
    startDateInput.onchange = function () {
        return changeFunction('startDate');
    }

    let endDateInput = document.getElementById('<portlet:namespace/>endDate');
    endDateInput.onchange = function () {
    return changeFunction('endDate');
    }




</aui:script>