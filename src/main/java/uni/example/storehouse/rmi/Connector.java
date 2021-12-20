package uni.example.storehouse.rmi;

import uni.example.storehouse.entities.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class Connector {
    protected static final String UNIQUE_BINDING_NAME = "server.reverse";
    protected static IUser user;
    //protected static IStorehouse storehouse;
    public Connector() throws RemoteException, NotBoundException {
        final Registry registry = LocateRegistry.getRegistry(8080);
        user = (IUser) registry.lookup(UNIQUE_BINDING_NAME);
    }

    public boolean checkUser(String login, String pass) throws RemoteException {
        return user.checkUser(login, pass);
    }

    public void addUser(String login, String name, String surname, String patronymic, byte role, Storehouse idStorehouse) throws RemoteException {
        user.addUser(login, name, surname, patronymic, role, idStorehouse);
    }

    public ArrayList<User> readUsers() throws RemoteException {
        return user.readUsers();
    }

    public User getUserByLogin(String login) throws RemoteException{
        return user.getUserByLogin(login);
    }

    public void changeUser(User profile) throws RemoteException{
        user.changeUser(profile);
    }
    public ArrayList<Storehouse> readStorehouses() throws RemoteException {
        return user.getStorehouses();
    }

    public ArrayList<Procurement> getProcurements() throws RemoteException {
        return user.getProcurements();
    }

    public ArrayList<Provider> getProviders() throws RemoteException {
        return user.getProviders();
    }

    public ArrayList<Product> getProducts() throws RemoteException {
        return user.getProducts();
    }

    public ArrayList<Product> getProductsByProcurement(Procurement p) throws RemoteException {
        return user.getProductsByProcurement(p);
    }

    public ArrayList<Integer> getProductsCountByProcurement(Procurement p) throws RemoteException {
        return user.getProductsCountByProcurement(p);
    }

    public ArrayList<ProcurementProduct> getProcurementProductByProcur(Procurement p) throws RemoteException {
        return user.getProcurementProductByProcur(p);
    }

    public void addProduct(Product product) throws RemoteException {
        user.addProduct(product);
    }

    public void addProcurementProduct(ProcurementProduct pp) throws RemoteException{
        user.addProcurementProduct(pp);
    }

    public int addProvider(Provider provider)  throws RemoteException{
        return user.addProvider(provider);
        //return 0;
    }

    public void addProcurement(Procurement p) throws RemoteException{
        user.addProcurement(p);
    }

    public void deleteProcurementProduct(Procurement procurement, Product product) throws RemoteException{
        user.deleteProcurementProduct(procurement,product);
    }
    public void deleteProcurementWithProducts(Procurement procurement) throws RemoteException{
        user.deleteProcurementWithProducts(procurement);
    }

    public ArrayList<ProductPlacement> filInProcurementProduct(ProcurementProduct p_p) throws  RemoteException{
        return user.filInProcurementProduct(p_p);
    }

    public void deleteUser(User selectedUser) throws  RemoteException, SecurityException{
        user.deleteUser(selectedUser);
    }

    public void addStorehouse(Storehouse storehouse) throws  RemoteException{
        user.addStorehouse(storehouse);
    }

    public void deleteStorehouse(Storehouse selectedStorehouse) throws  RemoteException{
        user.deleteStorehouse(selectedStorehouse);
    }

    public void updateStorehouse(Storehouse editableStorehouse) throws  RemoteException{
        user.updateStorehouse(editableStorehouse);
    }

    public int getCountFiledCellsInStorehouse(Storehouse selectedStorehouse) throws  RemoteException {
        return user.getCountFiledCellsInStorehouse(selectedStorehouse);
    }

    public void writeFileWithAnalize(Storehouse selectedStorehouse)  throws  RemoteException {
        user.writeFileWithAnalize(selectedStorehouse);
    }

    public boolean isUserAdmin(String login)  throws  RemoteException {
        return user.isUserAdmin(login);
    }

    public ArrayList<Procurement> getProcurementsByManager(User manager) throws  RemoteException {
        return user.getProcurementsByManager(manager);
    }

    public ArrayList<ProductPlacement> findProductInStorehouseById(Storehouse idStorehouse, Integer integer) throws RemoteException, NullPointerException  {
        return user.findProductInStorehouseById(idStorehouse, integer);
    }

    public void writeFileWithProducts(ArrayList<ProductPlacement> arr)throws RemoteException {
        user.writeFileWithProducts( arr);
    }

    public double getPriceInStorehouse(Storehouse storehouse)throws RemoteException {
        return user.getPriceInStorehouse(storehouse);
    }

    public int findWorkTime(User manager)throws RemoteException {
        return user.findWorkTime(manager);
    }
}
