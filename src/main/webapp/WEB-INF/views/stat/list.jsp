<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="stat-list">
  <ul>
    <li><a href="<c:url value='/admin/statistiques/connexions' />">Statistiques de connexion</a></li>
    <li><a href="<c:url value='/admin/statistiques/inscriptions' />">Statistiques d'inscription</a></li>
    <li><a href="<c:url value='/admin/statistiques/trajets' />">Statistiques des trajets</a></li>
    <li><a href="<c:url value='/admin/statistiques/recherches' />">Statistiques de recherche</a></li>
  </ul>
</div>
