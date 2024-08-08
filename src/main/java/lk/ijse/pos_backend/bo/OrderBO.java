package lk.ijse.pos_backend.bo;

import lk.ijse.pos_backend.dto.OrdersDTO;

import java.sql.Connection;
import java.sql.SQLException;

public interface OrderBO {
    boolean placeOrder(Connection connection, OrdersDTO orderDTO) throws SQLException;

    String getLastId(Connection connection) throws SQLException;

    OrdersDTO getOrderById(Connection connection, String id) throws SQLException, ClassNotFoundException;
}
