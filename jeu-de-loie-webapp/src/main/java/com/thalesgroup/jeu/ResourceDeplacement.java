package com.thalesgroup.jeu;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import jeuDeLoie.Deplacement;
import jeuDeLoie.JeuDelOie;

import com.thalesgroup.jeu.exchange.DeplacementExchange;

@Path("/deplacements")
public class ResourceDeplacement {

    @POST
    public Response deplacementJoueur(String pseudo, @Context UriInfo uriInfo) {
        JeuDelOie jeu = JeuDelOie.getInstance();

        // TODO : Verifier que c'est bien le tour du joueur

        // Creer le deplacement
        Deplacement deplacement = jeu.jouer(pseudo);
        URI uri = uriInfo.getAbsolutePathBuilder().path(deplacement.getId().toString()).build();
        return Response.created(uri).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public DeplacementExchange getDeplacement(@PathParam("id") String id) {
        Deplacement deplacement = JeuDelOie.getInstance().getDeplacement(id);
        return new DeplacementExchange(deplacement);
    }
}
