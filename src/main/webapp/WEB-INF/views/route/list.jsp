<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<c:forEach items="${ routes }" var="route">
<div class="route">
  <a href="<c:url value='/trajet/${ route.id }' />">
    <span class="owner ${ route.driver ? 'driver' : 'passenger' }">${ route.owner }</span>
    <c:choose>
      <c:when test="${ route.recurrent }">
        <span class="days">
          <c:forEach items="${ route.weekDays }" var="weekDay">
            <joda:parseDateTime var="day" value="${ weekDay }" pattern="e" locale="fr" />
            <joda:format value="${ day }" pattern="EEEE" />
          </c:forEach>
          - <joda:format value="${ route.wayOutTime }" pattern="H'h'mm" />
          <c:if test="${ route.roundTrip }">
            &harr; <joda:format value="${ route.wayBackTime }" pattern="H'h'mm" />
          </c:if>
        </span>
      </c:when>
      <c:otherwise>
        <span class="date">
          <joda:format value="${ route.wayOutDate }" pattern="dd/MM/YYYY - H'h'mm" />
        </span>
      </c:otherwise>
    </c:choose>
    <span class="path">
      <span class="from">${ route.from.city }</span> ${ route.roundTrip ? '&harr;' : '&rarr;' } <span class="to">${ route.to.city }</span>
    </span>
    <span class="seats">
      <span>${ route.seats }</span> pl. libre${ route.seats gt 1 ? 's' : '' }
    </span>
  </a>
</div>
</c:forEach>
