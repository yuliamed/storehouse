package uni.example.storehouse.rmi;

import uni.example.storehouse.entities.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IUser extends Remote {


    boolean checkUser(String login, String pass) throws RemoteException;
    void addUser(String login, String name, String surname, String patronymic, Byte role, Storehouse idStorehouse) throws RemoteException;
    ArrayList<User> readUsers() throws RemoteException;
    User getUserByLogin(String login)throws RemoteException;
    void changeUser(User profile)throws RemoteException;
    void deleteUser(User user) throws RemoteException, SecurityException;



    ArrayList<Storehouse> getStorehouses()throws RemoteException;


    ArrayList<Product> getProducts() throws RemoteException;
    void addProduct(Product product)throws RemoteException;
    ArrayList<Product> getProductsByProcurement(Procurement p) throws RemoteException;
    ArrayList<Integer> getProductsCountByProcurement(Procurement p) throws RemoteException;


    ArrayList<Procurement> getProcurements ()throws RemoteException;



    ArrayList<Provider> getProviders()throws RemoteException;

    ArrayList<ProcurementProduct> getProcurementProductByProcur(Procurement p) throws RemoteException;

    void addProcurementProduct(ProcurementProduct pp)throws RemoteException;

    int addProvider(Provider provider)throws RemoteException;

    void addProcurement(Procurement p)throws RemoteException;

    void deleteProcurementProduct(Procurement procurement, Product product) throws RemoteException;
    void deleteProcurementWithProducts(Procurement procurement) throws RemoteException;

    ArrayList<ProductPlacement> filInProcurementProduct(ProcurementProduct p_p) throws  RemoteException;

    void addStorehouse(Storehouse storehouse)throws  RemoteException;

    void deleteStorehouse(Storehouse storehouse)throws  RemoteException;
    void updateStorehouse(Storehouse storehouse)throws  RemoteException;

    int getCountFiledCellsInStorehouse(Storehouse selectedStorehouse) throws  RemoteException;

    void writeFileWithAnalize(Storehouse selectedStorehouse)throws  RemoteException;

    boolean isUserAdmin(String login)throws  RemoteException;;

    ArrayList<Procurement> getProcurementsByManager(User manager)throws  RemoteException;

    ArrayList<ProductPlacement> findProductInStorehouseById(Storehouse idStorehouse, Integer integer) throws RemoteException, NullPointerException;

    void writeFileWithProducts(ArrayList<ProductPlacement> arr)throws RemoteException;

    double getPriceInStorehouse(Storehouse storehouse)throws RemoteException;

    int findWorkTime(User manager)throws RemoteException;
}
