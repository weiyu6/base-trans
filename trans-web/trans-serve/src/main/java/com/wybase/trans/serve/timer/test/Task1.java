package com.wybase.trans.serve.timer.test;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Task1 {

    public void handle() throws InterruptedException {
        Date date = new Date();
        System.out.println("task1"+date+"开始");
        Thread.sleep(10000);
        System.out.println("task1"+date+"结束");
    }

}
