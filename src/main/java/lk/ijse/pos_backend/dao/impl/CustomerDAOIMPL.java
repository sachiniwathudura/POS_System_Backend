package lk.ijse.pos_backend.dao.impl;

import lk.ijse.pos_backend.dao.CrudUtil;
import lk.ijse.pos_backend.dao.CustomerDAO;
import lk.ijse.pos_backend.entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public final class CustomerDAOIMPL implements CustomerDAO {

    @Override
    public ArrayList<Customer> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        String sql = ("SELECT * FROM customer");
        ArrayList<Customer> customerList = new ArrayList<Customer>();
        ResultSet rst = CrudUtil.execute(connection, sql);

        while (rst.next()){

            Customer customer = new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4)
                            );
            customerList.add(customer);
        }
        return customerList;
    }

    @Override
    public boolean save(Connection connection, Customer entity) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO customer (id,name,address,salary) VALUES(?,?,?,?)";
        return CrudUtil.execute(connection,sql,entity.getId(),entity.getName(),entity.getAddress(),entity.getSalary());
    }

    @Override
    public boolean update(Connection connection, Customer entity) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE customer SET name = ?, address = ?,salary = ? WHERE id = ?";
        return CrudUtil.execute(connection,sql,entity.getName(),entity.getAddress(),entity.getSalary(),entity.getId());
    }

    @Override
    public boolean delete(Connection connection, String id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM customer WHERE id=?";
        return CrudUtil.execute(connection,sql,id);
    }

    @Override
    public Customer search(Connection connection, String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM customer WHERE id=?";
        Customer customer= new Customer();
        ResultSet rst = CrudUtil.execute(connection,sql,id);
        if(rst.next()){
            customer.setId(rst.getString(1));
            customer.setName(rst.getString(2));
            customer.setAddress(rst.getString(3));
            customer.setSalary(Double.valueOf(rst.getString(4)));
        }
        return customer;
        }
    public boolean exists(Connection connection, String custId) throws SQLException {
        String query = "SELECT 1 FROM customer WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, custId);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        }
    }

}
