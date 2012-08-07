(function($, window) {
  "use strict";

  // Google Maps enhancements
  // ------------------------

  // Déclaration des options de la carte.
  var mapOptions = {
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
  var routeMap;
  $routeForm.find('#mapCanvas').each(function() {
    routeMap = new google.maps.Map(document.getElementById("mapCanvas"), mapOptions);
  });

  (function(map) {
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

      return false;
    });
  })(routeMap);

  // Détermine si un trajet est récurrent ou non en fonction de l'onglet
  // sélectionné.
  $routeForm.find('a[data-toggle="tab"]').on('shown', function(e) {
    $routeForm.find('input#recurrent').val($(e.currentTarget).data('target') === '#tab-recurrent');
  });

  $routeForm.find('input#driver1').on('change', function() {
    if ($(this).is(':checked')) {
      $routeForm.find('select#seats').parents('.control-group').show();
    } else {
      $routeForm.find('select#seats').parents('.control-group').hide();
    }
  }).change();

  // Voir un trajet
  // --------------

  // Récupère la page afin de restreindre le scope des requêtes jQuery.
  var $routePage = $('#view-route');

  // Aperçus du lieu de départ et d'arrivée du trajet
  $routePage.find('.preview').each(function() {
    var $this = $(this)
      , lat = $this.data('lat')
      , lng = $this.data('lng')
      , address = $this.parent().find('.address').text();

    var map = new google.maps.Map($this[0], {
      zoom: 13,
      center: new google.maps.LatLng(lat, lng),
      mapTypeId: google.maps.MapTypeId.ROADMAP,
      disableDefaultUI: true,
      disableDoubleClickZoom: true,
      draggable: false,
      keyboardShortcuts: false,
      scrollwheel: false
    });

    new google.maps.Marker({
      map: map,
      position: new google.maps.LatLng(lat, lng),
      title: address
    });
  });

})(jQuery, window);
