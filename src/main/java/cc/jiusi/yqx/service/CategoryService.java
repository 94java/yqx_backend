package cc.jiusi.yqx.service;

import cc.jiusi.yqx.common.DeleteRequest;
import cc.jiusi.yqx.common.StatusUpdateRequest;
import cc.jiusi.yqx.model.dto.category.CategoryAddRequest;
import cc.jiusi.yqx.model.dto.category.CategoryQueryRequest;
import cc.jiusi.yqx.model.dto.category.CategoryUpdateRequest;
import cc.jiusi.yqx.model.entity.Category;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-02 17:00:08
 * @Description: 分类信息(Category)表服务接口
 */
public interface CategoryService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Category queryById(Long id);

    /**
     * 通过条件查询所有数据
     *
     * @param categoryQueryRequest 查询条件
     * @return List<Category> 实例对象列表
     */
    List<Category> queryAll(CategoryQueryRequest categoryQueryRequest);

    /**
     * 通过条件查询分页数据
     *
     * @param categoryQueryRequest 查询条件
     * @return PageInfo<Category> 分页信息对象
     */
    PageInfo<Category> queryPage(CategoryQueryRequest categoryQueryRequest);

    /**
     * 根据条件统计总行数
     *
     * @param categoryQueryRequest 查询条件
     * @return 总行数
     */
    long queryCount(CategoryQueryRequest categoryQueryRequest);

    /**
     * 新增数据
     *
     * @param categoryAddRequest 实例对象
     * @return 实例对象
     */
    Category insert(CategoryAddRequest categoryAddRequest);

    /**
     * 批量新增数据
     *
     * @param entities List<CategoryAddRequest> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(List<CategoryAddRequest> entities);

    /**
     * 修改数据
     *
     * @param categoryUpdateRequest 实例对象
     * @return 实例对象
     */
    Category update(CategoryUpdateRequest categoryUpdateRequest);

    /**
     * 通过主键集合批量删除数据
     *
     * @param deleteRequest 删除请求对象
     * @return 影响行数
     */
    int deleteBatchByIds(DeleteRequest deleteRequest);

    /**
     * 通过主键集合批量修改状态
     *
     * @param statusUpdateRequest 状态变更请求对象
     */
    void changeStatus(StatusUpdateRequest statusUpdateRequest);
}

