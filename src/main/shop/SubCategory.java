package main.shop;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class SubCategory<T> {
    @XmlElement
    private String name;
    private List<T> products;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<T> getProducts() {
        return products;
    }

    @XmlElement( name = "product" )
    public void setProducts(List<T> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "SubCategory{" +
                "name='" + name + '\'' +
                ", products=" + products +
                '}';
    }
}
