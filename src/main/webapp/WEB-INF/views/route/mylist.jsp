<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<div id="route-list">

  <div class="route-list">
    <c:choose>
      <c:when test="${ empty routes }">
        <div class="alert alert-info">
          <spring:message code="route.list.empty" />
        </div>
      </c:when>
      <c:otherwise>
        <tiles:insertAttribute name="list" />
      </c:otherwise>
    </c:choose>
  </div>

</div>
