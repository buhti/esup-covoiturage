<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<div id="route-list" data-warning="Êtes-vous sûr de vouloir supprimer ce trajet ?">

  <div class="controls clearfix">
    <button class="btn btn-small btn-info edit" type="button">
      <i class="icon-pencil icon-white"></i> Editer
    </button>
    <button class="btn btn-small cancel" type="button">
      <i class="icon-remove"></i> Annuler
    </button>
  </div>

  <div class="list">
    <tiles:insertAttribute name="list" />
  </div>

</div>
