import org.hibernate.Session;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import uni.example.storehouse.entities.User;
import uni.example.storehouse.hibernate.HibernateSessionFactory;
import uni.example.storehouse.hibernate.UserManager;
import org.junit.Test;

import javax.transaction.TransactionScoped;
import java.util.ArrayList;
import java.util.List;

public class UserManagerTest {

    @Test
    public void testReadingUsers() {
        ArrayList<User> actualUsers = UserManager.readUsers();

        List animals;
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        animals = session.createSQLQuery("SELECT * FROM user").addEntity(User.class).list();
        session.close();
        ArrayList<User> arr = (ArrayList<User>) animals;
        Assert.assertArrayEquals(new int[]{actualUsers.size()}, new int[]{arr.size()});
    }

    User user = null;

    @Test
    public void testAddingUser() {
        ArrayList<User> before = UserManager.readUsers();
        user = new User();
        user.setLogin("test-1");
        user.setRole((byte) 1);
        UserManager.addUsers(user);
        ArrayList<User> after = UserManager.readUsers();
        Assert.assertArrayEquals(new int[]{before.size()+1}, new int[]{after.size()});
        UserManager.deleteUser(user);
    }

}
