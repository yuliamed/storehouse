package uni.example.storehouse.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import uni.example.storehouse.entities.Procurement;
import uni.example.storehouse.entities.Product;
import uni.example.storehouse.entities.User;

import java.util.ArrayList;
import java.util.List;

public class ProcurementManager {
    public static ArrayList<Procurement> readProcurements(){
        List products;
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        products = session.createSQLQuery("SELECT * FROM procurement ").addEntity(Procurement.class).list();
        session.close();
        ArrayList<Procurement> arr = (ArrayList<Procurement>) products;
        return arr;
    }
    public static void addProcurement(Procurement u) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(u);
        session.getTransaction().commit();
        session.close();
    }
    public static void deleteProcurement(Procurement u) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(u);
        session.getTransaction().commit();
        session.close();
    }
    public static void updateProcurement(Procurement u){
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
