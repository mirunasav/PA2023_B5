package homework;

import org.example.compulsory.Catalog;
import org.example.compulsory.Document;

public class AddComand implements Command {
    public AddComand(Catalog catalog, Document document){
        catalog.getEntries().add(document);
    }
}
