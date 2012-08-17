<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<script>
window.predefinedLocations = ${ data.predefinedLocationsJSON };
</script>
<form:form method="post" modelAttribute="routeForm" cssClass="form-horizontal">
  <fieldset>
    <div class="row">
      <div class="span6">
        <spring:bind path="fromAddress">
          <c:set var="fromAddressError" value="${ status.errorMessage }"></c:set>
        </spring:bind>
        <div class="control-group ${ not empty fromAddressError ? 'error' : '' }">
          <form:label path="fromAddress" cssClass="control-label">Lieu de départ</form:label>
          <div class="controls">
            <form:input path="fromAddress" data-provide="typeahead" data-items="4" data-source-ref="predefinedLocations" />
            <form:errors path="fromAddress" cssClass="help-inline" />
            <div class="hidden-phone">
              <p><a href="#" data-map="#fromAddress">Cliquez ici pour vérifier sur la carte</a></p>
            </div>
          </div>
        </div>
        <spring:bind path="toAddress">
          <c:set var="toAddressError" value="${ status.errorMessage }"></c:set>
        </spring:bind>
        <div class="control-group ${ not empty toAddressError ? 'error' : '' }">
          <form:label path="toAddress" cssClass="control-label">Lieu d'arrivée</form:label>
          <div class="controls">
            <form:input path="toAddress" data-provide="typeahead" data-items="4" data-source-ref="predefinedLocations" />
            <form:errors path="toAddress" cssClass="help-inline" />
            <div class="hidden-phone">
              <p><a href="#" data-map="#toAddress">Cliquez ici pour vérifier sur la carte</a></p>
            </div>
          </div>
        </div>
        <div class="control-group">
          <div class="controls">
            <label>
              <form:radiobutton path="roundTrip" value="false" />
              Aller simple
            </label>
            <label>
              <form:radiobutton path="roundTrip" value="true" />
              Aller-retour
            </label>
          </div>
        </div>
        <div class="control-group">
          <div class="controls">
            <label>
              <form:checkbox path="driver" />
              Je suis le conducteur
            </label>
          </div>
        </div>
        <div class="control-group">
          <form:label path="seats" cssClass="control-label">Places</form:label>
          <div class="controls">
            <form:select path="seats" items="${ data.availableSeats }" />
          </div>
        </div>
      </div>
      <div class="span6 hidden-phone">
        <div id="mapCanvas" style="width: 100%; height: 300px"></div>
      </div>
    </div>
  </fieldset>
  <ul class="nav nav-tabs">
    <li class="${ routeForm.recurrent ? '' : 'active' }">
      <a data-toggle="tab" data-target="#tab-occasional">Ponctuel</a>
    </li>
    <li class="${ routeForm.recurrent ? 'active' : '' }">
      <a data-toggle="tab" data-target="#tab-recurrent">Récurrent</a>
    </li>
  </ul>
  <div class="tab-content">
    <div class="tab-pane ${ routeForm.recurrent ? '' : 'active' }" id="tab-occasional">
      <fieldset>
        <div class="row">
          <div class="span6 wayOut">
            <div class="control-group">
              <form:label path="occasionalForm.wayOut.day" cssClass="control-label">Aller</form:label>
              <div class="controls">
                <form:select path="occasionalForm.wayOut.day" items="${ data.days }" />
                <form:select path="occasionalForm.wayOut.month" items="${ data.months }" />
                <form:select path="occasionalForm.wayOut.year" items="${ data.years }" />
                <form:select path="occasionalForm.wayOut.time" items="${ data.hoursAndMinutes }" />
              </div>
            </div>
          </div>
          <div class="span6 wayBack">
            <div class="control-group">
              <form:label path="occasionalForm.wayBack.day" cssClass="control-label">Retour</form:label>
              <div class="controls">
                <form:select path="occasionalForm.wayBack.day" items="${ data.days }" />
                <form:select path="occasionalForm.wayBack.month" items="${ data.months }" />
                <form:select path="occasionalForm.wayBack.year" items="${ data.years }" />
                <form:select path="occasionalForm.wayBack.time" items="${ data.hoursAndMinutes }" />
              </div>
            </div>
          </div>
        </div>
      </fieldset>
    </div>
    <div class="tab-pane ${ routeForm.recurrent ? 'active' : '' }" id="tab-recurrent">
      <fieldset>
        <div class="row">
          <div class="span6">
            <div class="control-group">
              <form:label path="recurrentForm.startDate.day" cssClass="control-label">Commence le</form:label>
              <div class="controls">
                <form:select path="recurrentForm.startDate.day" items="${ data.days }" />
                <form:select path="recurrentForm.startDate.month" items="${ data.months }" />
                <form:select path="recurrentForm.startDate.year" items="${ data.years }" />
              </div>
            </div>
            <div class="control-group wayOut">
              <form:label path="recurrentForm.wayOutTime.time" cssClass="control-label">Départ à</form:label>
              <div class="controls">
                <form:select path="recurrentForm.wayOutTime.time" items="${ data.hoursAndMinutes }" />
              </div>
            </div>
          </div>
          <div class="span6">
            <div class="control-group">
              <form:label path="recurrentForm.endDate.day" cssClass="control-label">Termine le</form:label>
              <div class="controls">
                <form:select path="recurrentForm.endDate.day" items="${ data.days }" />
                <form:select path="recurrentForm.endDate.month" items="${ data.months }" />
                <form:select path="recurrentForm.endDate.year" items="${ data.years }" />
              </div>
            </div>
            <div class="control-group wayBack">
              <form:label path="recurrentForm.wayBackTime.time" cssClass="control-label">Retour à</form:label>
              <div class="controls">
                <form:select path="recurrentForm.wayBackTime.time" items="${ data.hoursAndMinutes }" />
              </div>
            </div>
          </div>
        </div>
        <spring:bind path="recurrentForm.weekDays">
          <c:set var="weekDaysError" value="${ status.errorMessage }"></c:set>
        </spring:bind>
        <div class="control-group ${ not empty weekDaysError ? 'error' : '' }">
          <form:label path="recurrentForm.weekDays" cssClass="control-label">Jours</form:label>
          <div class="controls">
            <form:checkboxes path="recurrentForm.weekDays" items="${ data.weekDays }" />
            <form:errors path="recurrentForm.weekDays" cssClass="help-inline" />
          </div>
        </div>
      </fieldset>
    </div>
    <div class="form-actions">
      <button class="btn btn-primary" type="submit">Proposer</button>
    </div>
  </div>
  <form:hidden path="recurrent"/>
</form:form>
