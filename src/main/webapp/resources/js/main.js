(function($, window) {
  "use strict";

  // Google Maps enhancements
  // ------------------------

  // Déclaration des variables utilisées.
  var map, mapOptions = {
    zoom: 5,
    maxZoom: 13,
    center: new google.maps.LatLng(46.888, 2.300),
    mapTypeId: google.maps.MapTypeId.ROADMAP
  };

  // Permet de faciliter l'appel à la méthode `geocode()`.
  var geocode = (function() {
    var geocoder = new google.maps.Geocoder();
    return function(address, resultCallback, errorCallback) {
      geocoder.geocode({'address': address}, function(results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
          if (resultCallback !== null) {
            resultCallback.call(null, results[0]);
          }
        } else {
          if (errorCallback !== null) {
            errorCallback.call(null, status);
          }
        }
      });
    };
  })();

  // Twitter Bootstrap enhancements
  // ------------------------------

  // Permet de référencer un _data provider_ aux composants **Typeahead**.
  // L'attribut `data-source-ref` doit être positionné sur une variable définie
  // avec un scope global (object `window`.
  $('input[data-source-ref]').each(function() {
    $(this).typeahead({
      source: window[$(this).data('source-ref')]
    });
  });

  // Proposer un trajet
  // ------------------

  // Récupère le formulaire afin de restreindre le scope des requêtes jQuery.
  var $routeForm = $('form#routeForm');

  // Initialise la carte grâce à Google Maps.
  $routeForm.find('#mapCanvas').each(function() {
    map = new google.maps.Map(document.getElementById("mapCanvas"), mapOptions);
  });

  (function() {
    var markers = {};

    // Factory de callback de succès.
    var resultCallbackFactory = function(target) {
      return function(result) {
        markers[target] = new google.maps.Marker({
          map: map,
          position: result.geometry.location,
          title: result.formatted_address
        });
        
        var bounds = new google.maps.LatLngBounds()
          , marker;
        
        for (marker in markers) {
          bounds.extend(markers[marker].position);
        }
        
        map.fitBounds(bounds);
      };
    };

    // Factory de callback d'erreur.
    var errorCallbackFactory = function(target) {
      return function(status) {
        console.log(target, "ERROR:", status);
      };
    };

    $routeForm.find('a[data-map]').on('click', function() {
      var target = $(this).data('map')
        , address = $(target).val();

      if (markers[target]) {
        markers[target].setMap(null);
        delete markers[target];
      }

      if (address.length > 0) {
        geocode.apply(this, [address, resultCallbackFactory(target), errorCallbackFactory(target)]);
      }
    });
  })(map);

  // Détermine si un trajet est récurrent ou non en fonction de l'onglet
  // sélectionné.
  $routeForm.find('a[data-toggle="tab"]').on('shown', function(e) {
    $routeForm.find('input#recurrent').val($(e.currentTarget).data('target') === '#tab-recurrent');
  });

})(jQuery, window);
