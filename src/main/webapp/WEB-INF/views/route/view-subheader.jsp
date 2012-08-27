<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<c:if test="${ route.owner.id == account.id }">
<div class="controls clearfix" data-warning="<spring:message code='route.controls.confirmDelete' />">
  <a href="<c:url value='/trajet/${ route.id }/modifier' />" class="btn btn-small edit">
    <i class="icon-pencil"></i>
    <span class="hidden-phone">
      <spring:message code="route.controls.edit" />
    </span>
  </a>
  <a href="<c:url value='/trajet/${ route.id }/supprimer' />" class="btn btn-small btn-danger delete">
    <i class="icon-trash icon-white"></i>
    <span class="hidden-phone">
      <spring:message code="route.controls.delete" />
    </span>
  </a>
</div>
</c:if>
<tiles:useAttribute id="titleCode" name="title" />
<h1><spring:message code="${ titleCode }" /></h1>
