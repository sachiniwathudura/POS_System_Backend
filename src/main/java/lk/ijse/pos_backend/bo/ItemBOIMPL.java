package lk.ijse.pos_backend.bo;

import lk.ijse.pos_backend.dao.ItemDAO;
import lk.ijse.pos_backend.dto.ItemDTO;
import lk.ijse.pos_backend.entity.Item;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOIMPL implements ItemBO{

    ItemDAO itemDAO = (ItemDAO) new ItemBOIMPL();
    @Override
    public boolean saveItem(Connection connection, ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.save(connection,new Item(dto.getItemId(), dto.getItemName(), dto.getItemQty(), dto.getItemPrice()));
    }

    @Override
    public ArrayList<ItemDTO> getAlItem(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<Item> itemArrayList = itemDAO.getAll(connection);
        ArrayList<ItemDTO> itemDTOArrayList = new ArrayList<>();
        for (Item item:itemArrayList) {
            ItemDTO dto = new ItemDTO(
                    item.getItemId(),
                    item.getItemName(),
                    item.getItemQty(),
                    item.getItemPrice()
            );
            itemDTOArrayList.add(dto);
        }
        return itemDTOArrayList;
    }

    @Override
    public ItemDTO getItemById(Connection connection, String id) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.search(connection, id);
        return new ItemDTO(
                item.getItemId(),
                item.getItemName(),
                item.getItemQty(),
                item.getItemPrice()
        );
    }

    @Override
    public boolean updateItem(Connection connection, ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.update(connection,new Item(dto.getItemId(), dto.getItemName(), dto.getItemQty(), dto.getItemPrice()));
    }

    @Override
    public boolean deleteItem(Connection connection, String id) throws SQLException, ClassNotFoundException {
       return itemDAO.delete(connection, id);
    }
}
