package com.basilio.flightsearch.components;

import org.apache.tapestry5.*;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.runtime.Component;
import org.chenillekit.tapestry.core.internal.PagedSource;

/**
 * copy and paste of org.apache.tapestry5.corelib.components.GridPager with modifications.
 *
 * @version $Id$
 */
@Import(stylesheet = {"CustomPager.css"})
public class Pager {
    /**
     * The source of the data displayed by the PagedList (this is used to
     * determine
     * {@link PagedSource#getTotalRowCount() how many rows are available},
     * which in turn determines the page count).
     */
    @Parameter
    private PagedSource<?> source;

    /**
     * The number of rows displayed per page.
     */
    @Parameter
    private int rowsPerPage;

    /**
     * The current page number (indexed from 1).
     */
    @Parameter
    private int currentPage;

    /**
     * If this pager is in a custom position it must provide the id of the
     * CustomPagedLoop it is associated with.
     */
    @Parameter(defaultPrefix = BindingConstants.LITERAL, name = "for")
    private String forId;

    /**
     * Number of pages before and after the current page in the range. The pager
     * always displays links for 2 * range + 1 pages, unless that's more than
     * the total number of available pages.
     */
    @Parameter("5")
    private int range;

    private int lastIndex;

    private int maxPages;

    @Inject
    private ComponentResources resources;

    @Inject
    private Messages messages;

    private Component pagedLoopComponent;

    private CustomPagedLoop pagedLoop;

    void setupRender() {
        if (forId != null) {
            source = getPagedLoop().getPagedSource();
            rowsPerPage = getPagedLoop().getRowsPerPage();
            currentPage = getPagedLoop().getCurrentPage();
        }
    }

    void beginRender(MarkupWriter writer) {

        int availableRows = source.getTotalRowCount();

        maxPages = ((availableRows - 1) / rowsPerPage) + 1;

        if (maxPages < 2)
            return;

        writer.element(resources.getContainer().getComponentResources().getElementName(), "class", "paged_loop_pager");
        writer.element("div", "class", "paged_loop_pager", "id", "non-printable");
        writer.element("label", "class", "paged_loop_pagenumber");
        writer.writeRaw("Page: ");
        writer.end();

        lastIndex = 0;

        for (int i = 1; i <= 2; i++)
            writePageLink(writer, i);

        int low = currentPage - range;
        int high = currentPage + range;

        if (low < 1) {
            low = 1;
            high = 2 * range + 1;
        } else {
            if (high > maxPages) {
                high = maxPages;
                low = high - 2 * range;
            }
        }

        for (int i = low; i <= high; i++)
            writePageLink(writer, i);

        for (int i = maxPages - 1; i <= maxPages; i++)
            writePageLink(writer, i);

        writer.end();

        writer.end();
    }

    void onAction(int newPage) {
        // TODO: Validate newPage in range
        currentPage = newPage;
        if (forId != null) {
            getPagedLoopComponent().getComponentResources().triggerEvent(
                    EventConstants.ACTION, new Integer[]{newPage},
                    null);
        }
    }

    private Component getPagedLoopComponent() {
        if (forId != null && pagedLoopComponent == null)
            pagedLoopComponent = resources.getPage().getComponentResources().getEmbeddedComponent(forId);

        return pagedLoopComponent;
    }

    private CustomPagedLoop getPagedLoop() {
        if (forId != null && pagedLoop == null)
            pagedLoop = (CustomPagedLoop) getPagedLoopComponent();

        return pagedLoop;
    }

    private void writePageLink(MarkupWriter writer, int pageIndex) {
        if (pageIndex < 1 || pageIndex > maxPages)
            return;

        if (pageIndex <= lastIndex)
            return;

        if (pageIndex != lastIndex + 1)
            writer.write(" ... "); // &#8230; is ellipsis

        lastIndex = pageIndex;

        if (pageIndex == currentPage) {
            writer.element("span", "class", "paged_loop_current");
            writer.write(Integer.toString(pageIndex));
            writer.end();
            return;
        }

        Link link = resources.createEventLink(EventConstants.ACTION, pageIndex);

        writer.element("a", "href", link, "title", messages.format("goto-page", pageIndex));

        writer.write(Integer.toString(pageIndex));
        writer.end();
    }
}