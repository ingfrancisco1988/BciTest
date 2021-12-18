package com.francisco.castanieda.BciTest.model.DTO;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class ErrorInfoDTO {
    private Timestamp timestamp;
    private Integer code;
    private String message;
}
