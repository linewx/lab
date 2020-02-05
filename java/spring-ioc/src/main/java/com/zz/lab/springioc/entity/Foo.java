package com.zz.lab.springioc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Foo {
    private String name;
    private String type;

    public String getMessage() {
        return "hi " + this.getName();
    }
}
