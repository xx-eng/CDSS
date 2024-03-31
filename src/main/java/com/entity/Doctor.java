package com.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 医生表 
 * </p>
 *
 * @author hospital
 * @since 2021-04-24
 */
@Data
  @EqualsAndHashCode(callSuper = false)
  @Accessors(chain = true)
public class Doctor implements Serializable {

    private static final long serialVersionUID=1L;

      /**
     * 编号
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      /**
     * 身份证号
     */
      private String idCard;

      /**
     * 办公电话
     */
      private String mobile;

      /**
     * 姓名
     */
      private String name;

      /**
     * 性别 0-所有，1-男，2-女
     */
      private Integer sex;

      /**
     * 照片
     */
      private String photo;

      /**
     * 年龄
     */
      private Integer age;

      /**
     * 职称
     */
      private String professionalTitle;

      /**
     * 科室
     */
      private Integer office;

      /**
     * 密码
     */
      private String password;

      /**
     * 宿舍区 0-全部，1-临湖，2-滨湖，3-环湖，4-中区
     */
      private Integer addressId;

      /**
     * 住址
     */
      private String address;

      /**
     * 规定签约人数 200
     */
      private Integer signNum;

      /**
     * 剩余签约人数 初始200
     */
      private Integer remainNum;

      /**
     * 入职时间
     */
      private LocalDateTime time;

      /**
     * 入职经历
     */
      private String experience;

      /**
     * 更新人
     */
      @TableField("UPDATED_BY")
    private String updatedBy;

      /**
     * 更新时间
     */
      @TableField("UPDATED_TIME")
    private LocalDateTime updatedTime;


}
