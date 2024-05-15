package cc.jiusi.yqx.controller;

import cc.jiusi.yqx.common.BaseResponse;
import cc.jiusi.yqx.common.DeleteRequest;
import cc.jiusi.yqx.model.entity.Role;
import cc.jiusi.yqx.service.RoleService;
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
 * @CreateTime: 2024-05-12 14:00:27
 * @Description: 角色信息(Role)表控制层
 */
@RestController
@RequestMapping("role")
@Slf4j
@Api(value = "角色信息", tags = {"角色信息"})
public class RoleController {
    /**
     * 服务对象
     */
    @Resource
    private RoleService roleService;


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/get")
    @ApiOperation("通过主键查询单条数据")
    public BaseResponse<Role> getById(Long id) {
        return ResultUtils.success(roleService.queryById(id));
    }

    /**
     * 通过条件查询所有数据
     *
     * @param role 查询条件
     * @return List<Role> 实例对象列表
     */
    @PostMapping("/list")
    @ApiOperation("通过条件查询所有数据")
    public BaseResponse<List<Role>> getList(@RequestBody Role role) {
        return ResultUtils.success(roleService.queryAll(role));
    }

    /**
     * 通过条件查询分页数据
     *
     * @param role 查询条件
     * @return List<Role> 实例对象列表
     */
    @PostMapping("/page")
    @ApiOperation("通过条件查询分页数据")
    public BaseResponse<PageInfo<Role>> getPage(@RequestBody Role role) {
        return ResultUtils.success(roleService.queryPage(role));
    }

    /**
     * 根据条件统计总行数
     *
     * @param role 查询条件
     * @return 总行数
     */
    @PostMapping("/count")
    @ApiOperation("根据条件统计总行数")
    public BaseResponse<Long> getCount(@RequestBody Role role) {
        return ResultUtils.success(roleService.queryCount(role));
    }

    /**
     * 新增数据
     *
     * @param role 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    @ApiOperation("新增数据")
    public BaseResponse<Role> add(@RequestBody Role role) {
        return ResultUtils.success(roleService.insert(role));
    }

    /**
     * 批量新增数据
     *
     * @param entities List<Role> 实例对象列表
     * @return 影响行数
     */
    @PostMapping("/addBatch")
    @ApiOperation("批量新增数据")
    public BaseResponse<Integer> addBatch(@RequestBody List<Role> entities) {
        return ResultUtils.success(roleService.insertBatch(entities));
    }

    /**
     * 编辑数据
     *
     * @param role 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    @ApiOperation("编辑数据")
    public BaseResponse<Role> update(@RequestBody Role role) {
        return ResultUtils.success(roleService.update(role));
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
        return ResultUtils.success(roleService.deleteBatchByIds(deleteRequest));
    }
}

