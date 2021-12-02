package com.qinC.edu.controller;

import com.qinC.commonutils.R;
import com.qinC.edu.client.VodClient;
import com.qinC.edu.entity.Video;
import com.qinC.edu.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author qinC
 * @since 2021-11-15
 */
@RestController
@RequestMapping("/edu/video")
@CrossOrigin
@RefreshScope
public class VideoController {

    @Autowired
    private VideoService videoService;

    @PostMapping("/addVideo")
    public R addVideo(@RequestBody Video video) {
        videoService.save(video);
        return R.ok();
    }

    //此删除进行了远程模块调用
    @DeleteMapping("/{id}")
    public R deleteVideo(@PathVariable("id") String id) {
        videoService.deleteVideo(id);
        return R.ok();
    }

    @PostMapping("/updateVideo")
    public R updateVideo(@RequestBody Video video) {
        videoService.updateById(video);
        return R.ok();
    }

    @GetMapping("/getVideoInfo/{videoId}")
    public R getVideoInfo(@PathVariable("videoId") String videoId) {
        Video video = videoService.getById(videoId);
        return R.ok().data("video", video);
    }

}

