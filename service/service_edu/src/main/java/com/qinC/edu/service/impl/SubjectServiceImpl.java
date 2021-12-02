package com.qinC.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qinC.edu.entity.Subject;
import com.qinC.edu.entity.excel.SubjectData;
import com.qinC.edu.entity.subject.OneSubject;
import com.qinC.edu.entity.subject.TwoSubject;
import com.qinC.edu.listener.SubjectExcelListener;
import com.qinC.edu.mapper.SubjectMapper;
import com.qinC.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author qinC
 * @since 2021-11-14
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Override
    public void saveSubject(MultipartFile file, SubjectService subjectService) {
        try {
            InputStream in = file.getInputStream();

            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        QueryWrapper<Subject> wrapperOne = new QueryWrapper<Subject>();
        wrapperOne.eq("parent_id", "0");
        List<Subject> oneSubjectList = baseMapper.selectList(wrapperOne);

        QueryWrapper<Subject> wrapperTwo = new QueryWrapper<Subject>();
        wrapperTwo.ne("parent_id", "0");
        List<Subject> twoSubjectList = baseMapper.selectList(wrapperTwo);

        //开始添加一级二级课程目录
        List<OneSubject> finalSubjectList = new ArrayList<OneSubject>();

        for (int i = 0; i < oneSubjectList.size(); i++) {
            Subject subject = oneSubjectList.get(i);
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(subject, oneSubject);
            finalSubjectList.add(oneSubject);

            List<TwoSubject> twoFinalSubjectList = new ArrayList<TwoSubject>();

            for (int m = 0; m < twoSubjectList.size(); m++) {
                Subject subject1 = twoSubjectList.get(m);

                if (subject1.getParentId().equals(subject.getId())) {
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(subject1, twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }
            }

            oneSubject.setChildren(twoFinalSubjectList);
        }

        return finalSubjectList;
    }

}
