package com.thalesgroup.jeu;

import static org.junit.Assert.assertEquals;

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

import exceptions.JoueurExisteDejaException;
import exceptions.PartieDemarreException;

public class ResourceJoueursTest extends AbstractResourceTest {

    @Before
    @Override
    public void setUp() throws Exception { // set up jetty server
        super.setUp();

        // clear jeudeloie from current JVM
        JeuDelOie.getInstance().reset();
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
        Assert.assertNotNull(response.getLocation());
    }

    @Test
    public void testRetrievePlayerRessource() {
        WebTarget targetPost = client.target(JOUEURS_RS_URL);
        Response responsePost = targetPost.request().post(Entity.entity(PRO_GAMER, MediaType.APPLICATION_JSON));

        WebTarget targetGet = client.target(responsePost.getLocation());
        Response responseGet = targetGet.request().get();
        Assert.assertEquals(Status.OK.getStatusCode(), responseGet.getStatus());
    }

    @Test
    public void testGetPlayerRessourceConnue() throws JoueurExisteDejaException, PartieDemarreException {
        JeuDelOie.getInstance().addJoueur(PRO_GAMER);
        WebTarget targetGet = client.target(JOUEURS_RS_URL + "/" + PRO_GAMER);

        Response response = targetGet.request().get();
        assertEquals(Status.OK.getStatusCode(), response.getStatus());

        Joueur joueur = response.readEntity(Joueur.class);
        assertEquals(PRO_GAMER, joueur.getPseudo());

    }

    @Test
    public void testGetListeJoueurs() throws JoueurExisteDejaException, PartieDemarreException {
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
    public void testCreate2PlayerRessource() throws JoueurExisteDejaException, PartieDemarreException {
        WebTarget target = client.target(JOUEURS_RS_URL);
        // insertion
        JeuDelOie.getInstance().addJoueur(PRO_GAMER);
        Response response = target.request().post(Entity.entity(PRO_GAMER, MediaType.APPLICATION_JSON));

        Assert.assertEquals(Status.CONFLICT.getStatusCode(), response.getStatus());
    }
}
