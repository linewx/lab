package com.zz.lab.springioc.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Foo {
    private String name;
    private String type;
}
