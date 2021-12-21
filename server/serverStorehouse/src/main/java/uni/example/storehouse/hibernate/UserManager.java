package uni.example.storehouse.hibernate;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import uni.example.storehouse.entities.Provider;
import uni.example.storehouse.entities.User;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    public static ArrayList<User> readUsers() {
        List animals;
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        animals = session.createSQLQuery("SELECT * FROM user").addEntity(User.class).list();
        session.close();
        ArrayList<User> arr = (ArrayList<User>) animals;
        return arr;
    }

    public static void addUsers(User u) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(u);
        session.getTransaction().commit();
        session.close();
    }

    public static void updateUser(User u){
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


    public static void deleteUser(User user) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(user);
        session.getTransaction().commit();
        session.close();
    }
}
