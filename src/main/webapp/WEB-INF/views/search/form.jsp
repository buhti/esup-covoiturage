<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<script>
window.predefinedLocations = ${ data.predefinedLocationsJSON };
</script>
<form:form method="post" modelAttribute="searchForm" cssClass="form-horizontal">
  <div class="row">
    <div class="span6">
    <fieldset>
      <legend>Départ</legend>
      <spring:bind path="from">
        <c:set var="fromError" value="${ status.errorMessage }"></c:set>
      </spring:bind>
      <div class="control-group ${ not empty fromError ? 'error' : '' }">
        <form:label path="from" cssClass="control-label">Adresse ou ville</form:label>
        <div class="controls">
          <form:input path="from" data-provide="typeahead" data-items="4" data-source-ref="predefinedLocations" />
          <form:errors path="from" cssClass="help-inline" />
        </div>
      </div>
      <div class="control-group">
        <form:label path="fromTolerance" cssClass="control-label">Rayon</form:label>
        <div class="controls">
          <form:select path="fromTolerance">
            <form:options items="${ data.distanceTolerances }"/>
          </form:select>
        </div>
      </div>
    </fieldset>
    </div>
    <div class="span6">
    <fieldset>
      <legend>Arrivée</legend>
      <spring:bind path="to">
        <c:set var="toError" value="${ status.errorMessage }"></c:set>
      </spring:bind>
      <div class="control-group ${ not empty toError ? 'error' : '' }">
        <form:label path="to" cssClass="control-label">Adresse ou ville</form:label>
        <div class="controls">
          <form:input path="to" data-provide="typeahead" data-items="4" data-source-ref="predefinedLocations" />
          <form:errors path="to" cssClass="help-inline" />
        </div>
      </div>
      <div class="control-group">
        <form:label path="toTolerance" cssClass="control-label">Rayon</form:label>
        <div class="controls">
          <form:select path="toTolerance">
            <form:options items="${ data.distanceTolerances }"/>
          </form:select>
        </div>
      </div>
    </fieldset>
    </div>
  </div>
  <div class="row">
    <div class="span6">
    <fieldset>
      <legend>Date</legend>
      <div class="control-group">
        <form:label path="date.day" cssClass="control-label">Aller</form:label>
        <div class="controls">
          <form:select path="date.day" items="${ data.days }" />
          <form:select path="date.month" items="${ data.months }" />
          <form:select path="date.year" items="${ data.years }" />
          <form:select path="date.time" items="${ data.hoursAndMinutes }" />
        </div>
      </div>
      <div class="control-group">
        <form:label path="dateTolerance" cssClass="control-label">+/- minutes</form:label>
        <div class="controls">
          <form:select path="dateTolerance">
            <form:options items="${ data.dateTolerances }"/>
          </form:select>
        </div>
      </div>
    </fieldset>
    </div>
  </div>
  <div class="row">
    <div class="form-actions">
      <button class="btn btn-primary" type="submit">Rechercher</button>
    </div>
  </div>
</form:form>
