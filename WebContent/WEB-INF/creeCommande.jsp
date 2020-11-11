<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Creation d'une commande</title>
        <link type="text/css" rel="stylesheet" href="inc/style.css" />
    </head>
    <body>
        <div>
        
        <c:import url="/inc/menu.jsp" />
                    <br />
                    
            <form method="post" action="CreationCommande">
           
               
               
               <fieldset>
                    <legend>Informations client</legend>
                    <label for="check">New client ? <span class="requis">*</span></label>
						 <input type="radio" name="yes" id="yes" onclick="myFunction()">Yes 
						  <input type="radio" name="yes" id="no"onclick="myFunction()" />No
						  
						   <br />
						    
								<span id="cli" class="cli"><c:import url="/inc/inc_commande_form.jsp"/></span> 
				
			              <br />
			                    
			                    
			                    
						<select name="newClient" id="newClient" class="newClient" onChange="show_selected(this);" >
									    <option selected disabled >--Please choose a Client--</option>
									    						    
									    <c:forEach items="${sessionScope.mapCommande}" var="entry" varStatus="boucle">
									    <option>
												<c:out value="${entry.value.client.nom } "/><c:out value="${entry.value.client.prenom } "/>
											</option>
									</c:forEach>
								</select>
							
             </fieldset>
             
             
            <fieldset>
                    <legend>Informations commande</legend>
                    
                    <label for="dateCommande">Date <span class="requis">*</span></label>
                    <input type="text" id="dateCommande" name="dateCommande" value="" 
                    size="20" maxlength="20" disabled />
                 
                    <br />
                    
                   
                    <label for="montantCommande">Montant <span class="requis">*</span></label>
                    <input type="text" id="montantCommande" name="montantCommande" value="<c:out value="${commande.montant }"/>" 
                    size="20" maxlength="20" />
                  <span class="erreur">${form.erreurs['montantCommande'] }</span> 

                    <br />
                    
                    <label for="modePaiementCommande">Mode de paiement <span class="requis">*</span></label>
                    <input type="text" id="modePaiementCommande" name="modePaiementCommande" value="<c:out value="${commande.modePaiement }"/> " 
                    size="20" maxlength="20" />
                    <span class="erreur">${form.erreurs['modePaiementCommande'] }</span> 
                    <br />
                    
                    <label for="statutPaiementCommande">Statut du paiement</label>
                    <input type="text" id="statutPaiementCommande" name="statutPaiementCommande" value="<c:out value="${commande.statutPaiement }"/> " size="20" maxlength="20" />
                  	    <span class="erreur">${form.erreurs['statutPaiementCommande'] }</span> 
                    <br />
                    
                    <label for="modeLivraisonCommande">Mode de livraison <span class="requis">*</span></label>
                    <input type="text" id="modeLivraisonCommande" name="modeLivraisonCommande" value="<c:out value="${commande.modeLivraison }"/> " size="20" maxlength="20" />
                        <span class="erreur">${form.erreurs['modeLivraisonCommande'] }</span> 
                    <br />
                    
                    <label for="statutLivraisonCommande">Statut de la livraison</label>
                    <input type="text" id="statutLivraisonCommande" name="statutLivraisonCommande" value="<c:out value="${commande.statutLivraison }"/> " size="20" maxlength="20" />
                        <span class="erreur">${form.erreurs['statutLivraisonCommande'] }</span> 
                    <br />
                    
      <p class="erreur">${ form.resultat }</p>
                </fieldset>
                <input type="submit" value="Valider"  />
                <input type="reset" value="Remettre à zéro" /> <br />
                
            </form>
        </div>
        
        
        <script>        
        function myFunction() {
    
        	if (document.getElementById('yes').checked) {
        		document.getElementById("cli").style.display = 'block';
        		document.getElementById("newClient").style.display = 'none';
			} 
        	
        	if(document.getElementById('no').checked){
        		document.getElementById("cli").style.display = 'none';
				document.getElementById("newClient").style.display = 'block'; 	  
			}}
        
        function show_selected(element) {
         
            var value = element.options[element.selectedIndex].text;
 
        }

		</script>
        
    </body>
</html>