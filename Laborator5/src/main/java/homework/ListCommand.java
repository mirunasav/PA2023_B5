package homework;

import org.example.compulsory.Catalog;

public class ListCommand implements Command {
    public ListCommand(Catalog catalog) {
        catalog.getEntries().forEach(System.out::println);
    }
}
