<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="admin-stats">

  <div id="visualization" style="height: 400px"
    data-type="${ type }" data-source="<c:url value='/admin/statistiques/json'/>"></div>

  <div class="btn-toolbar">
    <span class="category">Période</span>
    <div class="btn-group period" data-toggle="buttons-radio">
      <button class="btn active" data-period="WEEK">Semaine</button>
      <button class="btn" data-period="MONTH">Mois</button>
      <button class="btn" data-period="YEAR">Année</button>
    </div>
    <span class="category">Export</span>
    <div class="btn-group export">
      <a class="btn" href="<c:url value='/admin/statistiques/csv'/>">CSV</a>
    </div>
  </div>

  <script type="text/javascript" src="http://www.google.com/jsapi"></script>
  <script type="text/javascript">
      google.load('visualization', '1', {packages: ['corechart']});
  </script>

</div>
