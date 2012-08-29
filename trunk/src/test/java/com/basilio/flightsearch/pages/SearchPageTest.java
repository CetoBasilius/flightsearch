package com.basilio.flightsearch.pages;

import com.basilio.flightsearch.entities.AirportStub;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 7/18/12
 * Time: 6:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class SearchPageTest {


    @Test
    public void testSetupRender() {
        SearchPage searchPage = new SearchPage();
        searchPage.setupRender();

        assertEquals("1", searchPage.getNumberOfAdults());
        assertEquals("0", searchPage.getNumberOfChildren());
        assertEquals("0", searchPage.getNumberOfInfants());

        assertEquals((Object) new Integer(500), searchPage.getSliderValue());
    }

    @Test
    public void testAutoCompleteAlgorithm() {
        SearchPage searchPage = new SearchPage();

        List<AirportStub> allAirportStubs = new ArrayList<AirportStub>();
        allAirportStubs.add(new AirportStub("AAA", "aabbb aaa"));
        allAirportStubs.add(new AirportStub("BBB", "CCC"));
        allAirportStubs.add(new AirportStub("CCC", "aaA aaa aaa"));
        allAirportStubs.add(new AirportStub("DDD", "aaa bbb aAa ccc aaa"));
        allAirportStubs.add(new AirportStub("EEE", "cchhh ssscc bbhhh bbb "));

        searchPage.setAllTheAirportStubs(allAirportStubs);

        assertEquals(ArrayList.class, searchPage.getAutoCompleteList("a").getClass());

        assertEquals("(AAA) aabbb aaa", searchPage.getAutoCompleteList("aaa").get(0));

        assertEquals("(AAA) aabbb aaa", searchPage.getAutoCompleteList("aaa").get(0));
        assertEquals("(CCC) aaA aaa aaa", searchPage.getAutoCompleteList("aa").get(1));
        assertEquals("(DDD) aaa bbb aAa ccc aaa", searchPage.getAutoCompleteList("aa").get(2));
    }
}
