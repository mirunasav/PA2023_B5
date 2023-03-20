package org.example.compulsory;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Catalog {
    private List<Document> entries;

    public Catalog() {
        this.entries = new ArrayList<>();
    }

    public List<Document> getEntries() {
        return entries;
    }

    public void setEntries(List<Document> entries) {
        this.entries = entries;
    }

    public void add(Document newDocument) {
        this.entries.add(newDocument);
    }

    public void save() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("output.json"), this.entries);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void load() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Document[] newDocuments = mapper.readValue(Paths.get("output.json").toFile(), Document[].class);
            Arrays.stream(newDocuments).forEach(this::add);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }
}
