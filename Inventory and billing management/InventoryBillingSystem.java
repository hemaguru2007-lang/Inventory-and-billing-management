// InventoryBillingSystem.java
import java.util.*;

class Product {
    int id;
    String name;
    double price;
    int quantity;

    public Product(int id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public void display() {
        System.out.printf("ID: %d | Name: %s | Price: %.2f | Stock: %d\n", id, name, price, quantity);
    }
}

class Inventory {
    Map<Integer, Product> products = new HashMap<>();

    public void addProduct(Product p) {
        products.put(p.id, p);
    }

    public void displayInventory() {
        System.out.println("\n--- Inventory ---");
        for (Product p : products.values()) {
            p.display();
        }
    }

    public boolean updateStock(int id, int qty) {
        if (products.containsKey(id)) {
            Product p = products.get(id);
            if (p.quantity >= qty) {
                p.quantity -= qty;
                return true;
            } else {
                System.out.println("❌ Not enough stock!");
            }
        } else {
            System.out.println("❌ Product not found!");
        }
        return false;
    }

    public Product getProduct(int id) {
        return products.get(id);
    }
}

class BillItem {
    Product product;
    int quantity;

    public BillItem(Product p, int qty) {
        this.product = p;
        this.quantity = qty;
    }

    public double getTotal() {
        return product.price * quantity;
    }

    public void display() {
        System.out.printf("%s x %d = %.2f\n", product.name, quantity, getTotal());
    }
}

class Billing {
    List<BillItem> items = new ArrayList<>();

    public void addItem(Product p, int qty) {
        items.add(new BillItem(p, qty));
    }

    public void generateBill() {
        System.out.println("\n🧾 --- BILL ---");
        double total = 0;
        for (BillItem item : items) {
            item.display();
            total += item.getTotal();
        }
        System.out.printf("Total Amount: %.2f\n", total);
    }
}

public class InventoryBillingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Inventory inventory = new Inventory();
        Billing billing = new Billing();

        // Adding sample products
        inventory.addProduct(new Product(1, "Laptop", 1000.0, 5));
        inventory.addProduct(new Product(2, "Phone", 500.0, 10));
        inventory.addProduct(new Product(3, "Keyboard", 50.0, 20));

        boolean running = true;

        while (running) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Display Inventory");
            System.out.println("2. Buy Product");
            System.out.println("3. Generate Bill");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    inventory.displayInventory();
                    break;

                case 2:
                    System.out.print("Enter Product ID to buy: ");
                    int id = scanner.nextInt();
                    System.out.print("Enter quantity: ");
                    int qty = scanner.nextInt();

                    Product p = inventory.getProduct(id);
                    if (p != null && inventory.updateStock(id, qty)) {
                        billing.addItem(p, qty);
                        System.out.println("✅ Added to bill.");
                    }
                    break;

                case 3:
                    billing.generateBill();
                    break;

                case 4:
                    running = false;
                    System.out.println("👋 Exiting...");
                    break;

                default:
                    System.out.println("❌ Invalid choice!");
            }
        }

        scanner.close();
    }
}
