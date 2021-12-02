package com.qinC.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qinC.commonutils.R;
import com.qinC.commonutils.utils.JwtUtils;
import com.qinC.order.entity.Order;
import com.qinC.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author qinC
 * @since 2021-11-26
 */
@RestController
@RequestMapping("/order/order")
@CrossOrigin
@RefreshScope
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/createOrder/{courseId}")
    public R createOrder(@PathVariable("courseId") String courseId, HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(memberId)) {
            return R.error().code(28004).message("请登录");
        }

        String orderNo = orderService.saveOrder(courseId, memberId);

        return R.ok().data("orderId", orderNo);
    }

    @GetMapping("/getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable("orderId") String orderId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<Order>();
        wrapper.eq("order_no", orderId);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("item", order);
    }

    @GetMapping("/isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable("courseId") String courseId, @PathVariable("memberId") String memberId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<Order>();
        wrapper.eq("course_id", courseId);
        wrapper.eq("member_id", memberId);
        wrapper.eq("status", 1);

        int count = orderService.count(wrapper);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

}

