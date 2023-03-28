package com.roc.blog.server.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.roc.blog.common.constant.ResponseData;
import com.roc.blog.server.dao.entity.AppUser;
import com.roc.blog.server.dao.mapper.AppUserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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

    @SneakyThrows
    @Operation(summary = "查询用户", description = "返回用户数据")
    @GetMapping(value = "/user")
    public AppUser getUserList(@RequestParam(defaultValue = "1") Integer id) {
        AppUser user = userMapper.selectById(id);
        Thread.sleep(2000);
        log.info(user.getName());
        return user;
    }

    @Operation(summary = "查询用户列表", description = "返回所有用户数据")
    @GetMapping(value = "/userList1")
    public ResponseData<List<AppUser>> getUserList() {

        List<AppUser> list = userMapper.selectList(Wrappers.lambdaQuery(AppUser.class));
        log.info(String.valueOf(list.size()));
        log.warn(String.valueOf(list.size()));
        log.error(String.valueOf(list.size()));
        return ResponseData.success(list);
        //throw new RuntimeException("haha");

    }

    @Operation(summary = "查询用户列表", description = "返回所有用户数据")
    @GetMapping(value = "/userList2")
    public List<AppUser> getUserList2() {
        return userMapper.selectList(Wrappers.lambdaQuery(AppUser.class));
    }
}
