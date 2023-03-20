package org.example.compulsory;

import javafx.util.Pair;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import static org.example.compulsory.CatalogManager.add;

public class Compulsory {
    public static void main(String[] args) {
        Catalog catalog = new Catalog();
        String userDirectory = new File("").getAbsolutePath();

        Map<TypesOfTags, String> map = new HashMap<>();
        map.put(TypesOfTags.YEAR, "2002");

        CatalogManager.add(catalog,new Document(1, "document1", userDirectory,map ));
        CatalogManager.add(catalog,new Document(2, "document2", userDirectory, map));
        CatalogManager.add(catalog,new Document(3, "document3", userDirectory, map));

       CatalogManager.save(catalog);

        Catalog copyOfCatalog = new Catalog();
        CatalogManager.load(catalog, "output.json");
    }
}
