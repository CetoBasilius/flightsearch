package com.basilio.flightsearch.dal;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;


/**
 * Created with IntelliJ IDEA.
 * User: Cetobasilius
 * Date: 7/2/12
 * Time: 4:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class DomReader {
    public static String readTag(Document doc,String tag){
        String data = "";
        try {
            NodeList nList = doc.getElementsByTagName("airport");
            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;
                    data = getTagValue(tag, eElement);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    private static String getTagValue(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        return nValue.getNodeValue();
    }

}

