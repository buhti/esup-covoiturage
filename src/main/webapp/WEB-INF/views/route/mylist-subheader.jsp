<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<div class="controls clearfix" data-warning="<spring:message code='route.controls.confirmDelete' />">
  <a class="btn btn-small btn-inverse" href="<c:url value='/proposer-trajet' />">
    <i class="icon-plus icon-white"></i>
    <span class="hidden-phone">
      <spring:message code="route.controls.new" />
    </span>
  </a>
  <c:if test="${ not empty $routes }">
    <button class="btn btn-small edit">
      <i class="icon-pencil"></i>
      <span class="hidden-phone">
        <spring:message code="route.controls.edit" />
      </span>
    </button>
    <button class="btn btn-small cancel">
      <i class="icon-remove"></i>
      <span class="hidden-phone">
        <spring:message code="route.controls.cancel" />
      </span>
    </button>
  </c:if>
</div>
<tiles:useAttribute id="titleCode" name="title" />
<h1><spring:message code="${ titleCode }" /></h1>
