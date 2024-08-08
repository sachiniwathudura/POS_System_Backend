package lk.ijse.pos_backend.dao;

import lk.ijse.pos_backend.entity.Item;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;


public interface ItemDAO extends CrudDAO<Item>{
    boolean reduceQty(Connection connection, Item item) throws SQLException;

    boolean exists(Connection connection, String itemCode) throws SQLException;
}
