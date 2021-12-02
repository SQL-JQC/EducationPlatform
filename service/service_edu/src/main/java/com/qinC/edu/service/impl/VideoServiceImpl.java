package com.qinC.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qinC.commonutils.R;
import com.qinC.edu.client.VodClient;
import com.qinC.edu.entity.Video;
import com.qinC.edu.mapper.VideoMapper;
import com.qinC.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qinC.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author qinC
 * @since 2021-11-15
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Autowired
    private VodClient vodClient;

    @Override
    public void removeVideoByCourseId(String courseId) {
        QueryWrapper<Video> wrapperVideo = new QueryWrapper<Video>();
        wrapperVideo.eq("course_id", courseId);
        wrapperVideo.select("video_source_id");
        List<Video> videoList = baseMapper.selectList(wrapperVideo);

        List<String> videoIdList = new ArrayList<String>();
        for (int i = 0; i < videoList.size(); i++) {
            Video video = videoList.get(i);
            String videoSourceId = video.getVideoSourceId();
            if (!StringUtils.isEmpty(videoSourceId)) {
                videoIdList.add(videoSourceId);
            }
        }

        //远程调用
        if (videoIdList.size() > 0) {
            vodClient.deleteBatch(videoIdList);
        }

        QueryWrapper<Video> wrapper = new QueryWrapper<Video>();
        wrapper.eq("course_id", courseId);
        baseMapper.delete(wrapper);
    }

    @Override
    public void deleteVideo(String id) {
        Video video = baseMapper.selectById(id);
        String videoSourceId = video.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)) {
            //远程调用
            R result = vodClient.removeVideo(videoSourceId);
            if (result.getCode() == 20001) {
                throw new GuliException(20001, "删除视频失败，熔断器执行...");
            }
        }
        baseMapper.deleteById(id);
    }

}
