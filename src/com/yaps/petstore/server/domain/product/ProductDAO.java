package com.yaps.petstore.server.domain.product;

import com.yaps.petstore.server.domain.PersistentObject;
import com.yaps.petstore.server.domain.category.Category;
import com.yaps.petstore.server.util.persistence.AbstractDataAccessObject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class does all the database access for the class Product.
 *
 * @see Product
 */
final class ProductDAO extends AbstractDataAccessObject {

    // ======================================
    // =             Attributes             =
    // ======================================
    private static final String TABLE = "T_PRODUCT";
    private static final String COLUMNS = "ID, NAME, DESCRIPTION, CATEGORY_FK";

    // ======================================
    // =           Business methods         =
    // ======================================
    protected String getInsertSqlStatement(final PersistentObject object) {
        final Product product = (Product) object;
        final String sql;
        sql = "INSERT INTO " + TABLE + "(" + COLUMNS + ") VALUES ('" + product.getId() + "', '" + product.getName() + "', '" + product.getDescription() + "', '" + product.getCategory().getId() + "' )";
        return sql;
    }

    protected String getDeleteSqlStatement(final String id) {
        final String sql;
        sql = "DELETE FROM " + TABLE + " WHERE ID = '" + id + "'";
        return sql;
    }

    protected String getUpdateSqlStatement(final PersistentObject object) {
        final Product product = (Product) object;
        final String sql;
        sql = "UPDATE " + TABLE + " SET NAME = '" + product.getName() + "', DESCRIPTION = '" + product.getDescription() + "', CATEGORY_FK = '" + product.getCategory().getId() + "' WHERE ID = '" + product.getId() + "' ";
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
        final Product product;
        product = new Product(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), new Category(resultSet.getString(4)));
        return product;
    }
}
