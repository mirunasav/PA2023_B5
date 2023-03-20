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

}
