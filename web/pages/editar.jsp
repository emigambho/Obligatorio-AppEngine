<%@ page import="ort.obligatorio.appengine.estacionamiento.Estacionamiento" %>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!--<meta http-equiv="content-type" content="text/html; charset=UTF-8">-->
	<title>Estacionamientos</title>

	<link href="../../css/bootstrap.min.css" rel="stylesheet">

	<link href="../../css/starter-template.css" rel="stylesheet">

	<script>
		function validateForm() {
			var x = document.forms["editarEstacionamiento"]["puntaje"].value;
			if (x == null || x == "") {
				alert("Name must be filled out");
				return false;
			}
		}
	</script>

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
				<ul class="nav navbar-nav">
					<li class="active"><a href="/">Inicio</a></li>
					<li><a href="<%=logout%>">Salir</a></li>
				</ul>
			</div><!--/.nav-collapse -->
		</div>
	</nav>

	<div class="container">
		<h1>Editar estacionamiento</h1>
		<br>

		<%
			Estacionamiento item = (Estacionamiento)request.getAttribute("estacionamiento");
		%>

		<form name="editarEstacionamiento" method="post" action="../guardarCambiosAdm" >
			<input type="hidden" name="originalName" id="originalName" value="<%=item.getNombre() %>" />

			Nombre: <%=item.getNombre() %>
			<br><br>
			Puntaje: <input type="text" style="width: 185px;" maxlength="3" name="puntaje" id="puntaje" value="<%=item.getPuntaje() %>"
							pattern="([1-5]|[1-5][,.][0-9])" title="Valor numerico entre 1 y 5" required="required"/>
			<br><br>
			Capacidad: <input type="text" style="width: 185px;" maxlength="10" name="capacidad" id="capacidad" value="<%=item.getCapacidad() %>"
							  pattern="[0-9]{1,4}" title="Valor numerico entre 1 y 1000" required="required"/>
			<br><br>
			Horario, desde: <input type="text" style="width: 185px;" maxlength="10" name="apertura" id="apertura" value="<%=item.getHoraDeApertura() %>"
								   pattern="[0-2]{0,1}[0-9]:[0-5][0-9]" title="Hora válida, en formato 12 o 24." required="required"/> hasta:
							<input type="text" style="width: 185px;" maxlength="10" name="cierre" id="cierre" value="<%=item.getHoraDeCierre() %>"
								   pattern="[0-2]{0,1}[0-9]:[0-5][0-9]" title="Hora válida, en formato 12 o 24." required="required"/>
			<br><br>
			<br>

			<input type="submit" class="update btn btn-sm btn-default" title="guardar" value="Guardar" />
		</form>
	</div>

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<!-- type="text/javascript" -->
	<script  src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../../obl/js/bootstrap.min.js"></script>

</body>
</html>