package org.hexaware.dao;
import org.hexaware.entitty.Product;
import org.hexaware.entitty.User;
import java.util.*;

public interface DAO {
   void createOrder(User user,List<Product> prod);
   void cancelOrder(int u_id,int o_id);
   void createProduct(User user,Product prod);
   void createUser(User user);
   List<Product> getAllProducts();
   List<Product> getOrderByUsers(User user);
   
   
}
