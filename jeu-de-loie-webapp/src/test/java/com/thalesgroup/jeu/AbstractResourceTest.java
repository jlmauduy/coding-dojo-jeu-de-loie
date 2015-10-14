package com.thalesgroup.jeu;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.After;
import org.junit.Before;

public abstract class AbstractResourceTest {

    protected static final int HTTP_PORT = 9999;
    protected static final String CONTEXT_PATH = "/jeudeloie";
    private static Map<BootStrap, Handler> appContexts = new HashMap<>();
    protected static final String PRO_GAMER = "claude";
    protected static final String NOOB = "Khay-tech";

    protected static final String JOUEURS_RS_URL = "http://localhost:" + HTTP_PORT + CONTEXT_PATH + "/joueurs";

    static {
        appContexts.put(BootStrap.WEBXML, getWebXmlAppContext());
        appContexts.put(BootStrap.FULLJAVA, getFullJavaAppContext());
    }

    protected final Client client = ClientBuilder.newClient();
    private Server jettyServer;

    @Before
    public void setUp() throws Exception { // set up jetty server
        jettyServer = new Server(HTTP_PORT);
        jettyServer.setHandler(appContexts.get(BootStrap.WEBXML));
        jettyServer.start();
        Log.getRootLogger().info("JCG Embedded Jetty logging started.");
    }

    @After
    public void tearDown() throws Exception {
        jettyServer.stop();
    }

    protected static Handler getFullJavaAppContext() {
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

    protected static Handler getWebXmlAppContext() {
        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setContextPath(CONTEXT_PATH);
        webAppContext.setResourceBase("src/main/webapp");
        webAppContext.setClassLoader(ResourceJoueursTest.class.getClassLoader());
        return webAppContext;
    }

    enum BootStrap {
        WEBXML, FULLJAVA;
    }
}
