package be.designisdead.aem.demosite.core.importers;

import com.adobe.cq.commerce.pim.api.ProductImporter;
import com.adobe.cq.commerce.pim.common.AbstractProductImporter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component(
        property = {
                Constants.SERVICE_DESCRIPTION + "=Product importer for a custom catalog",
                Constants.SERVICE_ID + "=Custom catalog importer",
                Constants.SERVICE_VENDOR + "=Design Is Dead",
                "commerceProvider=Joeri Peeters"
        })
public class CustomProductImporter extends AbstractProductImporter implements ProductImporter {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Reference
    private SlingRepository repository;

    private Session session;

    @Override
    protected boolean validateInput(SlingHttpServletRequest slingHttpServletRequest,
                                    SlingHttpServletResponse slingHttpServletResponse) throws IOException {
        return true;
    }

    @Override
    protected void doImport(ResourceResolver resourceResolver, Node node, boolean b)
            throws RepositoryException, IOException {
        logger.info("Start importing products");

        // Assemble a list of products from the source
        List<Product> products = processProductFile("C:/Users/j.peeters/Documents/Projects/aem_commerce/test_import.csv");

        session = repository.login(new SimpleCredentials("admin", "admin".toCharArray())); // TODO use separate account
        // Write products to repository
        products.stream().forEach(prod -> {
            try {
                toJcrProduct(prod);
            } catch (RepositoryException e) {
                session.logout();
            } finally {
            }
        });
        session.save();
        session.logout();
        logger.info("Done importing products...");
    }

    private void toJcrProduct(Product product) throws RepositoryException {
        Node productNode = createProduct("/etc/commerce/products/DesignIsDead/product_" + product.getId(), session);
        productNode.setProperty("id", product.getId());
    }

    private List<Product> processProductFile(String pathUri) {
        List<Product> inputList = new ArrayList<Product>();

        Path productsFile = Paths.get(pathUri);

        try (Stream<String> stream = Files.lines(productsFile)) {
            inputList = stream
                    //.peek(System.out::println)
                    .skip(1) // don't parse the heaader of the csv file
                    .map(mapToProduct)
                    .filter(product -> product != null)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputList;
    }

    private Function<String, Product> mapToProduct = (line) -> {
        String[] p = line.split(";");
        Product.ProductBuilder productBuilder = new Product.ProductBuilder();

        if (p.length > 0 && !p[0].isEmpty()) {
            productBuilder.withId(new Long(p[0]));
            productBuilder.withProductNameNL(p[6]);
            productBuilder.withProductNameFR(p[7]);
            productBuilder.withPriceBE(p[11]);
            productBuilder.withPriceLUX(p[12]);
            return productBuilder.build();
        }

        return null;
        // not a valid product? Should we forsee some error handling to notify business in case of failed product imports
    };
/*
    public static void main(String [] args){
        CustomProductImporter importer = new CustomProductImporter();
        List<Product> products = importer.processProductFile("C:/Users/j.peeters/Documents/Projects/bla/test_import.csv");

        products.stream().forEach(System.out::println);



    }*/
}
