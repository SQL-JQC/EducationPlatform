package com.qinC.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qinC.edu.entity.Teacher;
import com.qinC.edu.mapper.TeacherMapper;
import com.qinC.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author qinC
 * @since 2021-10-06
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Cacheable(key = "'selectTeacherList'", value = "avatar")
    @Override
    public List<Teacher> selectAllAvatar() {
        QueryWrapper<Teacher> wrapper = new QueryWrapper<Teacher>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 4");
        List<Teacher> teacherList = baseMapper.selectList(wrapper);

        return teacherList;
    }

    @Override
    public Map<String, Object> getTeacherFrontList(Page<Teacher> pageTeacher) {
        QueryWrapper<Teacher> wrapper = new QueryWrapper<Teacher>();
        wrapper.orderByDesc("id");
        //把分页数据封装到pageTeacher对象
        baseMapper.selectPage(pageTeacher, wrapper);

        List<Teacher> records = pageTeacher.getRecords();
        long current = pageTeacher.getCurrent();
        long pages = pageTeacher.getPages();
        long size = pageTeacher.getSize();
        long total = pageTeacher.getTotal();
        boolean hasNext = pageTeacher.hasNext();//下一页
        boolean hasPrevious = pageTeacher.hasPrevious();//上一页

        //把分页数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        //map返回
        return map;
    }

}
