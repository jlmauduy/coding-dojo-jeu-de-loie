package com.thalesgroup.jeu;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import jeuDeLoie.JeuDelOie;
import jeuDeLoie.Joueur;

/**
 * Example resource class hosted at the URI path "/myresource"
 */
@Path("/joueurs")
public class MyResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{pseudo}")
    public Joueur getPlayer(@PathParam("pseudo") String pseudo) {
        return JeuDelOie.getInstance().getJoueurFromPseudo(pseudo);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPlayer(@Context HttpServletRequest request, @Context HttpServletResponse response, String pseudo)
            throws URISyntaxException {
        JeuDelOie jeuDeLoie = JeuDelOie.getInstance();

        jeuDeLoie.addJoueur(pseudo);

        return Response.created(new URI(pseudo)).build();
    }
    // @POST
    // @Produces("text/plain")
    // @Path("/joueurs/{monParam}")
    // public void foo(@Context HttpServletRequest request, @Context HttpServletResponse response,
    // @PathParam("monParam") String monPram) {
    //
    // }

}
