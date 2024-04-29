public class Clothing extends Product{
    private String size;
    private String colour;

    public Clothing(String id, String pName, int noAvailableItems, double price, String size, String colour) {
        super(id, pName, noAvailableItems, price);
        this.size = size;
        this.colour = colour;
    }

    public String getSize() {
        return size;
    }


    public String getColour() {
        return colour;
    }


    public String toString() {
        System.out.println("Product ID : " + getId() + " " + "Product Name : " + getpName() + " " + "Available Items : "
                + getNoAvailableItems() + " " + "Prize : " + getPrice() + " " + "Size : " + size + "Colour : " + colour);
        return null;
    }

    @Override
    public void add(Product listOfProducts) {

    }
}
