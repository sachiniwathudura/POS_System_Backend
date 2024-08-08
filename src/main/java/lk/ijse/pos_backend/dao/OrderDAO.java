package lk.ijse.pos_backend.dao;

import lk.ijse.pos_backend.entity.Orders;

import java.sql.Connection;
import java.sql.SQLException;

public interface OrderDAO extends CrudDAO<Orders> {
    String getLastId(Connection connection) throws SQLException;
}
