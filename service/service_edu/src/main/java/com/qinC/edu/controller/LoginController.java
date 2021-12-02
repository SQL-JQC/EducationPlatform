package com.qinC.edu.controller;

import com.qinC.commonutils.R;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/edu/user")
@CrossOrigin
@RefreshScope
public class LoginController {

    @PostMapping("/login")
    public R login() {
        return R.ok().data("token", "admin");
    }

    @GetMapping("/info")
    public R info() {
        return R.ok().data("roles", "[admin]")
                .data("name", "Super Admin")
                .data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif")
                .data("introduction", "I am a super administrator");
    }

    @PostMapping("/logout")
    public R logout() {
        return R.ok();
    }

}
