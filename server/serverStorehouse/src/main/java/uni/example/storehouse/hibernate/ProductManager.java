package uni.example.storehouse.hibernate;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import uni.example.storehouse.entities.Product;
import uni.example.storehouse.entities.Provider;
import uni.example.storehouse.entities.User;

import java.util.ArrayList;
import java.util.List;

public class ProductManager {
    public static ArrayList<Product> readProducts(){
        List<Product> products;
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        products = session.createSQLQuery("SELECT * FROM product").addEntity(Product.class).list();
        session.close();
        ArrayList<Product> arr = (ArrayList<Product>) products;
        return arr;
    }
    public static void addProduct(Product u) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(u);
        session.getTransaction().commit();
        session.close();
    }
}
