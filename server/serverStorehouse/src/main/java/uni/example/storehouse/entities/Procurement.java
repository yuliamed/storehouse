package uni.example.storehouse.entities;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "procurement")
public class Procurement implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    @Id
    @Column(name = "idProcurement")
    private Integer idProcurement;

    @Column(name = "status")
    private Boolean status = false;

    @ManyToOne()
    @JoinColumn(name = "providerId")
    private Provider provider;

    @ManyToOne()
    @JoinColumn(name = "managerLogin")
    private User managerLogin;

    @Transactional
    public Set<ProcurementProduct> getProcurementProducts() {
        return procurementProducts;
    }
    @Transactional
    public void setProcurementProducts(Set<ProcurementProduct> procurementProducts) {
        this.procurementProducts = procurementProducts;
    }
    @OneToMany(mappedBy = "key.procurement")
    private Set<ProcurementProduct> procurementProducts;

    public Procurement() {

    }

    public Integer getIdProcurement() {
        return idProcurement;
    }

    public void setIdProcurement(Integer idProcurement) {
        this.idProcurement = idProcurement;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public User getManagerLogin() {
        return managerLogin;
    }

    public void setManagerLogin(User managerLogin) {
        this.managerLogin = managerLogin;
    }
}
