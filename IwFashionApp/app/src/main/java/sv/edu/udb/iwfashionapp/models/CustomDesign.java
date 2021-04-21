package sv.edu.udb.iwfashionapp.models;

import java.util.List;

public class CustomDesign {

    public CustomDesign(List<item> results) {
        this.results = results;
    }

    public List<item> getResults() {
        return results;
    }

    public void setResults(List<item> results) {
        this.results = results;
    }

    private List<CustomDesign.item> results;


    public static class item {
        private int id_custom_design;

        public item(String name, String description, String url_img) {
            this.name = name;
            this.description = description;
            this.url_img = url_img;
        }

        private String name;
        private String description;
        private String url_img;


        public int getId_custom_design() {
            return id_custom_design;
        }

        public void setId_custom_design(int id_custom_design) {
            this.id_custom_design = id_custom_design;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUrl_img() {
            return url_img;
        }

        public void setUrl_img(String url_img) {
            this.url_img = url_img;
        }


    }

    }

