<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<script>
window.predefinedLocations = ${ routeForm.predefinedLocationsJSON };
</script>
<form:form method="post" modelAttribute="routeForm" cssClass="form-horizontal">
  <fieldset>
    <div class="row">
      <div class="span6">
        <div class="control-group">
          <form:label path="fromAddress" cssClass="control-label">Lieu de départ</form:label>
          <div class="controls">
            <form:input path="fromAddress" data-provide="typeahead" data-items="4" data-source-ref="predefinedLocations" />
            <div class="hidden-phone">
              <p><a href="#" data-map="#fromAddress">Cliquez ici pour vérifier sur la carte</a></p>
            </div>
          </div>
        </div>
        <div class="control-group">
          <form:label path="toAddress" cssClass="control-label">Lieu d'arrivée</form:label>
          <div class="controls">
            <form:input path="toAddress" data-provide="typeahead" data-items="4" data-source-ref="predefinedLocations" />
            <div class="hidden-phone">
              <p><a href="#" data-map="#toAddress">Cliquez ici pour vérifier sur la carte</a></p>
            </div>
          </div>
        </div>
        <div class="control-group">
          <form:label path="status" cssClass="control-label">Je suis</form:label>
          <div class="controls">
            <form:select path="status" items="${ routeForm.possibleStatuses }" />
          </div>
        </div>
        <div class="control-group">
          <form:label path="seats" cssClass="control-label">Places</form:label>
          <div class="controls">
            <form:select path="seats" items="${ routeForm.availableSeats }" />
          </div>
        </div>
      </div>
      <div class="span6 hidden-phone">
        <div id="mapCanvas" style="width: 100%; height: 300px"></div>
      </div>
    </div>
  </fieldset>
  <ul class="nav nav-tabs">
    <li class="active">
      <a data-toggle="tab" data-target="#tab-occasional">Ponctuel</a>
    </li>
    <li>
      <a data-toggle="tab" data-target="#tab-recurrent">Récurrent</a>
    </li>
  </ul>
  <div class="tab-content">
    <div class="tab-pane active" id="tab-occasional">
      <fieldset>
        <div class="row">
          <div class="span6">
            <div class="control-group">
              <form:label path="occasionalForm.wayOutDay" cssClass="control-label">Aller</form:label>
              <div class="controls">
                <form:select path="occasionalForm.wayOutDay" items="${ routeForm.dateDay }" />
                <form:select path="occasionalForm.wayOutMonth" items="${ routeForm.dateMonth }" />
                <form:select path="occasionalForm.wayOutYear" items="${ routeForm.dateYear }" />
                <form:select path="occasionalForm.wayOutTime" items="${ routeForm.dateTime }" />
              </div>
            </div>
          </div>
          <div class="span6">
            <div class="control-group">
              <form:label path="occasionalForm.wayBackDay" cssClass="control-label">Retour</form:label>
              <div class="controls">
                <form:select path="occasionalForm.wayBackDay" items="${ routeForm.dateDay }" />
                <form:select path="occasionalForm.wayBackMonth" items="${ routeForm.dateMonth }" />
                <form:select path="occasionalForm.wayBackYear" items="${ routeForm.dateYear }" />
                <form:select path="occasionalForm.wayBackTime" items="${ routeForm.dateTime }" />
              </div>
            </div>
          </div>
        </div>
      </fieldset>
    </div>
    <div class="tab-pane" id="tab-recurrent">
      <fieldset>
        <div class="row">
          <div class="span6">
            <div class="control-group">
              <form:label path="recurrentForm.startDay" cssClass="control-label">Commence le</form:label>
              <div class="controls">
                <form:select path="recurrentForm.startDay" items="${ routeForm.dateDay }" />
                <form:select path="recurrentForm.startMonth" items="${ routeForm.dateMonth }" />
                <form:select path="recurrentForm.startYear" items="${ routeForm.dateYear }" />
              </div>
            </div>
            <div class="control-group">
              <form:label path="recurrentForm.wayOutTime" cssClass="control-label">Départ à</form:label>
              <div class="controls">
                <form:select path="recurrentForm.wayOutTime" items="${ routeForm.dateTime }" />
              </div>
            </div>
          </div>
          <div class="span6">
            <div class="control-group">
              <form:label path="recurrentForm.endDay" cssClass="control-label">Termine le</form:label>
              <div class="controls">
                <form:select path="recurrentForm.endDay" items="${ routeForm.dateDay }" />
                <form:select path="recurrentForm.endMonth" items="${ routeForm.dateMonth }" />
                <form:select path="recurrentForm.endYear" items="${ routeForm.dateYear }" />
              </div>
            </div>
            <div class="control-group">
              <form:label path="recurrentForm.wayBackTime" cssClass="control-label">Retour à</form:label>
              <div class="controls">
                <form:select path="recurrentForm.wayBackTime" items="${ routeForm.dateTime }" />
              </div>
            </div>
          </div>
        </div>
        <%--
        <div class="control-group">
          <form:label path="wayOutDay" cssClass="control-label">Jours</form:label>
          <div class="controls">
            <form:checkboxes items="${ routeForm.dateWeekDay }" path="${ routeForm.weekDay }"/>
          </div>
        </div>
        --%>
      </fieldset>
    </div>
  </div>
  <form:hidden path="recurrent"/>
</form:form>
