package com.cloudrain.derecho.sandbox.stringreplacer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Example {
	public static void main(String[] args) {
		Pattern regex = Pattern.compile("\\[(.*?)\\]");

		String result = StringReplacer.replace("[AssetModel.displayLabel], {Request.name}", regex, (Matcher m) -> {
			System.out.println(m.group());
			return m.group();
		});

		System.out.println(result); // output: test12test24hoho
	}
}