package lk.ijse.pos_backend.dao;

import lk.ijse.pos_backend.dto.CustomerDTO;
import lk.ijse.pos_backend.entity.Customer;

import java.sql.Connection;
import java.sql.SQLException;

public interface CustomerDAO extends CrudDAO<Customer>{
    boolean exists(Connection connection, String custId) throws SQLException;
}

