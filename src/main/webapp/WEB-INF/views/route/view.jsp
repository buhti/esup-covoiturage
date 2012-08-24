<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<div id="view-route">
  <c:if test="${ route.ladiesOnly }">
    <div class="alert alert-error">
      <strong>Ce trajet est réservé aux femmes.</strong>
    </div>
  </c:if>
  <div class="route clearfix">
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
    <span class="info">
      <span class="seats">
        <c:if test="${ route.driver }">
          <span>${ route.seats }</span> pl. libre${ route.seats gt 1 ? 's' : '' }
        </c:if>
      </span>
      <span class="owner ${ route.driver ? 'driver' : 'passenger' }">
        <span>${ route.owner }</span>
        <a class="btn btn-small btn-primary" href="mailto:${ route.owner.email }">
          <i class="icon-white icon-envelope"></i>
          <span class="hidden-phone">Email</span>
        </a>
      </span>
    </span>
  </div>
  <c:if test="${ route.owner.chatting }">
    <div class="like chatting">
      <img src="<c:url value='/resources/img/balloon.png' />"/>
      <span>Aime discuter</span>
    </div>
  </c:if>
  <c:if test="${ route.owner.listeningMusic }">
    <div class="like music">
      <img src="<c:url value='/resources/img/music.png' />"/>
      <span>Ecoute la musique</span>
    </div>
  </c:if>
  <c:if test="${ route.owner.smoking }">
    <div class="like smoking">
      <img src="<c:url value='/resources/img/cigarette.png' />"/>
      <span>Fumeur</span>
    </div>
  </c:if>
  <div class="co2">
    <p>${ route.distance } km soit ${ config.co2.a * route.distance + config.co2.b } jk de rejet de CO<sub>2</sub> en moins.</p>
  </div>
  <div class="row">
    <div class="span6 origin">
      <div class="address">
        <span>Lieu de rendez-vous :</span>
        <address>${ route.from.address }</address>
      </div>
      <div class="hidden-phone preview" style="height: 250px"
        data-lat="${ route.from.lat }" data-lng="${ route.from.lng }"></div>
    </div>
    <div class="span6 destination">
      <div class="address">
        <span>Lieu de dépose :</span>
        <address>${ route.to.address }</address>
      </div>
      <div class="hidden-phone preview" style="height: 250px"
        data-lat="${ route.to.lat }" data-lng="${ route.to.lng }"></div>
    </div>
  </div>
</div>