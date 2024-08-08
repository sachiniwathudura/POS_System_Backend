package lk.ijse.pos_backend.dto;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
@ToString

public class OrderDetailsDTO {

    String order_id;
    String item_code;
    BigDecimal unit_price;
    int qty;

    public OrderDetailsDTO(String item_code, BigDecimal unit_price, int qty) {
        this.item_code = item_code;
        this.unit_price = unit_price;
        this.qty = qty;
    }
}
