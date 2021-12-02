package com.rui.entity;

import java.math.BigDecimal;
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
 * 商品表
 * </p>
 *
 * @author Rui
 * @since 2021-12-02
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class ProductInfo implements Serializable {

    //用于进行序列化 & 反序列化--用不到
    private static final long serialVersionUID=1L;

      @TableId(value = "product_id", type = IdType.AUTO)
      private Integer productId;

      /**
     * 商品名称
     */
      private String productName;

      /**
     * 商品单价
     */
      private BigDecimal productPrice;

      /**
     * 库存
     */
      private Integer productStock;

      /**
     * 描述
     */
      private String productDescription;

      /**
     * 小图
     */
      private String productIcon;

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

      /**
     * 商品状态，1正常0下架
     */
      private Integer productStatus;


}
