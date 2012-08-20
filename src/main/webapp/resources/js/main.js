(function($, window) {
  "use strict";
  
  // Helpers
  // -------

  function formatDate(date) {
    var f = function(num) {
      return num > 9 ? num : '0' + num;
    };
    
    return f(date.getDate()) + '/' + f(date.getMonth() + 1) + '/' + date.getFullYear();
  }

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

  // Affiche le choix du nombre de places seulement si l'utilisateur est le
  // conducteur.
  $routeForm.find('input#driver1').on('change', function() {
    if ($(this).is(':checked')) {
      $routeForm.find('select#seats').parents('.control-group').show();
    } else {
      $routeForm.find('select#seats').parents('.control-group').hide();
    }
  }).change();

  // Affiche le choix de la date et l'heure de retour si seulement il
  // s'agit d'un aller-retour.
  $routeForm.find('input#roundTrip1, input#roundTrip2').on('change', function() {
    var roundTrip = ($(this).val() === 'true') === $(this).is(':checked');
    if (roundTrip) {
      $routeForm.find('.wayBack').show();
    } else {
      $routeForm.find('.wayBack').hide();
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

  // Résultats de recherche
  // ----------------------

  // Récupère la page afin de restreindre le scope des requêtes jQuery.
  var $resultsPage = $('#search-results');
  if ($resultsPage.length > 0) {
    var currentPage = 1;

    var loadResults = function(page) {
      $.ajax($resultsPage.data('results') + page).done(function(data) {
        console.log(data);
        $resultsPage.find('#results-container').append(data);
      });
    }

    // Charge la première page de résultats
    loadResults(currentPage);

    $resultsPage.find('#more-results a').on('click', function() {
      // Load next results
    });
  }

  // Mes trajets
  // -----------

  // Récupère la page afin de restreindre le scope des requêtes jQuery.
  var $routeList = $('#route-list');
  if ($routeList.length > 0) {
    // Active le mode édition
    $routeList.find('.edit').on('click', function() {
      $routeList.addClass('edit-mode');
      $routeList.find('> div a').on('click', function() {
        window.location = $(this).attr('href') + '/supprimer';
        return false;
      });
      return false;
    });

    // Quitte le mode édition
    $routeList.find('.cancel').on('click', function() {
      $routeList.removeClass('edit-mode');
      $routeList.find('> div a').off('click');
      return false;
    });
  }

  // Statistiques
  // ------------

  var LABELS = {
    LOGINS: 'connexions',
    REGISTRATIONS: 'inscriptions',
    ROUTES: 'trajets',
    SEARCHES: 'recherches'
  };

  // Récupère la page afin de restreindre le scope des requêtes jQuery.
  var $statsPage = $('#admin-stats');
  if ($statsPage.length > 0) {
    var $chart = $statsPage.find('#visualization');

    $statsPage.find('button').on('click', function() {
      var type = $chart.data('type')
        , period = $(this).data('period');
      
      $.getJSON($chart.data('source') + '/' + type + '/' + period, function(json) {
        var dataTable = new google.visualization.DataTable();
        dataTable.addColumn('date', '');
        dataTable.addColumn('number', LABELS[type]);

        var i = 0, n = json.length;
        for (; i < n; i++) {
          dataTable.addRow([new Date(json[i].date), json[i].value]);
        }

        var areaChart = new google.visualization.AreaChart($chart.get(0));
        var areaChartOptions = {
          legend: 'none',
          lineWidth: 3,
          pointSize: 6
        };
        areaChart.draw(dataTable, areaChartOptions);
      });
    });
  }
})(jQuery, window);
