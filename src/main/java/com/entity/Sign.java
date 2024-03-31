package com.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 签约表 
 * </p>
 *
 * @author hospital
 * @since 2021-04-24
 */
@Data
  @EqualsAndHashCode(callSuper = false)
  @Accessors(chain = true)
public class Sign implements Serializable {

    private static final long serialVersionUID=1L;

      /**
     * 编号
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      /**
     * 医生编号
     */
      private Long doctorId;

      /**
     * 用户编号
     */
      private Long userId;

      /**
     * 签约状态 1-签约成功，2-等待解约,3-解约成功【解约需要审核】
     */
      private Integer status;

      /**
     * 解约原因
     */
      private String reason;

      /**
     * 创建时间
     */
      private LocalDateTime createdTime;


}
