package com.thalesgroup.jeu;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import jeuDeLoie.JeuDelOie;
import jeuDeLoie.Joueur;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ResourceJoueursTest extends AbstractResourceTest {

    @Before
    @Override
    public void setUp() throws Exception { // set up jetty server
        super.setUp();

        // clear jeudeloie from current JVM
        JeuDelOie.getInstance().resetJoueurs();
    }

    @Test
    public void testGetPlayerRessourceInconnue() {
        WebTarget target = client.target(JOUEURS_RS_URL + "/" + PRO_GAMER);

        Response response = target.request().get();
        Assert.assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testCreatePlayerRessource() {
        WebTarget target = client.target(JOUEURS_RS_URL);
        // insertion
        Response response = target.request().post(Entity.entity(PRO_GAMER, MediaType.APPLICATION_JSON));

        Assert.assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetPlayerRessourceConnue() {
        JeuDelOie.getInstance().addJoueur(PRO_GAMER);
        WebTarget targetGet = client.target(JOUEURS_RS_URL + "/" + PRO_GAMER);
        Response response = targetGet.request().get();
        Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetListeJoueurs() {
        JeuDelOie.getInstance().addJoueur(PRO_GAMER);
        JeuDelOie.getInstance().addJoueur(NOOB);

        WebTarget targetGet = client.target(JOUEURS_RS_URL);
        Response response = targetGet.request().get();

        Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());
        List<Joueur> joueurs = response.readEntity(new GenericType<List<Joueur>>() {
        });
        Assert.assertEquals(2, joueurs.size());

        Assert.assertTrue(joueurs.containsAll(Arrays.asList(new Joueur(PRO_GAMER), new Joueur(NOOB))));
    }

    @Test
    public void testCreate2PlayerRessource() {
        WebTarget target = client.target(JOUEURS_RS_URL);
        // insertion
        JeuDelOie.getInstance().addJoueur(PRO_GAMER);
        Response response = target.request().post(Entity.entity(PRO_GAMER, MediaType.APPLICATION_JSON));

        Assert.assertEquals(Status.CONFLICT.getStatusCode(), response.getStatus());
    }

    /*
     * @Test public void testGetPlayersWithOneAddedPlayer() throws Exception { Client client =
     * ClientBuilder.newClient();
     * 
     * WebTarget target = client.target(JOUEURS_RS_URL);
     * 
     * // 1 - add player target.request().post(Entity.entity("kevin93", MediaType.APPLICATION_JSON));
     * 
     * // 2 - get players Response response = target.request(MediaType.APPLICATION_JSON).get(); Assert.assertEquals(200,
     * response.getStatus()); List<Joueur> joueurs = response.readEntity(new GenericType<List<Joueur>>() { });
     * 
     * Assert.assertEquals(1, joueurs.size()); Assert.assertEquals("kevin93", joueurs.get(0).getPseudo()); }
     */

}
