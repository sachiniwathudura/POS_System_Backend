package lk.ijse.pos_backend.dao.impl;
;
import lk.ijse.pos_backend.dao.CrudUtil;

import lk.ijse.pos_backend.dao.ItemDAO;
import lk.ijse.pos_backend.entity.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOIMPL implements ItemDAO {
    @Override
    public ArrayList<Item> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        String sql = ("SELECT * FROM item");
        ArrayList<Item> itemArrayList = new ArrayList<Item>();
        ResultSet rst = CrudUtil.execute(connection, sql);

        while (rst.next()){
            Item item = new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getBigDecimal(4)
            );
            itemArrayList.add(item);
        }
        return itemArrayList;
    }

    @Override
    public boolean save(Connection connection, Item entity) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO item(code,name,qty,price)VALUES(?,?,?,?)";
        return CrudUtil.execute(connection,sql,entity.getCode(),entity.getName(),entity.getQty(),entity.getPrice());
    }

    @Override
    public boolean update(Connection connection, Item entity) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE item SET name=?,qty=?,price=? WHERE code=?";
        return CrudUtil.execute(connection,sql,entity.getName(),entity.getPrice(),entity.getQty(),entity.getCode());
    }

    @Override
    public boolean delete(Connection connection, String id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM item WHERE code=?";
        return CrudUtil.execute(connection,sql,id);
    }
    @Override
    public Item search(Connection connection, String id) throws SQLException, ClassNotFoundException {
//        String sql = "SELECT * FROM item WHERE code=?";
//        Item item = new Item(details.getItem_code(), details.getQty());
//        ResultSet rst = CrudUtil.execute(connection,sql,id);
//        if(rst.next()){
//            item.setCode(rst.getString(1));
//            item.setName(rst.getString(2));
//            item.setQty(Integer.parseInt(rst.getString(3)));
//            item.setPrice(rst.getBigDecimal(4));
//        }
//        return item;
        String sql = "SELECT * FROM item WHERE code = ?";
        Item item = new Item();
        ResultSet rst = CrudUtil.execute(connection, sql, id);

        if (rst.next()) {
            item.setCode(rst.getString(1));
            item.setName(rst.getString(2));
            item.setQty(Integer.parseInt(rst.getString(3)));
            item.setPrice(rst.getBigDecimal(4));

        }
        return item;
    }
    @Override
    public boolean reduceQty(Connection connection, Item item) throws SQLException {
        String sql = "UPDATE item SET qty = (qty - ?) WHERE code = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            System.out.println("Executing SQL: " + sql);
            System.out.println("Parameters: qty = " + item.getQty() + ", code = " + item.getCode());
            pst.setInt(1, item.getQty());
            pst.setString(2, item.getCode());
            int result = pst.executeUpdate();
            System.out.println("Update result: " + result);
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to reduce quantity for item: " + item.getCode(), e);
        }
    }

    public boolean exists(Connection connection, String itemCode) throws SQLException {
        String query = "SELECT 1 FROM item WHERE code = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, itemCode);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        }
    }
}
