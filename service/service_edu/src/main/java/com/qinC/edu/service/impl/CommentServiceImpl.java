package com.qinC.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qinC.commonutils.vo.CMember;
import com.qinC.edu.client.UcenterClient;
import com.qinC.edu.entity.Comment;
import com.qinC.edu.mapper.CommentMapper;
import com.qinC.edu.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author qinC
 * @since 2021-11-26
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public Map index(Long page, Long limit, String courseId) {
        Page<Comment> pageParam = new Page<Comment>(page, limit);
        QueryWrapper<Comment> wrapper = new QueryWrapper<Comment>();
        wrapper.eq("course_id", courseId);
        baseMapper.selectPage(pageParam, wrapper);
        List<Comment> commentList = pageParam.getRecords();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", commentList);
        map.put("current", pageParam.getCurrent());
        map.put("pages", pageParam.getPages());
        map.put("size", pageParam.getSize());
        map.put("total", pageParam.getTotal());
        map.put("hasNext", pageParam.hasNext());
        map.put("hasPrevious", pageParam.hasPrevious());

        return map;
    }

    @Override
    public void save(Comment comment, String memberId) {
        comment.setMemberId(memberId);

        CMember cMember = ucenterClient.getCMember(memberId);

        comment.setNickname(cMember.getNickname());
        comment.setAvatar(cMember.getAvatar());

        baseMapper.insert(comment);
    }

}
