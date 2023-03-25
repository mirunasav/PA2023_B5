package homework;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.compulsory.Catalog;

import java.nio.file.Paths;

import static org.example.compulsory.Utilities.isPathValid;

public class LoadCommand implements Command {
    public LoadCommand(Catalog catalog, String fileName) {
        if (!isPathValid(fileName))
            return;
        try {
            ObjectMapper mapper = new ObjectMapper();
            catalog = mapper.readValue(Paths.get(fileName).toFile(), Catalog.class);
            catalog.getEntries().forEach(System.out::println);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
