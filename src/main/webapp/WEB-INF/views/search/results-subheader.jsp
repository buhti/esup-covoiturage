<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<div class="controls clearfix">
  <a class="btn btn-small edit" href="<c:url value='/recherche?edit=1' />">
    <i class="icon-search"></i>
    <span class="hidden-phone">
      <spring:message code="search.controls.modify" />
    </span>
  </a>
</div>
<tiles:useAttribute id="titleCode" name="title" />
<h1><spring:message code="${ titleCode }" /></h1>
