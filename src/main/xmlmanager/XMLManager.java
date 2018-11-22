package main.xmlmanager;

import main.shop.Category;
import main.shop.Product;
import main.shop.Shop;
import main.shop.SubCategory;

import javax.xml.bind.*;
import javax.xml.namespace.QName;
import javax.xml.stream.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class XMLManager {

    public void marshal(String outputFile, Shop shop) {
        try {
            File file = new File(outputFile);
            JAXBContext jaxbContext = JAXBContext.newInstance(Shop.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "example.xsd");
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(shop, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public Shop unmarshal(String inputFile) {
        Shop shop = new Shop();
        try (StaxStreamProcessor processor = new StaxStreamProcessor(Files.newInputStream(Paths.get(inputFile)))) {
            XMLStreamReader reader = processor.getReader();
            shop = getShop("shop", "shops", reader);
        } catch (XMLStreamException | IOException | JAXBException e) {
            e.printStackTrace();
        }
        return shop;
    }

    private Shop getShop(
            String elementName, String parentElementName, XMLStreamReader reader)
            throws XMLStreamException, JAXBException {
        Shop shop = new Shop();
        while (reader.hasNext()) {
            int event = reader.next();
            if (XMLStreamConstants.START_ELEMENT == event && reader.getLocalName().equals(elementName)) {
                shop.setName(getText("name", reader));
                shop.setCategories(getCategories("category", "shop", reader));
            }
            if (XMLStreamConstants.END_ELEMENT == event && reader.getLocalName().equals(parentElementName)) break;
        }
        return shop;
    }

    private List<Category> getCategories(
            String elementName, String parentElementName, XMLStreamReader reader)
            throws XMLStreamException, JAXBException {
        List<Category> categories = new ArrayList<>();
        while (reader.hasNext()) {
            int event = reader.next();
            if (XMLStreamConstants.START_ELEMENT == event && reader.getLocalName().equals(elementName)) {
                Category category = new Category();
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
                SubCategory subCategory = new SubCategory();
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