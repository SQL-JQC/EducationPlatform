package com.qinC.sta.controller;


import com.qinC.commonutils.R;
import com.qinC.sta.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author qinC
 * @since 2021-11-28
 */
@RestController
@RequestMapping("/sta/statistics-daily")
@CrossOrigin
@RefreshScope
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    @PostMapping("/registerCount/{day}")
    public R registerCount(@PathVariable("day") String day) {
        statisticsDailyService.registerCount(day);
        return R.ok();
    }

    @GetMapping("/showData/{type}/{begin}/{end}")
    public R showData(@PathVariable("type") String type, @PathVariable("begin") String begin, @PathVariable("end") String end) {
        Map<String, Object> map = statisticsDailyService.showData(type, begin, end);
        return R.ok().data(map);
    }

}

