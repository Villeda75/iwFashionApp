package sv.edu.udb.iwfashionapp.models;

import java.util.List;

public class Producto {


    public List<item> getResults() {
        return results;
    }

    public void setResults(List<item> results) {
        this.results = results;
    }

    private List<item> results;
    public static class item{

    private String id_product;
    private String slug;
    private int stock;
    private int visible;
    private String color;
    private String size;
    private String gender;
    private String Brand;
    private String product_type;
    private String description;
    private Double sales_price;
    private Double discount_price;
    private String sub_category;
    private String url_img;


        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public int getVisible() {
            return visible;
        }

        public void setVisible(int visible) {
            this.visible = visible;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getBrand() {
            return Brand;
        }

        public void setBrand(String brand) {
            Brand = brand;
        }

        public String getProduct_type() {
            return product_type;
        }

        public void setProduct_type(String product_type) {
            this.product_type = product_type;
        }

        public String getSub_category() {
            return sub_category;
        }

        public void setSub_category(String sub_category) {
            this.sub_category = sub_category;
        }



        public String getUrl_img() {
            return url_img;
        }

        public void setUrl_img(String url_img) {
            this.url_img = url_img;
        }


        public String getId_product() {
            return id_product;
        }

        public void setId_product(String id_product) {
            this.id_product = id_product;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Double getSales_price() {
            return sales_price;
        }

        public void setSales_price(Double sales_price) {
            this.sales_price = sales_price;
        }

        public Double getDiscount_price() {
            return discount_price;
        }

        public void setDiscount_price(Double discount_price) {
            this.discount_price = discount_price;
        }



    }



}
