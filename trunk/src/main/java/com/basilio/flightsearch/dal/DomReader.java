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

    public static String readFile(Document doc){

        String data = "";
        try {

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("airport");
            System.out.println("-----------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    data+= "Airport Name : " + getTagValue("name", eElement)+"\n";
                    data+= "Latitude : " + getTagValue("latitude", eElement)+"\n";
                    data+= "Longitude : " + getTagValue("longitude", eElement)+"\n";

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(data);
        return data;
    }

    private static String getTagValue(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();

        Node nValue = (Node) nlList.item(0);

        return nValue.getNodeValue();
    }

}

