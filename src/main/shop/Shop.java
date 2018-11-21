package main.shop;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Shop {
    private String path = "C:\\Users\\Leonid_Moskovets\\Desktop\\workspace\\xml_json_task\\examples\\example.xml";
    public void run() {
        List<Category> categories = new ArrayList<>();
        try {
            getFromXML(path);
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        System.out.println(Arrays.toString(categories.toArray()));
    }

    private void getFromXML(String path) throws XMLStreamException {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
        StreamSource xml = new StreamSource(path);
        XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(xml);
        xmlStreamReader.nextTag();
        while (xmlStreamReader.hasNext()){
            System.out.println(xmlStreamReader.getLocalName());
            if (xmlStreamReader.getLocalName().equals("name")){
            System.out.println(xmlStreamReader.getElementText());
                xmlStreamReader.next();
                xmlStreamReader.next();
            }
            xmlStreamReader.nextTag();
        }
        xmlStreamReader.close();
    }
}
