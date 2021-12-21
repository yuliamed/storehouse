package uni.example.storehouse.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ProcurementProduct")
@AssociationOverrides({
        @AssociationOverride(name = "key.product",
                joinColumns = @JoinColumn(name = "id_product")),
        @AssociationOverride(name = "key.procurement",
                joinColumns = @JoinColumn(name = "id_procurement"))})
public class ProcurementProduct implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    @EmbeddedId
    private ProcurementProductKey key = new ProcurementProductKey();

    private Integer count;
    private Boolean status =  false;

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

    @Transient
    public Product getProduct() {
        return getKey().getProduct();
    }

    public void setProduct(Product p) {
        getKey().setProduct(p);
    }

    @Transient
    public Procurement getProcurement() {
        return getKey().getProcurement();
    }

    public void setProcurement(Procurement p) {
        getKey().setProcurement(p);
    }
}

@Embeddable
class ProcurementProductKey implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    public ProcurementProductKey() {
    }

    @ManyToOne
    // @JoinColumn(name = "idprocurement")
    private Procurement procurement;

    @ManyToOne
    // @JoinColumn(name = "id_product")
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
