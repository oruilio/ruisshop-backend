package com.rui.vo;

import lombok.Data;

import java.util.List;

@Data
public class BarDataVO {
    private List<String> names;
    private List<BarStyleVO> values;
}
