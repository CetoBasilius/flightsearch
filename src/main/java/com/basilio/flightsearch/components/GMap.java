package com.basilio.flightsearch.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.chenillekit.google.services.GoogleGeoCoder;


/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 7/8/12
 * Time: 1:36 AM
 * To change this template use File | Settings | File Templates.
 */

@SupportsInformalParameters
@Import(library = {"../Basi.js", "GMap.js"})
public class GMap implements ClientElement
{
    /**
     * For blocks, messages, crete actionlink, trigger event.
     */
    @Inject
    private ComponentResources resources;

    /**
     * Request object for information on current request.
     */
    @Inject
    private Request request;

    /**
     * RenderSupport to get unique client side id.
     */
    @Environmental
    private JavaScriptSupport javascriptSupport;

    /**
     * inject our google map service.
     */
    @Inject
    private GoogleGeoCoder geoCoder;

    /**
     * The id used to generate a page-unique client-side identifier for the component. If a component renders multiple
     * times, a suffix will be appended to the to id to ensure uniqueness.
     */
    @Parameter(value = "prop:componentResources.id", defaultPrefix = BindingConstants.LITERAL)
    private String clientId;

    @Parameter(defaultPrefix = BindingConstants.PROP)
    private Double lat;

    @Parameter(defaultPrefix = BindingConstants.PROP)
    private Double lng;


    /**
     * name of a javascript function that acts as error handler.
     */
    @Parameter(defaultPrefix = BindingConstants.LITERAL, value = "")
    private String errorCallbackFunction;

    @Parameter(defaultPrefix = BindingConstants.LITERAL, value = "")
    private String dragendCallbackFunction;

    private String assignedClientId;

    void setupRender()
    {
        assignedClientId = javascriptSupport.allocateClientId(clientId);
    }

    public String getPlotterId()
    {
        return getClientId();
    }

    /**
     * Tapestry render phase method.
     * Start a tag here, end it in afterRender
     *
     * @param writer the markup writer
     */
    void beginRender(MarkupWriter writer)
    {
        Element root = writer.getDocument().getRootElement();
        Element head = root.find("head");

        head.element("script",
                "src", "http://maps.google.com/maps?file=api&v=2&key=" + geoCoder.getKey() + "&hl=" +
                request.getLocale().getLanguage(),
                "type", "text/javascript",
                "id", "gmap");

        writer.element("div", "id", getClientId() + "_map");
        resources.renderInformalParameters(writer);
        writer.end();
    }

    /**
     * Tapestry render phase method. End a tag here.
     */
    void afterRender()
    {
        JSONObject configuration = new JSONObject();
        configuration.put("zoomLevel", 13);
        configuration.put("smallControl", true);
        configuration.put("largeControl", false);
        configuration.put("typeControl", true);
        configuration.put("label", "location");
        configure(configuration);

        javascriptSupport.addScript("var %s = new Hb.GMap('%s_map', '%s', '%s', '%s', %s);",
                getClientId(), getClientId(),
                geoCoder.getKey(),
                errorCallbackFunction,
                dragendCallbackFunction,
                configuration.toString());

        javascriptSupport.addScript("%s.setCenter(%s, %s);", getClientId(), lat, lng);

        //TESTS
        javascriptSupport.addScript("%s.setMarker(%s, %s);", getClientId(), lat, lng);

        JSONObject Polyline = new JSONObject();
        Polyline.put("lat1",29.0958995819);Polyline.put("lng1",-111.047996521);
        Polyline.put("lat2",32.1161003113);Polyline.put("lng2",-110.941001892);
        Polyline.put("lat3",33.94250107);Polyline.put("lng3",-118.4079971);

        javascriptSupport.addScript("%s.addPolyline(%s);", getClientId(),Polyline);

    }

    /**
     * for external configuration do override.
     *
     * @param jsonObject config object
     */
    protected void configure(JSONObject jsonObject)
    {
    }

    /**
     * Returns a unique id for the element. This value will be unique for any given rendering of a
     * page. This value is intended for use as the id attribute of the client-side element, and will
     * be used with any DHTML/Ajax related JavaScript.
     */
    public String getClientId()
    {
        return assignedClientId;
    }
}
