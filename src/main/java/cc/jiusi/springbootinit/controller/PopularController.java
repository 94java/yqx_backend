package cc.jiusi.springbootinit.controller;

import cc.jiusi.springbootinit.common.BaseResponse;
import cc.jiusi.springbootinit.common.DeleteRequest;
import cc.jiusi.springbootinit.model.entity.Popular;
import cc.jiusi.springbootinit.service.PopularService;
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
 * @CreateTime: 2024-05-12 16:05:54
 * @Description: 动态信息(Popular)表控制层
 */
@RestController
@RequestMapping("popular")
@Slf4j
@Api(value = "动态信息", tags = {"动态信息"})
public class PopularController {
    /**
     * 服务对象
     */
    @Resource
    private PopularService popularService;


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/get")
    @ApiOperation("通过主键查询单条数据")
    public BaseResponse<Popular> getById(Long id) {
        return ResultUtils.success(popularService.queryById(id));
    }

    /**
     * 通过条件查询所有数据
     *
     * @param popular 查询条件
     * @return List<Popular> 实例对象列表
     */
    @PostMapping("/list")
    @ApiOperation("通过条件查询所有数据")
    public BaseResponse<List<Popular>> getList(@RequestBody Popular popular) {
        return ResultUtils.success(popularService.queryAll(popular));
    }

    /**
     * 查询当前用户关注用户的动态列表
     *
     * @return List<Popular> 实例对象列表
     */
    @GetMapping("/currentFollow")
    @ApiOperation("查询当前用户关注用户的动态列表")
    public BaseResponse<List<Popular>> currentFollow() {
        return ResultUtils.success(popularService.currentFollow());
    }

    /**
     * 通过条件查询分页数据
     *
     * @param popular 查询条件
     * @return List<Popular> 实例对象列表
     */
    @PostMapping("/page")
    @ApiOperation("通过条件查询分页数据")
    public BaseResponse<PageInfo<Popular>> getPage(@RequestBody Popular popular) {
        return ResultUtils.success(popularService.queryPage(popular));
    }

    /**
     * 根据条件统计总行数
     *
     * @param popular 查询条件
     * @return 总行数
     */
    @PostMapping("/count")
    @ApiOperation("根据条件统计总行数")
    public BaseResponse<Long> getCount(@RequestBody Popular popular) {
        return ResultUtils.success(popularService.queryCount(popular));
    }

    /**
     * 新增数据
     *
     * @param popular 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    @ApiOperation("新增数据")
    public BaseResponse<Popular> add(@RequestBody Popular popular) {
        return ResultUtils.success(popularService.insert(popular));
    }

    /**
     * 批量新增数据
     *
     * @param entities List<Popular> 实例对象列表
     * @return 影响行数
     */
    @PostMapping("/addBatch")
    @ApiOperation("批量新增数据")
    public BaseResponse<Integer> addBatch(@RequestBody List<Popular> entities) {
        return ResultUtils.success(popularService.insertBatch(entities));
    }

    /**
     * 编辑数据
     *
     * @param popular 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    @ApiOperation("编辑数据")
    public BaseResponse<Popular> update(@RequestBody Popular popular) {
        return ResultUtils.success(popularService.update(popular));
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
        return ResultUtils.success(popularService.deleteBatchByIds(deleteRequest));
    }
}

