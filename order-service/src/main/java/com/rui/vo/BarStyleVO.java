package com.rui.vo;

import lombok.Data;

import java.util.Map;

@Data
public class BarStyleVO {
    private Integer value;
    private Map<String,String> itemStyle;
}
