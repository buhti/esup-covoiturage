<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<div id="admin-stats">

  <div id="visualization" style="height: 400px"
    data-type="${ type }" data-source="<c:url value='/admin/statistiques/json'/>"></div>

  <div class="btn-toolbar">
    <span class="category">
      <spring:message code="stats.period.title" />
    </span>
    <div class="btn-group period" data-toggle="buttons-radio">
      <button class="btn active" data-period="WEEK">
        <spring:message code="stats.period.week" />
      </button>
      <button class="btn" data-period="MONTH">
        <spring:message code="stats.period.month" />
      </button>
      <button class="btn" data-period="YEAR">
        <spring:message code="stats.period.year" />
      </button>
    </div>
    <span class="category">
      <spring:message code="stats.export.title" />
    </span>
    <div class="btn-group export">
      <a class="btn" href="<c:url value='/admin/statistiques/csv'/>">
        <spring:message code="stats.export.csv" />
      </a>
    </div>
  </div>

  <script type="text/javascript" src="http://www.google.com/jsapi"></script>
  <script type="text/javascript">
      google.load('visualization', '1', {packages: ['corechart']});
  </script>

</div>
