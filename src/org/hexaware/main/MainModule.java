package org.hexaware.main;
import java.util.*;
import org.hexaware.dao.*;
import org.hexaware.entitty.*;
import org.hexaware.exception.*;

public class MainModule {
      public static void main(String[] args) {
    	  Scanner sc=new Scanner(System.in);
    	  DAO obj=new DAO_Imp();
    	  
    	  while(true) {
    		  System.out.println("1. CREATE USER");
    		  System.out.println("2. CREATE PRODUCT");
    		  System.out.println("3. CANCEL ORDER");
    		  System.out.println("4. GET ALL PRODUCTS");
    		  System.out.println("5. GET ORDERS BY USERS");
    		  System.out.println("6. EXIT");
    		  
    		  int ch=sc.nextInt();
    		  switch(ch) {
    		  case 1:
    			  System.out.println("Enter User ID:");
    			    int userId = sc.nextInt();
    			    System.out.println("Enter Username:");
    			    String username = sc.next();
    			    System.out.println("Enter Password:");
    			    String password = sc.next();
    			    System.out.println("Enter Role (Admin/User):");
    			    String role = sc.next();
    			    
    			    User u=new User(userId,username,password,role);
    			    obj.createUser(u);
    			    System.out.println("User created successfully..!!");
    			    break;
    			    
    		  case 2:
    			  System.out.println("Enter Product ID:");
    			    int productId = sc.nextInt();
    			    System.out.println("Enter Product Name:");
    			    String productName = sc.next();
    			    System.out.println("Enter Description:");
    			    String description = sc.next();
    			    System.out.println("Enter Price:");
    			    double price = sc.nextDouble();
    			    System.out.println("Enter Quantity In Stock:");
    			    int quantity = sc.nextInt();
    			    System.out.println("Enter Product Type (Electronics/Clothing):");
    			    String type = sc.next();
    			    
    			    Product product;
    			    if (type.equals("Electronics")) {
    			        System.out.println("Enter Brand:");
    			        String brand = sc.next();
    			        System.out.println("Enter Warranty Period:");
    			        int warranty = sc.nextInt();
    			        product = new Electronics(productId, productName, description, price, quantity, brand, warranty);
    			    } else {
    			        System.out.println("Enter Size:");
    			        String size = sc.next();
    			        System.out.println("Enter Color:");
    			        String color = sc.next();
    			        product = new Clothing(productId, productName, description, price, quantity, size, color);
    			    }
    			    
    			    System.out.println("Enter Admin User ID:");
    			    int adminId = sc.nextInt();
    			    System.out.println("Enter Admin Username:");
    			    String adminName = sc.next();
    			    User adminUser = new User(adminId, adminName, "admin_password", "Admin");

    			    obj.createProduct(adminUser, product);
    			    System.out.println("Product created successfully!");
    			    break;
    			    
    			    
    		  case 3:
    			    System.out.println("Enter User ID:");
    			    int userId_c = sc.nextInt();
    			    System.out.println("Enter Order ID:");
    			    int orderId = sc.nextInt();

    			    try {
    			        obj.cancelOrder(userId_c, orderId);
    			        System.out.println("Order canceled successfully!");
    			    } catch (UserNotFoundException | OrderNotFoundException e) {
    			        System.out.println("Error: " + e.getMessage());
    			    }
    			    break;
    			    
    		  case 4:
    			    List<Product> products = obj.getAllProducts();
    			    for (Product prod : products) {
    			        System.out.println(prod);
    			    }
    			    break;

    		  case 5:
    			    System.out.println("Enter User ID:");
    			    int userId_or = sc.nextInt();
    			    System.out.println("Enter Username:");
    			    String username_or = sc.next();

    			    User user = new User(userId_or, username_or, "user_password", "User");
    			    List<Product> orderedProducts = obj.getOrderByUsers(user);
    			    for (Product prods : orderedProducts) {
    			        System.out.println(prods);
    			    }
    			    break;

    		  case 6:
    			    System.out.println("Exiting application...");
    			    sc.close();
    			    return;


    		  }
    		  
    	  }
      }
}
