(function($) {
  'use strict';

  // Formulaires
  // -----------

  // Permet de désactiver le champ de saisie si l'utilisateur choisit une
  // destination pré-configurée.
  $('select.location-selector').each(function() {
    var target = $($(this).data('target'));
    $(this).on('change', function() {
      var value = $(this).val();
      if (value.length > 0) {
        target.attr('disabled', 'disabled');
      } else {
        target.removeAttr('disabled');
      }
      target.val(value);
    });
  });

})(jQuery)