package cc.jiusi.yqx.controller;

import cc.jiusi.yqx.annotation.AuthCheck;
import cc.jiusi.yqx.common.BaseResponse;
import cc.jiusi.yqx.common.DeleteRequest;
import cc.jiusi.yqx.common.ErrorCode;
import cc.jiusi.yqx.common.StatusUpdateRequest;
import cc.jiusi.yqx.constant.UserConstant;
import cc.jiusi.yqx.exception.BusinessException;
import cc.jiusi.yqx.model.dto.questionBank.QuestionBankAddRequest;
import cc.jiusi.yqx.model.dto.questionBank.QuestionBankQueryRequest;
import cc.jiusi.yqx.model.dto.questionBank.QuestionBankUpdateRequest;
import cc.jiusi.yqx.model.entity.QuestionBank;
import cc.jiusi.yqx.service.QuestionBankService;
import cc.jiusi.yqx.utils.ResultUtils;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
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
 * @CreateTime: 2024-05-04 17:16:02
 * @Description: 题库信息(QuestionBank)表控制层
 */
@RestController
@RequestMapping("questionBank")
@Slf4j
@Api(value = "题库信息", tags = {"题库信息"})
@AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
public class QuestionBankController {
    /**
     * 服务对象
     */
    @Resource
    private QuestionBankService questionBankService;


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/get")
    @ApiOperation("通过主键查询单条数据")
    @AuthCheck(mustRole = UserConstant.USER_ROLE)
    public BaseResponse<QuestionBank> getById(Long id) {
        return ResultUtils.success(questionBankService.queryById(id));
    }

    /**
     * 通过条件查询所有数据
     *
     * @param questionBankQueryRequest 查询条件
     * @return List<QuestionBank> 实例对象列表
     */
    @PostMapping("/list")
    @ApiOperation("通过条件查询所有数据")
    @AuthCheck(enableCheck = false)
    public BaseResponse<List<QuestionBank>> getList(@RequestBody QuestionBankQueryRequest questionBankQueryRequest) {
        return ResultUtils.success(questionBankService.queryAll(questionBankQueryRequest));
    }

    /**
     * 通过条件查询分页数据
     *
     * @param questionBankQueryRequest 查询条件
     * @return List<QuestionBank> 实例对象列表
     */
    @PostMapping("/page")
    @ApiOperation("通过条件查询分页数据")
    @AuthCheck(enableCheck = false)
    public BaseResponse<PageInfo<QuestionBank>> getPage(@RequestBody QuestionBankQueryRequest questionBankQueryRequest) {
        return ResultUtils.success(questionBankService.queryPage(questionBankQueryRequest));
    }

    /**
     * 根据条件统计总行数
     *
     * @param questionBankQueryRequest 查询条件
     * @return 总行数
     */
    @PostMapping("/count")
    @ApiOperation("根据条件统计总行数")
    @AuthCheck(enableCheck = false)
    public BaseResponse<Long> getCount(@RequestBody QuestionBankQueryRequest questionBankQueryRequest) {
        return ResultUtils.success(questionBankService.queryCount(questionBankQueryRequest));
    }

    /**
     * 新增数据
     *
     * @param questionBankAddRequest 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    @ApiOperation("新增数据")
    public BaseResponse<QuestionBank> add(@RequestBody QuestionBankAddRequest questionBankAddRequest) {
        // 参数校验
        if(questionBankAddRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String name = questionBankAddRequest.getName();
        String coverImg = questionBankAddRequest.getCoverImg();
        Long categoryId = questionBankAddRequest.getCategoryId();
        if(!StrUtil.isAllNotBlank(name,coverImg) || categoryId == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(questionBankService.insert(questionBankAddRequest));
    }

    /**
     * 批量新增数据
     *
     * @param entities List<QuestionBankAddRequest> 实例对象列表
     * @return 影响行数
     */
    @PostMapping("/addBatch")
    @ApiOperation("批量新增数据")
    public BaseResponse<Integer> addBatch(@RequestBody List<QuestionBankAddRequest> entities) {
        // 参数校验
        if(CollUtil.isEmpty(entities)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        for (QuestionBankAddRequest entity : entities) {
            String name = entity.getName();
            String coverImg = entity.getCoverImg();
            Long categoryId = entity.getCategoryId();
            if(!StrUtil.isAllNotBlank(name,coverImg) || categoryId == null){
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        return ResultUtils.success(questionBankService.insertBatch(entities));
    }

    /**
     * 编辑数据
     *
     * @param questionBankUpdateRequest 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    @ApiOperation("编辑数据")
    public BaseResponse<QuestionBank> update(@RequestBody QuestionBankUpdateRequest questionBankUpdateRequest) {
        if(questionBankUpdateRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if(questionBankUpdateRequest.getId() == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(questionBankService.update(questionBankUpdateRequest));
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
        return ResultUtils.success(questionBankService.deleteBatchByIds(deleteRequest));
    }

    /**
     * 批量变更状态
     *
     * @param statusUpdateRequest 实体
     * @return 编辑结果
     */
    @PostMapping("/changeStatus")
    @ApiOperation("批量变更状态")
    public BaseResponse<Void> changeStatus(@RequestBody StatusUpdateRequest statusUpdateRequest) {
        if(statusUpdateRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String status = statusUpdateRequest.getStatus();
        List<Long> ids = statusUpdateRequest.getIds();
        if(CollUtil.isEmpty(ids) || StrUtil.isBlank(status)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        questionBankService.changeStatus(statusUpdateRequest);
        return ResultUtils.success(null);
    }
}

