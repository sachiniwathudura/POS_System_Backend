package lk.ijse.pos_backend.dto;

import lombok.*;

import java.io.Serializable;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@ToString

public class CustomerDTO implements Serializable {

    private String id;
    private String name;
    private String address;
    private Double salary;

}
