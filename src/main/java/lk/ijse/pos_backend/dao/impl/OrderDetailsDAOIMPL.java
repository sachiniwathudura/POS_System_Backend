package lk.ijse.pos_backend.dao.impl;

import lk.ijse.pos_backend.dao.CrudUtil;
import lk.ijse.pos_backend.dao.OrderDetailsDAO;
import lk.ijse.pos_backend.entity.OrderDetails;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsDAOIMPL implements OrderDetailsDAO {
    @Override
    public ArrayList<OrderDetails> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM order_details";
        ArrayList<OrderDetails> orderDetailsList = new ArrayList<OrderDetails>();
        ResultSet rst = CrudUtil.execute(connection, sql);

        while (rst.next()) {
            OrderDetails orderDetails = new OrderDetails(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getBigDecimal(3),
                    rst.getInt(4)

            );

            orderDetailsList.add(orderDetails);
        }
        return orderDetailsList;
    }

    @Override
    public boolean save(Connection connection, OrderDetails entity) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO order_details (order_id,item_code,unit_price,qty) VALUES (?,?,?,?)";
        return CrudUtil.execute(connection, sql, entity.getOrder_id(), entity.getItem_code(),entity.getUnit_price(), entity.getQty());

    }

    @Override
    public boolean update(Connection connection, OrderDetails entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Connection connection, String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public OrderDetails search(Connection connection, String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM order_details WHERE order_id = ?";
        ResultSet rs = CrudUtil.execute(connection, sql, id);

        OrderDetails orderDetails = new OrderDetails();
        System.out.println(orderDetails);
        if (rs.next()) {
            orderDetails.setOrder_id(rs.getString("order_id"));
            orderDetails.setItem_code("item_code");
            orderDetails.setUnit_price("unit_price");
            orderDetails.setQty(Integer.parseInt("qty"));

        }
        return orderDetails;
    }

    @Override
    public List<OrderDetails> getAllById(Connection connection, String id) throws SQLException {
        String sql = "SELECT item_code, unit_price, qty FROM order_details WHERE order_id = ?";
        ResultSet rs = CrudUtil.execute(connection, sql, id);

        List<OrderDetails> orderDetailList = new ArrayList<>();
        while (rs.next()) {
            OrderDetails orderDetails = new OrderDetails(
                    rs.getString("item_code"),  // Assuming column name is item_code
                    rs.getBigDecimal("unit_price"), // Assuming column name is unit_price
                    rs.getInt("qty") // Assuming column name is qty
            );
            orderDetailList.add(orderDetails);
        }
        return orderDetailList;
    }
}
