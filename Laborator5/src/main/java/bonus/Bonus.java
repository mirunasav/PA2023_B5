package bonus;

import com.github.javafaker.Faker;
import org.example.compulsory.Catalog;
import org.example.compulsory.CatalogManager;

import static org.example.compulsory.Utilities.createRandomCatalog;
import static org.example.compulsory.Utilities.minimumColoring;

public class Bonus {
    public static void main(String[] args) {
        Catalog catalog = createRandomCatalog();
       // CatalogManager.generateReport(catalog);
        minimumColoring(catalog);
    }
}
