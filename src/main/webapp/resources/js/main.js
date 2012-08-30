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
  if ($routeForm.length > 0) {
    var marker, map;

    $routeForm.find('#fromAddress, #toAddress').on('change', function() {
      var $this = $(this)
        , $link = $this.find('+ .help-block > a');

      if ($this.val().length > 0) {
        $link.removeClass('disabled');
      } else {
        $link.addClass('disabled');
      }
    }).change();

    $routeForm.find('a[data-map]').on('click', function() {
      var $this = $(this);

      // Stoppe l'exécution si le lien est désactivé
      if ($this.hasClass('disabled')) {
        return false;
      }

      // Récupère l'adresse saisie
      var address = $($this.data('map')).val();

      // Retire le précédent marqueur
      if (marker) {
        marker.setMap(null);
        marker = null;
      }

      // Callback de succès
      var resultCallback = function(result) {
        // Crée la carte si ce n'est pas déjà fait
        if (!map) {
          map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);
        }

        // Ajoute le marqueur
        marker = new google.maps.Marker({
          map: map,
          position: result.geometry.location,
          title: result.formatted_address
        });
        
        var bounds = new google.maps.LatLngBounds();

        bounds.extend(marker.position);
        map.fitBounds(bounds);

        // Affiche la popup contenant la carte
        $routeForm.find('#map-modal').modal('show');
      };

      // Callback d'erreur
      var errorCallback = function(status) {
        console.log("ERROR:", status);
      };

      if (address.length > 0) {
        geocode.apply(this, [address, resultCallback, errorCallback]);
      }

      return false;
    });
  }

  // Détermine si un trajet est récurrent ou non en fonction de l'onglet
  // sélectionné.
  $routeForm.find('input#recurrent1, input#recurrent2').on('change', function(e) {
    var recurrent = ($(this).val() === 'true') === $(this).is(':checked');
    if (recurrent) {
      $routeForm.find('.recurrent').show();
      $routeForm.find('.occasional').hide();
    } else {
      $routeForm.find('.recurrent').hide();
      $routeForm.find('.occasional').show();
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

  // Affiche le choix du nombre de places seulement si l'utilisateur est le
  // conducteur.
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
  if ($routePage.length > 0) {
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
    
    // Confirmation avant suppression
    var $controls = $('.subheader .controls');
    if ($controls.length > 0) {
      $controls.find('a.delete').on('click', function() {
        return confirm($controls.data('warning'));
      });
    }
  }

  // Résultats de recherche
  // ----------------------

  // Récupère la page afin de restreindre le scope des requêtes jQuery.
  var $resultsPage = $('#search-results');
  if ($resultsPage.length > 0) {
    var $more = $resultsPage.find('#more-results > button');

    var resultSource = $resultsPage.data('results')
      , resultCount = $resultsPage.data('count')
      , currentCount = 0
      , currentPage = 1;

    var loadResults = function(page) {
      $.ajax(resultSource + page).done(function(data) {
        var $results = $(data);
        if ($results.length) {
          $resultsPage.find('#results-container').append($results);
          currentCount += $results.length;
          if (currentCount == resultCount) {
            $more.addClass('disabled');
          } else {
            $more.removeClass('disabled');
          }
        } else {
          $more.addClass('disabled');
        }
      });
    }

    $more.on('click', function() {
      if ($more.hasClass('disabled')) {
        return false;
      }
      
      loadResults(++currentPage);
      return false;
    });

    // Charge la première page de résultats si nécessaire
    if (resultCount > 0) {
      loadResults(currentPage);
    }
  }

  // Mes trajets
  // -----------

  // Récupère la page afin de restreindre le scope des requêtes jQuery.
  var $routeList = $('#route-list');
  if ($routeList.length > 0) {
    var $controls = $('.subheader .controls')
      , $list = $routeList.find('.route-list');

    // Active le mode édition
    $controls.find('.edit').on('click', function() {
      $controls.addClass('edition');
      $list.addClass('edition');
      $list.find('a').each(function() {
        var $this = $(this);
        $this.attr('href', $this.attr('href') + '/supprimer');
        $this.on('click', function() {
          return confirm($controls.data('warning'));
        });
      });
      return false;
    });

    // Quitte le mode édition
    $controls.find('.cancel').on('click', function() {
      $controls.removeClass('edition');
      $list.removeClass('edition');
      $list.find('a').each(function() {
        var $this = $(this), href = $this.attr('href');
        $this.attr('href', href.substring(0, href.length - 10));
        $this.off('click');
      });
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
    var $chart = $statsPage.find('#visualization')
      , type = $chart.data('type')
      , period;

    $statsPage.find('.period button').on('click', function() {
      period = $(this).data('period');

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

    $statsPage.find('.period button.active').trigger('click');

    $statsPage.find('.export a').on('click', function() {
      window.location = $(this).attr('href') + '/' + type + '/' + period;
      return false;
    });
  }

})(jQuery, window);
