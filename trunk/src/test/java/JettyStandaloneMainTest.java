import org.junit.Test;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

import static org.easymock.EasyMock.*;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 8/10/12
 * Time: 9:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class JettyStandaloneMainTest {
    @Test
    public void testJettyStandalone() throws Exception {
        JettyStandaloneMain jettyStandalone = new JettyStandaloneMain();
        Server server = createNiceMock(Server.class);
        WebAppContext webApp = createNiceMock(WebAppContext.class);

        jettyStandalone.setServer(server);
        jettyStandalone.setWebApp(webApp);

        replay(server, webApp);

        jettyStandalone.initWebApp();

        verify(server, webApp);

    }
}
