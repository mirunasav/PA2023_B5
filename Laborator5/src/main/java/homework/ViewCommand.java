package homework;

import desktopAPI.DesktopAPI;
import exceptions.DesktopNotSupportedException;
import org.example.compulsory.Catalog;
import org.example.compulsory.Document;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.example.compulsory.CatalogManager.findDocument;

public class ViewCommand implements Command {
    public ViewCommand(Catalog catalog, String name) throws DesktopNotSupportedException, IOException, URISyntaxException {
        Document documentToView = findDocument(catalog,name);
        if(documentToView== null)
            throw new FileNotFoundException();
        else {
            if(!Desktop.isDesktopSupported())
                throw new DesktopNotSupportedException();

            Desktop desktop = Desktop.getDesktop();
            if(documentToView.getIsLocal()) //e un filePath
            {
                desktop.open(new File(documentToView.getPathName()));
            }
            else
                DesktopAPI.browse(new URI(documentToView.getPathName()));
        }

    }
}
