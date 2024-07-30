package lk.ijse.pos_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
@Data

public class ItemDTO implements Serializable {

    private String ItemId;
    private String ItemName;
    private String ItemQty;
    private String ItemPrice;
}
