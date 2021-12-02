package com.qinC.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qinC.commonutils.R;
import com.qinC.edu.entity.Course;
import com.qinC.edu.entity.vo.CourseInfo;
import com.qinC.edu.entity.vo.CoursePublish;
import com.qinC.edu.entity.vo.CourseQuery;
import com.qinC.edu.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author qinC
 * @since 2021-11-15
 */
@RestController
@RequestMapping("/edu/course")
@CrossOrigin
@RefreshScope
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/pageCourseCondition/{current}/{limit}")
    public R pageCourseCondition(@PathVariable("current") Long current, @PathVariable("limit") Long limit, @RequestBody(required = false) CourseQuery courseQuery) {
        Page<Course> pageCourse = new Page<Course>(current, limit);

        QueryWrapper<Course> wrapper = new QueryWrapper<Course>();
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();
        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(status)) {
            wrapper.eq("status", status);
        }

        courseService.page(pageCourse, wrapper);

        long total = pageCourse.getTotal();
        List<Course> records = pageCourse.getRecords();

        return R.ok().data("total", total).data("rows", records);
    }

    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfo courseInfo) {
        String id = courseService.saveCourseInfo(courseInfo);
        return R.ok().data("courseId", id);
    }

    @GetMapping("/getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable("courseId") String courseId) {
        CourseInfo courseInfo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfo", courseInfo);
    }

    @PostMapping("/updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfo courseInfo) {
        courseService.updateCourseInfo(courseInfo);
        return R.ok();
    }

    @GetMapping("/getCoursePublishInfo/{courseId}")
    public R getCoursePublishInfo(@PathVariable("courseId") String courseId) {
        CoursePublish coursePublish = courseService.getCoursePublishInfo(courseId);
        return R.ok().data("coursePublish", coursePublish);
    }

    @PostMapping("/publishCourse/{courseId}")
    public R publishCourse(@PathVariable("courseId") String courseId) {
        Course course = new Course();
        course.setId(courseId);
        course.setStatus("Normal");
        courseService.updateById(course);

        return R.ok();
    }

    //此删除进行了远程模块调用
    @DeleteMapping("/{courseId}")
    public R deleteCourse(@PathVariable("courseId") String courseId) {
        courseService.removeCourse(courseId);
        return R.ok();
    }

}