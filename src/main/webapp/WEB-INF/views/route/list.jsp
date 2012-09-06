<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<tiles:useAttribute id="controls" name="controls" ignore="true" />
<c:forEach items="${ routes }" var="route">
<a class="route" href="<c:url value='/trajet/${ route.id }' />">
  <span class="clearfix">
    <span class="description">
      <span class="date">
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
      </span>
      <span class="path">
        <span class="from">${ route.from.city }</span>
        <span class="arrow">${ route.roundTrip ? '&harr;' : '&rarr;' }</span>
        <span class="to">${ route.to.city }</span>
      </span>
    </span>
    <span class="info clearfix">
      <span class="seats">
        <c:if test="${ route.driver }">
          <span>${ route.seats }</span>
          <spring:message code="route.view.seats${ route.seats gt 1 ? '.plural' : '' }" />
        </c:if>
      </span>
      <span class="owner ${ route.driver ? 'driver' : 'passenger' }">${ route.owner }</span>
    </span>
  </span>
  <button class="btn btn-small view">
    <i class="icon-play"></i>
  </button>
  <button class="btn btn-small btn-danger delete">
    <i class="icon-trash icon-white"></i>
  </button>
</a>
</c:forEach>
