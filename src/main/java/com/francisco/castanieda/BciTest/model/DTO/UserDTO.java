package com.francisco.castanieda.BciTest.model.DTO;

import com.francisco.castanieda.BciTest.model.Entity.Phone;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {

    private String  name;
    private String password;
    private String email;
    private List<Phone> phones;

}
