package lk.ijse.pos_backend.bo.impl;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import lk.ijse.pos_backend.bo.OrderBO;
import lk.ijse.pos_backend.dao.*;
import lk.ijse.pos_backend.dto.OrderDetailsDTO;
import lk.ijse.pos_backend.dto.OrdersDTO;
import lk.ijse.pos_backend.entity.Item;
import lk.ijse.pos_backend.entity.OrderDetails;
import lk.ijse.pos_backend.entity.Orders;

import java.sql.Connection;
import java.sql.SQLException;

public class OrderBOIMPL implements OrderBO {
    CustomerDAO customerDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER_DAO);
    OrderDAO orderDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_DAO);
    OrderDetailsDAO orderDetailsDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAILS_DAO);
    ItemDAO itemDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM_DAO);

    @Override
    public boolean placeOrder(Connection connection, OrdersDTO dto) throws SQLException {
        try {
            connection.setAutoCommit(false);

            // Check if customer exists
            boolean customerExists = customerDAO.exists(connection, dto.getCust_id());
            if (!customerExists) {
                throw new SQLException("Customer ID does not exist: " + dto.getCust_id());
            }

            // Save order
            boolean isOrderSave = orderDAO.save(connection, new Orders(dto.getOrder_id(), dto.getCust_id(), dto.getDate(), dto.getDiscount(), dto.getTotal()));
            if (!isOrderSave) {
                throw new SQLException("Failed to save order");
            }

            // Save order details and update item quantities
            for (OrderDetailsDTO details : dto.getOrder_list()) {
                // Check if item exists and has sufficient quantity
                boolean itemExists = itemDAO.exists(connection, details.getItem_code());
                if (!itemExists) {
                    throw new SQLException("Item code does not exist: " + details.getItem_code());
                }

                Item item = itemDAO.search(connection, details.getItem_code());
                if (item.getQty() < details.getQty()) {
                    throw new SQLException("Insufficient quantity for item: " + details.getItem_code());
                }

                // Save order detail
                boolean isOrderDetailSaved = orderDetailsDAO.save(connection, new OrderDetails(details.getOrder_id(), details.getItem_code(), details.getUnit_price(), details.getQty()));
                if (!isOrderDetailSaved) {
                    throw new SQLException("Failed to save order detail for item: " + details.getItem_code());
                }

                // Reduce item quantity
//                boolean isQtyReduced = itemDAO.reduceQty(connection, new Item(details.getItem_code(), details.getQty()));
//                if (!isQtyReduced) {
//                    throw new SQLException("Failed to reduce quantity for item: " + details.getItem_code());
//                }
            }

            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
            return false;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getLastId(Connection connection) throws SQLException {
        return orderDAO.getLastId(connection);
    }

    @Override
    public OrdersDTO getOrderById(Connection connection, String id) throws SQLException, ClassNotFoundException {
        Orders orders = orderDAO.search(connection, id);
        System.out.println("getOrderById ::"+orders);
        return new OrdersDTO(
                orders.getOrder_id(),
                orders.getDate(),
                orders.getCust_id(),
                orders.getDiscount(),
                orders.getTotal()
        );
    }
}
