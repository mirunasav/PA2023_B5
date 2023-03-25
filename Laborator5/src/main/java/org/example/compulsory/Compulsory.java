package org.example.compulsory;

import homework.Command;
import javafx.util.Pair;
import org.apache.poi.ss.formula.functions.T;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import static org.example.compulsory.CatalogManager.add;
import static org.example.compulsory.Utilities.minimumColoring;

public class Compulsory {
    public static void main(String[] args) {
        Catalog catalog = new Catalog();
        String userDirectory = new File("").getAbsolutePath();

        Map<TypesOfTags, String> map = new HashMap<>();
        map.put(TypesOfTags.YEAR, "2002");

        try {
            CatalogManager.add(catalog, new Document(1, "document1", userDirectory, new HashMap<TypesOfTags, String>() {{
                put(TypesOfTags.YEAR, "2002");
                put(TypesOfTags.AUTHOR, "J.K.Rowling");
            }}));
            CatalogManager.add(catalog, new Document(2, "document2", "https://docs.oracle.com/javase/tutorial/essential/io/pathOps.html", new HashMap<TypesOfTags, String>() {{
                put(TypesOfTags.YEAR, "2003");
                put(TypesOfTags.AUTHOR, "J.K.Rowling");
            }}));
            CatalogManager.add(catalog, new Document(3, "document3", userDirectory,new HashMap<TypesOfTags, String>() {{
                put(TypesOfTags.YEAR, "2002");
                put(TypesOfTags.AUTHOR, "Fitzgerald");
            }}));
            CatalogManager.add(catalog, new Document(3, "document4", userDirectory,new HashMap<TypesOfTags, String>() {{
                put(TypesOfTags.YEAR, "2009");
                put(TypesOfTags.AUTHOR, "Fitzgerald");
            }}));

        } catch (FileNotFoundException exception) {
            System.out.println("File not found!");
        }


        CatalogManager.save(catalog);

        Catalog copyOfCatalog = new Catalog();
        CatalogManager.load(catalog, "output.json");

        CatalogManager.listDocuments(catalog);
        CatalogManager.findDocument(catalog, "document1");
        //   CatalogManager.viewDocument(catalog, "document2");

        CatalogManager.generateReport(catalog);
        // CatalogManager.getInformation(catalog);
        minimumColoring(catalog);

    }
}
