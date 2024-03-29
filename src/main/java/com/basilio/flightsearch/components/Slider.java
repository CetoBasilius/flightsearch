package com.basilio.flightsearch.components;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.*;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/6/12
 * Time: 1:39 PM
 * To change this template use File | Settings | File Templates.
 */

@Import(library = {"${tapestry.scriptaculous}/controls.js", "${tapestry.scriptaculous}/slider.js"},
        stylesheet = {"Slider.css"})
public class Slider implements ClientElement {
    private final static String handleCSS = "slider-handle";
    private final static String trackCSS = "slider-track";
    private final static String valueCSS = "slider-value";

    @Parameter(required = false)
    private String customHandleCSS;

    @Parameter(required = false)
    private String customTrackCSS;

    @Parameter(required = false)
    private String customValueCSS;

    /**
     * The id used to generate a page-unique client-side identifier for the component. If a component renders multiple
     * times, a suffix will be appended to the to id to ensure uniqueness.
     */
    @Parameter(value = "prop:componentResources.id", defaultPrefix = BindingConstants.LITERAL)
    private String clientId;

    /**
     * The value to read or update.
     */
    @Parameter(required = true)
    private Number value;

    @Parameter(required = true)
    private int min;

    @Parameter(required = true)
    private int max;

    @Parameter(required = true)
    private int steps;

    /**
     * If true, then the field will render out with a disabled attribute (to turn off client-side behavior).
     * Further, a disabled field ignores any value in the request when the form is submitted.
     */
    @Parameter(value = "false", required = false)
    private boolean disabled;

    @Inject
    private ComponentResources componentResources;

    @Inject
    private Request request;

    private String handleId;
    private String tackId;
    private String ouputId;

    @Environmental
    private JavaScriptSupport javascriptSupport;

    private String assignedClientId;

    void setupRender() {
        assignedClientId = javascriptSupport.allocateClientId(clientId);
    }

    void beginRender(MarkupWriter writer) {
        handleId = "handle_" + getClientId();
        tackId = "track_" + getClientId();
        ouputId = "ouput_" + getClientId();

        if (StringUtils.isNotBlank(customTrackCSS)) {
            writer.element("div", "id", tackId, "class", customTrackCSS);
        } else {
            writer.element("div", "id", tackId, "class", trackCSS);
        }

        if (StringUtils.isNotBlank(customHandleCSS)) {
            writer.element("div", "id", handleId, "class", customHandleCSS);
        } else {
            writer.element("div", "id", handleId, "class", handleCSS);
        }

        writer.end();
        writer.end();
    }

    void afterRender(MarkupWriter writer) {
        if (StringUtils.isNotBlank(customValueCSS)) {
            writer.element("div", "id", ouputId, "class", customValueCSS);
        } else {
            writer.element("div", "id", ouputId, "class", valueCSS);
        }


        if (value == null) {
            value = 0;
        }
        writer.writeRaw("No more than ");
        writer.write(value.toString());
        writer.writeRaw(" USD");
        writer.end();


        if (steps <= 0) {
            steps = 1;
        }
        StringBuffer ticks = new StringBuffer();
        int range = max - min;
        float realStep = (float) range / (float) steps;
        ticks.append("values: [");
        int accumulatedStep = min;
        for (int a = 0; a < steps; a++) {
            ticks.append(accumulatedStep);
            ticks.append(",");
            accumulatedStep += (int) realStep;
        }
        ticks.append(accumulatedStep);
        ticks.append("]");

        String jsCommand = "new Control.Slider('%s','%s',{sliderValue:" + getNumberPattern(value) + ",range:" +
                "$R('%d','%d')," + ticks.toString() +
                ",onSlide:function(v){$('%s').innerHTML = 'No more than ' + v  + ' USD'}";
        jsCommand = String.format(Locale.US, jsCommand, handleId, tackId, value, min, max, ouputId);

        if (disabled)
            jsCommand += ",disabled:true";

        jsCommand += ", onChange:function(value){$('%s').innerHTML = 'No more than ' + value + ' USD'; new Ajax.Request('%s/' + value ,{method:'get'})}});";
        jsCommand = String.format(Locale.US, jsCommand, ouputId, getActionLink());

        javascriptSupport.addScript(jsCommand);
    }

    @OnEvent(value = "action")
    private void onAction(Number value) {
        this.value = value;
    }

    public Number getValue() {
        return value;
    }

    public void setValue(Number value) {
        this.value = value;
    }

    public String getActionLink() {
        return componentResources.createEventLink(EventConstants.ACTION).toURI();
    }

    private String getNumberPattern(Number value) {
        return "%d";
    }

    /**
     * Returns a unique id for the element. This value will be unique for any given rendering of a
     * page. This value is intended for use as the id attribute of the client-side element, and will
     * be used with any DHTML/Ajax related JavaScript.
     */
    public String getClientId() {
        return assignedClientId;
    }
}