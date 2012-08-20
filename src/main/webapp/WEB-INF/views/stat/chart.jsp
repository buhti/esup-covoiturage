<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="admin-stats">
  <div id="visualization" style="width: 600px; height: 400px; position: relative"></div>
  
  <script type="text/javascript" src="http://www.google.com/jsapi"></script>
  <script type="text/javascript">
      google.load('visualization', '1', {packages: ['corechart']});
  </script>
  <script type="text/javascript">
    function drawVisualization() {
      // Some raw data (not necessarily accurate)
      var data = google.visualization.arrayToDataTable([
        ['Month',   'Bolivia', 'Ecuador', 'Madagascar', 'Papua New Guinea', 'Rwanda'],
        ['2004/05',    165,      938,         522,             998,           450],
        ['2005/06',    135,      1120,        599,             1268,          288],
        ['2006/07',    157,      1167,        587,             807,           397],
        ['2007/08',    139,      1110,        615,             968,           215],
        ['2008/09',    136,      691,         629,             1026,          366]
      ]);

      // Create and draw the visualization.
      var ac = new google.visualization.AreaChart(document.getElementById('visualization'));
      ac.draw(data, {
        title : 'Monthly Coffee Production by Country',
        isStacked: true,
        width: 600,
        height: 400,
        vAxis: {title: "Cups"},
        hAxis: {title: "Month"}
      });
    }

    drawVisualization();
  </script>
</div>
