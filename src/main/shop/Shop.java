package main.shop;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Shop {
    @XmlElement(name = "name")
    private String shopName;
    private List categories;

    public String getName() {
        return shopName;
    }

    public void setName(String name) {
        this.shopName = name;
    }

    public List getCategories() {
        return categories;
    }
    @XmlElement( name = "category" )
    public void setCategories(List categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "name='" + shopName + '\'' +
                ", categories=" + categories +
                '}';
    }
}
