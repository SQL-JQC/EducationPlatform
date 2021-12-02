package com.qinC.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qinC.commonutils.R;
import com.qinC.edu.entity.Teacher;
import com.qinC.edu.entity.vo.TeacherQuery;
import com.qinC.edu.service.TeacherService;
import com.qinC.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author qinC
 * @since 2021-10-06
 */
@RestController
@RequestMapping("/edu/teacher")
@CrossOrigin
@RefreshScope
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/findAll")
    public R findAllTeacher() {
        List<Teacher> list = teacherService.list(null);
        return R.ok().data("items", list);
    }

    @DeleteMapping("/delete/{id}")
    public R removeTeacher(@PathVariable("id") String id) {
        boolean flag = teacherService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @GetMapping("/pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable("current") Long current, @PathVariable("limit") Long limit) {
        Page<Teacher> pageTeacher = new Page<Teacher>(current, limit);

        /*try {
            int i = 10 / 0;
        } catch (Exception e) {
            throw new GuliException(20001, "执行了自定义异常处理");
        }*/

        teacherService.page(pageTeacher, null);

        long total = pageTeacher.getTotal();
        List<Teacher> records = pageTeacher.getRecords();

        return R.ok().data("total", total).data("rows", records);
    }

    @PostMapping("/pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable("current") Long current, @PathVariable("limit") Long limit, @RequestBody(required = false) TeacherQuery teacherQuery) {
        Page<Teacher> pageTeacher = new Page<Teacher>(current, limit);

        QueryWrapper<Teacher> wrapper = new QueryWrapper<Teacher>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }

        wrapper.orderByDesc("gmt_create");

        teacherService.page(pageTeacher, wrapper);

        long total = pageTeacher.getTotal();
        List<Teacher> records = pageTeacher.getRecords();

        return R.ok().data("total", total).data("rows", records);
    }

    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody(required = false) Teacher teacher) {
        boolean flag = teacherService.save(teacher);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @GetMapping("/getTeacher/{id}")
    public R getTeacher(@PathVariable("id") String id) {
        Teacher teacher = teacherService.getById(id);
        return R.ok().data("teacher", teacher);
    }

    @PostMapping("/updateTeacher")
    public R updateTeacher(@RequestBody(required = false) Teacher teacher) {
        boolean flag = teacherService.updateById(teacher);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

}

