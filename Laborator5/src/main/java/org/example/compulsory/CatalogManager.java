package org.example.compulsory;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.shape.Path;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static org.example.compulsory.Utilities.isPathValid;

public class CatalogManager {
    public static void add(Catalog catalog, Document document) {
        catalog.getEntries().add(document);
    }

    public static void save(Catalog catalog) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("output.json"), catalog);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void load(Catalog catalog, String fileName) {
//        try {
//            // create object mapper instance
//            ObjectMapper mapper = new ObjectMapper();
//
//            // convert JSON file to map
//            Map<?, ?> map = mapper.readValue(Paths.get("output.json").toFile(), Map.class);
//
//            // print map entries
//            for (Map.Entry<?, ?> entry : map.entrySet()) {
//                System.out.println(entry.getKey() + "=" + entry.getValue());
//            }
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
        if (!isPathValid(fileName))
            return;
        try {
            ObjectMapper mapper = new ObjectMapper();
            catalog = mapper.readValue(Paths.get(fileName).toFile(), Catalog.class);
            catalog.getEntries().forEach(System.out::println);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
