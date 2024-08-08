package lk.ijse.pos_backend.bo.impl;

import lk.ijse.pos_backend.bo.CustomerBO;
import lk.ijse.pos_backend.dao.CustomerDAO;

import lk.ijse.pos_backend.dao.impl.CustomerDAOIMPL;
import lk.ijse.pos_backend.dto.CustomerDTO;
import lk.ijse.pos_backend.entity.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOIMPL implements CustomerBO {

    CustomerDAO customerDAO =  new CustomerDAOIMPL();
    @Override
    public boolean saveCustomer(Connection connection, CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.save(connection,new Customer(dto.getId(), dto.getName(), dto.getAddress(), dto.getSalary()));
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customerArrayList = customerDAO.getAll(connection);
        ArrayList<CustomerDTO> customerDTOArrayList = new ArrayList<>();
        for (Customer customer:customerArrayList) {
            CustomerDTO dto = new CustomerDTO(
                    customer.getId(),
                    customer.getName(),
                    customer.getAddress(),
                    customer.getSalary()
                    );
            customerDTOArrayList.add(dto);
        }
        return customerDTOArrayList;
    }

    @Override
    public CustomerDTO getCustomerById(Connection connection, String id) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.search(connection,id);
        return new CustomerDTO(
                customer.getId(),
                customer.getName(),
                customer.getAddress(),
                customer.getSalary()
        );
    }

    @Override
    public boolean updateCustomer(Connection connection, CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(connection,new Customer(dto.getId(),dto.getName(), dto.getAddress(), dto.getSalary()));
    }

    @Override
    public boolean deleteCustomer(Connection connection, String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(connection,id);
    }
}
