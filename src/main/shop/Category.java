package main.shop;

import java.util.List;

public class Category<T> {
    private String name;
    private List<T> subCategories;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<T> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<T> subCategories) {
        this.subCategories = subCategories;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", subCategories=" + subCategories +
                '}';
    }
}
