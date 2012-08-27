<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<script>
window.predefinedLocations = ${ data.predefinedLocationsJSON };
</script>
<form:form method="post" modelAttribute="searchForm">
  <div class="row">
    <fieldset class="span6 form-horizontal">
      <legend>
        <spring:message code="search.form.fromAddress" />
      </legend>
      <spring:bind path="from">
        <c:set var="fromError" value="${ status.errorMessage }"></c:set>
      </spring:bind>
      <div class="control-group ${ not empty fromError ? 'error' : '' }">
        <form:label path="from" cssClass="control-label">
          <spring:message code="search.form.address" />
        </form:label>
        <div class="controls">
          <form:input cssClass="span4" path="from" data-provide="typeahead" data-items="4" data-source-ref="predefinedLocations" />
          <form:errors path="from" cssClass="help-inline" />
        </div>
      </div>
      <div class="control-group">
        <form:label path="fromTolerance" cssClass="control-label">
          <spring:message code="search.form.radius" />
        </form:label>
        <div class="controls">
          <form:select cssClass="span4" path="fromTolerance">
            <form:options items="${ data.distanceTolerances }"/>
          </form:select>
        </div>
      </div>
    </fieldset>
    <fieldset class="span6 form-horizontal">
      <legend>
        <spring:message code="search.form.toAddress" />
      </legend>
      <spring:bind path="to">
        <c:set var="toError" value="${ status.errorMessage }"></c:set>
      </spring:bind>
      <div class="control-group ${ not empty toError ? 'error' : '' }">
        <form:label path="to" cssClass="control-label">
          <spring:message code="search.form.address" />
        </form:label>
        <div class="controls">
          <form:input cssClass="span4" path="to" data-provide="typeahead" data-items="4" data-source-ref="predefinedLocations" />
          <form:errors path="to" cssClass="help-inline" />
        </div>
      </div>
      <div class="control-group">
        <form:label path="toTolerance" cssClass="control-label">
          <spring:message code="search.form.radius" />
        </form:label>
        <div class="controls">
          <form:select cssClass="span4" path="toTolerance">
            <form:options items="${ data.distanceTolerances }"/>
          </form:select>
        </div>
      </div>
    </fieldset>
  </div>
  <div class="row">
    <div class="span12">
    <fieldset class="form-horizontal">
      <legend>
        <spring:message code="search.form.date" />
      </legend>
      <div class="control-group">
        <form:label path="date.day" cssClass="control-label">
          <spring:message code="search.form.day" />
        </form:label>
        <div class="controls controls-row">
          <form:select cssClass="span1" path="date.day" items="${ data.days }" />
          <form:select cssClass="span1" path="date.month" items="${ data.months }" />
          <form:select cssClass="span1" path="date.year" items="${ data.years }" />
          <form:select cssClass="span1" path="date.time" items="${ data.hoursAndMinutes }" />
        </div>
      </div>
      <div class="control-group">
        <form:label path="dateTolerance" cssClass="control-label">
          <spring:message code="search.form.minutes" />
        </form:label>
        <div class="controls">
          <form:select cssClass="span4" path="dateTolerance">
            <form:options items="${ data.dateTolerances }"/>
          </form:select>
        </div>
      </div>
    </fieldset>
    </div>
  </div>
  <div class="row">
    <div class="form-actions">
      <button class="btn btn-primary" type="submit">
        <spring:message code="search.form.search" />
      </button>
    </div>
  </div>
</form:form>
