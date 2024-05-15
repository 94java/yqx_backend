package cc.jiusi.yqx.controller;

import cc.jiusi.yqx.common.BaseResponse;
import cc.jiusi.yqx.common.DeleteRequest;
import cc.jiusi.yqx.model.entity.Likes;
import cc.jiusi.yqx.service.LikesService;
import cc.jiusi.yqx.utils.ResultUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-10 21:27:28
 * @Description: 点赞信息(Likes)表控制层
 */
@RestController
@RequestMapping("likes")
@Slf4j
@Api(value = "点赞信息", tags = {"点赞信息"})
public class LikesController {
    /**
     * 服务对象
     */
    @Resource
    private LikesService likesService;

    /**
     * 改变点赞状态
     *
     * @param likes 点赞信息
     */
    @PostMapping("/changeLikes")
    @ApiOperation("改变点赞状态")
    public BaseResponse<Void> changeLikes(@RequestBody Likes likes) {
        likesService.changeLikes(likes);
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
    public BaseResponse<Likes> getById(Long id) {
        return ResultUtils.success(likesService.queryById(id));
    }

    /**
     * 通过条件查询所有数据
     *
     * @param likes 查询条件
     * @return List<Likes> 实例对象列表
     */
    @PostMapping("/list")
    @ApiOperation("通过条件查询所有数据")
    public BaseResponse<Map<String,List>> getList(@RequestBody Likes likes) {
        return ResultUtils.success(likesService.queryAll(likes));
    }

    /**
     * 通过条件查询分页数据
     *
     * @param likes 查询条件
     * @return List<Likes> 实例对象列表
     */
    @PostMapping("/page")
    @ApiOperation("通过条件查询分页数据")
    public BaseResponse<PageInfo<Likes>> getPage(@RequestBody Likes likes) {
        return ResultUtils.success(likesService.queryPage(likes));
    }

    /**
     * 根据条件统计总行数
     *
     * @param likes 查询条件
     * @return 总行数
     */
    @PostMapping("/count")
    @ApiOperation("根据条件统计总行数")
    public BaseResponse<Long> getCount(@RequestBody Likes likes) {
        return ResultUtils.success(likesService.queryCount(likes));
    }

    /**
     * 新增数据
     *
     * @param likes 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    @ApiOperation("新增数据")
    public BaseResponse<Likes> add(@RequestBody Likes likes) {
        return ResultUtils.success(likesService.insert(likes));
    }

    /**
     * 批量新增数据
     *
     * @param entities List<LikesAddRequest> 实例对象列表
     * @return 影响行数
     */
    @PostMapping("/addBatch")
    @ApiOperation("批量新增数据")
    public BaseResponse<Integer> addBatch(@RequestBody List<Likes> entities) {
        return ResultUtils.success(likesService.insertBatch(entities));
    }

    /**
     * 编辑数据
     *
     * @param likes 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    @ApiOperation("编辑数据")
    public BaseResponse<Likes> update(@RequestBody Likes likes) {
        return ResultUtils.success(likesService.update(likes));
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
        return ResultUtils.success(likesService.deleteBatchByIds(deleteRequest));
    }
}

