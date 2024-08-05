package lk.ijse.pos_backend.bo;

import lk.ijse.pos_backend.dto.CustomerDTO;
import lk.ijse.pos_backend.dto.ItemDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO{
    boolean saveItem(Connection connection, ItemDTO dto) throws SQLException, ClassNotFoundException;

    ArrayList<ItemDTO> getAlItem(Connection connection) throws SQLException, ClassNotFoundException;

    ItemDTO getItemById(Connection connection, String id) throws SQLException, ClassNotFoundException;

    boolean updateItem(Connection connection, ItemDTO dto) throws SQLException, ClassNotFoundException;

    boolean deleteItem(Connection connection, String id) throws SQLException, ClassNotFoundException;

}
