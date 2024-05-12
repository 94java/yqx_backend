package cc.jiusi.springbootinit.service.impl;

import cc.jiusi.springbootinit.common.DeleteRequest;
import cc.jiusi.springbootinit.model.entity.Popular;
import cc.jiusi.springbootinit.mapper.PopularMapper;
import cc.jiusi.springbootinit.service.PopularService;
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
 * @CreateTime: 2024-05-12 16:05:54
 * @Description: 动态信息(Popular)表服务实现类
 */
@Service("popularService")
public class PopularServiceImpl implements PopularService {
    @Resource
    private PopularMapper popularMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Popular queryById(Long id) {
        return popularMapper.selectById(id);
    }

    /**
     * 通过条件查询所有数据
     *
     * @param popular 查询条件
     * @return List<Popular> 实例对象列表
     */
    @Override
    public List<Popular> queryAll(Popular popular) {
        return popularMapper.selectAll(popular);
    }

    /**
     * 通过条件查询分页数据
     *
     * @param popular 查询条件
     * @return PageInfo<Popular> 分页信息对象
     */
    @Override
    public PageInfo<Popular> queryPage(Popular popular) {
        int pageNum = popular.getPageNum();
        int pageSize = popular.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<Popular> populars = popularMapper.selectAll(popular);
        PageInfo<Popular> pageInfo = new PageInfo<>(populars);
        return pageInfo;
    }

    /**
     * 根据条件统计总行数
     *
     * @param popular 查询条件
     * @return 总行数
     */
    @Override
    public long queryCount(Popular popular) {
        return popularMapper.count(popular);
    }

    /**
     * 新增数据
     *
     * @param popular 实例对象
     * @return 实例对象
     */
    @Override
    public Popular insert(Popular popular) {
        popularMapper.insert(popular);
        return popular;
    }

    /**
     * 批量新增数据
     *
     * @param entities List<Popular> 实例对象列表
     * @return 影响行数
     */
    @Override
    public int insertBatch(List<Popular> entities) {
        return popularMapper.insertBatch(entities);
    }

    /**
     * 修改数据
     *
     * @param popular 实例对象
     * @return 实例对象
     */
    @Override
    public Popular update(Popular popular) {
        popularMapper.update(popular);
        return queryById(popular.getId());
    }

    /**
     * 通过主键集合批量删除数据
     *
     * @param deleteRequest 删除请求对象
     * @return 影响行数
     */
    @Override
    public int deleteBatchByIds(DeleteRequest deleteRequest) {
        return popularMapper.deleteBatchByIds(deleteRequest.getIds());
    }
}

