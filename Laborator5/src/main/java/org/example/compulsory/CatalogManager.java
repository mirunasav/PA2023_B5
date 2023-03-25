package org.example.compulsory;

import exceptions.DesktopNotSupportedException;
import exceptions.DocumentNotFoundException;
import homework.*;

import java.io.IOException;
import java.net.URISyntaxException;

public class CatalogManager {
    public static void add(Catalog catalog, Document document) {
        AddComand add = new AddComand(catalog, document);
    }

    public static void save(Catalog catalog) {
        SaveCommand save = new SaveCommand(catalog);
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
        LoadCommand load = new LoadCommand(catalog, fileName);
    }

    public static void listDocuments(Catalog catalog) {
        ListCommand list = new ListCommand(catalog);
    }

    public static String toString(Catalog catalog) {
        StringBuilder string = new StringBuilder();
        string.append("Catalogul contine: ");
        for (Document document : catalog.getEntries()) {
            string.append(document.toString());
        }
        return string.toString();
    }

    public static Document findDocument(Catalog catalog, String name) {
        try {
            FindCommand find = new FindCommand(catalog, name);
            return find.getDocument();
        } catch (DocumentNotFoundException exception) {
            System.out.println("Documentul cu numele " + name + " nu exista!");
            return null;
        }
    }

    public static void viewDocument(Catalog catalog, String name) {
        try {
            ViewCommand view = new ViewCommand(catalog, name);
        } catch (IOException exception) {
            System.out.println("Could not open file!");
        } catch (DesktopNotSupportedException exception) {
            System.out.println("Desktop not supported!");
        } catch (URISyntaxException exception) {
            System.out.println("URI syntax is not right!");
        }

    }

    public static void generateReport(Catalog catalog) {
        ReportCommand report = new ReportCommand(catalog);
    }

    public static void getInformation(Catalog catalog) {
        try {
            InfoCommand info = new InfoCommand(catalog);
        } catch (Exception exception) {
            System.out.println("Exceptie la infoCommand");
        }

    }

}
