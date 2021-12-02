package com.qinC.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qinC.edu.entity.Chapter;
import com.qinC.edu.entity.Video;
import com.qinC.edu.entity.chapter.ChapterVo;
import com.qinC.edu.entity.chapter.VideoVo;
import com.qinC.edu.mapper.ChapterMapper;
import com.qinC.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qinC.edu.service.VideoService;
import com.qinC.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author qinC
 * @since 2021-11-15
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private VideoService videoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        QueryWrapper<Chapter> wrapperChapter = new QueryWrapper<Chapter>();
        wrapperChapter.eq("course_id", courseId);
        List<Chapter> chapterList = baseMapper.selectList(wrapperChapter);

        QueryWrapper<Video> wrapperVideo = new QueryWrapper<Video>();
        wrapperVideo.eq("course_id", courseId);
        List<Video> videoList = videoService.list(wrapperVideo);

        List<ChapterVo> finalList = new ArrayList<ChapterVo>();
        for (int i = 0; i < chapterList.size(); i++) {
            Chapter chapter = chapterList.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);
            finalList.add(chapterVo);

            List<VideoVo> videoVoList = new ArrayList<VideoVo>();
            for (int m = 0; m < videoList.size(); m++) {
                Video video = videoList.get(m);

                if (video.getChapterId().equals(chapter.getId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video, videoVo);
                    videoVoList.add(videoVo);
                }
            }

            chapterVo.setChildren(videoVoList);
        }

        return finalList;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper<Video> wrapper = new QueryWrapper<Video>();
        wrapper.eq("chapter_id", chapterId);
        int count = videoService.count(wrapper);

        if (count > 0) {
            throw new GuliException(20001, "不能删除");
        } else {
            int result = baseMapper.deleteById(chapterId);
            return result > 0;
        }
    }

    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<Chapter> wrapper = new QueryWrapper<Chapter>();
        wrapper.eq("course_id", courseId);
        baseMapper.delete(wrapper);
    }

}
