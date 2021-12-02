package com.qinC.edu.service;

import com.qinC.edu.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author qinC
 * @since 2021-11-26
 */
public interface CommentService extends IService<Comment> {

    Map index(Long page, Long limit, String courseId);

    void save(Comment comment, String memberId);

}
