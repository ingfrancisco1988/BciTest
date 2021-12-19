package com.francisco.castanieda.BciTest.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
public class Phone {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
    private Long number;
    private int cityCode;
    private String countryCode;

}
