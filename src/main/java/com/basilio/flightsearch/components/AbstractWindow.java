package com.basilio.flightsearch.components;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/26/12
 * Time: 12:16 PM
 * To change this template use File | Settings | File Templates.
 * creates a window based on jvascript <a href="http://prototype-window.xilinus.com/">window</a> library.
 */

import org.apache.tapestry5.*;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.SymbolSource;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;


@Import(library = {"${tapestry.scriptaculous}/effects.js", "../components/window/window.js",
        "../components/window/window_effects.js", "../components/window.js"}, stylesheet = {"../components/window/themes/default.css"})
abstract public class AbstractWindow implements ClientElement {

    /**
     * The id used to generate a page-unique client-side identifier for the component. If a component renders multiple
     * times, a suffix will be appended to the to id to ensure uniqueness. The uniqued value may be accessed via the
     * {@link #getClientId() clientId property}.
     */
    @Parameter(value = "prop:componentResources.id", defaultPrefix = BindingConstants.LITERAL)
    private String clientId;

    public void setGeneralClientId(String id) {
        this.clientId = id;
    }

    /**
     * style name for the window.
     * <p/>
     * possible values are:
     * <br/>
     * <ul>
     * <li>dialog</li>
     * <li>alert (attention! has no window buttons)</li>
     * <li>alert_lite (attention! has no window buttons)</li>
     * <li>alphacube</li>
     * <li>mac_os_x</li>
     * <li>blur_os_x</li>
     * <li>mac_os_x_dialog</li>
     * <li>nuncio</li>
     * <li>spread</li>
     * <li>darkX</li>
     * <li>greenlighting</li>
     * <li>bluelighting</li>
     * <li>greylighting</li>
     * <li>darkbluelighting</li>
     * </ul>
     */
    @Parameter(value = "alphacube", defaultPrefix = BindingConstants.LITERAL, name = "style")
    private String className;

    /**
     * initial width of the window.
     */
    @Parameter(value = "0", defaultPrefix = BindingConstants.PROP)
    private int width;

    /**
     * initial height of the window.
     */
    @Parameter(value = "0", defaultPrefix = BindingConstants.PROP)
    private int height;

    /**
     * Shows window at its current position.
     */
    @Parameter(value = "true", defaultPrefix = BindingConstants.PROP)
    private boolean show;

    /**
     * Shows window centered (only if parameter "show" is true).
     */
    @Parameter(value = "true", defaultPrefix = BindingConstants.PROP)
    private boolean center;

    /**
     * Shows window in modal mode (only if parameter "show" is true).
     */
    @Parameter(value = "true", defaultPrefix = BindingConstants.PROP)
    private boolean modal;

    /**
     * set the window title.
     */
    @Parameter(value = "", defaultPrefix = BindingConstants.PROP)
    private String title;

    private String assignedClientId;

    @Environmental
    private JavaScriptSupport javascriptSupport;

    @Inject
    private JavaScriptSupport javaScriptSupport;

    @Inject
    private ComponentResources resources;

    @Inject
    private AssetSource assetSource;

    @Inject
    private SymbolSource symbolSource;

    /**
     * Tapestry render phase method.
     * Initialize temporary instance variables here.
     */
    void setupRender() {
        // By default, use the component id as the (base) client id. If the clientid
        // parameter is bound, then that is the value to use.
        // Often, these controlName and clientId will end up as the same value. There are many
        // exceptions, including a form that renders inside a loop, or a form inside a component
        // that is used multiple times.
        assignedClientId = javascriptSupport.allocateClientId(clientId);
    }

    /**
     * Tapestry render phase method.
     * Start a tag here, end it in afterRender
     */
    void beginRender(MarkupWriter writer) {
        String cssStyleFile;

        String scriptPathSymbolValue = symbolSource.expandSymbols("${ck.components}") + "/window/themes";

        if (className.endsWith("lighting"))
            cssStyleFile = "lighting.css";
        else if (className.equals("dialog"))
            cssStyleFile = "default.css";
        else if (className.endsWith("_os_x"))
            cssStyleFile = "mac_os_x.css";
        else
            cssStyleFile = className + ".css";

        String assetString = scriptPathSymbolValue + "/" + cssStyleFile;
        Asset cssAsset = assetSource.getClasspathAsset(assetString);
        if (cssAsset != null) {
            javaScriptSupport.importStylesheet(cssAsset);
        }

    }

    /**
     * Returns a unique id for the element. This value will be unique for any given rendering of a
     * page. This value is intended for use as the id attribute of the client-side element, and will
     * be used with any DHTML/Ajax related JavaScript.
     */
    public String getClientId() {
        return assignedClientId;
    }


    public boolean isShow() {
        return show;
    }

    public boolean isCenter() {
        return center;
    }

    public boolean isModal() {
        return modal;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String name) {
        this.className = name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getTitle() {
        return title;
    }

    public String getAssignedClientId() {
        return assignedClientId;
    }

    public void setAssignedClientId(String assignedClientId) {
        this.assignedClientId = assignedClientId;
    }

    public JavaScriptSupport getJavascriptSupport() {
        return javascriptSupport;
    }

    public void setJavascriptSupport(JavaScriptSupport javascriptSupport) {
        this.javascriptSupport = javascriptSupport;
    }

    public JavaScriptSupport getJavaScriptSupport() {
        return javaScriptSupport;
    }

    public void setJavaScriptSupport(JavaScriptSupport javaScriptSupport) {
        this.javaScriptSupport = javaScriptSupport;
    }

    public ComponentResources getResources() {
        return resources;
    }

    public void setResources(ComponentResources resources) {
        this.resources = resources;
    }

    public AssetSource getAssetSource() {
        return assetSource;
    }

    public void setAssetSource(AssetSource assetSource) {
        this.assetSource = assetSource;
    }

    public SymbolSource getSymbolSource() {
        return symbolSource;
    }

    public void setSymbolSource(SymbolSource symbolSource) {
        this.symbolSource = symbolSource;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}