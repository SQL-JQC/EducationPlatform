package com.qinC.edu.service;

import com.qinC.edu.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author qinC
 * @since 2021-11-15
 */
public interface VideoService extends IService<Video> {

    void removeVideoByCourseId(String courseId);

    void deleteVideo(String id);

}
