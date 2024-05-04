package cc.jiusi.springbootinit.controller;

import cc.jiusi.springbootinit.common.BaseResponse;
import cc.jiusi.springbootinit.common.DeleteRequest;
import cc.jiusi.springbootinit.common.ErrorCode;
import cc.jiusi.springbootinit.common.StatusUpdateRequest;
import cc.jiusi.springbootinit.exception.BusinessException;
import cc.jiusi.springbootinit.model.dto.note.NoteAddRequest;
import cc.jiusi.springbootinit.model.dto.note.NoteQueryRequest;
import cc.jiusi.springbootinit.model.dto.note.NoteUpdateRequest;
import cc.jiusi.springbootinit.model.entity.Note;
import cc.jiusi.springbootinit.service.NoteService;
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
 * @CreateTime: 2024-05-02 21:27:52
 * @Description: 笔记信息(Note)表控制层
 */
@RestController
@RequestMapping("note")
@Slf4j
@Api(value = "笔记信息", tags = {"笔记信息"})
public class NoteController {
    /**
     * 服务对象
     */
    @Resource
    private NoteService noteService;


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/get")
    @ApiOperation("通过主键查询单条数据")
    public BaseResponse<Note> getById(Long id) {
        return ResultUtils.success(noteService.queryById(id));
    }

    /**
     * 通过条件查询所有数据
     *
     * @param noteQueryRequest 查询条件
     * @return List<Note> 实例对象列表
     */
    @PostMapping("/list")
    @ApiOperation("通过条件查询所有数据")
    public BaseResponse<List<Note>> getList(@RequestBody NoteQueryRequest noteQueryRequest) {
        return ResultUtils.success(noteService.queryAll(noteQueryRequest));
    }

    /**
     * 通过条件查询分页数据
     *
     * @param noteQueryRequest 查询条件
     * @return List<Note> 实例对象列表
     */
    @PostMapping("/page")
    @ApiOperation("通过条件查询分页数据")
    public BaseResponse<PageInfo<Note>> getPage(@RequestBody NoteQueryRequest noteQueryRequest) {
        return ResultUtils.success(noteService.queryPage(noteQueryRequest));
    }

    /**
     * 根据条件统计总行数
     *
     * @param noteQueryRequest 查询条件
     * @return 总行数
     */
    @PostMapping("/count")
    @ApiOperation("根据条件统计总行数")
    public BaseResponse<Long> getCount(@RequestBody NoteQueryRequest noteQueryRequest) {
        return ResultUtils.success(noteService.queryCount(noteQueryRequest));
    }

    /**
     * 新增数据
     *
     * @param noteAddRequest 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    @ApiOperation("新增数据")
    public BaseResponse<Note> add(@RequestBody NoteAddRequest noteAddRequest) {
        // 校验
        if(noteAddRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String title = noteAddRequest.getTitle();
        Long categoryId = noteAddRequest.getCategoryId();
        if(StrUtil.isBlank(title) || categoryId == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 处理
        return ResultUtils.success(noteService.insert(noteAddRequest));
    }

    /**
     * 批量新增数据
     *
     * @param entities List<NoteAddRequest> 实例对象列表
     * @return 影响行数
     */
    @PostMapping("/addBatch")
    @ApiOperation("批量新增数据")
    public BaseResponse<Integer> addBatch(@RequestBody List<NoteAddRequest> entities) {
        // 校验
        if(CollUtil.isEmpty(entities)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        for (NoteAddRequest entity : entities) {
            String title = entity.getTitle();
            Long categoryId = entity.getCategoryId();
            if(StrUtil.isBlank(title) || categoryId == null){
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        return ResultUtils.success(noteService.insertBatch(entities));
    }

    /**
     * 编辑数据
     *
     * @param noteUpdateRequest 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    @ApiOperation("编辑数据")
    public BaseResponse<Note> update(@RequestBody NoteUpdateRequest noteUpdateRequest) {
        // 校验
        if(noteUpdateRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long id = noteUpdateRequest.getId();
        if(id == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(noteService.update(noteUpdateRequest));
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
        noteService.changeStatus(statusUpdateRequest);
        return ResultUtils.success(null);
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
        return ResultUtils.success(noteService.deleteBatchByIds(deleteRequest));
    }
}

