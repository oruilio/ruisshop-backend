package com.rui.vo;

import lombok.Data;

import java.util.List;

@Data
public class CategoryVO {
    private String name;
    private Integer type;
    private List goods;
}
