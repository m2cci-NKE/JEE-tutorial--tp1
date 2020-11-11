<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ page import="java.util.*" %>
    <%@ page import=" com.sdzee.tp.beans.Client" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
  <link type="text/css" rel="stylesheet" href="inc/style.css" />
</head>
<body>
				<c:import url="/inc/menu.jsp"/>

				
				<p class="info">${form.resultat }<p/> 
			
	
			 	
			<br>
			<br>
			
		  <form method="post" action="creationClient">
           
               <fieldset>
               <legend>Informations client</legend>
               
                <label for="check">New client ? <span class="requis">*</span></label>
						 <input type="radio" name="yes" id="yes" onclick="myFunction()">Yes 
						  <input type="radio" name="yes" id="no"onclick="myFunction()" />No
						  
						   <br /><br />	
					       
						<select name="newClient" id="newClient" class="newClient"  onChange="show_selected(this);" >
									    <option >--Please choose a Client--</option>
									    						    
									    <c:forEach items="${sessionScope.mapClient}" var="entry" varStatus="boucle">
									    <option>
												<c:out value="${entry.value.nom } "/><c:out value="${entry.value.prenom } "/>
											</option>
									</c:forEach>
								</select>
							<br /><br />
							
									
                    <span id="cli" class="cli"> <c:import url="/inc/inc_client_form.jsp"></c:import></span>
                     
				</fieldset>
                <input type="submit" value="Valider" />
                <input type="reset" value="Remettre à zéro" /> <br />
                
            </form>

				

 <script>        
        function myFunction() {
    
        	if (document.getElementById('yes').checked) {
        		document.getElementById("cli").style.display = 'block';
        		document.getElementById("newClient").style.display = 'none';
			} 
        	
        	if(document.getElementById('no').checked){
        		document.getElementById("cli").style.display = 'none';
				document.getElementById("newClient").style.display = 'block'; 	  
			}
        	}
     
        	
   

		</script>

	
</body>
</html>