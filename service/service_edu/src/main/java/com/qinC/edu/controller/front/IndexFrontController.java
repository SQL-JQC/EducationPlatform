package com.qinC.edu.controller.front;

import com.qinC.commonutils.R;
import com.qinC.edu.entity.Course;
import com.qinC.edu.entity.Teacher;
import com.qinC.edu.service.CourseService;
import com.qinC.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/edu/indexfront")
@CrossOrigin
@RefreshScope
public class IndexFrontController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/index")
    public R index() {
        List<Course> courseList = courseService.selectAllCover();
        List<Teacher> teacherList = teacherService.selectAllAvatar();

        return R.ok().data("courseList", courseList).data("teacherList", teacherList);
    }

}
