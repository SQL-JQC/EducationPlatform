package com.qinC.edu.controller.front;


import com.qinC.commonutils.R;
import com.qinC.commonutils.utils.JwtUtils;
import com.qinC.edu.entity.Comment;
import com.qinC.edu.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author qinC
 * @since 2021-11-26
 */
@RestController
@RequestMapping("/edu/comment")
@CrossOrigin
@RefreshScope
public class CommentFrontController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/{page}/{limit}")
    public R index(@PathVariable("page") Long page, @PathVariable("limit") Long limit, String courseId) {
        Map map = commentService.index(page, limit, courseId);
        return R.ok().data(map);
    }

    @PostMapping("/auth/save")
    public R save(@RequestBody Comment comment, HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(memberId)) {
            return R.error().code(28004).message("请登录");
        }
        commentService.save(comment, memberId);

        return R.ok();
    }

}

