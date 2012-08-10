<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<div id="search-criterias">
  <c:if test="${ not empty search }">
    <p>
      ${ search['from'] } (<sup>+</sup>/<sub>-</sub> ${ search['fromTolerance'] }) &rarr;
      ${ search['to'] } (<sup>+</sup>/<sub>-</sub> ${ search['toTolerance'] })
    </p>
    <p>
      <joda:format value="${ search['date'] }" pattern="'le' dd/MM/YYYY 'à' HH:mm" />
      (<sup>+</sup>/<sub>-</sub> ${ search['dateTolerance'] })
    </p>
  </c:if>
  <p>${ count } résultat${ count gt 1 ? 's' : '' }</p>
</div>
<div id="search-results" data-results="<c:url value='/recherche/resultats/' />">
  <div id="results-container"></div>
  <div id="more-results">
    <a href="#">Plus de résultats</a>
  </div>
</div>
