package com.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class UserAppointDTO {
    private Integer do_id;
    private String do_name;
    private Date appointment;
    private int time;
}
