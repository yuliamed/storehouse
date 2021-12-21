package uni.example.storehouse.rmi;

import uni.example.storehouse.entities.*;
import uni.example.storehouse.hibernate.*;

import java.io.*;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class UserImpl /*extends UnicastRemoteObject*/ implements IUser {

    @Override
    public boolean checkUser(String login, String pass) throws RemoteException {
        ArrayList<User> users = new ArrayList<User>();
        users = UserManager.readUsers();
        for (User u : users) {
            if (u.getLogin().equals(login) && u.getPass().equals(pass)) {
                System.out.println("Вошёл пользователь " + u.getLogin());
                return true;
            }
        }
        return false;
    }

    @Override
    public void addUser(String login, String name, String surname, String patronymic, Byte role, Storehouse idStorehouse) throws RemoteException {
        User user = new User();
        user.setLogin(login);
        user.setRole(role);
        user.setSurname(surname);
        user.setPatronymic(patronymic);
        user.setName(name);
        user.setStorehouse(idStorehouse);
        UserManager.addUsers(user);
    }

    @Override
    public ArrayList<User> readUsers() throws RemoteException {
        ArrayList<User> users = new ArrayList<User>();
        users = UserManager.readUsers();
        return users;
    }

    @Override
    public User getUserByLogin(String login) throws RemoteException {
        ArrayList<User> users = new ArrayList<User>();
        users = UserManager.readUsers();
        for (User u : users) {
            if (u.getLogin().equals(login)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public void changeUser(User profile) throws RemoteException {
        UserManager.updateUser(profile);
    }

    @Override
    public void deleteUser(User user) throws RemoteException, SecurityException {
        if (user.getRole() == 1) throw new SecurityException();
        UserManager.deleteUser(user);
    }

    @Override
    public ArrayList<Storehouse> getStorehouses() throws RemoteException {
        ArrayList<Storehouse> arr = new ArrayList<Storehouse>();
        arr = StorehouseManager.readStorehouses();
        return arr;
    }

    @Override
    public ArrayList<Product> getProducts() throws RemoteException {
        ArrayList<Product> arr = new ArrayList<Product>();
        arr = ProductManager.readProducts();
        return arr;
    }

    @Override
    public void addProduct(Product product) throws RemoteException {
        ProductManager.addProduct(product);
    }

    @Override
    public ArrayList<Product> getProductsByProcurement(Procurement p) throws RemoteException {
        ArrayList<ProcurementProduct> arr = ProcurementProductManager.readProcurementProducts();
        ArrayList<ProcurementProduct> res_1 = new ArrayList<ProcurementProduct>();
        for (ProcurementProduct pp : arr)
            if (pp.getProcurement().getIdProcurement().equals(p.getIdProcurement()))
                res_1.add(pp);
        ArrayList<Product> res = new ArrayList<Product>();
        for (ProcurementProduct pr : res_1) {
            res.add(pr.getProduct());
        }
        return res;
    }

    @Override
    public ArrayList<Integer> getProductsCountByProcurement(Procurement p) throws RemoteException {
        ArrayList<ProcurementProduct> arr = ProcurementProductManager.readProcurementProducts();
        ArrayList<Integer> res_1 = new ArrayList<Integer>();
        for (ProcurementProduct pp : arr)
            if (pp.getProcurement().getIdProcurement().equals(p.getIdProcurement()))
                res_1.add(pp.getCount());
        return res_1;
    }


    @Override
    public ArrayList<Procurement> getProcurements() throws RemoteException {
        ArrayList<Procurement> arr = new ArrayList<Procurement>();
        arr = ProcurementManager.readProcurements();
        return arr;
    }

    @Override
    public ArrayList<Provider> getProviders() throws RemoteException {
        ArrayList<Provider> arr = new ArrayList<Provider>();
        arr = ProviderManager.readProviders();
        return arr;
    }

    @Override
    public int getCountFiledCellsInStorehouse(Storehouse selectedStorehouse) throws RemoteException {
        ArrayList<ProductPlacement> allPP = ProductPlacementManager.readProductPlacements();
        ArrayList<ProductPlacement> neededPP = new ArrayList<ProductPlacement>();
        for (ProductPlacement pp : allPP) {
            if (pp.getStorehouse().getId().compareTo(selectedStorehouse.getId()) == 0) {
                neededPP.add(pp);
            }
        }
        return neededPP.size();
    }

    void writeStorehouseResultToFile(String storehouseName, int filledCells, int freeSells) {
        PrintWriter vm = null;
        try {
            File file2 = new File(storehouseName + ".txt");
            if (!file2.exists())
                file2.createNewFile();
            vm = new PrintWriter(file2);
            vm.println("Количетсво свободных ячеек в складе " + storehouseName + " : " + freeSells);
            vm.println("Количетсво занятых ячеек в складе " + storehouseName + " : " + filledCells);
            double procent = (double) 100 * (double) filledCells / ((double) filledCells + (double) freeSells);
            vm.println("Склад занят на  " + (procent) + " % ");
        } catch (IOException e) {
            System.out.println("Error" + e);
        } finally {
            vm.close();
        }
    }

    @Override
    public void writeFileWithAnalize(Storehouse selectedStorehouse) throws RemoteException {
        String store = selectedStorehouse.getId() + " " + selectedStorehouse.getName();
        int filledCells = this.getCountFiledCellsInStorehouse(selectedStorehouse);
        int freeCells = selectedStorehouse.getMaxCells() * selectedStorehouse.getMaxRows() - filledCells;
        writeStorehouseResultToFile(store, filledCells, freeCells);
    }

    @Override
    public void addProcurementProduct(ProcurementProduct pp) throws RemoteException {
        ProcurementProductManager.addProcurementProduct(pp);
    }

    @Override
    public int addProvider(Provider provider) throws RemoteException {
        return ProviderManager.addProvider(provider);
    }

    @Override
    public void addProcurement(Procurement p) throws RemoteException {
        ProcurementManager.addProcurement(p);
    }


    @Override
    public ArrayList<ProcurementProduct> getProcurementProductByProcur(Procurement p) throws RemoteException {
        ArrayList<ProcurementProduct> arr = new ArrayList<ProcurementProduct>();
        arr = ProcurementProductManager.readProcurementProducts();
        ArrayList<ProcurementProduct> res = new ArrayList<ProcurementProduct>();
        for (ProcurementProduct pp : arr)
            if (pp.getProcurement().getIdProcurement().equals(p.getIdProcurement()))
                res.add(pp);
        return res;
    }

    @Override
    public void deleteProcurementProduct(Procurement procurement, Product product) throws RemoteException {
        ArrayList<ProcurementProduct> p_p = ProcurementProductManager.readProcurementProducts();
        ProcurementProduct pp = null;
        for (ProcurementProduct p0 : p_p) {
            if (p0.getProduct().getUniqueCode().equals(product.getUniqueCode()) && p0.getProcurement().getIdProcurement().equals(procurement.getIdProcurement())) {
                pp = p0;
                break;
            }
        }
        ProcurementProductManager.deleteProcurementProducts(pp);
    }

    @Override
    public void deleteProcurementWithProducts(Procurement procurement) throws RemoteException {
        ArrayList<ProcurementProduct> p_p = ProcurementProductManager.readProcurementProducts();
        for (ProcurementProduct p0 : p_p) {
            if (p0.getProcurement().getIdProcurement().equals(procurement.getIdProcurement())) {
                ProcurementProductManager.deleteProcurementProducts(p0);
            }
        }
        ProcurementManager.deleteProcurement(procurement);
    }

    @Override
    public ArrayList<ProductPlacement> filInProcurementProduct(ProcurementProduct p_p) throws RemoteException {
        if (p_p.getStatus()) return null;
        Storehouse storehouse = p_p.getProcurement().getManagerLogin().getStorehouse();
        ArrayList<ProductPlacement> resArr = new ArrayList<ProductPlacement>();
        for (int i = 0; i < p_p.getCount(); i++) {
            ProductPlacement res = findCell(storehouse);
            res.setProduct(p_p.getProduct());
            ProductPlacementManager.addProductPlacement(res);
            resArr.add(res);

        }
        p_p.setStatus(true);
        ProcurementProductManager.updateProcurementProduct(p_p);
        checkProcurementStatus(p_p.getProcurement());

        return resArr;

    }

    @Override
    public void addStorehouse(Storehouse storehouse) throws RemoteException {
        StorehouseManager.addStorehouse(storehouse);
    }

    @Override
    public void deleteStorehouse(Storehouse storehouse) throws RemoteException {
        ArrayList<ProductPlacement> productPlacements = ProductPlacementManager.readProductPlacements();
        for (ProductPlacement p : productPlacements) {
            if (p.getStorehouse().getId().compareTo(storehouse.getId()) == 0) {
                ProductPlacementManager.delteProductPlacement(p);
            }
        }
        ArrayList<User> users = UserManager.readUsers();
        for (User u : users) {
            if (u.getStorehouse().getId().compareTo(storehouse.getId()) == 0) {
                u.setStorehouse(null);
            }
        }
        StorehouseManager.deleteStorehouse(storehouse);
    }

    @Override
    public void updateStorehouse(Storehouse storehouse) throws RemoteException {
        StorehouseManager.updateStorehouse(storehouse);
    }

    @Override
    public boolean isUserAdmin(String login) throws RemoteException {
        ArrayList<User> users = new ArrayList<User>();
        users = UserManager.readUsers();

        for (User u : users) {
            if (u.getLogin().equals(login) && u.getRole() != 1) {
                System.out.println("В систему зашёл менеджер " + login);
                return false;
            }
        }

        System.out.println("В систему зашёл администратор " + login);
        return true;
    }

    @Override
    public double getPriceInStorehouse(Storehouse storehouse) throws RemoteException {
        double res = 0;
        ArrayList<Storehouse> storehouses = StorehouseManager.readStorehouses();
        for (Storehouse s : storehouses)
            if (s.getId().compareTo(storehouse.getId()) == 0) {
                storehouse = s;
                break;
            }
        List<ProductPlacement> productPlacementSet = storehouse.getProductPlacements();
        for (int i = 0; i < productPlacementSet.size(); i++) {
            res += productPlacementSet.get(i).getProduct().getPrice();
        }
        return res;
    }

    @Override
    public ArrayList<Procurement> getProcurementsByManager(User manager) throws RemoteException {
        ArrayList<Procurement> allProcurement = ProcurementManager.readProcurements();
        ArrayList<Procurement> res = new ArrayList<Procurement>();
        for (Procurement p : allProcurement) {
            if (p.getManagerLogin().getLogin().equals(manager.getLogin())) {
                res.add(p);
            }
        }
        return res;
    }

    @Override
    public void writeFileWithProducts(ArrayList<ProductPlacement> arr) throws RemoteException {
        PrintWriter vm = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss");
        Date date = new Date();
        try {
            File file2 = new File(arr.get(0).getProduct().getName() + dateFormat.format(date) + ".txt");
            if (!file2.exists())
                file2.createNewFile();
            vm = new PrintWriter(file2);
            vm.println("Размещение товара на склад  " + arr.get(0).getStorehouse().getName() + " продукта " + arr.get(0).getProduct().getName() + " : ");
            for (ProductPlacement pp : arr) {
                vm.println("Ряд " + pp.getRack() + " место " + pp.getPlace());
            }

        } catch (IOException e) {
            System.out.println("Error" + e);
        } finally {
            vm.close();
        }
    }

    @Override
    public int findWorkTime(User manager) throws RemoteException {
        ArrayList<Procurement> procurementsFull = this.getProcurementsByManager(manager);
        if (procurementsFull.isEmpty()) return 0;
        ArrayList<Procurement> procurements = new ArrayList<>();
        for (Procurement p : procurementsFull) {
            if (!p.getStatus())
                procurements.add(p);
        }
        if (procurements.isEmpty()) return 0;
        int res = 0;
        for (Procurement p : procurements) {
            ArrayList<ProcurementProduct> procurementProduct = this.getProcurementProductByProcur(p);
            for (ProcurementProduct pp : procurementProduct) {
                if (pp.getStatus())
                    continue;
                else {
                    int timeTrack = 0;
                    if (pp.getProduct().getMass() > 5)
                        timeTrack = 5;
                    else timeTrack = 2;
                    res += timeTrack * pp.getCount();
                }
            }
        }
        return res;
    }

    @Override
    public ArrayList<ProductPlacement> findProductInStorehouseById(Storehouse idStorehouse, Integer integer) throws RemoteException, NullPointerException {
        ArrayList<ProductPlacement> allProducts = ProductPlacementManager.readProductPlacements();
        ArrayList<ProductPlacement> productsInStorehouse = new ArrayList<>();
        for (ProductPlacement pp : allProducts) {
            if (pp.getStorehouse().getId().compareTo(idStorehouse.getId()) == 0 && pp.getProduct().getUniqueCode().compareTo(integer) == 0) {
                productsInStorehouse.add(pp);
            }
        }

        if (productsInStorehouse.isEmpty()) {
            throw new NullPointerException();
        }
        return productsInStorehouse;
    }


    private void checkProcurementStatus(Procurement procurement) {
        ArrayList<ProcurementProduct> procurementProduct = null;
        try {
            procurementProduct = this.getProcurementProductByProcur(procurement);
            for (ProcurementProduct o : procurementProduct) {
                if (o.getStatus()) continue;
                else return;
            }
            procurement.setStatus(true);
            ProcurementManager.updateProcurement(procurement);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private ProductPlacement findCell(Storehouse storehouse) {
        ProductPlacement res = new ProductPlacement();
        for (int i = 1; i <= storehouse.getMaxRows(); i++) {
            for (int j = 1; j <= storehouse.getMaxCells(); j++) {
                if (isCellFree(storehouse, i, j)) {
                    res.setPlace(j);
                    res.setRack(i);
                    res.setStorehouse(storehouse);
                    return res;
                }
            }
        }
        return null;
    }

    private boolean isCellFree(Storehouse storehouse, Integer rack, Integer place) {
        ArrayList<ProductPlacement> placementArrayList = ProductPlacementManager.readProductPlacements();
        ArrayList<ProductPlacement> filledPlacementArr = new ArrayList<ProductPlacement>();
        for (ProductPlacement l : placementArrayList) {
            if (l.getStorehouse().getId().compareTo(storehouse.getId()) == 0) {
                filledPlacementArr.add(l);
            }
        }
        for (ProductPlacement o : filledPlacementArr) {
            if (o.getPlace() == place && o.getRack() == rack)
                return false;
        }
        return true;
    }

}
