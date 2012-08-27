<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<form:form method="post" modelAttribute="customerForm" cssClass="form-horizontal">
  <spring:bind path="email">
    <c:set var="emailError" value="${ status.errorMessage }"></c:set>
  </spring:bind>
  <div class="control-group ${ not empty emailError ? 'error' : '' }">
    <form:label path="email" cssClass="control-label">
      <spring:message code="customer.form.email.label" />
    </form:label>
    <div class="controls">
      <form:input path="email" cssClass="input-xxlarge"/>
      <form:errors path="email" cssClass="help-inline"/>
      <span class="help-block">
        <spring:message code="customer.form.email.hint" />
      </span>
    </div>
  </div>
  <spring:bind path="lastname">
    <c:set var="lastnameError" value="${ status.errorMessage }"></c:set>
  </spring:bind>
  <div class="control-group ${ not empty lastnameError ? 'error' : '' }">
    <form:label path="lastname" cssClass="control-label">
      <spring:message code="customer.form.lastname.label" />
    </form:label>
    <div class="controls">
      <form:input path="lastname" cssClass="input-xxlarge"/>
      <form:errors path="lastname" cssClass="help-inline"/>
      <span class="help-block">
        <spring:message code="customer.form.lastname.hint" />
      </span>
    </div>
  </div>
  <spring:bind path="firstname">
    <c:set var="firstnameError" value="${ status.errorMessage }"></c:set>
  </spring:bind>
  <div class="control-group ${ not empty firstnameError ? 'error' : '' }">
    <form:label path="firstname" cssClass="control-label">
      <spring:message code="customer.form.firstname.label" />
    </form:label>
    <div class="controls">
      <form:input path="firstname" cssClass="input-xxlarge"/>
      <form:errors path="firstname" cssClass="help-inline"/>
    </div>
  </div>
  <div class="control-group">
    <div class="controls">
      <label class="checkbox">
        <form:checkbox path="chatting" />
        <spring:message code="customer.form.likes.chatting" />
      </label>
      <label class="checkbox">
        <form:checkbox path="listeningMusic" />
        <spring:message code="customer.form.likes.listeningMusic" />
      </label>
      <label class="checkbox">
        <form:checkbox path="smoking" />
        <spring:message code="customer.form.likes.smoking" />
      </label>
    </div>
  </div>
  <div class="form-actions">
    <button class="btn btn-primary" type="submit">
      <spring:message code="customer.form.save" />
    </button>
  </div>
</form:form>