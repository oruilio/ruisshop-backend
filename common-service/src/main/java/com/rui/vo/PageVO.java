package com.rui.vo;

import lombok.Data;

import java.util.List;

//用于分页展示的数据框架
@Data
public class PageVO<T> {
    private List<T> content;
    private Long size;
    private Long total;
}
