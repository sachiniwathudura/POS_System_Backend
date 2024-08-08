package lk.ijse.pos_backend.entity;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@Data
@AllArgsConstructor
@ToString

public class Item {
    private String code;
    private String name;
    private int qty;
    private BigDecimal price;



}
