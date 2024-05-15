package cc.jiusi.yqx.controller;

import cc.jiusi.yqx.annotation.AuthCheck;
import cc.jiusi.yqx.common.BaseResponse;
import cc.jiusi.yqx.common.DeleteRequest;
import cc.jiusi.yqx.constant.UserConstant;
import cc.jiusi.yqx.model.entity.Notice;
import cc.jiusi.yqx.service.NoticeService;
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
 * @CreateTime: 2024-05-15 15:38:49
 * @Description: 公告信息(Notice)表控制层
 */
@RestController
@RequestMapping("notice")
@Slf4j
@Api(value = "公告信息", tags = {"公告信息"})
@AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
public class NoticeController {
    /**
     * 服务对象
     */
    @Resource
    private NoticeService noticeService;


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/get")
    @ApiOperation("通过主键查询单条数据")
    @AuthCheck(enableCheck = false)
    public BaseResponse<Notice> getById(Long id) {
        return ResultUtils.success(noticeService.queryById(id));
    }

    /**
     * 通过条件查询所有数据
     *
     * @param notice 查询条件
     * @return List<Notice> 实例对象列表
     */
    @PostMapping("/list")
    @ApiOperation("通过条件查询所有数据")
    @AuthCheck(enableCheck = false)
    public BaseResponse<List<Notice>> getList(@RequestBody Notice notice) {
        return ResultUtils.success(noticeService.queryAll(notice));
    }

    /**
     * 通过条件查询分页数据
     *
     * @param notice 查询条件
     * @return List<Notice> 实例对象列表
     */
    @PostMapping("/page")
    @ApiOperation("通过条件查询分页数据")
    @AuthCheck(enableCheck = false)
    public BaseResponse<PageInfo<Notice>> getPage(@RequestBody Notice notice) {
        return ResultUtils.success(noticeService.queryPage(notice));
    }

    /**
     * 根据条件统计总行数
     *
     * @param notice 查询条件
     * @return 总行数
     */
    @PostMapping("/count")
    @ApiOperation("根据条件统计总行数")
    @AuthCheck(enableCheck = false)
    public BaseResponse<Long> getCount(@RequestBody Notice notice) {
        return ResultUtils.success(noticeService.queryCount(notice));
    }

    /**
     * 新增数据
     *
     * @param notice 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    @ApiOperation("新增数据")
    public BaseResponse<Notice> add(@RequestBody Notice notice) {
        return ResultUtils.success(noticeService.insert(notice));
    }

    /**
     * 批量新增数据
     *
     * @param entities List<Notice> 实例对象列表
     * @return 影响行数
     */
    @PostMapping("/addBatch")
    @ApiOperation("批量新增数据")
    public BaseResponse<Integer> addBatch(@RequestBody List<Notice> entities) {
        return ResultUtils.success(noticeService.insertBatch(entities));
    }

    /**
     * 编辑数据
     *
     * @param notice 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    @ApiOperation("编辑数据")
    public BaseResponse<Notice> update(@RequestBody Notice notice) {
        return ResultUtils.success(noticeService.update(notice));
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
        return ResultUtils.success(noticeService.deleteBatchByIds(deleteRequest));
    }
}

