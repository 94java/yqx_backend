package cc.jiusi.yqx.controller;

import cc.jiusi.yqx.annotation.AuthCheck;
import cc.jiusi.yqx.common.BaseResponse;
import cc.jiusi.yqx.common.DeleteRequest;
import cc.jiusi.yqx.common.UserContextHolder;
import cc.jiusi.yqx.constant.UserConstant;
import cc.jiusi.yqx.model.entity.Comment;
import cc.jiusi.yqx.service.CommentService;
import cc.jiusi.yqx.utils.ResultUtils;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-10 13:55:19
 * @Description: 评论信息(Comment)表控制层
 */
@RestController
@RequestMapping("comment")
@Slf4j
@Api(value = "评论信息", tags = {"评论信息"})
@AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
public class CommentController {
    /**
     * 服务对象
     */
    @Resource
    private CommentService commentService;


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/get")
    @ApiOperation("通过主键查询单条数据")
    @AuthCheck(enableCheck = false)
    public BaseResponse<Comment> getById(Long id) {
        return ResultUtils.success(commentService.queryById(id));
    }

    /**
     * 通过条件查询所有数据
     *
     * @param comment 查询条件
     * @return List<Comment> 实例对象列表
     */
    @PostMapping("/list")
    @ApiOperation("通过条件查询所有数据")
    @AuthCheck(enableCheck = false)
    public BaseResponse<List<Comment>> getList(@RequestBody Comment comment, HttpServletRequest request) {
        String flag = request.getHeader("Admin");
        String role = UserContextHolder.getUserRole();
        if (StrUtil.isNotBlank(flag) && "admin".equals(flag) && !UserConstant.ADMIN_ROLE.equals(role)) {
            // 后台，用户不是管理员只能查看自己的数据
            comment.setUid(UserContextHolder.getUserId());
        }
        return ResultUtils.success(commentService.queryAll(comment));
    }

    /**
     * 通过条件查询分页数据
     *
     * @param comment 查询条件
     * @return List<Comment> 实例对象列表
     */
    @PostMapping("/page")
    @ApiOperation("通过条件查询分页数据")
    @AuthCheck(enableCheck = false)
    public BaseResponse<PageInfo<Comment>> getPage(@RequestBody Comment comment,HttpServletRequest request) {
        String flag = request.getHeader("Admin");
        String role = UserContextHolder.getUserRole();
        if (StrUtil.isNotBlank(flag) && "admin".equals(flag) && !UserConstant.ADMIN_ROLE.equals(role)) {
            // 后台，用户不是管理员只能查看自己的数据
            comment.setUid(UserContextHolder.getUserId());
        }
        return ResultUtils.success(commentService.queryPage(comment));
    }

    /**
     * 根据条件统计总行数
     *
     * @param comment 查询条件
     * @return 总行数
     */
    @PostMapping("/count")
    @ApiOperation("根据条件统计总行数")
    @AuthCheck(enableCheck = false)
    public BaseResponse<Long> getCount(@RequestBody Comment comment) {
        return ResultUtils.success(commentService.queryCount(comment));
    }

    /**
     * 新增数据
     *
     * @param comment 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    @ApiOperation("新增数据")
    @AuthCheck(mustRole = UserConstant.USER_ROLE)
    public BaseResponse<Comment> add(@RequestBody Comment comment) {
        return ResultUtils.success(commentService.insert(comment));
    }

    /**
     * 批量新增数据
     *
     * @param entities List<Comment> 实例对象列表
     * @return 影响行数
     */
    @PostMapping("/addBatch")
    @ApiOperation("批量新增数据")
    public BaseResponse<Integer> addBatch(@RequestBody List<Comment> entities) {
        return ResultUtils.success(commentService.insertBatch(entities));
    }

    /**
     * 编辑数据
     *
     * @param comment 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    @ApiOperation("编辑数据")
    public BaseResponse<Comment> update(@RequestBody Comment comment) {
        return ResultUtils.success(commentService.update(comment));
    }

    /**
     * 通过主键集合批量删除数据
     *
     * @param deleteRequest 删除请求对象
     * @return 影响行数
     */
    @PostMapping("/delete")
    @ApiOperation("通过主键集合批量删除数据")
    @AuthCheck(mustRole = UserConstant.USER_ROLE)
    public BaseResponse<Integer> deleteBatchByIds(@RequestBody DeleteRequest deleteRequest) {
        return ResultUtils.success(commentService.deleteBatchByIds(deleteRequest));
    }
}

