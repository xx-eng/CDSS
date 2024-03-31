package com.dto;

import lombok.Data;

@Data
public class UserRegisterDTO {
    private String password;
    private String name;
    private int sex;
    private int age;
    private String mobile;
    private String idCard;
    private int addressId;
    private String address;
    private String medicalHistory;
    private String allergy;
}
