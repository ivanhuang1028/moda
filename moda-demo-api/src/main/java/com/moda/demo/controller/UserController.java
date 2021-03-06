package com.moda.demo.controller;

import com.moda.demo.entity.User;
import com.moda.demo.request.UserGetSimpleRequest;
import com.moda.demo.request.UserListByStatusRequest;
import com.moda.demo.request.UserSearchRequest;
import com.moda.demo.response.UserGetSimpleResponse;
import com.moda.demo.service.UserService;
import com.moda.entity.annotation.AuthPermission;
import com.moda.entity.persistence.page.Page;
import com.moda.entity.rest.BaseRequest;
import com.moda.entity.rest.Result;
import com.moda.entity.session.CurrentUser;
import com.moda.web.spring.boot.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller
 *
 * @author lyh
 * @date 2019-04-02 17:36:58
 **/
@RestController
@Api(tags = "用户", description = "用户操作相关接口")
@RequestMapping(value = "/user/", method = RequestMethod.POST)
public class UserController extends BaseController {
    @Reference
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("getSimple")
    @ApiOperation(value = "用户信息", notes = "根据输入用户ID查询用户基本信息")
    public Result<UserGetSimpleResponse> getSimple(@RequestBody UserGetSimpleRequest param) {
        return success(userService.getSimple(param));
    }

    @RequestMapping("search")
    @ApiOperation(value = "用户信息", notes = "根据关键词搜索用户信息")
    public Result<Page<User>> search(@RequestBody UserSearchRequest request) {
        logger.info("search...");
        return success(userService.search(request));
    }

    @RequestMapping("listByStatus")
    @ApiOperation(value = "用户信息", notes = "根据状态搜索用户信息")
    public Result<Page<User>> listByStatus(@RequestBody UserListByStatusRequest request) {
        return success(userService.listByStatus(request));
    }

    @RequestMapping("test")
    @AuthPermission("user.test")
    public Result test(@RequestBody BaseRequest request) {
        logger.info("test...");
        return success(sessionContext.getCurrentUser(request));
    }

    @RequestMapping("login")
    public Result login(@RequestBody BaseRequest request) {
        logger.info("test...");
        CurrentUser currentUser = new CurrentUser();
        currentUser.setAccessToken(request.getAccessToken());
        currentUser.setId(1);
        currentUser.setUsername("lyh");
        sessionContext.setCurrentUser(currentUser);
        return success(currentUser);
    }
}
