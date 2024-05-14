package cc.jiusi.springbootinit.service;

import cc.jiusi.springbootinit.common.DeleteRequest;
import cc.jiusi.springbootinit.model.entity.Wrong;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-14 18:41:57
 * @Description: 用户错题信息(Wrong)表服务接口
 */
public interface WrongService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Wrong queryById(Long id);

    /**
     * 通过条件查询所有数据
     *
     * @param wrong 查询条件
     * @return List<Wrong> 实例对象列表
     */
    List<Wrong> queryAll(Wrong wrong);

    /**
     * 通过条件查询分页数据
     *
     * @param wrong 查询条件
     * @return PageInfo<Wrong> 分页信息对象
     */
    PageInfo<Wrong> queryPage(Wrong wrong);

    /**
     * 根据条件统计总行数
     *
     * @param wrong 查询条件
     * @return 总行数
     */
    long queryCount(Wrong wrong);

    /**
     * 新增数据
     *
     * @param wrong 实例对象
     * @return 实例对象
     */
    Wrong insert(Wrong wrong);

    /**
     * 批量新增数据
     *
     * @param entities List<Wrong> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(List<Wrong> entities);

    /**
     * 修改数据
     *
     * @param wrong 实例对象
     * @return 实例对象
     */
    Wrong update(Wrong wrong);

    /**
     * 通过主键集合批量删除数据
     *
     * @param deleteRequest 删除请求对象
     * @return 影响行数
     */
    int deleteBatchByIds(DeleteRequest deleteRequest);
}

