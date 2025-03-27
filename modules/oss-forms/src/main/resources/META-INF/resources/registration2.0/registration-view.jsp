<div class="row no-gutters">
    <%
        String viewURL = displayContext.getViewURL(registration);
        viewURL += "?redirect=" + PortalUtil.getCurrentURL(request);
        String imageUrl = registration.getSmallImageURL(displayContext.getThemeDisplay());
        String title = registration.getTitle();
        String timeZoneId = registration.getTimeZoneId();
    %>
    <div class="col-2">
        <a href="<%=viewURL%>" >
            <img class="img-fluid" src="<%=imageUrl%>" alt=""/>
        </a>
    </div>
    <div class="col-8 px-3">
        <h4>
            <a href="<%= viewURL %>">
                <span class="text-truncate-inline">
                    <span class="text-truncate"><%= title %></span>
                </span>
            </a>
        </h4>
        <c:choose  >
            <c:when test="<%=registration.isMultiDayEvent()%>">
                <c:choose>
                    <c:when test="<%=registration.isDaily()%>">
                        <span class="c-sessions__item__time-date-place__time">
                            <%=DateUtil.getDate(registration.getStartTime(), "dd MMM yyyy HH:mm", locale) + " - " +
                            DateUtil.getDate(registration.getEndTime(), "HH:mm", locale) + '(' + timeZoneId + ')' %>
                        </span>
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="<%=registration.getStartAndEndTimesPerDay()%>" var="period">
                            <% Period period = (Period) pageContext.getAttribute("period"); %>
                            <span class="c-sessions__item__time-date-place__time">
                                <%=DateUtil.getDate(period.getStartDate(), "dd MMM yyyy HH:mm", locale) + " - " +
                                    DateUtil.getDate(period.getEndDate(), "HH:mm", locale) + '(' + timeZoneId + ')' %>
                            </span>
                            <br />
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise>
                <span class="c-sessions__item__time-date-place__time">
                    <%=DateUtil.getDate(registration.getStartTime(), "dd MMM yyyy HH:mm", locale) + " - " +
                            DateUtil.getDate(registration.getEndTime(), "HH:mm", locale) + '(' + timeZoneId + ')' %>
                </span>
            </c:otherwise>
        </c:choose>
    </div>
</div>
