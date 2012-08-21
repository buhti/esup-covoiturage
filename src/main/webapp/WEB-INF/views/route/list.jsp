<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<c:forEach items="${ routes }" var="route">
<%--   <a href="<c:url value='/trajet/${ route.id }' />"></a> --%>
<div class="row route">
  <div class="span2 owner ${ route.driver ? 'driver' : 'passenger' }">
    <div class="owner">${ route.owner }</div>
    <div>
      <a class="btn btn-small btn-primary" href="mailto:${ route.owner.email }">
        <i class="icon-white icon-envelope"></i> Email
      </a>
    </div>
  </div>
  <div class="span8 description">
    <div class="date">
      <c:choose>
        <c:when test="${ route.recurrent }">
          <c:forEach items="${ route.weekDays }" var="weekDay">
            <joda:parseDateTime var="day" value="${ weekDay }" pattern="e" locale="fr" />
            <joda:format value="${ day }" pattern="EEEE" />
          </c:forEach>
          <span class="dash">&ndash;</span>
          <joda:format value="${ route.wayOutTime }" pattern="H'h'mm" />
          <c:if test="${ route.roundTrip }">
            <span class="arrow">&harr;</span>
            <joda:format value="${ route.wayBackTime }" pattern="H'h'mm" />
          </c:if>
        </c:when>
        <c:otherwise>
          <joda:format value="${ route.wayOutDate }" pattern="dd/MM/YYYY" />
          <span class="dash">&ndash;</span>
          <joda:format value="${ route.wayOutDate }" pattern="H'h'mm" />
        </c:otherwise>
      </c:choose>
    </div>
    <div class="path">
      <span class="from">${ route.from.city }</span>
      <span class="arrow">${ route.roundTrip ? '&harr;' : '&rarr;' }</span>
      <span class="to">${ route.to.city }</span>
    </div>
  </div>
  <div class="span2 info">
    <div class="seats">
      <span>${ route.seats }</span> pl. libre${ route.seats gt 1 ? 's' : '' }
    </div>
  </div>
</div>
</c:forEach>
