<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

  <title><tiles:insertAttribute name="title" defaultValue="Co-voiturage" /></title>

  <link href="<c:url value='/resources/css/bootstrap.css'/>" rel="stylesheet" />
  <link href="<c:url value='/resources/css/bootstrap-responsive.css'/>" rel="stylesheet" />
  <link href="<c:url value='/resources/css/main.css'/>" rel="stylesheet" />

  <!--[if lt IE 9]>
    <script src="<c:url value='/resources/js/vendor/html5shiv-3.6.min.js'/>"></script>
  <![endif]-->
</head>
<body>

  <div class="navbar">
    <div class="navbar-inner">
      <div class="container">
        <a class="brand" href="<c:url value='/'/>">Co-voiturage</a>
        <div class="nav-collapse">
          <ul class="nav">
            <li><a href="<c:url value='/recherche'/>">Rechercher</a></li>
            <li><a href="<c:url value='/proposer-trajet'/>">Proposer</a></li>
            <li><a href="<c:url value='/mes-trajets'/>">Voir mes trajets</a></li>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
              <li><a href="<c:url value='/admin/statistiques'/>">Administration</a></li>
            </sec:authorize>
          </ul>
        </div>
      </div>
    </div>
  </div>

  <div class="container">
    <header class="header">
      <tiles:insertAttribute name="header" />
    </header>
    <div class="content">
      <tiles:insertAttribute name="content" />
    </div>
    <footer class="footer">
      <tiles:insertAttribute name="footer" />
    </footer>
  </div>

  <script src="http://maps.googleapis.com/maps/api/js?key=${ config.googleApiKey }&amp;sensor=false&amp;language=fr"></script>
  <script src="<c:url value='/resources/js/vendor/jquery-1.7.2.min.js'/>"></script>
  <script src="<c:url value='/resources/js/vendor/bootstrap-modal.js'/>"></script>
  <script src="<c:url value='/resources/js/vendor/bootstrap-tab.js'/>"></script>
  <script src="<c:url value='/resources/js/vendor/bootstrap-typeahead.js'/>"></script>
  <script src="<c:url value='/resources/js/main.js'/>"></script>

</body>
</html>