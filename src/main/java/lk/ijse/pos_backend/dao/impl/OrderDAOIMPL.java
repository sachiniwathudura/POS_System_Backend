package lk.ijse.pos_backend.dao.impl;

import lk.ijse.pos_backend.dao.CrudUtil;
import lk.ijse.pos_backend.dao.OrderDAO;
import lk.ijse.pos_backend.entity.Orders;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOIMPL implements OrderDAO {
    @Override
    public ArrayList<Orders> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM orders";
        ArrayList<Orders> orderList = new ArrayList<Orders>();
        ResultSet rst = CrudUtil.execute(connection, sql);

        while (rst.next()) {
            Orders orders = new Orders(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDate(3).toLocalDate(),
                    rst.getBigDecimal(4),
                    rst.getBigDecimal(5)

            );

            orderList.add(orders);
        }
        return orderList;
    }

    @Override
    public boolean save(Connection connection, Orders entity) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO orders (order_id, date, cust_id, discount, total) VALUES( ?, ?, ?, ?, ? )";
        return CrudUtil.execute(connection, sql, entity.getOrder_id(), entity.getDate(), entity.getCust_id(), entity.getDiscount(), entity.getTotal());

    }

    @Override
    public boolean update(Connection connection, Orders entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Connection connection, String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Orders search(Connection connection, String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM orders WHERE order_id = ?";
        ResultSet rs = CrudUtil.execute(connection, sql, id);

        Orders orders = new Orders();
        System.out.println(orders);
        if (rs.next()) {
            orders.setOrder_id(rs.getString("order_id"));
            orders.setDate(rs.getDate("date").toLocalDate());
            orders.setCust_id(rs.getString("cust_id"));
            orders.setDiscount(rs.getBigDecimal("discount"));
            orders.setTotal(rs.getBigDecimal("total"));
        }
        return orders;
    }

    @Override
    public String getLastId(Connection connection) throws SQLException {
        String sql = "SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1";
        ResultSet rs = CrudUtil.execute(connection, sql);

        String lastId = "no_ids";
        if (rs.next()) {
            lastId = rs.getString(1);
        }
        return lastId;
    }
}
