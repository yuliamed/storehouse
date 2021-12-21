package uni.example.storehouse.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Storehouse implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;

    private Integer id;
    private String name;
    private Integer maxCells;
    private Integer maxRows;

    public Storehouse(Integer id, String name, Integer maxCells, Integer maxRows) {
        this.id = id;
        this.name = name;
        this.maxCells = maxCells;
        this.maxRows = maxRows;
    }

    public Storehouse() {
    }
//    private List<ProductPlacement> productPlacements = new ArrayList<ProductPlacement>();
//
//    public List<ProductPlacement> getProductPlacements() {
//        return productPlacements;
//    }
//
//    public void setProductPlacements(ArrayList<ProductPlacement> productPlacements) {
//        this.productPlacements = productPlacements;
//    }

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
