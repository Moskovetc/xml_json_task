package main.shop;

import javax.xml.bind.annotation.XmlAccessorType;
import java.util.List;
@XmlAccessorType
public class SubCategory<T> {
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

    public void setProducts(List<T> products) {
        this.products = products;
    }
}