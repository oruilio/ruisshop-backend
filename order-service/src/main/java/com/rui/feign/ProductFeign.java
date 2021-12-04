package com.rui.feign;

import com.rui.entity.ProductInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

@FeignClient("product-service")
public interface ProductFeign {

    @GetMapping("/buyer/product/findPriceById/{id}")
    public BigDecimal findPriceById(@PathVariable("id") Integer id);

    @GetMapping("/buyer/product/findById/{id}")
    public ProductInfo findById(@PathVariable("id")Integer id);
}
