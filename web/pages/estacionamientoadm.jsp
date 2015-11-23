<%@ page import="ort.obligatorio.appengine.estacionamiento.Estacionamiento"%>
<%@ page import="ort.obligatorio.appengine.estacionamiento.Parcela" %>
<%@ page import="ort.obligatorio.appengine.estacionamiento.Calificacion" %>
<html>
<body>
	<h1>Estacionamiento para administrador</h1>

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


</body>
</html>