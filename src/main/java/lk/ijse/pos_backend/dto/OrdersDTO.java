package lk.ijse.pos_backend.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
@ToString

public class OrdersDTO {

        String order_id;
        LocalDate date;
        String cust_id;
        BigDecimal discount;
        BigDecimal total;
        List<OrderDetailsDTO> order_list;

        public OrdersDTO(String order_id, LocalDate date, String cust_id, BigDecimal discount, BigDecimal total) {
            this.order_id = order_id;
            this.date = date;
            this.cust_id = cust_id;
            this.discount = discount;
            this.total = total;
        }

        public OrdersDTO(String orderId, LocalDate date, String custId) {
            this.order_id = order_id;
            this.date = date;
            this.cust_id = cust_id;
        }

    }
