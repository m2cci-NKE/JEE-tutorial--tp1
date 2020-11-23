<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Liste de Commandes </title>
<link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"/>" />
</head>
<body>

<div>
<c:import url="/inc/menu.jsp" />


<br /> 
					<p class="info">${form.resultat }<p/>

					

			<br>
			<br>	
			<c:choose>
				<c:when test="${empty sessionScope.commande  }"> <p>aucun client est enregistre</p></c:when>
				<c:otherwise>
				
			<table id="t01">
			
					<tr>
					<th>Client</th>
					<th>Date</th>
					<th>Montant</th>
					<th>Mode de paiement</th>
					<th>Statut de paiement</th>
					<th>Mode de laivraison</th>
					<th>Statut de laivraison</th>
					<th>Image</th>
					<th class="action">Action</th>
					
					</tr>
					<c:forEach items="${sessionScope.commande}" var="entry" varStatus="boucle">
					<tr>
						<td><c:out value="${entry.value.client.nom } "/><c:out value="${entry.value.client.prenom }"/></td>
			 			<td><p><c:out value="${entry.value.date }"/></p></td>
			 			<td><p><c:out value="${entry.value.montant }"/></p></td>
			 			<td><p><c:out value="${entry.value.modePaiement }"/></p></td>
			 			<td><p><c:out value="${entry.value.statutPaiement }"/></p></td>
			 			<td><p><c:out value="${entry.value.modeLivraison }"/></p></td>
			 			<td><p><c:out value="${entry.value.modeLivraison }"/></p></td>
			 			<td>
			 			<c:if test="${!empty entry.value.client.chemin }">
			 			<c:set var="photo"><c:out value="${entry.value.client.chemin }"></c:out></c:set>
			 			<p><a href="<c:url value="/img/${photo }" />">Check</a></p>
			 			</c:if> 
			 			</td>
			 			<td><p><a href="<c:url value="/suppressionCommande?date=${ entry.value.date}" />" ><img src="image/delete.jpg" style="width:25px;height:25px" ></a></p></td>
					</tr>
					
				</c:forEach>
			</table>			
			</c:otherwise>
			</c:choose>	
	</div>
	
</body>
</html>