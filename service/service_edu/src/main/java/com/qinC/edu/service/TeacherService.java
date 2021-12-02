package com.qinC.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qinC.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author qinC
 * @since 2021-10-06
 */
public interface TeacherService extends IService<Teacher> {

    List<Teacher> selectAllAvatar();

    Map<String,Object> getTeacherFrontList(Page<Teacher> pageTeacher);

}
