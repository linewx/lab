package com.cloudrain.derecho.sandbox.law;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;

/**
 * Created by lugan on 11/16/2016.
 */
public class LawParser {
    public static void main(String [] args) throws Exception {
        long startTime = System.currentTimeMillis();
        for (int i=0; i < 1000; i++) {
            File input = new File("C:\\Users\\lugan\\git\\Derecho\\test.html");
            Document doc = Jsoup.parse(input, "GBK");
            Element element = doc.getElementById("DivContent");
            //System.out.println(element.child(1).ownText().endsWith("法院"));
            input.exists();
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);


    }
}
