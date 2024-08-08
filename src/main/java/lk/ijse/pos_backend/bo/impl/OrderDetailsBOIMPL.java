package lk.ijse.pos_backend.bo.impl;

import lk.ijse.pos_backend.bo.OrderDetailsBO;
import lk.ijse.pos_backend.dao.DAOFactory;
import lk.ijse.pos_backend.dao.OrderDAO;
import lk.ijse.pos_backend.dao.OrderDetailsDAO;
import lk.ijse.pos_backend.dao.impl.OrderDAOIMPL;
import lk.ijse.pos_backend.dto.OrderDetailsDTO;
import lk.ijse.pos_backend.dto.OrdersDTO;
import lk.ijse.pos_backend.entity.OrderDetails;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsBOIMPL implements OrderDetailsBO {

    OrderDAO orderDAO = (OrderDAO) new OrderDAOIMPL();
    OrderDetailsDAO orderDetailsDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAILS_DAO);

    @Override
    public OrdersDTO getOrderDetailsById(Connection connection, String id) throws SQLException {
        String sql = "SELECT * FROM orders WHERE order_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    LocalDate date = rs.getDate("date").toLocalDate();
                    BigDecimal discount = rs.getBigDecimal("discount");
                    BigDecimal total = rs.getBigDecimal("total");

                    OrdersDTO orderDTO = new OrdersDTO();
                    orderDTO.setOrder_id(rs.getString("order_id"));
                    orderDTO.setCust_id(rs.getString("cust_id"));
                    orderDTO.setDate(date);
                    orderDTO.setDiscount(discount);
                    orderDTO.setTotal(total);

                    List<OrderDetails> orderDetailList = orderDetailsDAO.getAllById(connection, id);
                    List<OrderDetailsDTO> orderDetailDTOList = new ArrayList<>();
                    for (OrderDetails orderDetail : orderDetailList) {
                        orderDetailDTOList.add(new OrderDetailsDTO(
                                orderDetail.getItem_code(),
                                orderDetail.getUnit_price(),
                                orderDetail.getQty()
                        ));

                    }
                    orderDTO.setOrder_list(orderDetailDTOList);

                    return orderDTO;
                } else {
                    return null; // Return null if no order is found
                }
            }
        }
    }
}
