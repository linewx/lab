package com.zz.lab.springioc.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Bar {
    private String name;
    private String type;
}
