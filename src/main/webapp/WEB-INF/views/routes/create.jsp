<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<form:form method="post" modelAttribute="routeForm" cssClass="form-horizontal">
  <fieldset>
    <legend>Départ</legend>
    <div class="control-group">
      <form:label path="fromCity" maxlength="80" cssClass="control-label">Ville</form:label>
      <div class="controls">
        <form:input path="fromCity"/>
      </div>
    </div>
    <div class="control-group">
      <form:label path="fromAddress" cssClass="control-label">Adresse</form:label>
      <div class="controls">
        <form:input path="fromAddress"/>
        <select class="places-selector" data-target="#fromAddress">
          <option value="">Sites pré-configurés</option>
          <c:forEach items="${ routeForm.predefinedLocations }" var="location">
            <option value="${ location.key }">${ location.value }</option>
          </c:forEach>
        </select>
      </div>
    </div>
  </fieldset>
  <fieldset>
    <legend>Arrivée</legend>
    <div class="control-group">
      <form:label path="toCity" cssClass="control-label">Ville</form:label>
      <div class="controls">
        <form:input path="toCity"/>
      </div>
    </div>
    <div class="control-group">
      <form:label path="toAddress" cssClass="control-label">Adresse</form:label>
      <div class="controls">
        <form:input path="toAddress"/>
        <select class="places-selector" data-target="#toAddress">
          <option value="">Sites pré-configurés</option>
          <c:forEach items="${ routeForm.predefinedLocations }" var="location">
            <option value="${ location.key }">${ location.value }</option>
          </c:forEach>
        </select>
      </div>
    </div>
  </fieldset>
  <fieldset>
    <div class="control-group">
      <form:label path="status" cssClass="control-label">Je suis</form:label>
      <div class="controls">
        <form:select path="status">
          <form:options items="${ routeForm.possibleStatuses }" />
        </form:select>
      </div>
    </div>
    <div class="control-group">
      <form:label path="seats" cssClass="control-label">Places</form:label>
      <div class="controls">
        <form:select path="seats">
          <form:options items="${ routeForm.availableSeats }" />
        </form:select>
      </div>
    </div>
  </fieldset>
</form:form>
