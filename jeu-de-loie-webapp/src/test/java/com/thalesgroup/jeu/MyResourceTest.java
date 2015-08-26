package com.thalesgroup.jeu;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import jeuDeLoie.Joueur;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MyResourceTest {

    private Server jettyServer;

    @Before
    public void setUp() throws Exception {
        // set up jetty server
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.setContextPath("/");

        jettyServer = new Server(9999);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        // Tells the Jersey Servlet which REST service/class to load.
        // need to add jackson package to tell jersey where is the provider to
        // use to serialize json
        jerseyServlet.setInitParameter("jersey.config.server.provider.packages",
                "com.thalesgroup.jeu;com.fasterxml.jackson.jaxrs.json");
        
        jettyServer.start();
    }
    
    @After
    public void tearDown() throws Exception {
        jettyServer.stop();
    }
    
    
    /* FIXME JLM Impossible to load web.xml using this setup :
    @Before
    public void setUp() throws Exception {
        Server server = new Server(9999);
            server.setStopAtShutdown(true);
            WebAppContext webAppContext = new WebAppContext();
            webAppContext.setContextPath("/");
            webAppContext.setResourceBase("src/main/webapp");       
            webAppContext.setClassLoader(getClass().getClassLoader());
            server.addHandler(webAppContext);
            server.start();
    }*/

    @Test
    public void testGetPlayersWithHttpClient() throws Exception {
        HttpClient httpclient = new DefaultHttpClient();
        
        HttpGet httpget = new HttpGet("http://localhost:9999/joueurs");
        httpget.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
        
        HttpResponse response = httpclient.execute(httpget);
        
        System.out.println(response.getStatusLine().toString());
        
        HttpEntity entity = response.getEntity();
        System.out.println();
        System.out.println(EntityUtils.toString(entity));
        
        Assert.assertEquals(200,  response.getStatusLine().getStatusCode());
    }
    
    @Test
    public void testGetPlayersWithJaxRsClient() throws Exception {
        Client client = ClientBuilder.newClient();
        
        WebTarget target = client.target("http://localhost:9999/joueurs");
        
        Response response = target.request(MediaType.APPLICATION_JSON).get();
        System.out.println(response.getStatus());
        Assert.assertEquals(200,  response.getStatus());
    }
    
    @Test
    public void testAddPlayer() throws Exception {
        Client client = ClientBuilder.newClient();
        
        WebTarget target = client.target("http://localhost:9999/joueurs");
        
        Response response = target.request().post(Entity.entity("kevin93", MediaType.APPLICATION_JSON));
        
        System.out.println(response.getStatus());
        Assert.assertEquals(201, response.getStatus());
    }
    @Test
    public void testGetPlayersWithOneAddedPlayer() throws Exception {
        Client client = ClientBuilder.newClient();
        
        WebTarget target = client.target("http://localhost:9999/joueurs");
        
        // 1 - add player
        target.request().post(Entity.entity("kevin93", MediaType.APPLICATION_JSON));
        
        // 2 - get players
        Response response = target.request(MediaType.APPLICATION_JSON).get();
        Assert.assertEquals(200,  response.getStatus());
        List<Joueur> joueurs = response.readEntity(new GenericType<List<Joueur>>(){});
        
        Assert.assertEquals(1, joueurs.size());
        Assert.assertEquals("kevin93", joueurs.get(0).getPseudo());
    }
}
