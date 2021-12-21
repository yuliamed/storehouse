package uni.example.storehouse.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Product implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;

    private Integer uniqueCode;

    private  String name;
    private String measureUnit;

    private Double price;
    private Double VAT;
    private Double mass;

//    private Set<ProcurementProduct> procurementProducts = new HashSet<>();
//    public Set<ProcurementProduct> getProcurementProducts() {
//        return procurementProducts;
//    }
//
//    public void setProcurementProducts(Set<ProcurementProduct> procurementProducts) {
//        this.procurementProducts = procurementProducts;
//    }

    public Product() {
    }

    public Integer getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(Integer uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getVAT() {
        return VAT;
    }

    public void setVAT(Double VAT) {
        this.VAT = VAT;
    }

    public Double getMass() {
        return mass;
    }

    public void setMass(Double mass) {
        this.mass = mass;
    }
}