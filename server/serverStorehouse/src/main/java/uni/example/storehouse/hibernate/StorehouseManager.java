package uni.example.storehouse.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import uni.example.storehouse.entities.Storehouse;
import uni.example.storehouse.entities.User;

import java.util.ArrayList;
import java.util.List;

public class StorehouseManager {
    public static ArrayList<Storehouse> readStorehouses(){
        List storehouses;
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        storehouses = session.createSQLQuery("SELECT * FROM storehouse").addEntity(Storehouse.class).list();
        session.close();
        ArrayList<Storehouse> arr = (ArrayList<Storehouse>) storehouses;
        return arr;
    }

    public static void addStorehouse(Storehouse storehouse) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(storehouse);
        session.getTransaction().commit();
        session.close();
    }
    public static void deleteStorehouse(Storehouse storehouse) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(storehouse);
        session.getTransaction().commit();
        session.close();
    }
    public static void updateStorehouse(Storehouse storehouse){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        try {
            Transaction transaction = session.beginTransaction();
            session.update(storehouse);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        session.close();
    }

}
