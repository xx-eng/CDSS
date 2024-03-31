package com.entity;

import java.sql.Date;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 排班表
 * </p>
 *
 * @author hospital
 * @since 2021-04-24
 */
@Data
  @EqualsAndHashCode(callSuper = false)
  @Accessors(chain = true)
public class Schedule implements Serializable {

    private static final long serialVersionUID=1L;

      /**
     * 编号
     */
      private Long id;

      /**
     * 医生编号
     */
      private Long doctorId;

      /**
     * 医生名字
     */
      private String doctorName;

      /**
     * 星期 1-周一，2-周二，3-周三，4-周四，5-周五，6-周六，7-周日
     */
      private Integer weekday;

      /**
     * 时间段 1-上午，2-下午，3-晚上
     */
      private Integer time;

      /**
     * 更新人
     */
      @TableField("UPDATED_BY")
    private Long updatedBy;

      /**
     * 更新时间
     */
      @TableField("UPDATED_TIME")
    private LocalDateTime updatedTime;

    /**
    * 排班日期
    */
      private Date date;

      /**
     * 看病人数 40
     */
      private Integer number;

      /**
     * 规定预约人数 20
     */
      private Integer appointNumber;

      /**
     * 剩余预约人数 初始20，预约一人减一
     */
      private Integer remainNumber;


}
