package com.qinC.edu.controller;


import com.qinC.commonutils.R;
import com.qinC.edu.entity.Subject;
import com.qinC.edu.entity.subject.OneSubject;
import com.qinC.edu.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author qinC
 * @since 2021-11-14
 */
@RestController
@RequestMapping("/edu/subject")
@CrossOrigin
@RefreshScope
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @PostMapping("/addSubject")
    public R addSubject(MultipartFile file) {
        subjectService.saveSubject(file, subjectService);

        return R.ok();
    }

    @GetMapping("/getAllSubject")
    public R getAllSubject() {
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("list", list);
    }

}

