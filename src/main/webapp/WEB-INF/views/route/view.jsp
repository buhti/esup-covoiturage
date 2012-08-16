<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<div id="view-route">
  <c:if test="${ route.owner.id == account.id }">
    <div>
      <a href="<c:url value='/trajet/${ route.id }/modifier' />">Modifier</a>
    </div>
  </c:if>
  <div class="row">
    <div class="span6">
      <p class="${ route.driver ? 'driver' : 'passenger' }">${ route.owner }</p>
      <p><a href="mailto:${ route.owner.email }">Me contacter</a></p>
      <p>${ route.from.city } ${ route.roundTrip ? '&harr;' : '&rarr;' } ${ route.to.city }</p>
      <c:if test="${ route.driver }">
        <p>${ route.seats } pl. libre${ route.seats gt 1 ? 's' : '' }</p>
      </c:if>
    </div>
    <c:choose>
      <c:when test="${ route.recurrent }">
        <div class="span6">
          <p>
            <c:forEach items="${ route.weekDays }" var="weekDay">
              <joda:parseDateTime var="day" value="${ weekDay }" pattern="e" locale="fr" />
              <joda:format value="${ day }" pattern="EEEE" />
            </c:forEach>
            - <joda:format value="${ route.wayOutTime }" pattern="H'h'mm" />
            <c:if test="${ route.roundTrip }">
              &harr; <joda:format value="${ route.wayBackTime }" pattern="H'h'mm" />
            </c:if>
          </p>
        </div>
      </c:when>
      <c:otherwise>
        <div class="span6">
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
        </div>
      </c:otherwise>
    </c:choose>
  </div>
  <div class="row">
    <div class="span12">
      <p>${ route.distance } km soit ${ config.co2.a * route.distance + config.co2.b } jk de rejet de CO<sub>2</sub> en moins.</p>
    </div>
  </div>
  <div class="row">
    <div class="span6">
      <div class="origin">
        <p>Lieu de rendez-vous :</p>
        <p class="address">${ route.from.address }</p>
      </div>
      <div class="hidden-phone preview" style="height: 200px" data-lat="${ route.from.lat }" data-lng="${ route.from.lng }"></div>
    </div>
    <div class="span6">
      <div class="origin">
        <p>Lieu de dépose :</p>
        <p class="address">${ route.to.address }</p>
      </div>
      <div class="hidden-phone preview" style="height: 200px" data-lat="${ route.to.lat }" data-lng="${ route.to.lng }"></div>
    </div>
  </div>
</div>