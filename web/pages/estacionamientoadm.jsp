<%@ page import="ort.obligatorio.appengine.estacionamiento.Estacionamiento"%>
<%@ page import="ort.obligatorio.appengine.estacionamiento.Parcela" %>
<%@ page import="ort.obligatorio.appengine.estacionamiento.Calificacion" %>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!--<meta http-equiv="content-type" content="text/html; charset=UTF-8">-->
	<title>Estacionamientos</title>

	<link href="../css/bootstrap.min.css" rel="stylesheet">

	<link href="../css/starter-template.css" rel="stylesheet">

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

	<div class="container">
		<h1>Estacionamiento para administrador</h1>

		<br>

		<%
			Estacionamiento item = (Estacionamiento)request.getAttribute("estacionamiento");
		%>

		Nombre: <%=item.getNombre() %>
		<br>
		Puntaje: <%=item.getPuntaje() %>
		<br>
		Capacidad: <%=item.getCapacidad() %>
		<br>
		Horario: Desde <%=item.getHoraDeApertura() %> hasta <%=item.getHoraDeCierre() %>
		<br>
		<a href="editar/<%=item.getNombre()%>">Editar</a>
		<br>
		<br>
		<!--Parcelas:
		<br>
		<%
			for (Parcela p : item.getParcelas()) { %>
				- <%= p.getId() + ", " + p.getDescripcion()%>
				<br>
		<%	}
		%>
		<br>-->
		Comentarios:
		<br>
		<ul>
		<%
			for (Calificacion c : item.getCalificaciones()) { %>
			<li>Puntaje: <%= c.getCalificacion()%>, Calificacion: <%= c.getComentario()%>, Usuario: <%= c.getUsuario()%></li>
		<%	}
		%>
		</ul>
		<br>
		<br>
		Agregar comentario:
		<br>
		<form method="post" action="agregarComentarioAdm" >
			<input type="hidden" name="originalName" id="originalName"
				   value="<%=item.getNombre() %>" />

			<table>
				<!--<tr>
					<td>Calificacion:</td>
					<td>
						<input type="radio" name="calificacion" value="1">1
						<input type="radio" name="calificacion" value="2">2
						<input type="radio" name="calificacion" value="3">3
						<input type="radio" name="calificacion" value="4">4
						<input type="radio" name="calificacion" value="5">5
					</td>
				</tr>-->
				<tr>
					<td>Comentario:</td>
				</tr>
				<tr>
					<td>
						<!--<input type="text" style="width: 400px; height: 100px;" maxlength="300" name="comentario" id="comentario"
							   value="" />-->
						<textarea cols="75" rows="5" maxlength="300" name="comentario" id="comentario" required="required"></textarea>
					</td>
				</tr>
			</table>
			<br>
			<input type="submit" class="update btn btn-sm btn-default" title="agregarComentario" value="Agregar comentario" />
		</form>

	</div>




	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<!-- type="text/javascript" -->
	<script  src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../obl/js/bootstrap.min.js"></script>

</body>
</html>