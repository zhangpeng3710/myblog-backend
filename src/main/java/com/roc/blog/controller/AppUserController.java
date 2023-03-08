package com.roc.blog.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.roc.blog.common.ResultData;
import com.roc.blog.dao.entity.AppUser;
import com.roc.blog.dao.mapper.AppUserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.Wrapper;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mybatis-plus-auto-generator
 * @since 2023--03-03 13:36:29
 */
@Tag(name = "用户管理", description = "用户数据增删改查")
@Slf4j
@RestController
@RequestMapping("/test/appUser")
public class AppUserController {

    @Resource
     private AppUserMapper userMapper;

    @Operation(summary = "查询用户", description = "返回用户数据")
    @GetMapping(value = "/user")
    public AppUser getUserList(@RequestParam(defaultValue = "1") Integer id) {
        return userMapper.selectById(id);
    }

    @Operation(summary = "查询用户列表", description = "返回所有用户数据")
    @GetMapping(value = "/userList1")
    public ResultData getUserList() {

        List<AppUser> list = userMapper.selectList(Wrappers.lambdaQuery(AppUser.class));
        log.info(String.valueOf(list.stream().count()));
        log.warn(String.valueOf(list.stream().count()));
        log.error(String.valueOf(list.stream().count()));
        throw new RuntimeException("haha");

    }

    @Operation(summary = "查询用户列表", description = "返回所有用户数据")
    @GetMapping(value = "/userList2")
    public List getUserList2() {
        return userMapper.selectList(Wrappers.lambdaQuery(AppUser.class));
    }
}
