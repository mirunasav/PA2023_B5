package org.example.compulsory;

import javafx.util.Pair;

import java.net.URL;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.Map;

import static org.example.compulsory.Utilities.isPathValid;

public class Document {
    private int ID;
    private String name;
    private String pathName;
    private Map<TypesOfTags, String> tag;

    public Document(int ID, String name, String pathName, Map<TypesOfTags, String> tag) {
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
        if (isPathValid(pathName))
            this.pathName = pathName;
    }

    public void setTag(Map<TypesOfTags, String> tag) {
        this.tag = tag;
    }

    public int getID() {
        return this.ID;
    }

    public String getName() {
        return this.name;
    }

    public String getPathName() {
        return this.pathName;
    }

    public Map<TypesOfTags, String> getTag() {
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

    public String toString() {
        String string = "ID: " +
                this.getID() +
                " name: " +
                this.getName() +
                " Pathname: " +
                this.getPathName() +
                " Tag: " +
                this.getTag().toString();
        return string;
    }


}
