package uni.example.storehouse.entities;

import java.io.Serializable;


public class ProcurementProduct implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    private ProcurementProductKey key = new ProcurementProductKey();

    private Integer count;
    private Boolean status =  false;
    private String strProduct;

    public String getStrProduct() {
        return key.getProduct().getName() + "_" + key.getProduct().getUniqueCode();
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
    public ProcurementProductKey getKey() {
        return key;
    }

    public void setKey(ProcurementProductKey key) {
        this.key = key;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
    public Product getProduct() {
        return getKey().getProduct();
    }

    public void setProduct(Product p) {
        getKey().setProduct(p);
    }


    public Procurement getProcurement() {
        return getKey().getProcurement();
    }

    public void setProcurement(Procurement p) {
        getKey().setProcurement(p);
    }
}

class ProcurementProductKey implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    public ProcurementProductKey() {
    }

    private Procurement procurement;

    private Product product;

    public Procurement getProcurement() {
        return procurement;
    }

    public void setProcurement(Procurement procurement) {
        this.procurement = procurement;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }
}

