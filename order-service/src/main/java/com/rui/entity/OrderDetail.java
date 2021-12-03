package com.rui.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 订单详情表
 * </p>
 *
 * @author Rui
 * @since 2021-12-03
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class OrderDetail implements Serializable {

    private static final long serialVersionUID=1L;

      private String detailId;

    private String orderId;

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
     * 商品数量
     */
      private Integer productQuantity;

      /**
     * 商品小图
     */
      private String productIcon;

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
