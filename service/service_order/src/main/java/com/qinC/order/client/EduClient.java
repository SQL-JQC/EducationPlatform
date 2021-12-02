package com.qinC.order.client;

import com.qinC.commonutils.vo.CourseWeb;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "service-edu", fallback = EduClientImpl.class)
public interface EduClient {

    @GetMapping("/edu/coursefront/getCourseInfoOrder/{id}")
    public CourseWeb getCourseInfoOrder(@PathVariable("id") String id);

}
