package cc.jiusi.yqx.service.impl;

import cc.jiusi.yqx.common.DeleteRequest;
import cc.jiusi.yqx.model.entity.Role;
import cc.jiusi.yqx.mapper.RoleMapper;
import cc.jiusi.yqx.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import javax.annotation.Resource;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-12 14:00:28
 * @Description: 角色信息(Role)表服务实现类
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleMapper roleMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Role queryById(Long id) {
        return roleMapper.selectById(id);
    }

    /**
     * 通过条件查询所有数据
     *
     * @param role 查询条件
     * @return List<Role> 实例对象列表
     */
    @Override
    public List<Role> queryAll(Role role) {
        return roleMapper.selectAll(role);
    }

    /**
     * 通过条件查询分页数据
     *
     * @param role 查询条件
     * @return PageInfo<Role> 分页信息对象
     */
    @Override
    public PageInfo<Role> queryPage(Role role) {
        int pageNum = role.getPageNum();
        int pageSize = role.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<Role> roles = roleMapper.selectAll(role);
        PageInfo<Role> pageInfo = new PageInfo<>(roles);
        return pageInfo;
    }

    /**
     * 根据条件统计总行数
     *
     * @param role 查询条件
     * @return 总行数
     */
    @Override
    public long queryCount(Role role) {
        return roleMapper.count(role);
    }

    /**
     * 新增数据
     *
     * @param role 实例对象
     * @return 实例对象
     */
    @Override
    public Role insert(Role role) {
        roleMapper.insert(role);
        return role;
    }

    /**
     * 批量新增数据
     *
     * @param entities List<Role> 实例对象列表
     * @return 影响行数
     */
    @Override
    public int insertBatch(List<Role> entities) {
        return roleMapper.insertBatch(entities);
    }

    /**
     * 修改数据
     *
     * @param role 实例对象
     * @return 实例对象
     */
    @Override
    public Role update(Role role) {
        roleMapper.update(role);
        return queryById(role.getId());
    }

    /**
     * 通过主键集合批量删除数据
     *
     * @param deleteRequest 删除请求对象
     * @return 影响行数
     */
    @Override
    public int deleteBatchByIds(DeleteRequest deleteRequest) {
        return roleMapper.deleteBatchByIds(deleteRequest.getIds());
    }
}

