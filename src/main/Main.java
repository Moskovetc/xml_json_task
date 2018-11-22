package main;

import main.shop.Shop;
import main.xmlmanager.XMLManager;

public class Main {
    public static void main(String[] args) {
        String path = "C:\\Users\\Leonid_Moskovets\\Desktop\\workspace\\xml_json_task\\examples\\example.xml";
        XMLManager xmlManager = new XMLManager();
        Shop shop = xmlManager.unmarshal(path);
        System.out.println(shop);
        xmlManager.marshal("out.xml", shop);
    }
}
