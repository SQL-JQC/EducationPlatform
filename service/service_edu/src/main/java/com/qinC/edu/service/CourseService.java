package com.qinC.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qinC.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qinC.edu.entity.frontvo.CourseFrontVo;
import com.qinC.edu.entity.frontvo.CourseWebVo;
import com.qinC.edu.entity.vo.CourseInfo;
import com.qinC.edu.entity.vo.CoursePublish;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author qinC
 * @since 2021-11-15
 */
public interface CourseService extends IService<Course> {

    String saveCourseInfo(CourseInfo courseInfo);

    CourseInfo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfo courseInfo);

    CoursePublish getCoursePublishInfo(String courseId);

    void removeCourse(String courseId);

    List<Course> selectAllCover();

    Map<String, Object> getCourseFrontList(Page<Course> pageCourse, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);

    boolean isBuyCourse(String courseId, String memberId);

}
