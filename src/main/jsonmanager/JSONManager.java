package main.jsonmanager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.shop.Shop;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class JSONManager {
    public void marshall(String outputFile, Shop shop) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        String json = gson.toJson(shop);
        try (PrintWriter out = new PrintWriter(outputFile)) {
            out.println(json);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Shop unmarshall(String inputFile) {
        Shop shop = new Shop();
        try (Stream<String> fileStream = Files.lines(Paths.get(inputFile), StandardCharsets.UTF_8)) {
            StringBuilder builder = new StringBuilder();
            fileStream.forEach(builder::append);
            String json = builder.toString();
            shop = new Gson().fromJson(json, Shop.class);
            return shop;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return shop;
    }
}
