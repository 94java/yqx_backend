package cc.jiusi.yqx.controller;

import cc.jiusi.yqx.common.BaseResponse;
import cc.jiusi.yqx.common.DeleteRequest;
import cc.jiusi.yqx.model.entity.Comment;
import cc.jiusi.yqx.service.CommentService;
import cc.jiusi.yqx.utils.ResultUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
    public BaseResponse<List<Comment>> getList(@RequestBody Comment comment) {
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
    public BaseResponse<PageInfo<Comment>> getPage(@RequestBody Comment comment) {
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
    public BaseResponse<Integer> deleteBatchByIds(@RequestBody DeleteRequest deleteRequest) {
        return ResultUtils.success(commentService.deleteBatchByIds(deleteRequest));
    }
}

