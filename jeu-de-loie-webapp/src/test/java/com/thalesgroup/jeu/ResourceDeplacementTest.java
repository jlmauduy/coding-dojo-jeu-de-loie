package com.thalesgroup.jeu;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import jeuDeLoie.JeuDelOie;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.thalesgroup.jeu.exchange.DeplacementExchange;

public class ResourceDeplacementTest extends AbstractResourceTest {

    private static final String DEPLACEMENT_URL = "http://localhost:" + HTTP_PORT + CONTEXT_PATH + "/deplacements";
    private JeuDelOie jeu;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();

        jeu = JeuDelOie.getInstance();

        // clear jeudeloie from current JVM
        jeu.reset();

        jeu.addJoueur(NOOB);
    }

    @Test
    public void testCheckPostCreateDeplacement() {

        // Creation du deplacement
        WebTarget target = client.target(DEPLACEMENT_URL);
        Response response = target.request().post(Entity.entity(NOOB, MediaType.APPLICATION_JSON));
        Assert.assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
        Assert.assertNotNull(response.getLocation());
    }

    @Test
    public void testGetDeplacement() {

        int positionInitiale = jeu.getJoueurFromPseudo(NOOB).getPosition();

        // Creation du deplacement
        WebTarget target = client.target(DEPLACEMENT_URL);
        Response response = target.request().post(Entity.entity(NOOB, MediaType.APPLICATION_JSON));

        int positionFinale = jeu.getJoueurFromPseudo(NOOB).getPosition();

        // Recuperation du deplacement
        URI deplacementLocation = response.getLocation();
        WebTarget deplacementTarget = client.target(deplacementLocation);
        Response responseDeplacement = deplacementTarget.request().get();

        DeplacementExchange createdDeplacement = responseDeplacement.readEntity(DeplacementExchange.class);

        assertEquals(NOOB, createdDeplacement.getJoueur());
        assertEquals(positionInitiale, createdDeplacement.getPositionInitiale());
        assertEquals(positionFinale, createdDeplacement.getPositionFinale());

    }

}
