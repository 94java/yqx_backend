package cc.jiusi.yqx.controller;

import cc.jiusi.yqx.annotation.AuthCheck;
import cc.jiusi.yqx.common.*;
import cc.jiusi.yqx.constant.UserConstant;
import cc.jiusi.yqx.exception.BusinessException;
import cc.jiusi.yqx.model.dto.user.*;
import cc.jiusi.yqx.model.vo.LoginUserVO;
import cc.jiusi.yqx.model.vo.UserVO;
import cc.jiusi.yqx.utils.ResultUtils;
import cc.jiusi.yqx.model.entity.User;
import cc.jiusi.yqx.service.UserService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-04-29 20:39:06
 * @Description: 用户信息(User)表控制层
 */
@RestController
@RequestMapping("user")
@Slf4j
@Api(value = "用户信息", tags = {"用户信息"})
@AuthCheck(mustRole = UserConstant.USER_ROLE)
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;

    // region 登录注册

    /**
     * 通过username and password 进行登录
     *
     * @param userLoginRequest 用户登录请求
     * @return token
     */
    @PostMapping("/login")
    @ApiOperation("账号密码登录")
    @AuthCheck(enableCheck = false)
    public BaseResponse<String> login(HttpServletRequest request, @RequestBody UserLoginRequest userLoginRequest) {
        // 参数校验
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String username = userLoginRequest.getUsername();
        String password = userLoginRequest.getPassword();
        if (!StrUtil.isAllNotBlank(username, password)) {
            // 账号或密码不能为空
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String token = userService.login(request, username, password);
        return ResultUtils.success(token);
    }

    /**
     * 获取当前用户信息
     *
     * @return token
     */
    @GetMapping("/currentUser")
    @ApiOperation("账号密码登录")
    @AuthCheck(enableCheck = false)
    public BaseResponse<LoginUserVO> currentUser(){
        User user = userService.queryById(UserContextHolder.getUserId());
        LoginUserVO loginUserVO = BeanUtil.copyProperties(user, LoginUserVO.class);
        return ResultUtils.success(loginUserVO);
    }

    /**
     * 发送邮箱验证码
     *
     * @param email 接收邮箱
     */
    @PostMapping("/sendEmailCode")
    @ApiOperation("发送邮箱验证码")
    @AuthCheck(enableCheck = false)
    public BaseResponse<Void> sendEmailCode(String email) {
        if (StrUtil.isEmpty(email)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        userService.sendEmailCode(email);
        return ResultUtils.success(null);
    }

    /**
     * 通过邮箱验证码快捷登录
     *
     * @param userLoginRequest 用户登录请求
     * @return token
     */
    @PostMapping("/loginByEmail")
    @ApiOperation("邮箱验证码快速登录")
    @AuthCheck(enableCheck = false)
    public BaseResponse<String> loginByEmail(HttpServletRequest request, @RequestBody UserLoginRequest userLoginRequest) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String email = userLoginRequest.getEmail();
        String code = userLoginRequest.getCode();
        if (!StrUtil.isAllNotBlank(email, code)) {
            // 有一个为空
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String token = userService.loginByEmail(request, email, code);
        return ResultUtils.success(token);
    }

    /**
     * 用户注册
     *
     * @param userRegisterRequest 用户注册请求
     * @return boolean 是否注册成功
     */
    @PostMapping("/register")
    @ApiOperation("用户注册")
    @AuthCheck(enableCheck = false)
    public BaseResponse<Void> register(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String username = userRegisterRequest.getUsername();
        String password = userRegisterRequest.getPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        // 有一个参数为空时
        if (!StrUtil.isAllNotBlank(username, password, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 校验两次密码是否一致
        if (!StrUtil.equals(password, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次密码输入不一致");
        }
        userService.register(userRegisterRequest);
        return ResultUtils.success(null);
    }

    /**
     * 用户注册
     *
     * @return boolean 是否注册成功
     */
    @PostMapping("/logout")
    @ApiOperation("退出登录")
    @AuthCheck(enableCheck = false)
    public BaseResponse<Void> logout(HttpServletRequest request) {
        String token = request.getHeader(UserConstant.USER_TOKEN_HEADER);
        if(StrUtil.isEmpty(token)){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        userService.logout(token);
        return ResultUtils.success(null);
    }

    // endregion


    // region 增删改查

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/get")
    @ApiOperation("通过主键查询单条数据")
    @AuthCheck(enableCheck = false)
    public BaseResponse<User> getById(Long id) {
        User user = userService.queryById(id);
        user.setPassword(null);
        return ResultUtils.success(user);
    }

    /**
     * 获取用户排名
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/getRange")
    @ApiOperation("获取用户排名")
    @AuthCheck(enableCheck = false)
    public BaseResponse<Integer> getRange(Long id) {
        return ResultUtils.success(userService.queryRange(id));
    }

    /**
     * 获取用户统计数据
     *
     * @return 单条数据
     */
    @GetMapping("/getStatistics")
    @ApiOperation("获取用户统计数据")
    public BaseResponse<Map<String,Object>> getStatistics() {
        return ResultUtils.success(userService.getStatistics());
    }


    /**
     * 通过条件查询所有数据
     *
     * @param userQueryRequest 查询条件
     * @return List<User> 实例对象列表
     */
    @PostMapping("/list")
    @ApiOperation("通过条件查询所有数据")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<List<User>> getList(@RequestBody UserQueryRequest userQueryRequest) {
        return ResultUtils.success(userService.queryAll(userQueryRequest));
    }

    /**
     * 获取当前用户访客数据
     *
     * @return List<User> 实例对象列表
     */
    @GetMapping("/currentVistor")
    @ApiOperation("获取当前用户访客数据")
    public BaseResponse<List<UserVO>> currentVistor() {
        return ResultUtils.success(userService.queryCurrentVistor());
    }

    /**
     * 通过条件查询分页数据
     *
     * @param userQueryRequest 查询条件
     * @return List<User> 实例对象列表
     */
    @PostMapping("/page")
    @ApiOperation("通过条件查询分页数据")
    @AuthCheck(enableCheck = false)
    public BaseResponse<PageInfo<User>> getPage(@RequestBody UserQueryRequest userQueryRequest) {
        return ResultUtils.success(userService.queryPage(userQueryRequest));
    }

    /**
     * 根据条件统计总行数
     *
     * @param userQueryRequest 查询条件
     * @return 总行数
     */
    @PostMapping("/count")
    @ApiOperation("根据条件统计总行数")
    @AuthCheck(enableCheck = false)
    public BaseResponse<Long> getCount(@RequestBody UserQueryRequest userQueryRequest) {
        return ResultUtils.success(userService.queryCount(userQueryRequest));
    }

    /**
     * 新增数据
     *
     * @param userAddRequest 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    @ApiOperation("新增数据")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<User> add(@RequestBody UserAddRequest userAddRequest) {
        return ResultUtils.success(userService.insert(userAddRequest));
    }

    /**
     * 批量新增数据
     *
     * @param entities List<UserAddRequest> 实例对象列表
     * @return 影响行数
     */
    @PostMapping("/addBatch")
    @ApiOperation("批量新增数据")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Integer> addBatch(@RequestBody List<UserAddRequest> entities) {
        return ResultUtils.success(userService.insertBatch(entities));
    }

    /**
     * 编辑数据
     *
     * @param userUpdateRequest 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    @ApiOperation("编辑数据")
    public BaseResponse<User> update(@RequestBody UserUpdateRequest userUpdateRequest) {
        return ResultUtils.success(userService.update(userUpdateRequest));
    }

    /**
     * 批量变更状态
     *
     * @param statusUpdateRequest 实体
     * @return 编辑结果
     */
    @PostMapping("/changeStatus")
    @ApiOperation("批量变更状态")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Void> changeStatus(@RequestBody StatusUpdateRequest statusUpdateRequest) {
        if(statusUpdateRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String status = statusUpdateRequest.getStatus();
        List<Long> ids = statusUpdateRequest.getIds();
        if(CollUtil.isEmpty(ids) || StrUtil.isBlank(status)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        userService.changeStatus(statusUpdateRequest);
        return ResultUtils.success(null);
    }

    /**
     * 通过主键集合批量删除数据
     *
     * @param deleteRequest 删除请求对象
     * @return 影响行数
     */
    @PostMapping("/delete")
    @ApiOperation("通过主键集合批量删除数据")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Integer> deleteBatchByIds(@RequestBody DeleteRequest deleteRequest) {
        return ResultUtils.success(userService.deleteBatchByIds(deleteRequest));
    }
    // endregion

    // region 前台模块

    /**
     * 获取活跃用户Top10
     *
     * @return 影响行数
     */
    @GetMapping("/activityUser")
    @ApiOperation("获取活跃用户Top10")
    @AuthCheck(enableCheck = false)
    public BaseResponse<List<UserVO>> getActivityUser() {
        return ResultUtils.success(userService.getActivityUser());
    }

    // endregion
}

