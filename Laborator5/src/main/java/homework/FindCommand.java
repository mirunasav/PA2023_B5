package homework;

import exceptions.DocumentNotFoundException;
import org.example.compulsory.Catalog;
import org.example.compulsory.Document;

public class FindCommand implements Command {
    Document document;
    public FindCommand(Catalog catalog, String name) throws DocumentNotFoundException {
      Document foundDocument  = catalog.getEntries().stream().filter(document -> document.getName().equals(name)).findFirst().orElse(null);
      if(foundDocument == null){
          throw new DocumentNotFoundException();
      }
      else
          this.document = foundDocument;
    }

    public Document getDocument() {
        return document;
    }
}
