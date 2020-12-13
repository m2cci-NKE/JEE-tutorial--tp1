<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Liste des clients</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
<style>


</style>
</head>
<body>

	<div>
	  <c:import url="/inc/menu.jsp" />
                    <br /> 
				
			<br>
			<br>	
			<h1>Session</h1>
			<c:choose>
				<c:when test="${empty sessionScope.clients  }"> 
				<p>aucun client est enregistre</p>
				</c:when>
				
				<c:otherwise>
				
			
			<table id="t01">
			
				<tr>
				<th>Nom</th>
				<th>Prenom</th>
				<th>Adresse</th>
				<th>Téléphone</th>
				<th>Email</th>
				<th>Picture</th>
				<th class="thaction">Action</th>
				
				</tr>
				<c:forEach items="${sessionScope.clients}" var="entry" varStatus="boucle">
				<tr>
					<td><p><c:out value="${entry.value.nom }"/></p></td>
		 			<td><p><c:out value="${entry.value.prenom }"/></p></td>
		 			<td><p><c:out value="${entry.value.adresse }"/></p></td>
		 			<td><p><c:out value="${entry.value.telephone }"/></p></td>
		 			<td><p><c:out value="${entry.value.email }"/></p></td>
		 			<td><p><c:out value="${client.image }"/></p></td>
		 			<td><a href="<c:url value="/suppressionClient" > <c:param name="idClient" value="${entry.key }" /> </c:url>">   <img src="image/delete.jpg" style="width:25px;height:25px" /></a>
		 			
		 			</td>
				</tr>
			</c:forEach>
			</table>			
			
			</c:otherwise>
			</c:choose>
		
				
				<h1>Databases</h1>
				<c:choose>
				<c:when test="${empty list  }"> 
				<p>aucun client est enregistre</p>
				</c:when>
				
				<c:otherwise>
				
			<table id="t01">
			
				<tr>
				<th>id</th>
				<th>Nom</th>
				<th>Prenom</th>
				<th>Adresse</th>
				<th>Téléphone</th>
				<th>Email</th>
				<th>Picture</th>
				<th class="thaction">Action</th>
				
				</tr>
				<c:forEach items="${list}" var="employee">
				<tr>
					<td><p><c:out value="${employee.id }"/></p></td>
					<td><p><c:out value="${employee.nom }"/></p></td>
		 			<td><p><c:out value="${employee.prenom }"/></p></td>
		 			<td><p><c:out value="${employee.adresse }"/></p></td>
		 			<td><p><c:out value="${employee.telephone }"/></p></td>
		 			<td><p><c:out value="${employee.email }"/></p></td>
		 			<td><p><c:out value="${employee.image }"/></p></td>
		 			<td><a href="<c:url value="/suppressionClient" > <c:param name="idClient" value="${employee.id }" /> </c:url>">   <img src="image/delete.jpg" style="width:25px;height:25px" /></a>
		 			
		 			</td>
				</tr>
			</c:forEach>
			</table>	
			</c:otherwise>
			</c:choose>
	</div>
</body>
</html>