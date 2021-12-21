package uni.example.storehouse.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "storehouse")
public class Storehouse implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "storehouseid")
    private Integer id;
    @Column(name = "adress")
    private String name;
    @Column(name = "maxcells")
    private Integer maxCells;
    @Column(name = "maxrows")
    private Integer maxRows;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "storehouse", fetch=FetchType.EAGER)
    private List<ProductPlacement> productPlacements = new ArrayList<>();

    public void setProductPlacements(List<ProductPlacement> productPlacements) {
        this.productPlacements = productPlacements;
    }


    public List<ProductPlacement> getProductPlacements() {
        return this.productPlacements;
    }


    public Storehouse() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getMaxCells() {
        return maxCells;
    }

    public Integer getMaxRows() {
        return maxRows;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaxCells(Integer maxCells) {
        this.maxCells = maxCells;
    }

    public void setMaxRows(Integer maxRows) {
        this.maxRows = maxRows;
    }
}
