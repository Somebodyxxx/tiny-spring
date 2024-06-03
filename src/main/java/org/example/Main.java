package org.example;

import org.example.service.Config;
import org.example.spring.MyApplicationContext;

public class Main {
    public static void main(String[] args) {
        MyApplicationContext myApplicationContext = new MyApplicationContext(Config.class);
        System.out.println(myApplicationContext.getBean("test-bean-1"));
    }
}