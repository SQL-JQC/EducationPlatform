package com.qinC.ucenter.controller;


import com.qinC.commonutils.R;
import com.qinC.commonutils.utils.JwtUtils;
import com.qinC.commonutils.vo.CMember;
import com.qinC.ucenter.entity.Member;
import com.qinC.ucenter.entity.vo.RegisterVo;
import com.qinC.ucenter.service.MemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author qinC
 * @since 2021-11-22
 */
@RestController
@RequestMapping("/ucenter/member")
@CrossOrigin
@RefreshScope
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/login")
    public R loginUser(@RequestBody Member member) {
        String token = memberService.login(member);

        return R.ok().data("token", token);
    }

    @PostMapping("/register")
    public R registerUser(@RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return R.ok();
    }

    @GetMapping("/getMemberInfo")
    public R getMemberInfo(HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        Member member = memberService.getById(memberId);
        return R.ok().data("userInfo", member);
    }

    @GetMapping("/getCMember/{id}")
    public CMember getCMember(@PathVariable("id") String id) {
        Member member = memberService.getById(id);
        CMember cMember = new CMember();
        BeanUtils.copyProperties(member, cMember);
        return cMember;
    }

    @GetMapping("/countRegister/{day}")
    public R countRegister(@PathVariable("day") String day) {
        Integer count = memberService.countRegister(day);
        return R.ok().data("countRegister", count);
    }

}

