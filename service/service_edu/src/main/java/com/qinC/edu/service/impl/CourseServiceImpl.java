package com.qinC.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qinC.edu.client.OrderClient;
import com.qinC.edu.entity.Course;
import com.qinC.edu.entity.CourseDescription;
import com.qinC.edu.entity.frontvo.CourseFrontVo;
import com.qinC.edu.entity.frontvo.CourseWebVo;
import com.qinC.edu.entity.vo.CourseInfo;
import com.qinC.edu.entity.vo.CoursePublish;
import com.qinC.edu.mapper.CourseMapper;
import com.qinC.edu.service.ChapterService;
import com.qinC.edu.service.CourseDescriptionService;
import com.qinC.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qinC.edu.service.VideoService;
import com.qinC.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author qinC
 * @since 2021-11-15
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseDescriptionService courseDescriptionService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private OrderClient orderClient;

    @Override
    public String saveCourseInfo(CourseInfo courseInfo) {
        Course course = new Course();
        BeanUtils.copyProperties(courseInfo, course);
        int insert = baseMapper.insert(course);
        if (insert == 0) {
            throw new GuliException(20001, "添加课程信息失败");
        }

        String cid = course.getId();

        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setId(cid);
        courseDescription.setDescription(courseInfo.getDescription());
        courseDescriptionService.save(courseDescription);

        return cid;
    }

    @Override
    public CourseInfo getCourseInfo(String courseId) {
        Course course = baseMapper.selectById(courseId);
        CourseInfo courseInfo = new CourseInfo();
        BeanUtils.copyProperties(course, courseInfo);

        CourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfo.setDescription(courseDescription.getDescription());

        return courseInfo;
    }

    @Override
    public void updateCourseInfo(CourseInfo courseInfo) {
        Course course = new Course();
        BeanUtils.copyProperties(courseInfo, course);
        int update = baseMapper.updateById(course);
        if (update == 0) {
            throw new GuliException(20001, "修改课程信息失败");
        }

        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setId(courseInfo.getId());
        courseDescription.setDescription(courseInfo.getDescription());
        courseDescriptionService.updateById(courseDescription);
    }

    @Override
    public CoursePublish getCoursePublishInfo(String courseId) {
        CoursePublish coursePublishInfo = baseMapper.getCoursePublishInfo(courseId);
        return coursePublishInfo;
    }

    @Override
    public void removeCourse(String courseId) {
        videoService.removeVideoByCourseId(courseId);
        chapterService.removeChapterByCourseId(courseId);
        courseDescriptionService.removeById(courseId);
        int result = baseMapper.deleteById(courseId);
        if (result == 0) {
            throw new GuliException(20001, "删除失败");
        }
    }

    @Cacheable(key = "'selectCourseList'", value = "cover")
    @Override
    public List<Course> selectAllCover() {
        QueryWrapper<Course> wrapper = new QueryWrapper<Course>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<Course> courseList = baseMapper.selectList(wrapper);

        return courseList;
    }

    @Override
    public Map<String, Object> getCourseFrontList(Page<Course> pageCourse, CourseFrontVo courseFrontVo) {
        //2 根据讲师id查询所讲课程
        QueryWrapper<Course> wrapper = new QueryWrapper<Course>();
        //判断条件值是否为空，不为空拼接
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) { //一级分类
            wrapper.eq("subject_parent_id", courseFrontVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())) { //二级分类
            wrapper.eq("subject_id", courseFrontVo.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) { //关注度
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) { //最新
            wrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {//价格
            wrapper.orderByDesc("price");
        }

        baseMapper.selectPage(pageCourse, wrapper);

        List<Course> records = pageCourse.getRecords();
        long current = pageCourse.getCurrent();
        long pages = pageCourse.getPages();
        long size = pageCourse.getSize();
        long total = pageCourse.getTotal();
        boolean hasNext = pageCourse.hasNext();//下一页
        boolean hasPrevious = pageCourse.hasPrevious();//上一页

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

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }

    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        boolean isbuyCourse = orderClient.isBuyCourse(courseId, memberId);

        return isbuyCourse;
    }

}
