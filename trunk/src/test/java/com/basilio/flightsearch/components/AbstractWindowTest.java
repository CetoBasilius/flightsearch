package com.basilio.flightsearch.components;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.internal.services.ajax.JavaScriptSupportImpl;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.services.SymbolSource;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 8/8/12
 * Time: 10:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class AbstractWindowTest {

    @Test
    public void testSetupRender() {
        AbstractWindow window = new Window();

        window.setGeneralClientId("id");

        window.setJavascriptSupport(new JavaScriptSupportImpl(null, null, null));
        window.setJavaScriptSupport(new JavaScriptSupportImpl(null, null, null));

        window.setupRender();

        assertEquals("id", window.getAssignedClientId());
        assertEquals("id", window.getClientId());

        window.setAssignedClientId("new id");
        assertEquals("new id", window.getAssignedClientId());

    }

    @Test
    public void testBeginRender() {
        AbstractWindow window = new Window();
        JavaScriptSupport javaScriptSupport = createNiceMock(JavaScriptSupport.class);
        window.setJavascriptSupport(javaScriptSupport);
        window.setJavaScriptSupport(javaScriptSupport);

        assertEquals(javaScriptSupport, window.getJavascriptSupport());
        assertEquals(javaScriptSupport, window.getJavaScriptSupport());

        SymbolSource symbolSource = createNiceMock(SymbolSource.class);
        AssetSource assetSource = createNiceMock(AssetSource.class);

        window.setAssetSource(assetSource);
        assertEquals(assetSource, window.getAssetSource());
        window.setSymbolSource(symbolSource);
        assertEquals(symbolSource, window.getSymbolSource());

        expect(symbolSource.expandSymbols("${ck.components}")).andReturn("path").anyTimes();

        TestAsset testAsset = new TestAsset();
        assertNull(testAsset.toClientURL());
        assertNull(testAsset.getResource());

        expect(assetSource.getClasspathAsset("path/window/themes/lighting.css")).andReturn(testAsset);
        expect(assetSource.getClasspathAsset("path/window/themes/default.css")).andReturn(testAsset);
        expect(assetSource.getClasspathAsset("path/window/themes/mac_os_x.css")).andReturn(testAsset);
        expect(assetSource.getClasspathAsset("path/window/themes/customname.css")).andReturn(testAsset);


        javaScriptSupport.importStylesheet(testAsset);
        expectLastCall();

        replay(symbolSource, assetSource, javaScriptSupport);

        window.setClassName("classlighting");
        window.beginRender(null);
        window.setClassName("dialog");
        window.beginRender(null);
        window.setClassName("_os_x");
        window.beginRender(null);
        window.setClassName("customname");
        window.beginRender(null);

        verify(symbolSource, assetSource, javaScriptSupport);

    }

    class TestAsset implements Asset {
        public String toClientURL() {
            return null;
        }

        public Resource getResource() {
            return null;
        }
    }

    ;

    @Test
    public void testOtherMethods() {
        AbstractWindow window = new Window();

        assertFalse(window.isShow());
        assertFalse(window.isCenter());
        assertFalse(window.isModal());

        window.setClassName("class name");
        assertEquals("class name", window.getClassName());

        assertEquals(0, window.getHeight());
        assertEquals(0, window.getWidth());

        window.setTitle("test title");
        assertEquals("test title", window.getTitle());

        window.setResources(null);
        assertNull(window.getResources());
    }

    @Test
    public void testIsAnnotationPresent() {
        assertTrue(AbstractWindow.class.isAnnotationPresent(Import.class));
    }


}
