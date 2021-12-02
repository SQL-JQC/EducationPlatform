package com.qinC.edu.client;

import com.qinC.commonutils.vo.CMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-ucenter", fallback = UcenterClientImpl.class)
@Component
public interface UcenterClient {

    @GetMapping("/ucenter/member/getCMember/{id}")
    public CMember getCMember(@PathVariable("id") String id);

}
