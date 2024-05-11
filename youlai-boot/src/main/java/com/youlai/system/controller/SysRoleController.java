package com.youlai.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.system.mapper.SysRoleMenuMapper;
import com.youlai.system.mapper.SysUserMapper;
import com.youlai.system.plugin.dupsubmit.annotation.PreventDuplicateSubmit;
import com.youlai.system.common.model.Option;
import com.youlai.system.common.result.PageResult;
import com.youlai.system.common.result.Result;
import com.youlai.system.model.form.RoleForm;
import com.youlai.system.model.query.RolePageQuery;
import com.youlai.system.model.vo.RolePageVO;
import com.youlai.system.service.SysRoleService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@Tag(name = "03.角色接口")
@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class SysRoleController {

    private final SysRoleService roleService;

    @Operation(summary = "角色分页列表")
    @GetMapping("/page")
    public PageResult<RolePageVO> getRolePage(
            @RequestParam(value = "current", defaultValue = "1") Integer current,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @ParameterObject RolePageQuery queryParams
    ) {
        System.out.println("查找的参数是: " + queryParams);
        System.out.println("查找的关键词是: " + queryParams.getKeywords());
        Page<RolePageVO> page = new Page<>(current, size);
        IPage<RolePageVO> result= sysRoleMenuMapper.queryByKeywords(page,queryParams.getKeywords());
        System.out.println("result is: " + result);


//        Page<RolePageVO> result = roleService.getRolePage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "角色下拉列表")
    @GetMapping("/options")
    public Result<List<Option>> listRoleOptions() {
        List<Option> list = roleService.listRoleOptions();
        return Result.success(list);
    }

    @Operation(summary = "新增角色")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('sys:role:add')")
    @PreventDuplicateSubmit
    public Result addRole(@Valid @RequestBody RoleForm roleForm) {
        boolean result = roleService.saveRole(roleForm);
        return Result.judge(result);
    }

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Operation(summary = "操作：新增角色")
    @PostMapping("/add")
//    @PreAuthorize("@ss.hasPerm('sys:role:add')")
    @PreventDuplicateSubmit
    public Result addNewRole(@Valid @RequestBody RoleForm roleForm) {
//        boolean result = roleService.saveRole(roleForm);
//        System.out.println("新增角色结果："+result);

        try {
            sysRoleMenuMapper.insertRole(roleForm);
            System.out.println("insertRole方法执行成功！");
        } catch (Exception e) {
            System.err.println("insertRole方法执行失败：" + e.getMessage());
            e.printStackTrace();
        }

        boolean result =true;
        return Result.judge(result);
    }



    @Operation(summary = "角色表单数据")
    @GetMapping("/{roleId}/form")
    public Result<RoleForm> getRoleForm(
            @Parameter(description = "角色ID") @PathVariable Long roleId
    ) {
        RoleForm roleForm = roleService.getRoleForm(roleId);
        return Result.success(roleForm);
    }

//    @Operation(summary = "修改角色")
//    @PutMapping(value = "/{id}")
//    @PreAuthorize("@ss.hasPerm('sys:role:edit')")
//    public Result updateRole(@Valid @RequestBody RoleForm roleForm) {
//        boolean result = roleService.saveRole(roleForm);
//        System.out.println("执行updateRole，修改角色结果："+result);
//        return Result.judge(result);
//    }


    @Operation(summary = "操作:修改角色")
    @PutMapping(value = "/update/{id}")
    @PreAuthorize("@ss.hasPerm('sys:role:edit')")
    public Result updateTheRole(@PathVariable Long id,
                                @Valid @RequestBody RoleForm roleForm) {
        System.out.println("执行updateTheRole，修改角色结果："+id);
        //决定了能否成功
//        boolean result = roleService.saveRole(roleForm);
//        System.out.println("修改角色结果："+result);
        boolean result =true;
        try {
            sysRoleMenuMapper.updateRole(id, roleForm);
            System.out.println("updateRole方法执行成功！");
        } catch (Exception e) {
            System.err.println("updateRole方法执行失败：" + e.getMessage());
            e.printStackTrace();
        }

        return Result.judge(result);
    }



    @Operation(summary = "删除角色")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('sys:role:delete')")
    public Result deleteRoles(
            @Parameter(description = "删除角色，多个以英文逗号(,)拼接") @PathVariable String ids
    ) {
        boolean result = roleService.deleteRoles(ids);
        return Result.judge(result);
    }



    @Operation(summary = "操作:删除角色")
    @DeleteMapping("/delete/{ids}")
    @PreAuthorize("@ss.hasPerm('sys:role:delete')")
    public Result deleteOneRoles(
            @Parameter(description = "删除角色，多个以英文逗号(,)拼接") @PathVariable String ids
    ) {
//        boolean result = roleService.deleteRoles(ids);
        boolean result =false;

        try {
            sysRoleMenuMapper.deleteOneRoles(ids);
            result =true;
            System.out.println("deleteOneRoles方法执行成功！");
        } catch (Exception e) {
            System.err.println("deleteOneRoles方法执行失败：" + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("删除角色结果："+result);
        return Result.judge(result);
    }




    @Operation(summary = "修改角色状态")
    @PutMapping(value = "/{roleId}/status")
    public Result updateRoleStatus(
            @Parameter(description = "角色ID") @PathVariable Long roleId,
            @Parameter(description = "状态(1:启用;0:禁用)") @RequestParam Integer status
    ) {
        boolean result = roleService.updateRoleStatus(roleId, status);
        return Result.judge(result);
    }

    @Operation(summary = "获取角色的菜单ID集合")
    @GetMapping("/{roleId}/menuIds")
    public Result<List<Long>> getRoleMenuIds(
            @Parameter(description = "角色ID") @PathVariable Long roleId
    ) {
        List<Long> menuIds = roleService.getRoleMenuIds(roleId);
        return Result.success(menuIds);
    }

    @Operation(summary = "分配菜单(包括按钮权限)给角色")
    @PutMapping("/{roleId}/menus")
    public Result assignMenusToRole(
            @PathVariable Long roleId,
            @RequestBody List<Long> menuIds
    ) {
        boolean result = roleService.assignMenusToRole(roleId, menuIds);
        return Result.judge(result);
    }
}
