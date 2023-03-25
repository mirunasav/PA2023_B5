package homework;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.*;
import org.example.compulsory.Catalog;
import org.example.compulsory.Document;

import javax.print.DocFlavor;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ReportCommand implements Command{
    private static Configuration configuration;
    private Catalog catalog;

    public ReportCommand (Catalog catalog){
        try {
            this.setConfiguration();
            this.catalog = catalog;
            this.processTemplate();
        }
        catch (IOException exception){
            exception.printStackTrace();
        }
        catch (TemplateException exception){
            System.out.println("Template exception!");
        }

    }
    private void setConfiguration() throws IOException {
        configuration = new Configuration();
        configuration.setTemplateLoader(new FileTemplateLoader(new File("/home/mrnk/IdeaProjects/PA2023_B5/Laborator5/src/main/java/org/example/compulsory")));
        configuration.setIncompatibleImprovements(new Version(2, 3, 20));
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }
    private void processTemplate() throws IOException, TemplateException {
        Map<String, Object> input = new HashMap<>();
        input.put("documents", this.catalog.getEntries());


       /* List<Map<String, Object>> documentData = new ArrayList<>();
        for (Document doc : this.catalog.getEntries()) {
            Map<String, Object> docInfo = new HashMap<>();
            docInfo.put("name", doc.getName());
            docInfo.put("pathname", doc.getPathName());
            docInfo.put("tag", doc.getTag());
            documentData.add(docInfo);
        }
        input.put("documentData", documentData);*/
        Template template = configuration.getTemplate("template2.ftl");

      //  Writer consoleWriter = new OutputStreamWriter(System.out);
       // template.process(input, consoleWriter);
        try (Writer fileWriter = new FileWriter(new File("output.html"))) {
            template.process(input, fileWriter);
        }
    }

    public static Configuration getConfiguration() {
        return configuration;
    }

    public static void setConfiguration(Configuration configuration) {
        ReportCommand.configuration = configuration;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }
}
