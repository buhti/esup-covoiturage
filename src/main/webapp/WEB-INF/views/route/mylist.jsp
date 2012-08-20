<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<div id="route-list">
  <div class="controls">
    <a class="edit" href="#edit">Editer</a>
    <a class="cancel" href="#cancel">Annuler</a>
  </div>
  <div class="list">
    <tiles:insertAttribute name="list" />
  </div>
</div>
