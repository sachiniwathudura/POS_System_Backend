package lk.ijse.pos_backend.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@ToString

public class ItemDTO implements Serializable {

    private String code;
    private String name;
    private int qty;
    private BigDecimal price;

}

