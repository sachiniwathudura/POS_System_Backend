package lk.ijse.pos_backend.entity;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Data
@AllArgsConstructor

public class Item {
    private String ItemId;
    private String ItemName;
    private int ItemQty;
    private double ItemPrice;

}
