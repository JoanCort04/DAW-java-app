
package com.joancortes.aplicacioweb.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

/**
 * Recurso RESTful que proporciona un servei per respondre a sol·licituds GET amb un missatge de ping.
 * Aquesta classe utilitza Jakarta RESTful Web Services (JAX-RS) per exposar un servei web que retorna un missatge
 * de text per verificar que el servei REST funciona correctament.
 *
 * Ruta: /jakartaee11
 *
 * Exemple de resposta:
 * 200 OK - "ping Jakarta EE"
 *
 * @author Joan Cortés
 */
@Path("jakartaee11")
public class JakartaEE11Resource {
    
    /**
     * Mètode que processa les sol·licituds GET a la ruta /jakartaee11.
     * Retorna una resposta OK amb el missatge "ping Jakarta EE" per indicar que el servei REST està operatiu.
     *
     * @return La resposta HTTP amb el missatge de ping.
     */
    @GET
    public Response ping(){
        return Response
                .ok("ping Jakarta EE")
                .build();
    }
}
