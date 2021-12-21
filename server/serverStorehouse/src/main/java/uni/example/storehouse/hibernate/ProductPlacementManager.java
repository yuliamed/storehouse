package uni.example.storehouse.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import uni.example.storehouse.entities.ProductPlacement;
import uni.example.storehouse.entities.Storehouse;
import uni.example.storehouse.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProductPlacementManager {
    public static ArrayList<ProductPlacement> readProductPlacements() {
        List animals;
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        animals = session.createSQLQuery("SELECT * FROM productplacement").addEntity(ProductPlacement.class).list();
        session.close();
        ArrayList<ProductPlacement> arr = (ArrayList<ProductPlacement>) animals;
        return arr;
    }

    public static void addProductPlacement(ProductPlacement u) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(u);
        session.getTransaction().commit();
        session.close();
    }
    public static void delteProductPlacement(ProductPlacement u) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(u);
        session.getTransaction().commit();
        session.close();
    }
    public static void updateProductPlacement(ProductPlacement u){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        try {
            Transaction transaction = session.beginTransaction();
            session.update(u);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        session.close();
    }
}
