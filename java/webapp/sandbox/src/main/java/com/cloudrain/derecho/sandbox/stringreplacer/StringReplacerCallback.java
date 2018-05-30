package com.cloudrain.derecho.sandbox.stringreplacer;

import java.util.regex.Matcher;

@FunctionalInterface
public interface StringReplacerCallback {
    public String replace(Matcher match);
}