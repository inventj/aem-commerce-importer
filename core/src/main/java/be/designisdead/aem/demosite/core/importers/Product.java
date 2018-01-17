package be.designisdead.aem.demosite.core.importers;

/**
 * Created by j.peeters on 05/10/2017.
 */
public class Product {

    private long id;
    //private Category category;
    private String priceBE;
    private String priceLUX;
    private String imageId;
    private String productNameNL;
    private String productNameFR;

    private Product(ProductBuilder pb){
        this.id = pb.id;
        this.priceBE = pb.priceBE;
        this.priceLUX = pb.priceLUX;
        this.imageId = pb.imageId;
        this.productNameNL = pb.productNameNL;
        this.productNameFR = pb.productNameFR;
    }

    public long getId() {
        return id;
    }

    public String getPriceBE() {
        return priceBE;
    }

    public String getPriceLUX() {
        return priceLUX;
    }

    public String getImageId() {
        return imageId;
    }

    public String getProductNameNL() {
        return productNameNL;
    }

    public String getProductNameFR() {
        return productNameFR;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (id != product.id) return false;
        if (productNameNL != null ? !productNameNL.equals(product.productNameNL) : product.productNameNL != null)
            return false;
        return productNameFR != null ? productNameFR.equals(product.productNameFR) : product.productNameFR == null;
    }

    @Override
    public int hashCode() {
        int result;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + (imageId != null ? imageId.hashCode() : 0);
        result = 31 * result + (productNameNL != null ? productNameNL.hashCode() : 0);
        result = 31 * result + (productNameFR != null ? productNameFR.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", priceBE=" + priceBE +
                ", priceLUX=" + priceLUX +
                ", imageId='" + imageId + '\'' +
                ", productNameNL='" + productNameNL + '\'' +
                ", productNameFR='" + productNameFR + '\'' +
                '}';
    }

    public static class ProductBuilder {

        private long id;
        private String priceBE;
        private String priceLUX;
        private String imageId;
        private String productNameNL;
        private String productNameFR;

        public ProductBuilder(){
        }

        public ProductBuilder(long id, String priceBE, String priceLUX, String imageId, String productNameNL,
                              String productNameFR){
            this.id = id;
            this.priceBE = priceBE;
            this.priceLUX = priceLUX;
            this.imageId = imageId;
            this.productNameNL = productNameNL;
            this.productNameFR = productNameFR;
        }

        public ProductBuilder withId(long id) {
            this.id = id;
            return this;
        }

        public ProductBuilder withPriceBE(String priceBE) {
            this.priceBE = priceBE;
            return this;
        }

        public ProductBuilder withPriceLUX(String priceLUX) {
            this.priceLUX = priceLUX;
            return this;
        }

        public ProductBuilder withImageId(String priceLUX) {
            this.imageId = imageId;
            return this;
        }

        public ProductBuilder withProductNameNL(String productNameNL) {
            this.productNameNL = productNameNL;
            return this;
        }

        public ProductBuilder withProductNameFR(String productNameFR) {
            this.productNameFR = productNameFR;
            return this;
        }

        public Product build(){
            return new Product(this);
        }
    }
}
