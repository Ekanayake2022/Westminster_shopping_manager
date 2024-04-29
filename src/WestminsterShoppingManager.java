
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class WestminsterShoppingManager implements ShoppingManager {
    public static List<Product> productList;
    //public List<Product>getProductList;

    public WestminsterShoppingManager() {
        productList = new ArrayList<>();
        loadProducts();
    }
    public void systemManager() {
        Scanner reader = new Scanner(System.in);
        displayMenu();
        System.out.println("Enter your choice : ");
        int choice = reader.nextInt();
        switch (choice) {
            case 1:
                addProduct();
                saveFile();
                systemManager();
                break;
            case 2:
                deleteProduct();
                saveFile();
                systemManager();
                break;
            case 3:
                listProduct();
                systemManager();
                break;
            case 4:
                try{
                    MainInterface mi = new MainInterface();
                    mi.setVisible(true);
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
                break;
            case 5:
                System.out.println("Exiting the system.");
                break;
            default:
                System.out.println("Invalid choice. Please enter a valid option.");
                systemManager();
        }
    }

    //display menu
    public static void displayMenu() {
        System.out.println();
        System.out.println("Welcome to Westminster Shopping...");
        System.out.println();
        System.out.println("Please select the option.");
        System.out.println();
        System.out.println("1 ==> Add a new product");
        System.out.println("2 ==> Delete a product");
        System.out.println("3 ==> Print the list of the product");
        System.out.println("4 ==> Open GUI ");
        System.out.println("5 ==> Exit ");
    }
    // add products to system
    public void addProduct() {
        Scanner addproductReader = new Scanner(System.in);
        System.out.println("Please select product type...");
        System.out.println("10 ==> Electronic");
        System.out.println("11==> Clothing");
        System.out.println("Enter your selected : ");
        int productType = addproductReader.nextInt();

        if (productType == 10) {
            electricProductAdd(addproductReader);
        } else if (productType == 11) {
            clothingProductAdd(addproductReader);
        } else {
            System.out.println("Unknown type...");
        }
    }

    // add electronic products to system
    private static void electricProductAdd(Scanner addproductReader) {
        addproductReader.nextLine();
        System.out.print("Enter Product ID : ");
        String id = addproductReader.nextLine();

        System.out.print("Enter product name : ");
        String pName = addproductReader.nextLine();

        System.out.print("Enter available items : ");
        int noAvailableItems = addproductReader.nextInt();

        System.out.print("Enter price : ");
        double price = addproductReader.nextDouble();
        addproductReader.nextLine();

        System.out.print("Enter brand : ");
        String brand = addproductReader.nextLine();

        System.out.print("Enter warranty period :");
        String warranty = addproductReader.nextLine();

        Electronics electronics = new Electronics(id, pName, noAvailableItems, price, brand, warranty);
        productAddSystem(electronics);
    }
    // add clothing products to system
    private static void clothingProductAdd(Scanner addproductReader) {
        addproductReader.nextLine();
        System.out.print("Enter Product ID : ");
        String id = addproductReader.nextLine();

        System.out.println("Enter product name : ");
        String pName = addproductReader.nextLine();

        System.out.println("Enter available items : ");
        int noAvailableItems = addproductReader.nextInt();

        System.out.println("Enter price : ");
        double price = addproductReader.nextDouble();

        System.out.println("Enter size : ");
        String size = addproductReader.next();
        addproductReader.nextLine();

        System.out.println("Enter colour :");
        String colour = addproductReader.nextLine();

        Clothing clothings = new Clothing(id, pName, noAvailableItems, price, size, colour);
        productAddSystem(clothings);
    }

    private static void productAddSystem(Product product) {
        if (productList.size() < 50) {
            productList.add(product);
            System.out.println("Product added.");
        } else {
            System.out.println("Can't add product..");
        }
    }
    // delete product from system
    public void deleteProduct() {
        Scanner deleteproductReader = new Scanner(System.in);
        System.out.println("Enter product ID for delete.");
        String wantDelete = deleteproductReader.nextLine();

        boolean foundProduct = false;

        for (Product product : productList) {
            if (product.getId().equals(wantDelete)) {
                productList.remove(wantDelete);
                product.toString();
                System.out.println("Prouct Deleted...");
                foundProduct = true;
                break;
            }
        }
        if (!foundProduct) {
            System.out.println("No product found...");
        }
    }
    // print list of products
    public void listProduct() {
        List<Product> sortProducts = new ArrayList<>(productList);
        sortProducts.sort((p1, p2) -> p1.getId().compareTo(p2.getId()));
        for (Product product : sortProducts) {
            product.toString();
            if (product instanceof Electronics) {
                System.out.println("Type : electronics");
            } else if (product instanceof Clothing) {
                System.out.println("Type : cloths");
            }
        }
    }
    // save products to file
    public void saveFile() {
        try (FileWriter fileWriter = new FileWriter("Data of product.txt")) {
            for (Product product : productList) {
                StringBuilder newline = new StringBuilder();
                newline.append(product.getId()).append(",");
                newline.append(product.getpName()).append(",");
                newline.append(product.getNoAvailableItems()).append(",");
                newline.append(product.getPrice()).append(",");
                if (product instanceof Electronics) {
                    Electronics electronics = (Electronics) product;
                    newline.append(electronics.getBrand()).append(",");
                    newline.append(electronics.getWarranty()).append(",");
                    newline.append("Electronic").append(",");

                } else if (product instanceof Clothing) {
                    Clothing clothing = (Clothing) product;
                    newline.append(((Clothing) product).getSize()).append(",");
                    newline.append(((Clothing) product).getColour()).append(",");
                    newline.append("Clothing").append(",");
                }
                fileWriter.write(newline.toString() + System.lineSeparator());
            }
            System.out.println("Data saved to file.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    //  load products from file to system
    private void loadProducts() {
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
                    productAddSystem(new Electronics(productId, productName, availableItems, price, brand, warranty));
                } else if ("Clothing".equals(type)) {
                    String size = data[4];
                    String color = data[5];
                    productAddSystem(new Clothing(productId, productName, availableItems, price, size, color));
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


}