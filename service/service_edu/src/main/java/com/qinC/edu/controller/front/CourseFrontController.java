package com.qinC.edu.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qinC.commonutils.R;
import com.qinC.commonutils.utils.JwtUtils;
import com.qinC.commonutils.vo.CourseWeb;
import com.qinC.edu.entity.Course;
import com.qinC.edu.entity.chapter.ChapterVo;
import com.qinC.edu.entity.frontvo.CourseFrontVo;
import com.qinC.edu.entity.frontvo.CourseWebVo;
import com.qinC.edu.service.ChapterService;
import com.qinC.edu.service.CourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/edu/coursefront")
@CrossOrigin
@RefreshScope
public class CourseFrontController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ChapterService chapterService;

    //1 带条件的分页课程查询
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable("page") long page, @PathVariable("limit") long limit, @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<Course> pageCourse = new Page<Course>(page, limit);
        Map<String, Object> map = courseService.getCourseFrontList(pageCourse, courseFrontVo);
        //返回分页所有数据
        return R.ok().data(map);
    }

    //2 课程详情的方法
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable("courseId") String courseId, HttpServletRequest request) {
        //根据课程id，编写sql语句查询课程信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);

        //根据课程id查询章节和小节
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);

        //查询具体用户对应的该课程是否购买
        boolean isbuyCourse = courseService.isBuyCourse(courseId, JwtUtils.getMemberIdByJwtToken(request));

        return R.ok().data("courseWebVo", courseWebVo).data("chapterVideoList", chapterVideoList).data("isbuyCourse", isbuyCourse);
    }
    
    @GetMapping("/getCourseInfoOrder/{id}")
    public CourseWeb getCourseInfoOrder(@PathVariable("id") String id) {
        CourseWebVo baseCourseInfo = courseService.getBaseCourseInfo(id);
        CourseWeb courseWeb = new CourseWeb();
        BeanUtils.copyProperties(baseCourseInfo, courseWeb);
        return courseWeb;
    }

}
