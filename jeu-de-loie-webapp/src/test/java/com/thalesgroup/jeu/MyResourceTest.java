package com.thalesgroup.jeu;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import jeuDeLoie.JeuDelOie;
import jeuDeLoie.Joueur;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MyResourceTest {

    private static final int HTTP_PORT = 9999;
    private static final String CONTEXT_PATH = "/jeudeloie";
    private static final String JOUEURS_RS_URL = "http://localhost:" + HTTP_PORT + CONTEXT_PATH + "/joueurs";
    private static Map<BootStrap, Handler> appContexts = new HashMap<>();
    private static final String PRO_GAMER = "claude";
    private static final String NOOB = "Khay-tech";

    static {
        appContexts.put(BootStrap.WEBXML, getWebXmlAppContext());
        appContexts.put(BootStrap.FULLJAVA, getFullJavaAppContext());
    }

    private final Client client = ClientBuilder.newClient();
    private Server jettyServer;

    @Before
    public void setUp() throws Exception { // set up jetty server
        jettyServer = new Server(HTTP_PORT);
        jettyServer.setHandler(appContexts.get(BootStrap.WEBXML));
        jettyServer.start();
        Log.getRootLogger().info("JCG Embedded Jetty logging started.");

        // clear jeudeloie from current JVM
        JeuDelOie.getInstance().resetJoueurs();
    }

    @After
    public void tearDown() throws Exception {
        jettyServer.stop();
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

    private static Handler getFullJavaAppContext() {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath(CONTEXT_PATH);
        ServletHolder jerseyServlet = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(1);

        // Tells the Jersey Servlet which REST service/class to load.
        // need to add jackson package to tell jersey where is the provider to
        // use to serialize json
        jerseyServlet.setInitParameter("jersey.config.server.provider.packages",
                "com.thalesgroup.jeu;com.fasterxml.jackson.jaxrs.json");

        return context;
    }

    private static Handler getWebXmlAppContext() {
        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setContextPath(CONTEXT_PATH);
        webAppContext.setResourceBase("src/main/webapp");
        webAppContext.setClassLoader(MyResourceTest.class.getClassLoader());
        return webAppContext;
    }

    enum BootStrap {
        WEBXML, FULLJAVA;
    }
}
