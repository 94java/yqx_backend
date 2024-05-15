package cc.jiusi.yqx.service.impl;

import cc.jiusi.yqx.common.DeleteRequest;
import cc.jiusi.yqx.common.StatusUpdateRequest;
import cc.jiusi.yqx.model.dto.category.CategoryAddRequest;
import cc.jiusi.yqx.model.dto.category.CategoryQueryRequest;
import cc.jiusi.yqx.model.dto.category.CategoryUpdateRequest;
import cc.jiusi.yqx.model.entity.Category;
import cc.jiusi.yqx.mapper.CategoryMapper;
import cc.jiusi.yqx.service.CategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;

import cn.hutool.core.bean.BeanUtil;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-02 17:00:08
 * @Description: 分类信息(Category)表服务实现类
 */
@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Category queryById(Long id) {
        return categoryMapper.selectById(id);
    }

    /**
     * 通过条件查询所有数据
     *
     * @param categoryQueryRequest 查询条件
     * @return List<Category> 实例对象列表
     */
    @Override
    public List<Category> queryAll(CategoryQueryRequest categoryQueryRequest) {
        Category category = BeanUtil.copyProperties(categoryQueryRequest, Category.class);
        return categoryMapper.selectAll(category);
    }

    /**
     * 通过条件查询分页数据
     *
     * @param categoryQueryRequest 查询条件
     * @return PageInfo<Category> 分页信息对象
     */
    @Override
    public PageInfo<Category> queryPage(CategoryQueryRequest categoryQueryRequest) {
        // 获取分页参数
        int pageNum = categoryQueryRequest.getPageNum();
        int pageSize = categoryQueryRequest.getPageSize();
        // 开启分页查询
        PageHelper.startPage(pageNum, pageSize);
        Category category = BeanUtil.copyProperties(categoryQueryRequest, Category.class);
        // 查询所有分类信息（若输入了查询参数，则根据查询参数进一步筛选数据）
        List<Category> categorys = categoryMapper.selectAll(category);
        PageInfo<Category> pageInfo = new PageInfo<>(categorys);
        return pageInfo;
    }

    /**
     * 根据条件统计总行数
     *
     * @param categoryQueryRequest 查询条件
     * @return 总行数
     */
    @Override
    public long queryCount(CategoryQueryRequest categoryQueryRequest) {
        Category category = BeanUtil.copyProperties(categoryQueryRequest, Category.class);
        return categoryMapper.count(category);
    }

    /**
     * 新增数据
     *
     * @param categoryAddRequest 实例对象
     * @return 实例对象
     */
    @Override
    public Category insert(CategoryAddRequest categoryAddRequest) {
        Category category = BeanUtil.copyProperties(categoryAddRequest, Category.class);
        categoryMapper.insert(category);
        return category;
    }

    /**
     * 批量新增数据
     *
     * @param entities List<CategoryAddRequest> 实例对象列表
     * @return 影响行数
     */
    @Override
    public int insertBatch(List<CategoryAddRequest> entities) {
        List<Category> categorys = entities.stream()
                .map(item -> BeanUtil.copyProperties(item, Category.class))
                .collect(Collectors.toList());
        return categoryMapper.insertBatch(categorys);
    }

    /**
     * 修改数据
     *
     * @param categoryUpdateRequest 实例对象
     * @return 实例对象
     */
    @Override
    public Category update(CategoryUpdateRequest categoryUpdateRequest) {
        Category category = BeanUtil.copyProperties(categoryUpdateRequest, Category.class);
        categoryMapper.update(category);
        return queryById(category.getId());
    }

    /**
     * 通过主键集合批量删除数据
     *
     * @param deleteRequest 删除请求对象
     * @return 影响行数
     */
    @Override
    public int deleteBatchByIds(DeleteRequest deleteRequest) {
        return categoryMapper.deleteBatchByIds(deleteRequest.getIds());
    }

    /**
     * 通过主键集合批量修改状态
     *
     * @param statusUpdateRequest 状态变更请求对象
     */
    @Override
    public void changeStatus(StatusUpdateRequest statusUpdateRequest) {
        categoryMapper.updateStatus(statusUpdateRequest.getIds(),statusUpdateRequest.getStatus());
    }
}

