package org.example.compulsory;

import javafx.util.Pair;

import java.net.URL;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

public class Document {
    private int ID;
    private String name;
    private String pathName;
    private Pair<TypesOfTags, String> tag;

    public Document(int ID, String name, String pathName, Pair<TypesOfTags, String> tag) {
        this.ID = ID;
        this.name = name;
        setPathName(pathName);
        this.tag = tag;
    }

    public Document() {

    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPathName(String pathName) {
        if (this.isPathValid(pathName))
            this.pathName = pathName;
    }

    public void setTag(Pair<TypesOfTags, String> tag) {
        this.tag = tag;
    }

    public int getID() {
        return this.ID;
    }

    public String getName() {
        return this.name;
    }

    public String getPath() {
        return this.pathName;
    }

    public Pair<TypesOfTags, String> getTag() {
        return this.tag;
    }

    boolean isURLValid(String URL) {
        try {
            new URL(URL).toURI();
            return true;
        } catch (Exception exception) {

            exception.printStackTrace();
            return false;
        }
    }

    public boolean isPathValid(String path) {

        try {

            Paths.get(path);

        } catch (InvalidPathException ex) {
            return false;
        }

        return true;
    }
}
