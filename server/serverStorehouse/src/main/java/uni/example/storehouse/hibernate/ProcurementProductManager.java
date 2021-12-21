package uni.example.storehouse.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import uni.example.storehouse.entities.ProcurementProduct;
import uni.example.storehouse.entities.Product;
import uni.example.storehouse.entities.User;

import java.util.ArrayList;
import java.util.List;

public class ProcurementProductManager {
    public static void addProcurementProduct(ProcurementProduct u) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(u);
        session.getTransaction().commit();
        session.close();
    }

    public static ArrayList<ProcurementProduct> readProcurementProducts(){
        List products;
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        products = session.createSQLQuery("SELECT * FROM ProcurementProduct").addEntity(ProcurementProduct.class).list();
        session.close();
        ArrayList<ProcurementProduct> arr = (ArrayList<ProcurementProduct>) products;
        return arr;
    }
    public static void updateProcurementProduct(ProcurementProduct u){
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
    public static void deleteProcurementProducts(ProcurementProduct u){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(u); session.getTransaction().commit();
        session.close();
    }
}
