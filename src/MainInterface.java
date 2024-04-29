import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class MainInterface extends JFrame{
    private JButton shoppingCartBtn;
    private JComboBox<String> comboSelect;
    private JTable infoTable;
    private JPanel frame1;
    private JTextArea textArea;
    private JButton addtoC;

    private final List<Product> productLine = new ArrayList<Product>();
    private final List<Electronics> elecLine = new ArrayList<Electronics>();
    private final List<Clothing> clothLine = new ArrayList<Clothing>();
    private final ShoppingCart shoppingCart = new ShoppingCart();


    public MainInterface(){
        super("Westminster Shopping Center");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(frame1);
        this.setSize(500, 500);
        comboSelect.addItem("All");
        comboSelect.addItem("Electronics");
        comboSelect.addItem("Clothing");
        getProductsFromList();
        TabelConfig();

        infoTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if(infoTable.getSelectedRow() > -1){
                    int rowVal = infoTable.getSelectedRow();
                    String tempD= (String)infoTable.getValueAt(rowVal, 4);
                    String[] productData = tempD.split(",");
                    String productID = (String)infoTable.getValueAt(rowVal, 0);
                    int itemsCount = 0;
                    Product pTemp = findProductById(productID);
                    itemsCount = pTemp.getNoAvailableItems();
                    StringBuilder details = new StringBuilder();
                    details.append("Selected Products Details\n");
                    details.append("Product ID: ").append(infoTable.getValueAt(rowVal, 0)).append("\n");
                    details.append("Category: ").append(infoTable.getValueAt(rowVal, 2)).append("\n");
                    details.append("Name: ").append(infoTable.getValueAt(rowVal, 1)).append("\n");
                    details.append("Price: ").append(infoTable.getValueAt(rowVal, 3)).append("\n");

                    if ("Clothing".equals(infoTable.getValueAt(rowVal, 2))) {
                        details.append("Size: ").append(productData[0]).append("\n");
                        details.append("Color: ").append(productData[1]).append("\n");
                    } else if ("Electronics".equals(infoTable.getValueAt(rowVal, 2))) {
                        details.append("Brand: ").append(productData[0]).append("\n");
                        details.append("Warranty Period: ").append(productData[1]).append("\n");
                    }
                    details.append("Items Available: ").append(itemsCount);
                    textArea.setText(details.toString());
                    textArea.setCaretPosition(0); // Scroll to the top
                }
            }
        });

        comboSelect.addActionListener(e-> {
            String selectedCategory = (String) comboSelect.getSelectedItem();
            assert selectedCategory != null;
            if (selectedCategory.equals("Electronics")){
                TabelConfigElec();
            } else if (selectedCategory.equals("Clothing")) {
                TabelConfigCloth();
            }else{
                TabelConfig();
            }

        });



        shoppingCartBtn.addActionListener(e ->{
                try{
                    CartInterface ci = new CartInterface(shoppingCart);
                    ci.setVisible(true);
                }catch (Exception error){
                    System.out.print(error.getMessage());
                }
        });

        addtoC.addActionListener(e ->{
            int row = infoTable.getSelectedRow();
            if (row > -1) {
                String productId = infoTable.getValueAt(row, 0).toString();
                Product prod = findProductById(productId);
                if (prod != null) {
                    shoppingCart.addProducts(prod);
                    JOptionPane.showMessageDialog(this, "Product Added Successfully");
                }
            } else {
                JOptionPane.showMessageDialog(this, "No Product Selected!!");
            }
        });
    }

    public void TabelConfig(){
        String[] topic = {"Product ID" , "Name" , "Category" , "Price" , "Info"};
        String[] tempData = new String[5];
        DefaultTableModel infotableModel = new DefaultTableModel();
        infotableModel.addColumn("Product ID");
        infotableModel.addColumn("Name");
        infotableModel.addColumn("Category");
        infotableModel.addColumn("Price");
        infotableModel.addColumn("Info");

        infotableModel.addRow(topic);
        for(Product prdList : productLine){
            tempData[0] = prdList.getId();
            tempData[1] = prdList.getpName();
            tempData[2] = (prdList instanceof Electronics) ? "Electronics" : (prdList instanceof Clothing) ? "Clothing" : "Unknown";
            tempData[3] = String.valueOf(prdList.getPrice());
            if (prdList instanceof Electronics elec) {
                String data = elec.getBrand();
                data += ",";
                data += elec.getWarranty();
                tempData[4] = data;
            } else if (prdList instanceof Clothing cloth) {
                String data = cloth.getColour();
                data += ",";
                data += cloth.getSize();
                tempData[4] = data;
            }

            infotableModel.addRow(tempData);
        }
        infoTable.setModel(infotableModel);
    }

    public void TabelConfigCloth(){
        String[] topic = {"Product ID" , "Name" , "Category" , "Price" , "Info"};
        String[] tempData = new String[5];
        DefaultTableModel infotableModel = new DefaultTableModel();
        infotableModel.addColumn("Product ID");
        infotableModel.addColumn("Name");
        infotableModel.addColumn("Category");
        infotableModel.addColumn("Price");
        infotableModel.addColumn("Info");

        infotableModel.addRow(topic);
        for(Product prdList : clothLine){
            tempData[0] = prdList.getId();
            tempData[1] = prdList.getpName();
            tempData[2] = "Clothing";
            tempData[3] = String.valueOf(prdList.getPrice());
            if (prdList instanceof Clothing cloth) {
                String data = cloth.getColour();
                data += ",";
                data += cloth.getSize();
                tempData[4] = data;
            }

            infotableModel.addRow(tempData);
        }
        infoTable.setModel(infotableModel);
    }

    public void TabelConfigElec(){
        String[] topic = {"Product ID" , "Name" , "Category" , "Price" , "Info"};
        String[] tempData = new String[5];
        DefaultTableModel infotableModel = new DefaultTableModel();
        infotableModel.addColumn("Product ID");
        infotableModel.addColumn("Name");
        infotableModel.addColumn("Category");
        infotableModel.addColumn("Price");
        infotableModel.addColumn("Info");

        infotableModel.addRow(topic);
        for(Product prdList : elecLine){
            tempData[0] = prdList.getId();
            tempData[1] = prdList.getpName();
            tempData[2] = "Electronics";
            tempData[3] = String.valueOf(prdList.getPrice());
            if (prdList instanceof Electronics elec) {
                String data = elec.getBrand();
                data += ",";
                data += elec.getWarranty();
                tempData[4] = data;
            }

            infotableModel.addRow(tempData);
        }
        infoTable.setModel(infotableModel);
    }

    public static void main(String[] args){

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainInterface().setVisible(true);
            }
        });
    }

    public void getProductsFromList(){

        try  {
            BufferedReader scanner = new BufferedReader(new FileReader("Data of product.txt"));
            String dataLine;
            while ((dataLine = scanner.readLine())!= null) {
                String[] data = dataLine.split(",");
                if (data.length < 7) {
                    System.out.println("Error: Insufficient data in line.");
                    continue;
                }

                String productId = data[0];
                String productName = data[1];
                int availableItems = Integer.parseInt(data[2]);
                double price = Double.parseDouble(data[3]);
                String type = data[6];
                System.out.println(productId);
                if ("Electronic".equals(type)) {
                    String brand = data[4];
                    String warranty = data[5];
                    Electronics ec = new Electronics(productId, productName, availableItems, price, brand, warranty);
                    productLine.add(ec);
                    elecLine.add(ec);
                } else if ("Clothing".equals(type)) {
                    String size = data[4];
                    String color = data[5];
                    Clothing cc = new Clothing(productId, productName, availableItems, price, size, color);
                    productLine.add(cc);
                    clothLine.add(cc);
                } else {
                    System.out.println("Error: Unknown product type in line.");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No existing data found. Starting with an empty product list.");
        } catch (NumberFormatException e) {
            System.out.println("Error: Number format is incorrect.");
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }

    public Product findProductById(String productId) {
        for (Product product : productLine) {
            if (product.getId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

}
