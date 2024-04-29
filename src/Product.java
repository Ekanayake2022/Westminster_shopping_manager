public abstract class Product {
    private String id;
    private String pName;
    private int noAvailableItems;
    private double price;

    public Product(String id, String pName, int noAvailableItems, double price) {
        this.id = id;
        this.pName = pName;
        this.noAvailableItems = noAvailableItems;
        this.price = price;
    }

    public String getId() {
        return this.id;
    }


    public String getpName() {
        return this.pName;
    }

    public int getNoAvailableItems() {
        return this.noAvailableItems;
    }

    public double getPrice() {
        return this.price;
    }

    public abstract void add(Product listOfProducts);
}

