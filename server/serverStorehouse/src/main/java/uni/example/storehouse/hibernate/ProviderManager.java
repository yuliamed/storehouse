package uni.example.storehouse.hibernate;

import org.hibernate.Session;
import uni.example.storehouse.entities.Provider;

import java.util.ArrayList;
import java.util.List;

public class ProviderManager {
    public static int addProvider(Provider u) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(u);
        session.getTransaction().commit();
        session.close();
        return u.getIdProvider();
    }

    public static ArrayList<Provider> readProviders() {
        List providers;
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        providers = session.createSQLQuery("SELECT * FROM provider").addEntity(Provider.class).list();
        session.close();
        ArrayList<Provider> arr = (ArrayList<Provider>) providers;
        return arr;
    }
}
