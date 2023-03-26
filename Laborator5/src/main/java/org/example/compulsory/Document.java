package org.example.compulsory;

import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.example.compulsory.Utilities.isPathValid;

public class Document {
    private int ID;
    private String name;
    private String pathName;
    private Map<TypesOfTags, String> tags;

    private boolean isLocal;

    public Document(int ID, String name, String pathName, Map<TypesOfTags, String> tags) throws FileNotFoundException {
        this.ID = ID;
        this.name = name;
        this.setPathName(pathName);
        this.tags= tags;
    }

    public Document() {

    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPathName(String pathName) throws FileNotFoundException {
        try {
            new URL(pathName).toURI();
            this.pathName = pathName;
            this.isLocal = false;
        } catch (Exception exception) {
            File file = new File(pathName);
            if (file.exists()) {
                this.pathName = pathName;
                this.isLocal = true;
            } //else throw new FileNotFoundException();
        }
    }


    public int getID() {
        return this.ID;
    }

    public boolean getIsLocal()
    {
        return this.isLocal;
    }

    public String getName() {
        return this.name;
    }

    public String getPathName() {
        return this.pathName;
    }

    public Map<TypesOfTags, String> getTags() {
        return this.tags;
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
        return "ID: " +
                this.getID() +
                " name: " +
                this.getName() +
                " Pathname: " +
                this.getPathName() +
                " Tag: " +
                this.getTags().toString();
    }


}
