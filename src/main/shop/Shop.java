package main.shop;

import main.StaxStreamProcessor;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Shop {
    private String path = "C:\\Users\\Leonid_Moskovets\\Desktop\\workspace\\xml_json_task\\examples\\example.xml";
    public void run() {
        List<Category> categories = new ArrayList<>();
        try {
//            xmlToString(path);
            getCategories(path);
        } catch (XMLStreamException | IOException | JAXBException e) {
            e.printStackTrace();
        }
        System.out.println(Arrays.toString(categories.toArray()));
    }

    private void xmlToString(String path) throws IOException, XMLStreamException, JAXBException {
        try (StaxStreamProcessor processor = new StaxStreamProcessor(Files.newInputStream(Paths.get(path)))) {
            XMLStreamReader reader = processor.getReader();
            while (reader.hasNext()) {
                int event = reader.next();
                switch (event){
                    case XMLStreamConstants.START_DOCUMENT:
                        System.out.println("Start Document.");
                        break;
                    case XMLStreamConstants.START_ELEMENT:
                        System.out.println("Start Element: " + reader.getName());
                        for(int i = 0, n = reader.getAttributeCount(); i < n; ++i)
                            System.out.println("Attribute: " + reader.getAttributeName(i)
                                    + "=" + reader.getAttributeValue(i));

                        break;
                    case XMLStreamConstants.CHARACTERS:
                        if (reader.isWhiteSpace())
                            break;

                        System.out.println("Text: " + reader.getText());
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        System.out.println("End Element:" + reader.getName());
                        break;
                    case XMLStreamConstants.END_DOCUMENT:
                        System.out.println("End Document.");
                        break;
                }
            }
        }
    }

    private void getCategories(String path) throws IOException, XMLStreamException, JAXBException {
        try (StaxStreamProcessor processor = new StaxStreamProcessor(Files.newInputStream(Paths.get(path)))) {
            XMLStreamReader reader = processor.getReader();
            while (reader.hasNext()) {
                int event = reader.next();
                if (XMLStreamConstants.START_ELEMENT == event && reader.getName().equals("category")){
                    Category category = new Category();
                    while (reader.hasNext()){
                        event = reader.next();
                        if (XMLStreamConstants.START_ELEMENT == event && reader.getName().equals("name")){
                            while (reader.hasNext()) {
                                event = reader.next();
                                if (XMLStreamConstants.CHARACTERS == event && !reader.isWhiteSpace()) {
                                    category.setName(reader.getText());
                                }
                            }
                        }
                    }
                }
                if (XMLStreamConstants.START_ELEMENT == event && reader.getName().equals("subcategory")) {
                    SubCategory subCategory = new SubCategory();
                    while (reader.hasNext()){
                        event = reader.next();
                        if (XMLStreamConstants.START_ELEMENT == event && reader.getName().equals("name")){
                            while (reader.hasNext()) {
                                event = reader.next();
                                if (XMLStreamConstants.CHARACTERS == event && !reader.isWhiteSpace()) {
                                    subCategory.setName(reader.getText());
                                }
                            }
                        }
                    }
                }
                if (XMLStreamConstants.CHARACTERS == event)
                    if (!reader.isWhiteSpace())
                        System.out.println(reader.getText());
            }
        }
    }

    private String getText(String elementName, XMLStreamReader reader) throws XMLStreamException {
        while (reader.hasNext()) {
            int event = reader.next();
            if (XMLStreamConstants.START_ELEMENT == event && reader.getName().equals("name")) {
                while (reader.hasNext()) {
                    event = reader.next();
                    if (XMLStreamConstants.CHARACTERS == event && !reader.isWhiteSpace()) {
                        return reader.getText();
                    }
                }
            }
        }
    }
}
