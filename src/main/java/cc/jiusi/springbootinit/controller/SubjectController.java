package cc.jiusi.springbootinit.controller;

import cc.jiusi.springbootinit.common.BaseResponse;
import cc.jiusi.springbootinit.common.DeleteRequest;
import cc.jiusi.springbootinit.common.ErrorCode;
import cc.jiusi.springbootinit.common.StatusUpdateRequest;
import cc.jiusi.springbootinit.exception.BusinessException;
import cc.jiusi.springbootinit.model.dto.subject.SubjectAddRequest;
import cc.jiusi.springbootinit.model.dto.subject.SubjectQueryRequest;
import cc.jiusi.springbootinit.model.dto.subject.SubjectUpdateRequest;
import cc.jiusi.springbootinit.model.entity.Subject;
import cc.jiusi.springbootinit.service.SubjectService;
import cc.jiusi.springbootinit.utils.ResultUtils;
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
 * @CreateTime: 2024-05-05 08:52:13
 * @Description: 题目信息(Subject)表控制层
 */
@RestController
@RequestMapping("subject")
@Slf4j
@Api(value = "题目信息", tags = {"题目信息"})
public class SubjectController {
    /**
     * 服务对象
     */
    @Resource
    private SubjectService subjectService;


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/get")
    @ApiOperation("通过主键查询单条数据")
    public BaseResponse<Subject> getById(Long id) {
        return ResultUtils.success(subjectService.queryById(id));
    }

    /**
     * 通过条件查询所有数据
     *
     * @param subjectQueryRequest 查询条件
     * @return List<Subject> 实例对象列表
     */
    @PostMapping("/list")
    @ApiOperation("通过条件查询所有数据")
    public BaseResponse<List<Subject>> getList(@RequestBody SubjectQueryRequest subjectQueryRequest) {
        return ResultUtils.success(subjectService.queryAll(subjectQueryRequest));
    }

    /**
     * 通过条件查询分页数据
     *
     * @param subjectQueryRequest 查询条件
     * @return List<Subject> 实例对象列表
     */
    @PostMapping("/page")
    @ApiOperation("通过条件查询分页数据")
    public BaseResponse<PageInfo<Subject>> getPage(@RequestBody SubjectQueryRequest subjectQueryRequest) {
        return ResultUtils.success(subjectService.queryPage(subjectQueryRequest));
    }

    /**
     * 根据条件统计总行数
     *
     * @param subjectQueryRequest 查询条件
     * @return 总行数
     */
    @PostMapping("/count")
    @ApiOperation("根据条件统计总行数")
    public BaseResponse<Long> getCount(@RequestBody SubjectQueryRequest subjectQueryRequest) {
        return ResultUtils.success(subjectService.queryCount(subjectQueryRequest));
    }

    /**
     * 新增数据
     *
     * @param subjectAddRequest 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    @ApiOperation("新增数据")
    public BaseResponse<Subject> add(@RequestBody SubjectAddRequest subjectAddRequest) {
        if (subjectAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String content = subjectAddRequest.getContent();
        Long bankId = subjectAddRequest.getBankId();
        String type = subjectAddRequest.getType();
        if (!StrUtil.isAllNotBlank(content, type) || bankId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(subjectService.insert(subjectAddRequest));
    }

    /**
     * 批量新增数据
     *
     * @param entities List<SubjectAddRequest> 实例对象列表
     * @return 影响行数
     */
    @PostMapping("/addBatch")
    @ApiOperation("批量新增数据")
    public BaseResponse<Integer> addBatch(@RequestBody List<SubjectAddRequest> entities) {
        if(CollUtil.isEmpty(entities)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 校验参数
        entities.forEach(entity -> {
            String content = entity.getContent();
            Long bankId = entity.getBankId();
            String type = entity.getType();
            if (!StrUtil.isAllNotBlank(content, type) || bankId == null) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        });
        return ResultUtils.success(subjectService.insertBatch(entities));
    }

    /**
     * 编辑数据
     *
     * @param subjectUpdateRequest 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    @ApiOperation("编辑数据")
    public BaseResponse<Subject> update(@RequestBody SubjectUpdateRequest subjectUpdateRequest) {
        // 校验参数
        if(subjectUpdateRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long id = subjectUpdateRequest.getId();
        if(id == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(subjectService.update(subjectUpdateRequest));
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
        return ResultUtils.success(subjectService.deleteBatchByIds(deleteRequest));
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
        subjectService.changeStatus(statusUpdateRequest);
        return ResultUtils.success(null);
    }
}

