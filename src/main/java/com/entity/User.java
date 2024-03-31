package com.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户 
 * </p>
 *
 * @author hospital
 * @since 2021-04-24
 */
@Data
  @EqualsAndHashCode(callSuper = false)
  @Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID=1L;

      /**
     * 编号
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      /**
     * 密码
     */
      private String password;

      /**
     * 用户姓名
     */
      private String name;

      /**
     * 用户性别 0-所有，1-男，2-女
     */
      private Integer sex;

      /**
     * 年龄
     */
      private Integer age;

      /**
     * 电话
     */
      private String mobile;

      /**
     * 身份证号
     */
      private String idCard;

      /**
     * 宿舍区 0-全部，1-临湖，2-滨湖，3-环湖，4-中区
     */
      private Integer addressId;

      /**
     * 地址
     */
      private String address;

      /**
     * 家族病史
     */
      private String medicalHistory;

      /**
     * 过敏史
     */
      private String allergy;


}
