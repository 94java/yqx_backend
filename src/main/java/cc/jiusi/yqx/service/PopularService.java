package cc.jiusi.yqx.service;

import cc.jiusi.yqx.common.DeleteRequest;
import cc.jiusi.yqx.model.entity.Popular;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-12 16:05:54
 * @Description: 动态信息(Popular)表服务接口
 */
public interface PopularService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Popular queryById(Long id);

    /**
     * 通过条件查询所有数据
     *
     * @param popular 查询条件
     * @return List<Popular> 实例对象列表
     */
    List<Popular> queryAll(Popular popular);

    /**
     * 通过条件查询分页数据
     *
     * @param popular 查询条件
     * @return PageInfo<Popular> 分页信息对象
     */
    PageInfo<Popular> queryPage(Popular popular);

    /**
     * 根据条件统计总行数
     *
     * @param popular 查询条件
     * @return 总行数
     */
    long queryCount(Popular popular);

    /**
     * 新增数据
     *
     * @param popular 实例对象
     * @return 实例对象
     */
    Popular insert(Popular popular);

    /**
     * 批量新增数据
     *
     * @param entities List<Popular> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(List<Popular> entities);

    /**
     * 修改数据
     *
     * @param popular 实例对象
     * @return 实例对象
     */
    Popular update(Popular popular);

    /**
     * 通过主键集合批量删除数据
     *
     * @param deleteRequest 删除请求对象
     * @return 影响行数
     */
    int deleteBatchByIds(DeleteRequest deleteRequest);

    List<Popular> currentFollow();

}

