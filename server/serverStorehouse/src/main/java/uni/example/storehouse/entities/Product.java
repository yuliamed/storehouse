package uni.example.storehouse.entities;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "product")
public class Product implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;

    @Id
    @Column(name = "uniqueCode")
    private Integer uniqueCode;

    @Column(name = "name")
    private  String name;
    @Column(name = "measureUnit")
    private String measureUnit;

    @Column(name = "price")
    private Double price;
    @Column(name = "VAT")
    private Double VAT;
    @Column(name = "mass")
    private Double mass;

    //@ManyToMany
    //@JoinTable(name="procurementproduct", joinColumns=@JoinColumn(name="id_Product"), inverseJoinColumns = @JoinColumn(name="id_procurement"))
    @OneToMany(mappedBy = "key.product")
    private Set<ProcurementProduct> procurementProducts = new HashSet<>();
    @Transactional
    public Set<ProcurementProduct> getProcurementProducts() {
        return procurementProducts;
    }
    @Transactional
    public void setProcurementProducts(Set<ProcurementProduct> procurementProducts) {
        this.procurementProducts = procurementProducts;
    }

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
