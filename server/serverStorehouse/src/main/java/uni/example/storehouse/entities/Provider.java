package uni.example.storehouse.entities;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "provider")
public class Provider implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idProvider")
    private Integer idProvider;
    @Column(name = "providerName")
    private String providerName;
    @Column(name = "providerAdress")
    private String providerAdress;
    @Column(name = "providerPhNumb")
    private Integer providerPhNumber;

    public Provider() {
    }

    public Integer getIdProvider() {
        return idProvider;
    }

    public void setIdProvider(Integer idProvider) {
        this.idProvider = idProvider;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderAdress() {
        return providerAdress;
    }

    public void setProviderAdress(String providerAdress) {
        this.providerAdress = providerAdress;
    }

    public Integer getProviderPhNumber() {
        return providerPhNumber;
    }

    public void setProviderPhNumber(Integer providerPhNumber) {
        this.providerPhNumber = providerPhNumber;
    }
}
