<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<script>
window.predefinedLocations = ${ data.predefinedLocationsJSON };
</script>
<form:form method="post" modelAttribute="routeForm" cssClass="form-horizontal">
  <div class="row">
    <fieldset class="span6">
      <legend>
        <spring:message code="route.form.addresses" />
      </legend>
      <spring:bind path="fromAddress">
        <c:set var="fromAddressError" value="${ status.errorMessage }"></c:set>
      </spring:bind>
      <div class="control-group ${ not empty fromAddressError ? 'error' : '' }">
        <form:label path="fromAddress" cssClass="control-label">
          <spring:message code="route.form.toAddress" />
        </form:label>
        <div class="controls">
          <form:input path="fromAddress" cssClass="span4" data-provide="typeahead" data-items="4" data-source-ref="predefinedLocations" />
          <form:errors path="fromAddress" cssClass="help-inline" />
          <span class="help-block hidden-phone">
            <a class="disabled" href="#" data-map="#fromAddress" data-color="red">
              <spring:message code="route.form.checkOnMap" />
            </a>
          </span>
        </div>
      </div>
      <spring:bind path="toAddress">
        <c:set var="toAddressError" value="${ status.errorMessage }"></c:set>
      </spring:bind>
      <div class="control-group ${ not empty toAddressError ? 'error' : '' }">
        <form:label path="toAddress" cssClass="control-label">
          <spring:message code="route.form.toAddress" />
        </form:label>
        <div class="controls">
          <form:input path="toAddress" cssClass="span4" data-provide="typeahead" data-items="4" data-source-ref="predefinedLocations" />
          <form:errors path="toAddress" cssClass="help-inline" />
          <span class="help-block hidden-phone">
            <a class="disabled" href="#" data-map="#toAddress" data-color="blue">
              <spring:message code="route.form.checkOnMap" />
            </a>
          </span>
        </div>
      </div>
    </fieldset>
    <fieldset class="span6">
      <legend>
        <spring:message code="route.form.type" />
      </legend>
      <div class="control-group">
        <label class="radio">
          <form:radiobutton path="roundTrip" value="false" />
          <spring:message code="route.form.roundTrip.no" />
        </label>
        <label class="radio">
          <form:radiobutton path="roundTrip" value="true" />
          <spring:message code="route.form.roundTrip.yes" />
        </label>
      </div>
      <div class="control-group">
        <label class="radio">
          <form:radiobutton path="recurrent" value="false" />
          <spring:message code="route.form.recurrent.no" />
        </label>
        <label class="radio">
          <form:radiobutton path="recurrent" value="true" />
          <spring:message code="route.form.recurrent.yes" />
        </label>
      </div>
    </fieldset>
  </div>
  <fieldset class="occasional ${ routeForm.recurrent ? 'hide' : '' }">
    <legend>
      <spring:message code="route.form.dates" />
    </legend>
    <div class="control-group wayOut">
      <form:label path="occasionalForm.wayOut.day" cssClass="control-label">
        <spring:message code="route.form.wayOut" />
      </form:label>
      <div class="controls controls-row">
        <form:select cssClass="span1" path="occasionalForm.wayOut.day" items="${ data.days }" />
        <form:select cssClass="span2" path="occasionalForm.wayOut.month" items="${ data.months }" />
        <form:select cssClass="span1" path="occasionalForm.wayOut.year" items="${ data.years }" />
        <form:select cssClass="span1" path="occasionalForm.wayOut.time" items="${ data.hoursAndMinutes }" />
      </div>
    </div>
    <div class="control-group wayBack">
      <form:label path="occasionalForm.wayBack.day" cssClass="control-label">
        <spring:message code="route.form.wayBack" />
      </form:label>
      <div class="controls controls-row">
        <form:select cssClass="span1" path="occasionalForm.wayBack.day" items="${ data.days }" />
        <form:select cssClass="span2" path="occasionalForm.wayBack.month" items="${ data.months }" />
        <form:select cssClass="span1" path="occasionalForm.wayBack.year" items="${ data.years }" />
        <form:select cssClass="span1" path="occasionalForm.wayBack.time" items="${ data.hoursAndMinutes }" />
      </div>
    </div>
  </fieldset>
  <fieldset class="recurrent ${ routeForm.recurrent ? '' : 'hide' }">
    <legend>
      <spring:message code="route.form.dates" />
    </legend>
    <div class="row">
      <div class="span6">
        <div class="control-group">
          <form:label path="recurrentForm.startDate.day" cssClass="control-label">
            <spring:message code="route.form.startDate" />
          </form:label>
          <div class="controls controls-row">
            <form:select cssClass="span1" path="recurrentForm.startDate.day" items="${ data.days }" />
            <form:select cssClass="span2" path="recurrentForm.startDate.month" items="${ data.months }" />
            <form:select cssClass="span1" path="recurrentForm.startDate.year" items="${ data.years }" />
          </div>
        </div>
        <div class="control-group wayOut">
          <form:label path="recurrentForm.wayOutTime.time" cssClass="control-label">
            <spring:message code="route.form.wayOutTime" />
          </form:label>
          <div class="controls">
            <form:select cssClass="span4" path="recurrentForm.wayOutTime.time" items="${ data.hoursAndMinutes }" />
          </div>
        </div>
      </div>
      <div class="span6">
        <div class="control-group">
          <form:label path="recurrentForm.endDate.day" cssClass="control-label">
            <spring:message code="route.form.endDate" />
          </form:label>
          <div class="controls controls-row">
            <form:select cssClass="span1" path="recurrentForm.endDate.day" items="${ data.days }" />
            <form:select cssClass="span2" path="recurrentForm.endDate.month" items="${ data.months }" />
            <form:select cssClass="span1" path="recurrentForm.endDate.year" items="${ data.years }" />
          </div>
        </div>
        <div class="control-group wayBack">
          <form:label path="recurrentForm.wayBackTime.time" cssClass="control-label">
            <spring:message code="route.form.wayBackTime" />
          </form:label>
          <div class="controls">
            <form:select cssClass="span4" path="recurrentForm.wayBackTime.time" items="${ data.hoursAndMinutes }" />
          </div>
        </div>
      </div>
    </div>
    <spring:bind path="recurrentForm.weekDays">
      <c:set var="weekDaysError" value="${ status.errorMessage }"></c:set>
    </spring:bind>
    <div class="control-group ${ not empty weekDaysError ? 'error' : '' }">
      <form:label path="recurrentForm.weekDays" cssClass="control-label">
        <spring:message code="route.form.days" />
      </form:label>
      <div class="controls">
        <c:forEach items="${ data.weekDays }" var="entry">
          <label class="checkbox inline">
            <form:checkbox path="recurrentForm.weekDays" value="${ entry.key }" />
            ${ entry.value }
          </label>
        </c:forEach>
        <form:errors path="recurrentForm.weekDays" cssClass="help-inline" />
      </div>
    </div>
  </fieldset>
  <fieldset>
    <legend>
      <spring:message code="route.form.info" />
    </legend>
    <div class="control-group">
      <label class="checkbox">
        <form:checkbox path="ladiesOnly" />
        <spring:message code="route.form.ladiesOnly" />
      </label>
      <label class="checkbox">
        <form:checkbox path="driver" />
        <spring:message code="route.form.driver" />
      </label>
    </div>
    <div class="control-group">
      <form:label path="seats" cssClass="control-label">
        <spring:message code="route.form.seats" />
      </form:label>
      <div class="controls">
        <form:select path="seats" items="${ data.availableSeats }" />
      </div>
    </div>
  </fieldset>
  <div class="form-actions">
    <button class="btn btn-primary" type="submit">
      <spring:message code="route.form.save" />
    </button>
  </div>
  <div class="hidden-phone">
    <div id="map-modal" class="modal hide">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>
          <spring:message code="route.form.view" />
        </h3>
      </div>
      <div class="modal-body">
        <div id="map-canvas" style="width: 530px; height: 300px"></div>
      </div>
    </div>
  </div>
</form:form>
