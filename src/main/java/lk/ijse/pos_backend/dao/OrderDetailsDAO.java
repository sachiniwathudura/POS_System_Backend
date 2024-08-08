package lk.ijse.pos_backend.dao;

import lk.ijse.pos_backend.entity.OrderDetails;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderDetailsDAO extends CrudDAO<OrderDetails> {
    List<OrderDetails> getAllById(Connection connection, String id) throws SQLException;
}
