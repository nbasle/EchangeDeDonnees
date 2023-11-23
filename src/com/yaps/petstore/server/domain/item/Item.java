package com.yaps.petstore.server.domain.item;

import com.yaps.petstore.common.exception.CheckException;
import com.yaps.petstore.server.domain.PersistentObject;
import com.yaps.petstore.server.domain.product.Product;

/**
 * This class represents an Item in the catalog of the YAPS company.
 * The catalog is divided into catagories. Each one divided into products
 * and each product in items.
 */
public final class Item extends PersistentObject {

    // ======================================
    // =             Attributes             =
    // ======================================
    private String _name;
    private double _unitCost;
    private Product _product;

    // ======================================
    // =            Constructors            =
    // ======================================
    {
        _dao = new ItemDAO();
    }

    public Item() {
    }

    public Item(final String id) {
        setId(id);
    }

    public Item(final String id, final String name, final double unitCost, final Product product) {
        setId(id);
        setName(name);
        setUnitCost(unitCost);
        setProduct(product);
    }

    // ======================================
    // =          Protected methods         =
    // ======================================
    protected void loadObject(final Object object) {
        final Item temp = (Item) object;

        // Sets data to current object
        setId(temp.getId());
        setName(temp.getName());
        setUnitCost(temp.getUnitCost());
        setProduct(temp.getProduct());
    }

    protected void checkData() throws CheckException {
        checkId(getId());
        if (getName() == null || "".equals(getName()))
            throw new CheckException("Invalid name");
        if (getUnitCost() <= 0)
            throw new CheckException("Invalid unit cost");
        if (getProduct() == null || getProduct().getId() == null || "".equals(getProduct().getId()))
            throw new CheckException("Invalid product");
    }

    // ======================================
    // =         Getters and Setters        =
    // ======================================
    public String getName() {
        return _name;
    }

    public void setName(final String name) {
        _name = name;
    }

    public double getUnitCost() {
        return _unitCost;
    }

    public void setUnitCost(final double unitCost) {
        _unitCost = unitCost;
    }

    public Product getProduct() {
        return _product;
    }

    public void setProduct(final Product product) {
        _product = product;
    }

    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("Item{");
        buf.append("id=").append(getId());
        buf.append(",name=").append(getName());
        buf.append(",unitCost=").append(getUnitCost());
        buf.append('}');
        return buf.toString();
    }
}
