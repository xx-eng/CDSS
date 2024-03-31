package com.entity;

import java.sql.Date;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 预约表 
 * </p>
 *
 * @author hospital
 * @since 2021-04-24
 */
@Data
  @EqualsAndHashCode(callSuper = false)
  @Accessors(chain = true)
public class Appointment implements Serializable {

    private static final long serialVersionUID=1L;

      /**
     * 编号
     */
      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      /**
     * 挂号人
     */
      private Long userId;

      /**
     * 挂号人姓名
     */
      private String userName;

      /**
     * 挂号医生 挂哪个科室的医生
     */
      private Long doctorId;

      /**
     * 挂号医生姓名
     */
      private String doctorName;

      /**
     * 预约时间
     */
      private Date appointment;

      /**
      * 时间段
     */
      private int time;

      /**
     * 创建时间
     */
      private LocalDateTime createdTime;

      /**
     * 更新时间
     */
      private LocalDateTime updatedTime;


}
