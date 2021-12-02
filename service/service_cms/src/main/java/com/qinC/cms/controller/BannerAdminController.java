package com.qinC.cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qinC.cms.entity.CrmBanner;
import com.qinC.cms.service.CrmBannerService;
import com.qinC.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author qinC
 * @since 2021-11-19
 */
@RestController
@RequestMapping("/cms/banneradmin")
@CrossOrigin
@RefreshScope
public class BannerAdminController {

    @Autowired
    private CrmBannerService bannerService;

    @GetMapping("/pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable("page") Long page, @PathVariable("limit") Long limit) {
        Page<CrmBanner> pageBanner = new Page<CrmBanner>(page, limit);
        bannerService.page(pageBanner,null);
        return R.ok().data("items", pageBanner.getRecords()).data("total", pageBanner.getTotal());
    }

    @PostMapping("/addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner) {
        bannerService.save(crmBanner);
        return R.ok();
    }

    @GetMapping("/get/{id}")
    public R get(@PathVariable("id") String id) {
        CrmBanner banner = bannerService.getById(id);
        return R.ok().data("item", banner);
    }

    @PutMapping("/update")
    public R updateById(@RequestBody CrmBanner banner) {
        bannerService.updateById(banner);
        return R.ok();
    }

    @DeleteMapping("/remove/{id}")
    public R remove(@PathVariable("id") String id) {
        bannerService.removeById(id);
        return R.ok();
    }

}

