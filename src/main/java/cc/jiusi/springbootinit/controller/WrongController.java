package cc.jiusi.springbootinit.controller;

import cc.jiusi.springbootinit.common.BaseResponse;
import cc.jiusi.springbootinit.common.DeleteRequest;
import cc.jiusi.springbootinit.model.entity.Wrong;
import cc.jiusi.springbootinit.service.WrongService;
import cc.jiusi.springbootinit.utils.ResultUtils;
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
 * @CreateTime: 2024-05-14 18:41:57
 * @Description: 用户错题信息(Wrong)表控制层
 */
@RestController
@RequestMapping("wrong")
@Slf4j
@Api(value = "用户错题信息", tags = {"用户错题信息"})
public class WrongController {
    /**
     * 服务对象
     */
    @Resource
    private WrongService wrongService;


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/get")
    @ApiOperation("通过主键查询单条数据")
    public BaseResponse<Wrong> getById(Long id) {
        return ResultUtils.success(wrongService.queryById(id));
    }

    /**
     * 通过条件查询所有数据
     *
     * @param wrong 查询条件
     * @return List<Wrong> 实例对象列表
     */
    @PostMapping("/list")
    @ApiOperation("通过条件查询所有数据")
    public BaseResponse<List<Wrong>> getList(@RequestBody Wrong wrong) {
        return ResultUtils.success(wrongService.queryAll(wrong));
    }

    /**
     * 通过条件查询分页数据
     *
     * @param wrong 查询条件
     * @return List<Wrong> 实例对象列表
     */
    @PostMapping("/page")
    @ApiOperation("通过条件查询分页数据")
    public BaseResponse<PageInfo<Wrong>> getPage(@RequestBody Wrong wrong) {
        return ResultUtils.success(wrongService.queryPage(wrong));
    }

    /**
     * 根据条件统计总行数
     *
     * @param wrong 查询条件
     * @return 总行数
     */
    @PostMapping("/count")
    @ApiOperation("根据条件统计总行数")
    public BaseResponse<Long> getCount(@RequestBody Wrong wrong) {
        return ResultUtils.success(wrongService.queryCount(wrong));
    }

    /**
     * 新增数据
     *
     * @param wrong 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    @ApiOperation("新增数据")
    public BaseResponse<Wrong> add(@RequestBody Wrong wrong) {
        return ResultUtils.success(wrongService.insert(wrong));
    }

    /**
     * 批量新增数据
     *
     * @param entities List<Wrong> 实例对象列表
     * @return 影响行数
     */
    @PostMapping("/addBatch")
    @ApiOperation("批量新增数据")
    public BaseResponse<Integer> addBatch(@RequestBody List<Wrong> entities) {
        return ResultUtils.success(wrongService.insertBatch(entities));
    }

    /**
     * 编辑数据
     *
     * @param wrong 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    @ApiOperation("编辑数据")
    public BaseResponse<Wrong> update(@RequestBody Wrong wrong) {
        return ResultUtils.success(wrongService.update(wrong));
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
        return ResultUtils.success(wrongService.deleteBatchByIds(deleteRequest));
    }
}

