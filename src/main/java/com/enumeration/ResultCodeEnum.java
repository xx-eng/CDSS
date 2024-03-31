package com.enumeration;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {
    DB_FIND_SUCCESS("2000","数据库查找成功"),
    DB_UPDATE_SUCCESS("2001","数据库更新成功"),
    DB_DELETE_SUCCESS("2002","数据库删除成功"),
    DB_ADD_SUCCESS("2003","数据库增加成功"),
    DB_FIND_FAILURE("2007","数据库查找失败，没有该条记录"),
    DB_UPDATE_FAILURE("2008","数据库更新失败"),
    DB_DELETE_FAILURE("2009","数据库删除失败"),
    DB_ADD_FAILURE("2010","数据库增加失败"),
    LOGIN_SUCCESS("1000","登录成功"),
    LOGIN_FAILURE("1001","登录失败，账号或密码错误"),
    REGISTER_SUCCESS("1002","注册成功"),
    REGISTER_FAILURE("1003","该身份已被注册过"),
    LOGIN_INVALID("1004","登录失效");

    private String code;
    private String desc;

    ResultCodeEnum(String code, String desc)
    {
        this.code = code;
        this.desc = desc;
    }
}
