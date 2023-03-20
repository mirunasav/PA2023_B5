package org.example.compulsory;

import javafx.util.Pair;

import java.io.File;

public class Compulsory {
    public static void main(String[] args) {
        Catalog catalog = new Catalog();
        String userDirectory = new File("").getAbsolutePath();
        catalog.add(new Document(1, "document1", userDirectory, new Pair<>(TypesOfTags.YEAR, "2002")));
        catalog.add(new Document(2, "document2", userDirectory, new Pair<>(TypesOfTags.AUTHOR, "Anton Matei")));
        catalog.add(new Document(3, "document3", userDirectory, new Pair<>(TypesOfTags.PUBLISHING_DATE, "12/02/2009")));
        catalog.save();

        Catalog copyOfCatalog = new Catalog();
        copyOfCatalog.load();
    }
}
