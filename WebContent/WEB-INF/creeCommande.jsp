<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Creation d'une commande</title>
        <link type="text/css" rel="stylesheet" href="inc/style.css" />
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    </head>
    <body>
        <div>
        
        <c:import url="/inc/menu.jsp" />
                    <br />
                    
            <form method="post" action="<c:url value="/creationCommande"/>" enctype="multipart/form-data">
               
               <fieldset>
                    <legend>Informations client</legend>
                    
                    
                    <c:if test="${!empty sessionScope.clients }">
                    
                    	 <label for="check">New client ? <span class="requis">*</span></label>
							 <input type="radio" name="choixNouveauClient" id="choixNouveauClient" value="nouveauClient" />Yes 
						 	 <input type="radio" name="choixNouveauClient" id="choixNouveauClient" value="ancienClient" />No	
                    
                    </c:if>
                   
						  
				<c:set var="client" value="${ commande.client }" scope="request" />
						    <div id="nouveauClient">
						    	<c:import url="/inc/inc_client_form.jsp"/>
						    	
						    </div>
								 
				
			       
			                     <c:if test="${!empty sessionScope.clients }">  
			                     <div id="ancienClient">
			                     
			                     <select name="listeClients" id="listeClients" >
									    <option value="" >--Please choose a Client--</option>
									    						    
								    <c:forEach items="${sessionScope.clients}" var="entry" varStatus="boucle">
								    <option value="${entry.value.nom }"> ${entry.value.prenom } ${entry.value.nom }</option>
								</c:forEach>
								</select>
								
			                     </div>
			                     </c:if> 
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
        
        	$(function(){
        		
        		$("div#ancienClient").hide();
        		jQuery('input[name=choixNouveauClient]:radio').click(function(){
        			$("div#nouveauClient").hide();
        			$("div#ancienClient").hide();
        			var divId= jQuery(this).val();
        			$("div#"+divId).show();
        		})
        		
        		
        	});
        	
		</script>
        
    </body>
</html>