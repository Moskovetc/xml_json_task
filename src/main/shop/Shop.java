package main.shop;

import main.StaxStreamProcessor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Shop {
    private String path = "C:\\Users\\Leonid_Moskovets\\Desktop\\workspace\\xml_json_task\\examples\\example.xml";

    public void run() {
        List<Category<SubCategory>> categories = new ArrayList<>();
        try (StaxStreamProcessor processor = new StaxStreamProcessor(Files.newInputStream(Paths.get(path)))) {
            XMLStreamReader reader = processor.getReader();
            categories = getCategories("category", "shop", reader);
        } catch (XMLStreamException | IOException | JAXBException e) {
            e.printStackTrace();
        }
        System.out.println(categories);
    }

    private List<Category<SubCategory>> getCategories(
            String elementName, String parentElementName, XMLStreamReader reader)
            throws XMLStreamException, JAXBException {
        List<Category<SubCategory>> categories = new ArrayList<>();
        while (reader.hasNext()) {
            int event = reader.next();
            if (XMLStreamConstants.START_ELEMENT == event && reader.getLocalName().equals(elementName)) {
                Category<SubCategory> category = new Category<>();
                category.setName(getText("name", reader));
                category.setSubCategories(getSubcategories("subcategory",
                        "category", reader));
                categories.add(category);
            }
            if (XMLStreamConstants.END_ELEMENT == event && reader.getLocalName().equals(parentElementName)) break;
        }
        return categories;
    }

    private List<SubCategory> getSubcategories(
            String elementName, String parentElementName, XMLStreamReader reader)
            throws XMLStreamException, JAXBException {
        List<SubCategory> subCategories = new ArrayList<>();
        while (reader.hasNext()) {
            int event = reader.next();
            if (XMLStreamConstants.START_ELEMENT == event && reader.getLocalName().equals(elementName)) {
                SubCategory<Product> subCategory = new SubCategory<>();
                subCategory.setName(getText("name", reader));
                List<Product> products = getProducts("product", "subcategory", reader);
                subCategory.setProducts(products);
                subCategories.add(subCategory);
            }
            if (XMLStreamConstants.END_ELEMENT == event && reader.getLocalName().equals(parentElementName)) break;
        }
        return subCategories;
    }

    private List<Product> getProducts(
            String elementName, String parentElementName, XMLStreamReader reader)
            throws XMLStreamException, JAXBException {
        List<Product> products = new ArrayList<>();
        while (reader.hasNext()) {
            int event = reader.next();
            if (XMLStreamConstants.START_ELEMENT == event && reader.getLocalName().equals(elementName)) {
                JAXBContext jc = JAXBContext.newInstance(Product.class);
                Unmarshaller unmarshaller = jc.createUnmarshaller();
                JAXBElement<Product> jb = unmarshaller.unmarshal(reader, Product.class);
                products.add(jb.getValue());
            }
            if (XMLStreamConstants.END_ELEMENT == event && reader.getLocalName().equals(parentElementName)) break;
        }
        return products;
    }

    private String getText(String elementName, XMLStreamReader reader) throws XMLStreamException {
        while (reader.hasNext()) {
            int event = reader.next();
            if (XMLStreamConstants.START_ELEMENT == event && reader.getLocalName().equals(elementName)) {
                while (reader.hasNext()) {
                    event = reader.next();
                    if (XMLStreamConstants.CHARACTERS == event && !reader.isWhiteSpace()) {
                        return reader.getText();
                    }
                }
            }
        }
        return "";
    }
}
