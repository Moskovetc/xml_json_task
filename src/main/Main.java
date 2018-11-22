package main;

import main.jsonmanager.JSONManager;
import main.shop.Shop;
import main.xmlmanager.XMLManager;

public class Main {
    public static void main(String[] args) {
        String path = "example.xml";
        XMLManager xmlManager = new XMLManager();
        JSONManager jsonManager = new JSONManager();
        Shop shop = xmlManager.unmarshall(path);
        System.out.println("Unmarshall XML:\n" + shop);
        xmlManager.marshall("out.xml", shop);
        jsonManager.marshall("out.json", shop);
        shop = jsonManager.unmarshall("example.json");
        System.out.println("Unmarshall JSON:\n" + shop);
    }
}
