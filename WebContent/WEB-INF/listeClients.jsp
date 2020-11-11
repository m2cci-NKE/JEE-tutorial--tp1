<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Liste des clients</title>
<link type="text/css" rel="stylesheet" href="inc/style.css" />
<style>


</style>
</head>
<body>

	<div>
	  <c:import url="/inc/menu.jsp" />
                    <br /> 
					<p class="info">${form.resultat }<p/> 
				
	
			 	
			<br>
			<br>	
			<c:choose>
				<c:when test="${empty sessionScope.mapClient  }"> <p>aucun client est enregistre</p></c:when>
				
				<c:otherwise>
				
			
			<table id="t01">
			
				<tr>
				<th>Nom</th>
				<th>Prenom</th>
				<th>Adresse</th>
				<th>Téléphone</th>
				<th>Email</th>
				<th class="thaction">Action</th>
				
				</tr>
				<c:forEach items="${sessionScope.mapClient}" var="entry" varStatus="boucle">
				<tr>
					<td><p><c:out value="${entry.value.nom }"/></p></td>
		 			<td><p><c:out value="${entry.value.prenom }"/></p></td>
		 			<td><p><c:out value="${entry.value.adresse }"/></p></td>
		 			<td><p><c:out value="${entry.value.telephone }"/></p></td>
		 			<td><p><c:out value="${entry.value.email }"/></p></td>
		 			<td><p><a href="<c:url value="/suppressionClient?nom=${ entry.value.nom}" />"><img src="image/delete.jpg" style="width:25px;height:25px" /></p></td>
				</tr>
			</c:forEach>
			</table>			
			
			</c:otherwise>
			</c:choose>
		
		
		
		
	</div>
</body>
</html>