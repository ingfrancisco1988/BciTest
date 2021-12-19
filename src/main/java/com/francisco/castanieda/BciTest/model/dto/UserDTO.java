package com.francisco.castanieda.BciTest.model.dto;

import com.francisco.castanieda.BciTest.model.entity.Phone;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {

    private String  name;
    private String password;
    private String email;
    private List<Phone> phones;

}
