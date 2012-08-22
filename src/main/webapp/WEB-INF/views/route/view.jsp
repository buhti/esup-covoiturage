<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<div id="view-route">
  <c:if test="${ route.owner.id == account.id }">
    <div>
      <a href="<c:url value='/trajet/${ route.id }/modifier' />">Modifier</a>
    </div>
  </c:if>
  <div class="row">
    <div class="span6">
      <p class="${ route.driver ? 'driver' : 'passenger' }">${ route.owner }</p>
      <p>
        <a class="btn btn-small btn-primary" href="mailto:${ route.owner.email }">
          <i class="icon-white icon-envelope"></i> Email
        </a>
      </p>
      <c:if test="${ route.driver }">
        <p><span>${ route.seats }</span> pl. libre${ route.seats gt 1 ? 's' : '' }</p>
      </c:if>
    </div>
    <div class="span6">
      <c:choose>
        <c:when test="${ route.recurrent }">
          <p>
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
          </p>
        </c:when>
        <c:otherwise>
          <p>
            Départ le <joda:format value="${ route.wayOutDate }" pattern="dd/MM/YYYY" />
            à <joda:format value="${ route.wayOutDate }" pattern="HH'h'mm" />
          </p>
          <c:if test="${ route.roundTrip }">
            <p>
              Retour le <joda:format value="${ route.wayBackDate }" pattern="dd/MM/YYYY" />
              à <joda:format value="${ route.wayBackDate }" pattern="HH'h'mm" />
            </p>
          </c:if>
        </c:otherwise>
      </c:choose>
      <p>
        <span class="from">${ route.from.city }</span>
        <span class="arrow">${ route.roundTrip ? '&harr;' : '&rarr;' }</span>
        <span class="to">${ route.to.city }</span>
      </p>
    </div>
  </div>
  <div class="row">
    <div class="span12 co2">
      <p>${ route.distance } km soit ${ config.co2.a * route.distance + config.co2.b } jk de rejet de CO<sub>2</sub> en moins.</p>
    </div>
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