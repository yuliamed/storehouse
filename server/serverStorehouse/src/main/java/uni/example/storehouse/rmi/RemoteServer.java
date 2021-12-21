package uni.example.storehouse.rmi;

import uni.example.storehouse.entities.Storehouse;
import uni.example.storehouse.entities.User;
import uni.example.storehouse.hibernate.StorehouseManager;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class RemoteServer {
    protected static final String UNIQUE_BINDING_NAME = "server.reverse";
    public static void startServer() {
        ArrayList<Storehouse> arr = StorehouseManager.readStorehouses();
        try {
            final UserImpl service = new UserImpl();
            final Registry registry = LocateRegistry.createRegistry(8080);

            Remote stub = UnicastRemoteObject.exportObject(service, 0);
            registry.bind(UNIQUE_BINDING_NAME, stub);

            System.out.println("Сервер запущен...");
        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
        }
    }
}
