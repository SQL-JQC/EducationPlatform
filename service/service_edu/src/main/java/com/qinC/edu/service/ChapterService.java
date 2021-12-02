package com.qinC.edu.service;

import com.qinC.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qinC.edu.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author qinC
 * @since 2021-11-15
 */
public interface ChapterService extends IService<Chapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    boolean deleteChapter(String chapterId);

    void removeChapterByCourseId(String courseId);

}
