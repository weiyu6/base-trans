package com.wybase.trans.serve.timer.test;

import com.wybase.trans.serve.model.entity.generate.CronJobConfig;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Task1 {

    public void handle(CronJobConfig cronJobConfig) throws InterruptedException {
        Date date = new Date();
        System.out.println("task1" + date + "开始");
        System.out.println(cronJobConfig.toString());
        Thread.sleep(10000);
        System.out.println("task1" + date + "结束");
    }

}
