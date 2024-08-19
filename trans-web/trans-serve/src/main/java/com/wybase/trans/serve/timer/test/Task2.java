package com.wybase.trans.serve.timer.test;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Task2 {

    public void handle(){
        Date date = new Date();
        System.out.println("task2"+date+"开始");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("task2"+date+"结束");
    }

}
