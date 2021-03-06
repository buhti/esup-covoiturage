<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<c:set var="minify" value="${ config.debugSkin ? '' : '.min' }" />
<tiles:useAttribute id="tab" name="tab" ignore="true" />
<tiles:useAttribute id="titleCode" name="title" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

  <title><spring:message code="${ titleCode }" /></title>

  <link href="<c:url value='/resources/css/bootstrap${ minify }.css'/>" rel="stylesheet" />
  <link href="<c:url value='/resources/css/bootstrap-responsive${ minify }.css'/>" rel="stylesheet" />
  <link href="<c:url value='/resources/css/main.css'/>" rel="stylesheet" />

  <!--[if lt IE 9]>
    <script src="<c:url value='/resources/js/html5shiv-3.6.min.js'/>"></script>
  <![endif]-->
</head>
<body>

  <div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
      <div class="container">
        <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </a>
        <a class="brand" href="<c:url value='/'/>">
          <spring:message code="nav.brand"/>
        </a>
        <div class="nav-collapse">
          <ul class="nav">
            <li class="${ tab == 1 ? 'active' : '' }">
              <a href="<c:url value='/recherche'/>">
                <spring:message code="nav.search"/>
              </a>
            </li>
            <li class="${ tab == 2 ? 'active' : '' }">
              <a href="<c:url value='/proposer-trajet'/>">
                <spring:message code="nav.createRoute"/>
              </a>
            </li>
            <li class="${ tab == 3 ? 'active' : '' }">
              <a href="<c:url value='/mes-trajets'/>">
                <spring:message code="nav.viewMyRoutes"/>
              </a>
            </li>
            <li class="${ tab == 4 ? 'active' : '' }">
              <a href="<c:url value='/mon-compte'/>">
                <spring:message code="nav.viewMyProfile"/>
              </a>
            </li>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
              <li class="dropdown${ tab == 5 ? ' active' : '' }">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                  <spring:message code="nav.stats"/>
                  <b class="caret"></b>
                </a>
                <ul class="dropdown-menu">
                  <li>
                    <a href="<c:url value='/admin/statistiques/connexions' />">
                      <spring:message code="nav.stat.logins"/>
                    </a>
                  </li>
                  <li>
                    <a href="<c:url value='/admin/statistiques/inscriptions' />">
                      <spring:message code="nav.stat.registrations"/>
                    </a>
                  </li>
                  <li>
                    <a href="<c:url value='/admin/statistiques/trajets' />">
                      <spring:message code="nav.stat.routes"/>
                    </a>
                  </li>
                  <li>
                    <a href="<c:url value='/admin/statistiques/recherches' />">
                      <spring:message code="nav.stat.searches"/>
                    </a>
                  </li>
                </ul>
              </li>
            </sec:authorize>
          </ul>
        </div>
      </div>
    </div>
  </div>

  <div class="subheader">
    <div class="inner">
      <div class="container">
        <tiles:insertAttribute name="subheader" />
      </div>
    </div>
  </div>

  <div class="content">
    <div class="inner">
      <div class="container">
        <tiles:insertAttribute name="content" />
      </div>
    </div>
  </div>

  <script src="http://maps.googleapis.com/maps/api/js?key=${ config.googleApiKey }&amp;sensor=false&amp;language=fr"></script>
  <script src="<c:url value='/resources/js/jquery-1.8.1.min.js'/>"></script>
  <script src="<c:url value='/resources/js/bootstrap${ minify }.js'/>"></script>
  <script src="<c:url value='/resources/js/main.js'/>"></script>

</body>
</html>