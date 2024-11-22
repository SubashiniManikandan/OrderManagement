package org.hexaware.dao;
import org.hexaware.entitty.*;
//import org.hexaware.exception.*;
import org.hexaware.util.DbConnect;
import java.sql.*;
import java.util.*;


public class DAO_Imp implements DAO {

	
	@Override
	public void createOrder(User user, List<Product> products) {
	    try (Connection conn = DbConnect.getDBConn()) {
	        // USER EXISTS
	        PreparedStatement userStmt = conn.prepareStatement("SELECT * FROM Users WHERE userId = ?");
	        userStmt.setInt(1, user.getUserId());
	        ResultSet rs= userStmt.executeQuery();

	        if (!rs.next()) {
	            createUser(user);
	        }

	        // ORDER CREATION
	        PreparedStatement orderStmt = conn.prepareStatement("INSERT INTO Orders (userId) VALUES (?)");
	        orderStmt.setInt(1, user.getUserId());
	        orderStmt.executeUpdate();
	        ResultSet keys= orderStmt.getGeneratedKeys();
	        int orderId = 0;
	        if (keys.next()) {
	            orderId = keys.getInt(1);
	        }

	        // Insert products into Ordered_Items
	        PreparedStatement orderItemStmt = conn.prepareStatement("INSERT INTO Ordered_Items (orderId, productId, quantity) VALUES (?, ?, ?)");
	        for (Product product : products) {
	            orderItemStmt.setInt(1, orderId);
	            orderItemStmt.setInt(2, product.getProductId());
	            orderItemStmt.setInt(3, 1); // Default quantity is 1
	            orderItemStmt.addBatch();
	        }
	        orderItemStmt.executeBatch();

	        System.out.println("Order created successfully with Order ID: " + orderId);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@Override
	public void cancelOrder(int userId, int orderId) {
	    try (Connection conn = DbConnect.getDBConn()) {
             // Cancel the order
	        PreparedStatement deleteOrderStmt = conn.prepareStatement("DELETE FROM Orders WHERE orderId = ?");
	        deleteOrderStmt.setInt(1, orderId);
	        deleteOrderStmt.executeUpdate();

	        System.out.println("Order canceled successfully.");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
      
	
	@Override
	public void createProduct(User user, Product product) {
	    try (Connection conn = DbConnect.getDBConn()) {
	        // ADMIN VERIFICATION
	        if (!user.getRole().equals("Admin")) {
	            throw new Exception("Only admin users can add products.");
	        }

	        // INSERTION OF PRODUCTS
	        PreparedStatement prodStmt = conn.prepareStatement(
	            "INSERT INTO Products (productId, productName, description, price, quantityInStock, type) VALUES (?, ?, ?, ?, ?, ?)");
	        prodStmt.setInt(1, product.getProductId());
	        prodStmt.setString(2, product.getProductName());
	        prodStmt.setString(3, product.getDescription());
	        prodStmt.setDouble(4, product.getPrice());
	        prodStmt.setInt(5, product.getQuantityInStock());
	        prodStmt.setString(6, product.getType());
	        prodStmt.executeUpdate();

	        System.out.println("Product created successfully.");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
     
	
	@Override
	public void createUser(User user) {
	    try (Connection conn = DbConnect.getDBConn()) {
	        PreparedStatement userStmt = conn.prepareStatement("INSERT INTO Users (userId, username, password, role) VALUES (?, ?, ?, ?)");
	        userStmt.setInt(1, user.getUserId());
	        userStmt.setString(2, user.getUsername());
	        userStmt.setString(3, user.getPassword());
	        userStmt.setString(4, user.getRole());
	        userStmt.executeUpdate();

	        System.out.println("User created successfully.");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	
	@Override
	public List<Product> getAllProducts() {
	    List<Product> products = new ArrayList<>();
	    try (Connection conn = DbConnect.getDBConn()) {
	        PreparedStatement productStmt = conn.prepareStatement("SELECT * FROM Products");
	        ResultSet rs = productStmt.executeQuery();

	        while (rs.next()) {
	            Product product = new Product(
	                rs.getInt("productId"),
	                rs.getString("productName"),
	                rs.getString("description"),
	                rs.getDouble("price"),
	                rs.getInt("quantityInStock"),
	                rs.getString("type")
	            );
	            products.add(product);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return products;
	}

	
	@Override
	public List<Product> getOrderByUsers(User user) {
	    List<Product> orderedProducts = new ArrayList<>();
	    try (Connection conn = DbConnect.getDBConn()) {
	        PreparedStatement orderStmt = conn.prepareStatement( "SELECT p.* FROM Products p JOIN Ordered_Items oi ON p.productId = oi.productId " +
	            "JOIN Orders o ON oi.orderId = o.orderId WHERE o.userId = ?");
	        orderStmt.setInt(1, user.getUserId());
	        ResultSet rs = orderStmt.executeQuery();

	        while (rs.next()) {
	            Product product = new Product(
	                rs.getInt("productId"),
	                rs.getString("productName"),
	                rs.getString("description"),
	                rs.getDouble("price"),
	                rs.getInt("quantityInStock"),
	                rs.getString("type")
	            );
	            orderedProducts.add(product);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return orderedProducts;
	}


}
