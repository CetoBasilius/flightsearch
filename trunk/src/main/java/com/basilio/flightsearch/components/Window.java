package com.basilio.flightsearch.components;

import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.AfterRenderBody;
import org.apache.tapestry5.annotations.BeforeRenderBody;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/23/12
 * Time: 5:06 PM
 * creates a window based on javascript <a href="http://prototype-window.xilinus.com/">window</a> library.
 */

public class Window extends AbstractWindow {
    @Environmental
    private JavaScriptSupport javascriptSupport;

    private boolean hasBody = false;

    /**
     * Tapestry render phase method.
     * Called before component body is rendered.
     *
     * @param writer the markup writer
     */

    private String contentDivId;

    @BeforeRenderBody
    void beforeRenderBody(MarkupWriter writer) {
        contentDivId = javascriptSupport.allocateClientId(getClientId() + "Content");
        hasBody = true;
        writer.element("div",
                "id", contentDivId,
                "style", "display:none;");
    }

    /**
     * Tapestry render phase method.
     * Called after component body is rendered.
     * return false to render body again.
     *
     * @param writer the markup writer
     */
    @AfterRenderBody
    void afterRenderBody(MarkupWriter writer) {
        writer.end();
    }


    /**
     * Tapestry render phase method. End a tag here.
     *
     * @param writer the markup writer
     */
    @AfterRender
    void afterRender(MarkupWriter writer) {
        JSONObject windowOptions = new JSONObject();

        windowOptions.put("className", getClassName());
        windowOptions.put("width", getWidth());
        windowOptions.put("height", getHeight());
        windowOptions.put("id", getClientId());
        windowOptions.put("title", getTitle());

        JSONObject options = new JSONObject();
        options.put("windowoptions", windowOptions);
        options.put("hasbody", hasBody);
        options.put("show", isShow());
        options.put("modal", isModal());
        options.put("clientid", getClientId());
        options.put("contentid", contentDivId);

        javascriptSupport.addInitializerCall("ckwindow", options);
    }
}
