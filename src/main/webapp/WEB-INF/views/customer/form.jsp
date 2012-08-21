<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<form:form method="post" modelAttribute="customerForm" cssClass="form-horizontal">
  <spring:bind path="email">
    <c:set var="emailError" value="${ status.errorMessage }"></c:set>
  </spring:bind>
  <div class="control-group ${ not empty emailError ? 'error' : '' }">
    <form:label path="email" cssClass="control-label">Email</form:label>
    <div class="controls">
      <form:input path="email" cssClass="input-xxlarge"/>
      <form:errors path="email" cssClass="help-inline" />
      <span class="help-block">Cette adresse est visible afin que les autres utilisateurs puissent vous contacter.</span>
    </div>
  </div>
  <spring:bind path="lastname">
    <c:set var="lastnameError" value="${ status.errorMessage }"></c:set>
  </spring:bind>
  <div class="control-group ${ not empty lastnameError ? 'error' : '' }">
    <form:label path="lastname" cssClass="control-label">Nom</form:label>
    <div class="controls">
      <form:input path="lastname" cssClass="input-xxlarge"/>
      <form:errors path="lastname" cssClass="help-inline" />
      <span class="help-block">Afin de conserver votre anonymat, seule la première lettre du nom est affichée.</span>
    </div>
  </div>
  <spring:bind path="firstname">
    <c:set var="firstnameError" value="${ status.errorMessage }"></c:set>
  </spring:bind>
  <div class="control-group ${ not empty firstnameError ? 'error' : '' }">
    <form:label path="firstname" cssClass="control-label">Prénom</form:label>
    <div class="controls">
      <form:input path="firstname" cssClass="input-xxlarge"/>
      <form:errors path="firstname" cssClass="help-inline" />
    </div>
  </div>
  <div class="form-actions">
    <button class="btn btn-primary" type="submit">Enregister</button>
  </div>
</form:form>