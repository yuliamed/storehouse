package uni.example.storehouse.entities;

import java.io.Serializable;
import java.util.Set;

public class Procurement implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;

    private Integer idProcurement;

    private Boolean status = false;

    private Provider provider;
    transient private String providerStr;

    transient private String managerStr;


    public String getManagerStr() {
        return managerLogin.getLogin();
    }

    public String getProviderStr() {
        return provider.getProviderName();
    }

    private User managerLogin;


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
