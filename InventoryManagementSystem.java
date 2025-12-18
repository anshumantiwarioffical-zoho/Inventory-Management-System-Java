// Inventory Management System (Console Based Java Project)

// Import required packages
import java.util.*;
import java.io.*;

// --------------------------------------------
// Product class represents a single product
// --------------------------------------------
class Product implements Serializable {

    int productId;        // Unique product ID
    String productName;   // Product name
    int quantity;         // Available quantity
    double price;         // Price per unit

    // Constructor to initialize product
    Product(int productId, String productName, int quantity, double price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    // Display product details
    void display() {
        System.out.println(
            "ID: " + productId +
            ", Name: " + productName +
            ", Quantity: " + quantity +
            ", Price: " + price
        );
    }
}

// --------------------------------------------
// Main class
// --------------------------------------------
public class InventoryManagementSystem {

    static ArrayList<Product> products = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static final String FILE_NAME = "inventory.dat";

    public static void main(String[] args) {

        loadFromFile();
        int choice = 0;

        do {
            System.out.println("\n--- Inventory Management System ---");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Update Quantity");
            System.out.println("4. Sell Product");
            System.out.println("5. Save & Exit");
            System.out.print("Enter choice: ");

            try {
                choice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Please enter numbers only.");
                sc.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    viewProducts();
                    break;
                case 3:
                    updateQuantity();
                    break;
                case 4:
                    sellProduct();
                    break;
                case 5:
                    saveToFile();
                    System.out.println("Data saved. Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 5);
    }

    // Add new product
    static void addProduct() {
        System.out.print("Enter Product ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Product Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Quantity: ");
        int qty = sc.nextInt();

        System.out.print("Enter Price: ");
        double price = sc.nextDouble();

        products.add(new Product(id, name, qty, price));
        System.out.println("Product added successfully.");
    }

    // View all products
    static void viewProducts() {
        if (products.isEmpty()) {
            System.out.println("No products available.");
        } else {
            for (Product p : products) {
                p.display();
            }
        }
    }

    // Update product quantity
    static void updateQuantity() {
        System.out.print("Enter Product ID: ");
        int id = sc.nextInt();

        Product p = findProduct(id);
        if (p != null) {
            System.out.print("Enter new quantity: ");
            p.quantity = sc.nextInt();
            System.out.println("Quantity updated successfully.");
        } else {
            System.out.println("Product not found.");
        }
    }

    // Sell product
    static void sellProduct() {
        System.out.print("Enter Product ID: ");
        int id = sc.nextInt();

        Product p = findProduct(id);
        if (p != null) {
            System.out.print("Enter quantity to sell: ");
            int sellQty = sc.nextInt();

            if (sellQty > p.quantity) {
                System.out.println("Not enough stock.");
            } else {
                p.quantity -= sellQty;
                System.out.println("Product sold successfully.");
            }
        } else {
            System.out.println("Product not found.");
        }
    }

    // Find product by ID
    static Product findProduct(int id) {
        for (Product p : products) {
            if (p.productId == id) {
                return p;
            }
        }
        return null;
    }

    // Save inventory to file
    static void saveToFile() {
        try {
            ObjectOutputStream oos =
                new ObjectOutputStream(new FileOutputStream(FILE_NAME));
            oos.writeObject(products);
            oos.close();
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }

    // Load inventory from file
    static void loadFromFile() {
        try {
            ObjectInputStream ois =
                new ObjectInputStream(new FileInputStream(FILE_NAME));
            products = (ArrayList<Product>) ois.readObject();
            ois.close();
        } catch (Exception e) {
            products = new ArrayList<>();
        }
    }
            }
