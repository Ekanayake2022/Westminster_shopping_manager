import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<Product> listOfProducts = new ArrayList();


    public void addProducts(Product prob) {
        listOfProducts.add(prob);
    }

    public void removeProducts() {
        this.listOfProducts.remove(this.listOfProducts);
    }

    public double totalCost() {
        double totcost = 0.0;

        for(int i = 0; i < this.listOfProducts.size(); ++i) {
            totcost += (double)this.listOfProducts.size();
        }

        return totcost;
    }

    public List<Product> getListOfProducts() {
        return listOfProducts;
    }

    public void setListOfProducts(ArrayList listOfProducts) {
        this.listOfProducts = listOfProducts;
    }

    public boolean findProductFromID(String id) {
        return false;
    }
}
