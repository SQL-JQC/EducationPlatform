package com.qinC.edu.mapper;

import com.qinC.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qinC.edu.entity.frontvo.CourseWebVo;
import com.qinC.edu.entity.vo.CoursePublish;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author qinC
 * @since 2021-11-15
 */
public interface CourseMapper extends BaseMapper<Course> {

    CoursePublish getCoursePublishInfo(String courseId);

    CourseWebVo getBaseCourseInfo(String courseId);

}
