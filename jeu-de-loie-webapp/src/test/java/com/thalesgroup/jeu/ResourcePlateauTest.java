package com.thalesgroup.jeu;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import jeuDeLoie.Case;
import jeuDeLoie.JeuDelOie;

import org.junit.Assert;
import org.junit.Test;

import com.thalesgroup.jeu.exchange.CaseExchange;
import com.thalesgroup.jeu.exchange.PlateauExchange;

public class ResourcePlateauTest extends AbstractResourceTest {

    private static final String PLATEAU_URL = "http://localhost:" + HTTP_PORT + CONTEXT_PATH + "/plateau";
    private static final String CASE_URL = "/cases";

    @Test
    public void testgetPlateau() {
        WebTarget target = client.target(PLATEAU_URL);

        Response response = target.request().get();

        Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());

        PlateauExchange plateauExchanged = response.readEntity(PlateauExchange.class);

        Assert.assertEquals(JeuDelOie.getInstance().getPlateau().getCases().size(), plateauExchanged.getCases().size());

        for (int i = 0; i < plateauExchanged.getCases().size(); i++) {
            Assert.assertEquals(JeuDelOie.getInstance().getPlateau().getCases().get(i).getCaseType(), plateauExchanged
                    .getCases().get(i).getType());
        }
    }

    @Test
    public void testgetCase() {
        WebTarget target = client.target(PLATEAU_URL + CASE_URL + "/14");

        Response response = target.request().get();

        Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());

        CaseExchange caseExchanged = response.readEntity(CaseExchange.class);

        Case caseTest = JeuDelOie.getInstance().getPlateau().getCases().get(14);

        Assert.assertEquals(caseTest.getCaseType(), caseExchanged.getType());
        Assert.assertEquals(14, caseExchanged.getNumero());

    }

    @Test
    public void testgetCaseInexistante() {
        WebTarget target = client.target(PLATEAU_URL + CASE_URL + "/53");

        Response response = target.request().get();

        Assert.assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
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
