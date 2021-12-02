package com.qinC.order.service;

import com.qinC.order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author qinC
 * @since 2021-11-26
 */
public interface OrderService extends IService<Order> {

    String saveOrder(String courseId, String memberId);

}
