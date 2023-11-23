package com.yaps.petstore.server.domain.item;

import com.yaps.petstore.server.domain.PersistentObject;
import com.yaps.petstore.server.domain.product.Product;
import com.yaps.petstore.server.util.persistence.AbstractDataAccessObject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class does all the database access for the class Item.
 *
 * @see Item
 */
final class ItemDAO extends AbstractDataAccessObject {

    // ======================================
    // =             Attributes             =
    // ======================================
    private static final String TABLE = "T_ITEM";
    private static final String COLUMNS = "ID, NAME, UNITCOST , PRODUCT_FK";

    // ======================================
    // =           Business methods         =
    // ======================================
    protected String getInsertSqlStatement(final PersistentObject object) {
        final Item item = (Item) object;
        final String sql;
        sql = "INSERT INTO " + TABLE + "(" + COLUMNS + ") VALUES ('" + item.getId() + "', '" + item.getName() + "', '" + item.getUnitCost() + "', '" + item.getProduct().getId() + "' )";
        return sql;
    }

    protected String getDeleteSqlStatement(final String id) {
        final String sql;
        sql = "DELETE FROM " + TABLE + " WHERE ID = '" + id + "'";
        return sql;
    }

    protected String getUpdateSqlStatement(final PersistentObject object) {
        final Item item = (Item) object;
        final String sql;
        sql = "UPDATE " + TABLE + " SET NAME = '" + item.getName() + "', UNITCOST = '" + item.getUnitCost() + "', PRODUCT_FK = '" + item.getProduct().getId() + "' WHERE ID = '" + item.getId() + "' ";
        return sql;
    }

    protected String getSelectSqlStatement(final String id) {
        final String sql;
        sql = "SELECT " + COLUMNS + " FROM " + TABLE + " WHERE ID = '" + id + "' ";
        return sql;
    }

    protected String getSelectAllSqlStatement() {
        final String sql;
        sql = "SELECT " + COLUMNS + " FROM " + TABLE;
        return sql;
    }

    protected PersistentObject transformResultset2PersistentObject(final ResultSet resultSet) throws SQLException {
        final Item item;
        item = new Item(resultSet.getString(1), resultSet.getString(2), resultSet.getDouble(3), new Product(resultSet.getString(4)));
        return item;
    }
}
