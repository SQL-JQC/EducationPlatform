package com.qinC.acl.controller;

import com.qinC.acl.entity.Role;
import com.qinC.acl.service.RoleService;
import com.qinC.commonutils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@RestController
@RequestMapping("/admin/acl/role")
@RefreshScope
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "获取角色分页列表")
    @GetMapping("/{page}/{limit}")
    public R index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable("page") Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable("limit") Long limit,
            Role role) {
        Page<Role> pageParam = new Page<Role>(page, limit);
        QueryWrapper<Role> wrapper = new QueryWrapper<Role>();
        if (!StringUtils.isEmpty(role.getRoleName())) {
            wrapper.like("role_name", role.getRoleName());
        }
        roleService.page(pageParam, wrapper);
        return R.ok().data("items", pageParam.getRecords()).data("total", pageParam.getTotal());
    }

    @ApiOperation(value = "获取角色")
    @GetMapping("/get/{id}")
    public R get(@PathVariable("id") String id) {
        Role role = roleService.getById(id);
        return R.ok().data("item", role);
    }

    @ApiOperation(value = "新增角色")
    @PostMapping("/save")
    public R save(@RequestBody Role role) {
        roleService.save(role);
        return R.ok();
    }

    @ApiOperation(value = "修改角色")
    @PutMapping("/update")
    public R updateById(@RequestBody Role role) {
        roleService.updateById(role);
        return R.ok();
    }

//    @GetMapping("/toAssign/{roleId}")
//    public R toAssign(@PathVariable("roleId") String roleId) {
//        Map<String, Object> roleMap = roleService.findRoleByUserId();
//        return R.ok().data(roleMap);
//    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("/remove/{id}")
    public R remove(@PathVariable("id") String id) {
        roleService.removeById(id);
        return R.ok();
    }

    @ApiOperation(value = "根据id列表删除角色")
    @DeleteMapping("/batchRemove")
    public R batchRemove(@RequestBody List<String> idList) {
        roleService.removeByIds(idList);
        return R.ok();
    }

}
