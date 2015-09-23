package com.thalesgroup.jeu;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import jeuDeLoie.JeuDelOie;
import jeuDeLoie.Joueur;
import exceptions.JoueurExisteDejaException;

/**
 * Example resource class hosted at the URI path "/myresource"
 */
@Path("/joueurs")
public class ResourceJoueurs {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{pseudo}")
    public Joueur getPlayer(@PathParam("pseudo") String pseudo) {
        Joueur joueur = JeuDelOie.getInstance().getJoueurFromPseudo(pseudo);
        if (joueur != null)
            return joueur;
        else
            throw new WebApplicationException(404);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPlayer(String pseudo) throws URISyntaxException {
        JeuDelOie jeuDeLoie = JeuDelOie.getInstance();

        try {
            jeuDeLoie.addJoueur(pseudo);
            return Response.created(new URI(pseudo)).build();
        } catch (JoueurExisteDejaException ex) {
            return Response.status(Response.Status.CONFLICT).entity(ex.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Joueur> getJoueurs() {
        JeuDelOie jeuDeLoie = JeuDelOie.getInstance();
        List<Joueur> joueurs = jeuDeLoie.getJoueurs();

        return joueurs;
    }

    // @POST
    // @Produces("text/plain")
    // @Path("/joueurs/{monParam}")
    // public void foo(@Context HttpServletRequest request, @Context HttpServletResponse response,
    // @PathParam("monParam") String monPram) {
    //
    // }

}
