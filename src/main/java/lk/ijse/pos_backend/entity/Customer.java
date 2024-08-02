package lk.ijse.pos_backend.entity;

import lombok.*;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Getter
@Setter
public class Customer {
    private String id;
    private String name;
    private String address;
    private Double salary;

}
