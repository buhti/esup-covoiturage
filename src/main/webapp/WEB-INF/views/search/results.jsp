<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<div id="search-criterias">
  <c:if test="${ not empty search }">
    <p>
      ${ search['from'] } (<span class="about"><sup>+</sup>/-</span> ${ search['fromTolerance'] })
      <span class="arrow">&rarr;</span>
      ${ search['to'] } (<span class="about"><sup>+</sup>/-</span> ${ search['toTolerance'] })
    </p>
    <p>
      <joda:format value="${ search['date'] }" pattern="'le' dd/MM/YYYY 'à' HH:mm" />
      (<span class="about"><sup>+</sup>/-</span> ${ search['dateTolerance'] })
    </p>
  </c:if>
</div>
<div id="search-results" data-results="<c:url value='/recherche/resultats/' />" data-count="${ count }">
  <c:choose>
    <c:when test="${ count gt 0 }">
      <p class="lead">
        <spring:message code="search.results.count${ count gt 1 ? '.plural' : '' }" arguments="${ count }" />
      </p>
      <div id="results-container" class="route-list"></div>
      <div id="more-results">
        <button class="btn input-xxlarge disabled">
          <spring:message code="search.results.more" />
        </button>
      </div>
    </c:when>
    <c:otherwise>
      <div class="alert alert-warning">
        <spring:message code="search.results.empty" />
      </div>
    </c:otherwise>
  </c:choose>
</div>
