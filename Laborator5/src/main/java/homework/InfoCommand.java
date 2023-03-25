package homework;

import org.apache.log4j.varia.NullAppender;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.example.compulsory.Catalog;
import org.xml.sax.SAXException;
import org.apache.log4j.BasicConfigurator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Member;
import java.util.Map;

public class InfoCommand implements Command {
    private Catalog catalog;

    public InfoCommand(Catalog catalog) throws IOException, TikaException, SAXException {
        this.catalog = catalog;
        this.extractMetadata();
    }

    private void extractMetadata() throws IOException, TikaException, SAXException {
        File file = new File("output.json");

        BasicConfigurator.configure(new NullAppender());

        Parser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        FileInputStream inputStream = new FileInputStream(file);
        ParseContext context = new ParseContext();

        parser.parse(inputStream, handler, metadata, context);
        System.out.println(handler.toString());
        String[] metadataNames = metadata.names();

        for (String name : metadataNames) {
            System.out.println(name + ": " + metadata.get(name));

        }
    }
}
