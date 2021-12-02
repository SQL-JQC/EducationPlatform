package com.qinC.sta.schedule;

import com.qinC.sta.service.StatisticsDailyService;
import com.qinC.sta.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@EnableScheduling
public class ScheduledTask {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    @Scheduled(cron = "0/5 * * * * ?")
    public void task1() {
        System.out.println("hahaha......");
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void task2() {
        statisticsDailyService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }

}
