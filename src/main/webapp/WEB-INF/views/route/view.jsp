<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<div id="view-route">
  <div class="row">
    <div class="span6">
      <p class="${ route.driver ? 'driver' : 'passenger' }">${ route.owner }</p>
      <p><a href="mailto:${ route.owner.email }">Me contacter</a></p>
      <p>${ route.from.city } &rarr; ${ route.to.city }</p>
      <p>${ route.seats } pl. libre${ route.seats gt 1 ? 's' : '' }</p>
    </div>
    <c:choose>
      <c:when test="${ route.recurrent }">
        <div class="span6">
          <p>Départ à ${ route.wayOutTime }</p>
          <p>Retour à ${ route.wayBackTime }</p>
        </div>
      </c:when>
      <c:otherwise>
        <div class="span6">
          <p>
            Départ le <joda:format value="${ route.wayOutDate }" pattern="dd/MM/YYYY" />
            à <joda:format value="${ route.wayOutDate }" pattern="HH'h'mm" />
          </p>
          <p>
            Retour le <joda:format value="${ route.wayBackDate }" pattern="dd/MM/YYYY" />
            à <joda:format value="${ route.wayBackDate }" pattern="HH'h'mm" />
          </p>
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