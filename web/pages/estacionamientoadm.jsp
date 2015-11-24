<%@ page import="ort.obligatorio.appengine.estacionamiento.Estacionamiento"%>
<%@ page import="ort.obligatorio.appengine.estacionamiento.Parcela" %>
<%@ page import="ort.obligatorio.appengine.estacionamiento.Calificacion" %>
<html>
<body>
	<h1>Estacionamiento para administrador</h1>
	<%String logout = (String) request.getAttribute("logout");%>
	<a href="<%=logout%>">Salir</a>
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
	Parcelas:
	<br>
	<%
		for (Parcela p : item.getParcelas()) { %>
			- <%= p.getId() + ", " + p.getDescripcion()%>
			<br>
	<%	}
	%>
	<br>
	Comentarios:
	<br>
	<%
		for (Calificacion c : item.getCalificaciones()) { %>
			- <%= c.getCalificacion() + ", " + c.getComentario() + " - " + c.getUsuario()%>
			<br>
	<%	}
	%>
	<br>
	<br>
	Agregar comentario:
	<br>
	<form method="post" action="agregarComentarioAdm" >
		<input type="hidden" name="originalName" id="originalName"
			   value="<%=item.getNombre() %>" />

		<table>
			<tr>
				<td>Calificacion:</td>
				<td>
					<input type="radio" name="calificacion" value="1">1
					<input type="radio" name="calificacion" value="2">2
					<input type="radio" name="calificacion" value="3">3
					<input type="radio" name="calificacion" value="4">4
					<input type="radio" name="calificacion" value="5">5
				</td>
			</tr>
			<tr>
				<td>Comentario:</td>
				<td>
					<!--<input type="text" style="width: 400px; height: 100px;" maxlength="300" name="comentario" id="comentario"
						   value="" />-->
					<textarea cols="75" rows="5" maxlength="300" name="comentario" id="comentario"></textarea>
				</td>
			</tr>
		</table>
		<input type="submit" class="update" title="agregarComentario" value="Agregar comentario" />
	</form>

</body>
</html>