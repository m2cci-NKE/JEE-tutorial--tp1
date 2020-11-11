<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
 <link type="text/css" rel="stylesheet" href="inc/style.css" />
</head>
<body>
<p class="menu">

<a href="<c:url value="/creationClient" />">Créer un nouveau Client</a>
<br>
<br>
<a href="<c:url value="/creationCommande" />">Créer une nouvelle Commande</a>
<br>
<br>
<a href="<c:url value="listeClients" />">Voir les Clients existants</a>
<br>
<br>
<a href="<c:url value="/listeCommandes" />">Voir les commandes existants </a>
</p>

</body>
</html>