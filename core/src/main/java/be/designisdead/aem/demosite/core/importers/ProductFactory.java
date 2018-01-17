package be.designisdead.aem.demosite.core.importers;

import java.util.function.Function;

/**
 * Created by j.peeters on 06/10/2017.
 */
public class ProductFactory {


    private ProductFactory(){
    }

    public static ProductFactory getInstance(){
        return new ProductFactory();
    }

    public static Function<String, Product> create = (line) -> {
        String[] p = line.split(";");
        Product.ProductBuilder productBuilder = new Product.ProductBuilder();

        if(p.length > 0 && !p[0].isEmpty()) {
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
}
