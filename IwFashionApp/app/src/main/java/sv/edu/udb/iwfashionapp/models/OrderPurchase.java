package sv.edu.udb.iwfashionapp.models;

import java.util.ArrayList;
import java.util.List;

public class OrderPurchase {


    public OrderPurchase(String userEmail, double total, ArrayList<item> items) {
        this.userEmail = userEmail;
        this.total = total;
        this.items = items;
    }

    private String userEmail;
    private double total;
    private ArrayList<OrderPurchase.item> items;

    public void setItems(ArrayList<item> items) {
        this.items = items;
    }



    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<item> getItems() {
        return items;
    }



    public static class item
    {
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCant() {
            return cant;
        }

        public void setCant(int cant) {
            this.cant = cant;
        }

        public item(int id, int cant) {
            this.id = id;
            this.cant = cant;
        }

        private int id;
        private int cant;
    }
}
