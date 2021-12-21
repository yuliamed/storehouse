package uni.example.storehouse.entities;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "productplacement")
public class ProductPlacement implements Serializable{
    private static final long serialVersionUID = 6529685098267757690L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "productPlacementId")
    private Integer productPlacementId;

    @Column(name="place")
    private Integer place;

    @Column(name="rack")
    private Integer rack;

    @ManyToOne()
    @JoinColumn(name="storehouse_Id")
    private Storehouse storehouse;

    @ManyToOne()
    @JoinColumn(name="product_Id")
    private Product product;

    public ProductPlacement() {
    }

    public Integer getProductPlacementId() {
        return productPlacementId;
    }

    public void setProductPlacementId(Integer productPlacementId) {
        this.productPlacementId = productPlacementId;
    }

    public Integer getPlace() {
        return place;
    }

    public void setPlace(Integer place) {
        this.place = place;
    }

    public Integer getRack() {
        return rack;
    }

    public void setRack(Integer rack) {
        this.rack = rack;
    }

    public Storehouse getStorehouse() {
        return storehouse;
    }

    public void setStorehouse(Storehouse storehouse) {
        this.storehouse = storehouse;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
