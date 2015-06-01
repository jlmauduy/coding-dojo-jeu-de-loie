package com.thales.jeuDeLoie;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import jeuDeLoie.JeuDelOie;
import jeuDeLoie.Joueur;

/**
 * REST api for boardgame "jeu de l'oie"
 */
@Path("/game")
public class GameResource {

    /**
     * 
     * @param HttpServletRequest
     * @return JeuDelOie
     */
    private JeuDelOie getJeu(HttpServletRequest request) {

        ServletContext context = request.getSession().getServletContext();
        JeuDelOie jeu = (JeuDelOie) context.getAttribute("jeu");

        // vérification si le plateau existe déjà
        if (jeu == null) {
            jeu = new JeuDelOie();
            context.setAttribute("jeu", jeu);
        }
        return jeu;
    }

    /**
     * @return board vision
     */
    @GET
    @Path("/board")
    @Produces("text/plain")
    public String getBoard(@Context HttpServletRequest request) {
        return getJeu(request).getEtatPlateau();
    }

    /**
     * @return players
     */
    @GET
    @Path("/players")
    @Produces("text/plain")
    public String getPlayers(@Context HttpServletRequest request) {
        JeuDelOie jeu = getJeu(request);

        StringBuilder builder = new StringBuilder();
        builder.append("Les joueurs sont : ");

        for (Joueur joueur : jeu.getJoueurs()) {
            builder.append(joueur.getPseudo() + " - ");
        }
        // return jeuDeLoie.getJoueurs().stream().map(Joueur::getPseudo).collect(Collectors.joining(" - "));
        return builder.toString();
    }

    /**
     * add a player
     */
    @POST
    @Produces("text/plain")
    @Path("/player/{playerName}")
    public void addPlayers(@Context HttpServletRequest request, @Context HttpServletResponse response,
            @PathParam("playerName") String playerName) {
        JeuDelOie jeu = getJeu(request);
        jeu.addJoueur(playerName);

        response.setHeader("location", request.getRequestURI());
    }
}
