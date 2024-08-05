package lk.ijse.pos_backend.bo;

import lk.ijse.pos_backend.dto.CustomerDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO{
    boolean saveCustomer(Connection connection, CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;

    ArrayList<CustomerDTO> getAllCustomers(Connection connection) throws SQLException, ClassNotFoundException;

    CustomerDTO getCustomerById(Connection connection, String id) throws SQLException, ClassNotFoundException;

    boolean updateCustomer(Connection connection, CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;

    boolean deleteCustomer(Connection connection, String id) throws SQLException, ClassNotFoundException;

}
