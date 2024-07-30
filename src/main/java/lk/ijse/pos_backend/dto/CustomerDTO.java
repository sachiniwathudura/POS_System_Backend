package lk.ijse.pos_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@NoArgsConstructor
@AllArgsConstructor
@Data

public class CustomerDTO implements Serializable {

    private String Id;
    private String name;
    private String address;
    private String salary;

}
