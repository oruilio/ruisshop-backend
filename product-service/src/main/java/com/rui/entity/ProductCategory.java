package com.rui.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 类目表
 * </p>
 *
 * @author Rui
 * @since 2021-12-02
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class ProductCategory implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "category_id", type = IdType.AUTO)
      private Integer categoryId;

      /**
     * 类目名称
     */
      private String categoryName;

      /**
     * 类目编号
     */
      private Integer categoryType;

      /**
     * 创建时间
     */
        @TableField(fill = FieldFill.INSERT)
      private LocalDateTime createTime;

      /**
     * 修改时间
     */
        @TableField(fill = FieldFill.INSERT_UPDATE)
      private LocalDateTime updateTime;


}
