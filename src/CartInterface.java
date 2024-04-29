import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartInterface extends JFrame {
    private JTable tableCart;
    private JLabel total;
    private JLabel threeItemDis;
    private JLabel FInalTotal;
    private JPanel MainPanel;
    private DefaultTableModel infoModel;

    private final HashMap<Product,Integer> data = new HashMap<Product , Integer>();

    public CartInterface(ShoppingCart shoppingCart){


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(MainPanel);
        this.setSize(400, 400);
        DefaultTableModel infoModel = new DefaultTableModel();
        infoModel.addColumn("Product");
        infoModel.addColumn("Quantity");
        infoModel.addColumn("Price");
        String[] topic = {"Product" , "Quantity" , "Price"};
        infoModel.addRow(topic);

        String details = null;
        System.out.println(shoppingCart.getListOfProducts().size());
        for (Product product : shoppingCart.getListOfProducts()) {
            int count = 0;
            for(Product tempProd: shoppingCart.getListOfProducts()){
                if(product.getId().equals(tempProd.getId())) {
                    count++;
                }
            }
            details = product.getId() + "\n" +product.getpName()+ "\n";
            if(product instanceof Electronics electronics){
                details += electronics.getBrand() + " , " + electronics.getWarranty();
            }else if (product instanceof Clothing cloth){
                details += cloth.getSize() +" , "+ cloth.getColour();

            }
            System.out.println(details);
            data.put(product , count);
        }

        for (Map.Entry<Product, Integer> entry : data.entrySet()) {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();
            double price = product.getPrice();
            double total = price * quantity;


            infoModel.addRow(new Object[]{
                    details,
                    quantity,
                    String.format("%.2f", total)
            });
        }
        tableCart.setModel(infoModel);
        int e_Cnt = 0, c_cnt = 0;
        double finalTotal = 0.0;
        for (Map.Entry<Product, Integer> entry : data.entrySet()) {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();

            double price = product.getPrice();
            double total = price * quantity;

            finalTotal += total;

            if(product instanceof Electronics){
                e_Cnt+= quantity;
            }else if(product instanceof Clothing){
                c_cnt+= quantity;
            }
        }


        double categDis;
        if(e_Cnt >= 3 || c_cnt >= 3) { categDis = finalTotal * 0.2; }
        else{ categDis =  0.0;}

        finalTotal -= categDis;

        String Text1 = "Total: " + String.format("%.2f €", finalTotal + categDis);
        String Text2 = "Three Items in same Category Discount (20%): - " + String.format("%.2f €", categDis);

        String Text3 = "Final Total: " + String.format("%.2f €", finalTotal);

        total.setText(Text1);
        threeItemDis.setText(Text2);
        FInalTotal.setText(Text3);



    }
}
