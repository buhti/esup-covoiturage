<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<form:form method="post" modelAttribute="searchForm" cssClass="form-horizontal">
  <fieldset>
    <legend>Départ</legend>
    <div class="control-group">
      <form:label path="from" cssClass="control-label">Ville</form:label>
      <div class="controls">
        <form:input path="from"/>
        <select class="location-selector" data-target="#from">
          <option value="">Sites pré-configurés</option>
          <c:forEach items="${ searchForm.predefinedLocations }" var="location">
            <option value="${ location.key }">${ location.value }</option>
          </c:forEach>
        </select>
      </div>
    </div>
    <div class="control-group">
      <form:label path="fromTolerance" cssClass="control-label">Rayon</form:label>
      <div class="controls">
        <form:select path="fromTolerance">
          <form:option value="">Indifférent</form:option>
          <form:options items="${ searchForm.distanceTolerances }"/>
        </form:select>
      </div>
    </div>
  </fieldset>
  <fieldset>
    <legend>Arrivée</legend>
    <div class="control-group">
      <form:label path="to" cssClass="control-label">Ville</form:label>
      <div class="controls">
        <form:input path="to"/>
        <select class="location-selector" data-target="#to">
          <option value="">Sites pré-configurés</option>
          <c:forEach items="${ searchForm.predefinedLocations }" var="location">
            <option value="${ location.key }">${ location.value }</option>
          </c:forEach>
        </select>
      </div>
    </div>
    <div class="control-group">
      <form:label path="toTolerance" cssClass="control-label">Rayon</form:label>
      <div class="controls">
        <form:select path="toTolerance">
          <form:option value="">Indifférent</form:option>
          <form:options items="${ searchForm.distanceTolerances }"/>
        </form:select>
      </div>
    </div>
  </fieldset>
</form:form>
