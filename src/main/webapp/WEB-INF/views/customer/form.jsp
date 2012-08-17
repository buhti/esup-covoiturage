<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<form:form method="post" modelAttribute="customerForm" cssClass="form-horizontal">
  <fieldset>
    <legend>Mes informations</legend>
    <spring:bind path="lastname">
      <c:set var="lastnameError" value="${ status.errorMessage }"></c:set>
    </spring:bind>
    <div class="control-group ${ not empty lastnameError ? 'error' : '' }">
      <form:label path="lastname" cssClass="control-label">Nom</form:label>
      <div class="controls">
        <form:input path="lastname"/>
        <form:errors path="lastname" cssClass="help-inline" />
      </div>
    </div>
    <spring:bind path="firstname">
      <c:set var="firstnameError" value="${ status.errorMessage }"></c:set>
    </spring:bind>
    <div class="control-group ${ not empty firstnameError ? 'error' : '' }">
      <form:label path="firstname" cssClass="control-label">Prénom</form:label>
      <div class="controls">
        <form:input path="firstname"/>
        <form:errors path="firstname" cssClass="help-inline" />
      </div>
    </div>
    <spring:bind path="email">
      <c:set var="emailError" value="${ status.errorMessage }"></c:set>
    </spring:bind>
    <div class="control-group ${ not empty emailError ? 'error' : '' }">
      <form:label path="email" cssClass="control-label">Email</form:label>
      <div class="controls">
        <form:input path="email"/>
        <form:errors path="email" cssClass="help-inline" />
      </div>
    </div>
  </fieldset>
  <div class="form-actions">
    <button class="btn btn-primary" type="submit">Enregister</button>
  </div>
</form:form>