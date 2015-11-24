<%@ page import="ort.obligatorio.appengine.estacionamiento.Estacionamiento" %>
<html>
<body>
	<h1>Editar estacionamiento</h1>
	<%String logout = (String) request.getAttribute("logout");%>
	<a href="<%=logout%>">Salir</a>
	<br>

	<%
		Estacionamiento item = (Estacionamiento)request.getAttribute("estacionamiento");
	%>

	<form method="post" action="../guardarCambiosAdm" >
		<input type="hidden" name="originalName" id="originalName" value="<%=item.getNombre() %>" />

		Nombre: <input type="text" style="width: 185px;" maxlength="50" name="nombre" id="nombre" value="<%=item.getNombre() %>" />
		<br><br>
		Puntaje: <input type="text" style="width: 185px;" maxlength="3" name="puntaje" id="puntaje" value="<%=item.getPuntaje() %>" />
		<br><br>
		Capacidad: <input type="text" style="width: 185px;" maxlength="10" name="capacidad" id="capacidad" value="<%=item.getCapacidad() %>" />
		<br><br>
		Horario, desde: <input type="text" style="width: 185px;" maxlength="10" name="apertura" id="apertura" value="<%=item.getHoraDeApertura() %>" /> hasta: <input type="text" style="width: 185px;" maxlength="10" name="cierre" id="cierre" value="<%=item.getHoraDeCierre() %>" />
		<br><br>
		<br>

		<input type="submit" class="update" title="guardar" value="Guardar" />
	</form>

</body>
</html>