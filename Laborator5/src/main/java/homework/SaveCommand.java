package homework;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.compulsory.Catalog;

import java.io.File;
import java.io.IOException;

public class SaveCommand implements Command {
    public SaveCommand(Catalog catalog) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("output.json"), catalog);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
