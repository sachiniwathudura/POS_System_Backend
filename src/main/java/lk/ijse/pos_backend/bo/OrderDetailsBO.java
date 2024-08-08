package lk.ijse.pos_backend.bo;

import lk.ijse.pos_backend.dto.OrdersDTO;

import java.sql.Connection;
import java.sql.SQLException;

public interface OrderDetailsBO extends SuperBO{
    OrdersDTO getOrderDetailsById(Connection connection, String id) throws SQLException;
}
