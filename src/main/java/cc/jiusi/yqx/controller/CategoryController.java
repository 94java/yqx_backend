package cc.jiusi.yqx.controller;

import cc.jiusi.yqx.common.BaseResponse;
import cc.jiusi.yqx.common.DeleteRequest;
import cc.jiusi.yqx.common.ErrorCode;
import cc.jiusi.yqx.common.StatusUpdateRequest;
import cc.jiusi.yqx.exception.BusinessException;
import cc.jiusi.yqx.model.dto.category.CategoryAddRequest;
import cc.jiusi.yqx.model.dto.category.CategoryQueryRequest;
import cc.jiusi.yqx.model.dto.category.CategoryUpdateRequest;
import cc.jiusi.yqx.model.entity.Category;
import cc.jiusi.yqx.service.CategoryService;
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
 * @CreateTime: 2024-05-02 17:00:08
 * @Description: 分类信息(Category)表控制层
 */
@RestController
@RequestMapping("category")
@Slf4j
@Api(value = "分类信息", tags = {"分类信息"})
public class CategoryController {
    /**
     * 服务对象
     */
    @Resource
    private CategoryService categoryService;


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/get")
    @ApiOperation("通过主键查询单条数据")
    public BaseResponse<Category> getById(Long id) {
        return ResultUtils.success(categoryService.queryById(id));
    }

    /**
     * 通过条件查询所有数据
     *
     * @param categoryQueryRequest 查询条件
     * @return List<Category> 实例对象列表
     */
    @PostMapping("/list")
    @ApiOperation("通过条件查询所有数据")
    public BaseResponse<List<Category>> getList(@RequestBody CategoryQueryRequest categoryQueryRequest) {
        return ResultUtils.success(categoryService.queryAll(categoryQueryRequest));
    }

    /**
     * 通过条件查询分页数据
     *
     * @param categoryQueryRequest 查询条件
     * @return List<Category> 实例对象列表
     */
    @PostMapping("/page")
    @ApiOperation("通过条件查询分页数据")
    public BaseResponse<PageInfo<Category>> getPage(@RequestBody CategoryQueryRequest categoryQueryRequest) {
        return ResultUtils.success(categoryService.queryPage(categoryQueryRequest));
    }

    /**
     * 根据条件统计总行数
     *
     * @param categoryQueryRequest 查询条件
     * @return 总行数
     */
    @PostMapping("/count")
    @ApiOperation("根据条件统计总行数")
    public BaseResponse<Long> getCount(@RequestBody CategoryQueryRequest categoryQueryRequest) {
        return ResultUtils.success(categoryService.queryCount(categoryQueryRequest));
    }

    /**
     * 新增数据
     *
     * @param categoryAddRequest 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    @ApiOperation("新增数据")
    public BaseResponse<Category> add(@RequestBody CategoryAddRequest categoryAddRequest) {
        return ResultUtils.success(categoryService.insert(categoryAddRequest));
    }

    /**
     * 批量新增数据
     *
     * @param entities List<CategoryAddRequest> 实例对象列表
     * @return 影响行数
     */
    @PostMapping("/addBatch")
    @ApiOperation("批量新增数据")
    public BaseResponse<Integer> addBatch(@RequestBody List<CategoryAddRequest> entities) {
        return ResultUtils.success(categoryService.insertBatch(entities));
    }

    /**
     * 编辑数据
     *
     * @param categoryUpdateRequest 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    @ApiOperation("编辑数据")
    public BaseResponse<Category> update(@RequestBody CategoryUpdateRequest categoryUpdateRequest) {
        return ResultUtils.success(categoryService.update(categoryUpdateRequest));
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
        categoryService.changeStatus(statusUpdateRequest);
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
        return ResultUtils.success(categoryService.deleteBatchByIds(deleteRequest));
    }
}

