package com.thalesgroup.jeu;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import jeuDeLoie.JeuDelOie;
import jeuDeLoie.Plateau;

import com.thalesgroup.jeu.exchange.CaseExchange;
import com.thalesgroup.jeu.exchange.PlateauExchange;

/**
 * Example resource class hosted at the URI path "/myresource"
 */
@Path("/plateau")
public class ResourcePlateau {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PlateauExchange getPlateau() {
        Plateau plateau = JeuDelOie.getInstance().getPlateau();

        if (plateau != null) {
            return new PlateauExchange(plateau);
        } else
            throw new WebApplicationException(404);
    }

    @GET
    @Path("/cases/{index}")
    @Produces(MediaType.APPLICATION_JSON)
    public CaseExchange getCase(@PathParam("index") int index) {
        Plateau plateau = JeuDelOie.getInstance().getPlateau();

        if (plateau.getCases() != null && plateau.getCases().size() > index) {
            return new CaseExchange(plateau.getCases().get(index).getCaseType(), index);
        } else
            throw new WebApplicationException(404);
    }
}
