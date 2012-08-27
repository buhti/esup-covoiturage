<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<script>
window.predefinedLocations = ${ data.predefinedLocationsJSON };
</script>
<form:form method="post" modelAttribute="routeForm" cssClass="form-horizontal">
  <div class="row">
    <fieldset class="span6">
      <legend>Adresses</legend>
      <spring:bind path="fromAddress">
        <c:set var="fromAddressError" value="${ status.errorMessage }"></c:set>
      </spring:bind>
      <div class="control-group ${ not empty fromAddressError ? 'error' : '' }">
        <form:label path="fromAddress" cssClass="control-label">Lieu de départ</form:label>
        <div class="controls">
          <form:input path="fromAddress" cssClass="span4" data-provide="typeahead" data-items="4" data-source-ref="predefinedLocations" />
          <form:errors path="fromAddress" cssClass="help-inline" />
          <span class="help-block hidden-phone">
            <a class="disabled" href="#" data-map="#fromAddress" data-color="red">Vérifier sur la carte</a>
          </span>
        </div>
      </div>
      <spring:bind path="toAddress">
        <c:set var="toAddressError" value="${ status.errorMessage }"></c:set>
      </spring:bind>
      <div class="control-group ${ not empty toAddressError ? 'error' : '' }">
        <form:label path="toAddress" cssClass="control-label">Lieu d'arrivée</form:label>
        <div class="controls">
          <form:input path="toAddress" cssClass="span4" data-provide="typeahead" data-items="4" data-source-ref="predefinedLocations" />
          <form:errors path="toAddress" cssClass="help-inline" />
          <span class="help-block hidden-phone">
            <a class="disabled" href="#" data-map="#toAddress" data-color="blue">Vérifier sur la carte</a>
          </span>
        </div>
      </div>
    </fieldset>
    <fieldset class="span6">
      <legend>Type de trajet</legend>
      <div class="control-group">
        <label class="radio">
          <form:radiobutton path="roundTrip" value="false" />
          Aller simple
        </label>
        <label class="radio">
          <form:radiobutton path="roundTrip" value="true" />
          Aller-retour
        </label>
      </div>
      <div class="control-group">
        <label class="radio">
          <form:radiobutton path="recurrent" value="false" />
          Trajet ponctuel
        </label>
        <label class="radio">
          <form:radiobutton path="recurrent" value="true" />
          Trajet fréquent
        </label>
      </div>
    </fieldset>
  </div>
  <fieldset class="occasional ${ routeForm.recurrent ? 'hide' : '' }">
    <legend>Dates</legend>
    <div class="control-group wayOut">
      <form:label path="occasionalForm.wayOut.day" cssClass="control-label">Aller</form:label>
      <div class="controls controls-row">
        <form:select cssClass="span1" path="occasionalForm.wayOut.day" items="${ data.days }" />
        <form:select cssClass="span2" path="occasionalForm.wayOut.month" items="${ data.months }" />
        <form:select cssClass="span1" path="occasionalForm.wayOut.year" items="${ data.years }" />
        <form:select cssClass="span1" path="occasionalForm.wayOut.time" items="${ data.hoursAndMinutes }" />
      </div>
    </div>
    <div class="control-group wayBack">
      <form:label path="occasionalForm.wayBack.day" cssClass="control-label">Retour</form:label>
      <div class="controls controls-row">
        <form:select cssClass="span1" path="occasionalForm.wayBack.day" items="${ data.days }" />
        <form:select cssClass="span2" path="occasionalForm.wayBack.month" items="${ data.months }" />
        <form:select cssClass="span1" path="occasionalForm.wayBack.year" items="${ data.years }" />
        <form:select cssClass="span1" path="occasionalForm.wayBack.time" items="${ data.hoursAndMinutes }" />
      </div>
    </div>
  </fieldset>
  <fieldset class="recurrent ${ routeForm.recurrent ? '' : 'hide' }">
    <legend>Dates</legend>
    <div class="row">
      <div class="span6">
        <div class="control-group">
          <form:label path="recurrentForm.startDate.day" cssClass="control-label">Commence le</form:label>
          <div class="controls controls-row">
            <form:select cssClass="span1" path="recurrentForm.startDate.day" items="${ data.days }" />
            <form:select cssClass="span2" path="recurrentForm.startDate.month" items="${ data.months }" />
            <form:select cssClass="span1" path="recurrentForm.startDate.year" items="${ data.years }" />
          </div>
        </div>
        <div class="control-group wayOut">
          <form:label path="recurrentForm.wayOutTime.time" cssClass="control-label">Départ à</form:label>
          <div class="controls">
            <form:select cssClass="span4" path="recurrentForm.wayOutTime.time" items="${ data.hoursAndMinutes }" />
          </div>
        </div>
      </div>
      <div class="span6">
        <div class="control-group">
          <form:label path="recurrentForm.endDate.day" cssClass="control-label">Termine le</form:label>
          <div class="controls controls-row">
            <form:select cssClass="span1" path="recurrentForm.endDate.day" items="${ data.days }" />
            <form:select cssClass="span2" path="recurrentForm.endDate.month" items="${ data.months }" />
            <form:select cssClass="span1" path="recurrentForm.endDate.year" items="${ data.years }" />
          </div>
        </div>
        <div class="control-group wayBack">
          <form:label path="recurrentForm.wayBackTime.time" cssClass="control-label">Retour à</form:label>
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
      <form:label path="recurrentForm.weekDays" cssClass="control-label">Jours</form:label>
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
    <legend>Informations complémentaires</legend>
    <div class="control-group">
      <label class="checkbox">
        <form:checkbox path="ladiesOnly" />
        Ce trajet est réservé aux femmes
      </label>
      <label class="checkbox">
        <form:checkbox path="driver" />
        Je suis le conducteur
      </label>
    </div>
    <div class="control-group">
      <form:label path="seats" cssClass="control-label">Places</form:label>
      <div class="controls">
        <form:select path="seats" items="${ data.availableSeats }" />
      </div>
    </div>
  </fieldset>
  <div class="form-actions">
    <button class="btn btn-primary" type="submit">Proposer</button>
  </div>
  <div class="hidden-phone">
    <div id="map-modal" class="modal hide">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>Aperçu</h3>
      </div>
      <div class="modal-body">
        <div id="map-canvas" style="width: 530px; height: 300px"></div>
      </div>
    </div>
  </div>
</form:form>
