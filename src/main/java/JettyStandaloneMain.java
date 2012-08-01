
import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import java.net.URL;
import java.security.ProtectionDomain;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 6/28/12
 * Time: 6:56 PM
 * This class is able to run a Jetty server from console using java -jar.
 */
public class JettyStandaloneMain {

    private static final String WEB_XML_PATH = "/WEB-INF/web.xml";
    private static final String DEF_PORT = "8089";

    public static void main(String[] args)
    {
        new JettyStandaloneMain();
    }

    public JettyStandaloneMain(){
        initLogger();
        initWebApp();
    }

    private void initWebApp(){

        int port = Integer.parseInt(System.getProperty("port", DEF_PORT));
        Server server = new Server(port);

        ProtectionDomain domain = JettyStandaloneMain.class.getProtectionDomain();
        URL location = domain.getCodeSource().getLocation();

        WebAppContext webApp = new WebAppContext();
        webApp.setContextPath("/");

        webApp.setDescriptor(location.toExternalForm() + WEB_XML_PATH);
        webApp.setServer(server);
        webApp.setWar(location.toExternalForm());

        server.setHandler(webApp);
        startServer(server);

    }

    private void startServer(Server server) {
        Logger.getLogger(JettyStandaloneMain.class).info("Starting server at port "+DEF_PORT);
        Logger.getLogger(JettyStandaloneMain.class).info("Please wait a moment while the web application is deploying...");
        try {
            server.start();
            server.join();
        } catch (InterruptedException e) {
            Logger.getLogger(JettyStandaloneMain.class).error("Server could not be joined");
        } catch (Exception e) {
            Logger.getLogger(JettyStandaloneMain.class).error("Server could not start, port "+DEF_PORT+" may already be in use.");
        }
    }

    private void initLogger() {
        URL properties = JettyStandaloneMain.class.getResource("log4j.properties");
        PropertyConfigurator.configure(properties);
        Logger.getLogger(JettyStandaloneMain.class).info("Logger started");
    }
}

