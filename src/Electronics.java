public class Electronics extends Product{
    private String brand;
    private String warranty;

    public Electronics(String id, String pName, int noAvailableItems, double price,String brand, String warranty) {
        super(id, pName, noAvailableItems, price);
        this.brand = brand;
        this.warranty= warranty;
    }

    public String getBrand() {
        return brand;
    }

    public String getWarranty() {
        return warranty;
    }

    public String toString(){
        System.out.println("product ID : " + getId() + " " + "Product Name : " + getpName() + " " + "Availabale Items : " + getNoAvailableItems() + " "
                + "Price : " + getPrice() + " " + "Brand : " + brand + " " + "Warranty : " + warranty);
        return null;
    }

    @Override
    public void add(Product listOfProducts) {

    }
}

