<%@ page import="ort.obligatorio.appengine.estacionamiento.Estacionamiento"%>
<%@ page import="ort.obligatorio.appengine.estacionamiento.Parcela" %>
<%@ page import="ort.obligatorio.appengine.estacionamiento.Calificacion" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!--<meta http-equiv="content-type" content="text/html; charset=UTF-8">-->
    <title>Estacionamientos</title>

    <link href="/css/bootstrap.min.css" rel="stylesheet">

    <link href="/css/starter-template.css" rel="stylesheet">

    <link href="/css/dashboard.css" rel="stylesheet">

    <style>
      #map {
        width: 500px;
        height: 400px;
      }
    </style>

</head>
<body>
<%String logout = (String) request.getAttribute("logout");%>
<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">Control de Estacionamientos</a>
    </div>
    <div id="navbar" class="collapse navbar-collapse">
      <ul class="nav navbar-nav navbar-right">
        <li><a href="/">Inicio</a></li>
        <li><a href="<%=logout%>">Salir</a></li>
      </ul>
    </div><!--/.nav-collapse -->
  </div>
</nav>

<div class="container-fluid">
  <div class="row">
    <div class="col-sm-3 col-md-2 sidebar">
      <ul class="nav nav-sidebar">
        <li class="active">Estacionamientos<span class="sr-only">(current)</span></li>
        <%
          List<String> lista = (List<String>)request.getAttribute("lista");

          for (String nombre : lista) {
        %>
        <li><a href="/obl/ver/<%=nombre%>"><%=nombre%></a></li>
        <%
          }
        %>
      </ul>
    </div>
    <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
      <%
        Estacionamiento seleccionado = (Estacionamiento) request.getAttribute("seleccionado");
        if (seleccionado != null) { %>
      <h1 class="page-header"><%=seleccionado.getNombre()%></h1>

      Puntaje: <%=seleccionado.getPuntaje() %>
      <br>
      Capacidad: <%=seleccionado.getCapacidad() %>
      <br>
      Horario: Desde <%=seleccionado.getHoraDeApertura() %> hasta <%=seleccionado.getHoraDeCierre() %>
      <br>
      Comentarios
      <br>
      <div class="row">
        <div class="col-md-6">
          <table class="table table-striped">
            <thead>
            <tr>
              <th>Puntaje</th>
              <th>Comentario</th>
            </tr>
            </thead>
            <tbody>
            <%
              for (Calificacion c : seleccionado.getCalificaciones()) { %>
            <tr>
              <td><%=c.getCalificacion()%></td>
              <td><%=c.getComentario()%></td>
            </tr>
            <%	}
            %>
            </tbody>
          </table>
        </div>
      </div>
      <div class="row">
        <div class="col-md-6">
        Agregar comentario:
        <br>
        <form method="post" action="agregarCalificacionUsuario" >
          <input type="hidden" name="originalName" id="originalName" value="<%=seleccionado.getNombre() %>" />

          Calificacion:
          <input type="radio" name="calificacion" value="1" required="required">1
          <input type="radio" name="calificacion" value="2" required="required">2
          <input type="radio" name="calificacion" value="3" required="required">3
          <input type="radio" name="calificacion" value="4" required="required">4
          <input type="radio" name="calificacion" value="5" required="required">5

          <table>
            <tr>
            </tr>
            <tr>
              <td>Comentario:</td>
            </tr>
            <tr>
              <td>
                <textarea cols="75" rows="5" maxlength="300" name="comentario" id="comentario" required="required"></textarea>
              </td>
            </tr>
          </table>
          <br>
          <input type="submit" class="update btn btn-sm btn-default" title="agregarCalificacionUsuario" value="Agregar comentario" />
        </form>
        </div>
      </div>

      <script src="https://maps.googleapis.com/maps/api/js"></script>
      <script>
        function initialize() {
          var mapCanvas = document.getElementById('map');
          var mapOptions = {
            center: new google.maps.LatLng(<%=seleccionado.getLatitud()%>, <%=seleccionado.getLongitud()%>),
            zoom: 17,
            mapTypeId: google.maps.MapTypeId.ROADMAP,
            mapMaker: true
          }
          var map = new google.maps.Map(mapCanvas, mapOptions);
          var myLatLng = {lat: <%=seleccionado.getLatitud()%>, lng: <%=seleccionado.getLongitud()%>};
          var marker = new google.maps.Marker({
            position: myLatLng,
            map: map
          });
        }
        google.maps.event.addDomListener(window, 'load', initialize);
      </script>
      <div id="map"></div>

      <%
        }
      %>

    </div>
  </div>
</div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<!-- type="text/javascript" -->
<script  src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="/obl/js/bootstrap.min.js"></script>

</body>
</html>