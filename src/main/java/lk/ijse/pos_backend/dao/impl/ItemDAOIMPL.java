package lk.ijse.pos_backend.dao.impl;
;
import lk.ijse.pos_backend.dao.CrudUtil;
import lk.ijse.pos_backend.dao.ItemDAO;
import lk.ijse.pos_backend.entity.Item;

import java.sql.Connection;
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
                    rst.getDouble(4)
            );
            itemArrayList.add(item);
        }
        return itemArrayList;
    }

    @Override
    public boolean save(Connection connection, Item entity) throws SQLException, ClassNotFoundException {
        String sql = "INSERT TNTO item(ItemId,ItemName,ItemQty,ItemPrice)VALUES(?,?,?,?)";
        return CrudUtil.execute(connection,sql,entity.getItemId(),entity.getItemName(),entity.getItemQty(),entity.getItemPrice());
    }

    @Override
    public boolean update(Connection connection, Item entity) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE item SET ItemName=?,ItemQty=?,ItemPrice=? WHERE ItemId=?";
        return CrudUtil.execute(connection,sql,entity.getItemName(),entity.getItemPrice(),entity.getItemQty(),entity.getItemId());
    }

    @Override
    public boolean delete(Connection connection, String id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM item WHERE ItemId=?";
        return CrudUtil.execute(connection,sql,id);
    }
    @Override
    public Item search(Connection connection, String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM item WHERE ItemId=?";
        Item item = new Item();
        ResultSet rst = CrudUtil.execute(connection,sql,id);
        if(rst.next()){
            item.setItemId(rst.getString(1));
            item.setItemName(rst.getString(2));
            item.setItemQty(rst.getInt(3));
            item.setItemPrice(rst.getDouble(4));
        }
        return item;
    }
}
