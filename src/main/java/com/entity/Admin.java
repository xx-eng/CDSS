package com.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 管理员
 * </p>
 *
 * @author hospital
 * @since 2021-04-24
 */
@Data
  @EqualsAndHashCode(callSuper = false)
  @Accessors(chain = true)
public class Admin implements Serializable {

    private static final long serialVersionUID=1L;

      /**
     * 管理员
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      /**
     * 密码
     */
      private String password;

      /**
     * 是否删除 0-未删除,1-已删除
     */
      private Integer isDeleted;


}
