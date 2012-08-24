<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<div class="controls clearfix" data-warning="Êtes-vous sûr de vouloir supprimer ce trajet ?">
  <button class="btn btn-small edit">
    <i class="icon-pencil"></i>
    <span class="hidden-phone">Editer</span>
  </button>
  <button class="btn btn-small cancel">
    <i class="icon-remove"></i>
    <span class="hidden-phone">Annuler</span>
  </button>
</div>
<h1><tiles:insertAttribute name="title" /></h1>
