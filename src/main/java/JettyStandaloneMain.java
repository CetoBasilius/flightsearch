import java.net.URL;
import java.security.ProtectionDomain;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;
/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 6/28/12
 * Time: 6:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class JettyStandaloneMain {

    public static void main(String[] args) throws Exception
    {
        int port = Integer.parseInt(System.getProperty("port", "8080"));
        Server server = new Server(port);

        ProtectionDomain domain = JettyStandaloneMain.class.getProtectionDomain();
        URL location = domain.getCodeSource().getLocation();

        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/");
        webapp.setDescriptor(location.toExternalForm() + "/WEB-INF/web.xml");
        webapp.setServer(server);
        webapp.setWar(location.toExternalForm());

        // Directory the war will be extracted to
        // java.io.tmpdir will be used since it is commented out, can cause problems if tmp is cleaned regularly
        //webapp.setTempDirectory(new File("somepath i did not use/"));

        server.setHandler(webapp);
        server.start();
        server.join();
    }
}

