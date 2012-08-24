<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<div class="controls clearfix" data-warning="Êtes-vous sûr de vouloir supprimer ce trajet ?">
  <a class="btn btn-small btn-inverse" href="<c:url value='/proposer-trajet' />">
    <i class="icon-plus icon-white"></i>
    <span class="hidden-phone">Nouveau</span>
  </a>
  <c:if test="${ not empty $routes }">
    <button class="btn btn-small edit">
      <i class="icon-pencil"></i>
      <span class="hidden-phone">Editer</span>
    </button>
    <button class="btn btn-small cancel">
      <i class="icon-remove"></i>
      <span class="hidden-phone">Annuler</span>
    </button>
  </c:if>
</div>
<h1><tiles:insertAttribute name="title" /></h1>
