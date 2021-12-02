package com.qinC.edu.service;

import com.qinC.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qinC.edu.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author qinC
 * @since 2021-11-14
 */
public interface SubjectService extends IService<Subject> {

    void saveSubject(MultipartFile file, SubjectService subjectService);

    List<OneSubject> getAllOneTwoSubject();

}
