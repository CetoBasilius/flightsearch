package com.basilio.flightsearch.components;

import org.apache.tapestry5.*;
import org.apache.tapestry5.internal.services.AssetSourceImpl;
import org.apache.tapestry5.internal.services.ajax.JavaScriptSupportImpl;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.internal.services.ThreadLocaleImpl;
import org.apache.tapestry5.ioc.services.SymbolSource;
import org.apache.tapestry5.services.AssetSource;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 8/8/12
 * Time: 10:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class AbstractWindowTest {

    @Test
    public void testSetupRender(){
        AbstractWindow window = new Window();

        window.setGeneralClientId("id");

        window.setJavascriptSupport(new JavaScriptSupportImpl(null,null,null));
        window.setJavaScriptSupport(new JavaScriptSupportImpl(null, null, null));

        window.setupRender();

        assertEquals("id",window.getAssignedClientId());
        assertEquals("id",window.getClientId());

    }

    @Test
    public void testBeginRender(){
        AbstractWindow window = new Window();
        window.setJavascriptSupport(new JavaScriptSupportImpl(null,null,null));
        window.setJavaScriptSupport(new JavaScriptSupportImpl(null, null, null));

        SymbolSource symbolSource = createNiceMock(SymbolSource.class);
        AssetSource assetSource = createNiceMock(AssetSource.class);

        window.setAssetSource(assetSource);
        window.setSymbolSource(symbolSource);

        window.setClassName("lightning");

        expect(symbolSource.expandSymbols("${ck.components}")).andReturn("path").anyTimes();
        expect(assetSource.getClasspathAsset("path/window/themes/lightning.css")).andReturn(new Asset() {
            public String toClientURL() {
                return "";
            }

            public Resource getResource() {
                return null;
            }
        });

        replay(symbolSource);

        window.beginRender(null);

        verify(symbolSource);

    }
}
