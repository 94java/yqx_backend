package cc.jiusi.yqx.controller;

import cc.jiusi.yqx.common.BaseResponse;
import cc.jiusi.yqx.common.DeleteRequest;
import cc.jiusi.yqx.model.entity.Answer;
import cc.jiusi.yqx.service.AnswerService;
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
 * @CreateTime: 2024-05-05 11:11:20
 * @Description: 题目选项信息(Answer)表控制层
 */
@RestController
@RequestMapping("answer")
@Slf4j
@Api(value = "题目选项信息", tags = {"题目选项信息"})
public class AnswerController {
    /**
     * 服务对象
     */
    @Resource
    private AnswerService answerService;


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/get")
    @ApiOperation("通过主键查询单条数据")
    public BaseResponse<Answer> getById(Long id) {
        return ResultUtils.success(answerService.queryById(id));
    }

    /**
     * 通过条件查询所有数据
     *
     * @param answer 查询条件
     * @return List<Answer> 实例对象列表
     */
    @PostMapping("/list")
    @ApiOperation("通过条件查询所有数据")
    public BaseResponse<List<Answer>> getList(@RequestBody Answer answer) {
        return ResultUtils.success(answerService.queryAll(answer));
    }

    /**
     * 通过条件查询分页数据
     *
     * @param answer 查询条件
     * @return List<Answer> 实例对象列表
     */
    @PostMapping("/page")
    @ApiOperation("通过条件查询分页数据")
    public BaseResponse<PageInfo<Answer>> getPage(@RequestBody Answer answer) {
        return ResultUtils.success(answerService.queryPage(answer));
    }

    /**
     * 根据条件统计总行数
     *
     * @param answer 查询条件
     * @return 总行数
     */
    @PostMapping("/count")
    @ApiOperation("根据条件统计总行数")
    public BaseResponse<Long> getCount(@RequestBody Answer answer) {
        return ResultUtils.success(answerService.queryCount(answer));
    }

    /**
     * 新增数据
     *
     * @param answer 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    @ApiOperation("新增数据")
    public BaseResponse<Answer> add(@RequestBody Answer answer) {
        return ResultUtils.success(answerService.insert(answer));
    }

    /**
     * 批量新增数据
     *
     * @param entities List<AnswerAddRequest> 实例对象列表
     * @return 影响行数
     */
    @PostMapping("/addBatch")
    @ApiOperation("批量新增数据")
    public BaseResponse<Integer> addBatch(@RequestBody List<Answer> entities) {
        return ResultUtils.success(answerService.insertBatch(entities));
    }

    /**
     * 编辑数据
     *
     * @param answer 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    @ApiOperation("编辑数据")
    public BaseResponse<Answer> update(@RequestBody Answer answer) {
        return ResultUtils.success(answerService.update(answer));
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
        return ResultUtils.success(answerService.deleteBatchByIds(deleteRequest));
    }
}

