package com.youlai.system.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.youlai.system.common.result.PageResult;
import com.youlai.system.common.result.Result;
import com.youlai.system.common.util.ExcelUtils;
import com.youlai.system.mapper.SysUserMapper;
import com.youlai.system.plugin.dupsubmit.annotation.PreventDuplicateSubmit;
import com.youlai.system.plugin.easyexcel.UserImportListener;
import com.youlai.system.model.vo.UserImportVO;
import com.youlai.system.model.form.UserForm;
import com.youlai.system.model.entity.SysUser;
import com.youlai.system.model.query.UserPageQuery;
import com.youlai.system.model.vo.UserExportVO;
import com.youlai.system.model.vo.UserInfoVO;
import com.youlai.system.model.vo.UserPageVO;
import com.youlai.system.service.SysUserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * 用户控制器
 *
 * @author haoxr
 * @since 2022/10/16
 */
@Tag(name = "02.用户接口")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserService userService;

    @Operation(summary = "用户分页列表")
    @GetMapping("/page")
    public PageResult<UserPageVO> listPagedUsers(
            @ParameterObject UserPageQuery queryParams
    ) {
        IPage<UserPageVO> result = userService.listPagedUsers(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "新增用户")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('sys:user:add')")
    @PreventDuplicateSubmit
    public Result saveUser(
            @RequestBody @Valid UserForm userForm
    ) {
        System.out.println("执行saveUser");
        boolean result = userService.saveUser(userForm);
        System.out.println("结果1是:"+result);
        System.out.println("接收到的用户数据是:");
        System.out.println("ID: " + userForm.getId());
        System.out.println("Username: " + userForm.getUsername());
        System.out.println("Nickname: " + userForm.getNickname());
        return Result.judge(result);
    }


    @Autowired
    private SysUserMapper sysUserMapper;
    @Operation(summary = "新增用户操作")
    @PreAuthorize("@ss.hasPerm('sys:user:add')")
    @PreventDuplicateSubmit
    @PostMapping("/add")
    public Result addUser(@RequestBody @Valid UserForm userForm) {
        //没执行
//        boolean result = userService.saveUser(userForm);
//        System.out.println("结果2是:"+result);

        boolean result =false;

        try {
            sysUserMapper.insertUser(userForm);
            result =true;
            System.out.println("insertUser方法执行成功！");
        } catch (Exception e) {
            System.err.println("insertUser方法执行失败：" + e.getMessage());
            e.printStackTrace();
        }


        System.out.println("执行addUser");
        System.out.println("Received user data:");
        System.out.println("ID: " + userForm.getId());
        System.out.println("Username: " + userForm.getUsername());
        System.out.println("Nickname: " + userForm.getNickname());
        System.out.println("Mobile: " + userForm.getMobile());
        System.out.println("Gender: " + userForm.getGender());
        System.out.println("Avatar: " + userForm.getAvatar());
        System.out.println("Email: " + userForm.getEmail());
        System.out.println("Status: " + userForm.getStatus());
        System.out.println("Dept ID: " + userForm.getDeptId());
        return Result.judge(result);
    }


    @Operation(summary = "用户表单数据")
    @GetMapping("/{userId}/form")
    public Result<UserForm> getUserForm(
            @Parameter(description = "用户ID") @PathVariable Long userId
    ) {
        UserForm formData = userService.getUserFormData(userId);
        return Result.success(formData);
    }




    @Operation(summary = "修改用户")
    @PutMapping(value = "/{userId}")
    @PreAuthorize("@ss.hasPerm('sys:user:edit')")
    public Result updateUser(
            @Parameter(description = "用户ID") @PathVariable Long userId,
            @RequestBody @Validated UserForm userForm) {
        System.out.println("执行updateUser");
        boolean result = userService.updateUser(userId, userForm);


        return Result.judge(result);
    }


    @Operation(summary = "修改用户操作")
    @PutMapping(value = "/update/{userId}")
    @PreAuthorize("@ss.hasPerm('sys:user:edit')")
    public Result updateSysUser(
            @Parameter(description = "用户ID") @PathVariable Long userId,
            @RequestBody @Validated UserForm userForm) {
//        boolean result = userService.updateUser(userId, userForm);
//        System.out.println("执行结果3是："+result);

        boolean result =false;

        try {
            sysUserMapper.updateUser(userId,userForm);
            result =true;
            System.out.println("updateUser方法执行成功！");
        } catch (Exception e) {
            System.err.println("updateUser方法执行失败：" + e.getMessage());
            e.printStackTrace();
        }


        return Result.judge(result);
    }


    @Operation(summary = "删除用户")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('sys:user:delete')")
    public Result deleteUsers(
            @Parameter(description = "用户ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = userService.deleteUsers(ids);
        return Result.judge(result);
    }


    @Operation(summary = "操作：删除用户")
    @DeleteMapping("/delete/{ids}")
    @PreAuthorize("@ss.hasPerm('sys:user:delete')")
    public Result deleteSysUsers(
            @Parameter(description = "用户ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
//        boolean result = userService.deleteUsers(ids);

        boolean result =false;

        try {
            sysUserMapper.deleteSysUsers(ids);
            result =true;
            System.out.println("deleteSysUsers方法执行成功！");
        } catch (Exception e) {
            System.err.println("deleteSysUsers方法执行失败：" + e.getMessage());
            e.printStackTrace();
        }



        System.out.println("执行结果4是："+result);
        return Result.judge(result);
    }



    @Operation(summary = "修改用户密码")
    @PatchMapping(value = "/{userId}/password")
    @PreAuthorize("@ss.hasPerm('sys:user:password:reset')")
    public Result updatePassword(
            @Parameter(description = "用户ID") @PathVariable Long userId,
            @RequestParam String password
    ) {
        boolean result = userService.updatePassword(userId, password);
        return Result.judge(result);
    }

    @Operation(summary = "修改用户状态")
    @PatchMapping(value = "/{userId}/status")
    public Result updateUserStatus(
            @Parameter(description = "用户ID") @PathVariable Long userId,
            @Parameter(description = "用户状态(1:启用;0:禁用)") @RequestParam Integer status
    ) {
        boolean result = userService.update(new LambdaUpdateWrapper<SysUser>()
                .eq(SysUser::getId, userId)
                .set(SysUser::getStatus, status)
        );
        return Result.judge(result);
    }

    @Operation(summary = "获取当前登录用户信息")
    @GetMapping("/me")
    public Result<UserInfoVO> getCurrentUserInfo() {
        UserInfoVO userInfoVO = userService.getCurrentUserInfo();
        return Result.success(userInfoVO);
    }

    @Operation(summary = "用户导入模板下载")
    @GetMapping("/template")
    public void downloadTemplate(HttpServletResponse response) throws IOException {
        String fileName = "用户导入模板.xlsx";
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));

        String fileClassPath = "excel-templates" + File.separator + fileName;
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileClassPath);

        ServletOutputStream outputStream = response.getOutputStream();
        ExcelWriter excelWriter = EasyExcel.write(outputStream).withTemplate(inputStream).build();

        excelWriter.finish();
    }

    @Operation(summary = "导入用户")
    @PostMapping("/_import")
    public Result importUsers(@Parameter(description = "部门ID") Long deptId, MultipartFile file) throws IOException {
        UserImportListener listener = new UserImportListener(deptId);
        String msg = ExcelUtils.importExcel(file.getInputStream(), UserImportVO.class, listener);
        return Result.success(msg);
    }

    @Operation(summary = "导出用户")
    @GetMapping("/_export")
    public void exportUsers(UserPageQuery queryParams, HttpServletResponse response) throws IOException {
        String fileName = "用户列表.xlsx";
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));

        List<UserExportVO> exportUserList = userService.listExportUsers(queryParams);
        EasyExcel.write(response.getOutputStream(), UserExportVO.class).sheet("用户列表")
                .doWrite(exportUserList);
    }
}
