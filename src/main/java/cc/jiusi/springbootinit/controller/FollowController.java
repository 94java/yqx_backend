package cc.jiusi.springbootinit.controller;

import cc.jiusi.springbootinit.common.BaseResponse;
import cc.jiusi.springbootinit.common.DeleteRequest;
import cc.jiusi.springbootinit.model.entity.Follow;
import cc.jiusi.springbootinit.service.FollowService;
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
 * @CreateTime: 2024-05-10 20:11:03
 * @Description: 用户关注信息(Follow)表控制层
 */
@RestController
@RequestMapping("follow")
@Slf4j
@Api(value = "用户关注信息", tags = {"用户关注信息"})
public class FollowController {
    /**
     * 服务对象
     */
    @Resource
    private FollowService followService;


    /**
     * 改变关注状态
     *
     * @param follow 关注信息
     * @return 单条数据
     */
    @PostMapping("/changeFollow")
    @ApiOperation("改变关注状态")
    public BaseResponse<Void> changeFollow(@RequestBody Follow follow) {
        followService.changeFollow(follow);
        return ResultUtils.success(null);
    }


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/get")
    @ApiOperation("通过主键查询单条数据")
    public BaseResponse<Follow> getById(Long id) {
        return ResultUtils.success(followService.queryById(id));
    }

    /**
     * 通过条件查询所有数据
     *
     * @param follow 查询条件
     * @return List<Follow> 实例对象列表
     */
    @PostMapping("/list")
    @ApiOperation("通过条件查询所有数据")
    public BaseResponse<List<Follow>> getList(@RequestBody Follow follow) {
        return ResultUtils.success(followService.queryAll(follow));
    }

    /**
     * 通过条件查询分页数据
     *
     * @param follow 查询条件
     * @return List<Follow> 实例对象列表
     */
    @PostMapping("/page")
    @ApiOperation("通过条件查询分页数据")
    public BaseResponse<PageInfo<Follow>> getPage(@RequestBody Follow follow) {
        return ResultUtils.success(followService.queryPage(follow));
    }

    /**
     * 根据条件统计总行数
     *
     * @param follow 查询条件
     * @return 总行数
     */
    @PostMapping("/count")
    @ApiOperation("根据条件统计总行数")
    public BaseResponse<Long> getCount(@RequestBody Follow follow) {
        return ResultUtils.success(followService.queryCount(follow));
    }

    /**
     * 新增数据
     *
     * @param follow 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    @ApiOperation("新增数据")
    public BaseResponse<Follow> add(@RequestBody Follow follow) {
        return ResultUtils.success(followService.insert(follow));
    }

    /**
     * 批量新增数据
     *
     * @param entities List<Follow> 实例对象列表
     * @return 影响行数
     */
    @PostMapping("/addBatch")
    @ApiOperation("批量新增数据")
    public BaseResponse<Integer> addBatch(@RequestBody List<Follow> entities) {
        return ResultUtils.success(followService.insertBatch(entities));
    }

    /**
     * 编辑数据
     *
     * @param follow 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    @ApiOperation("编辑数据")
    public BaseResponse<Follow> update(@RequestBody Follow follow) {
        return ResultUtils.success(followService.update(follow));
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
        return ResultUtils.success(followService.deleteBatchByIds(deleteRequest));
    }
}

