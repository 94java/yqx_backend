package cc.jiusi.yqx.mapper;

import cc.jiusi.yqx.model.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-12 14:00:27
 * @Description: 角色信息(Role)表数据库访问层
 */
public interface RoleMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Role selectById(Long id);

    /**
     * 查询所有数据
     *
     * @param role 查询条件
     * @return List<Role> 实例对象列表
     */
    List<Role> selectAll(Role role);

    /**
     * 统计总行数
     *
     * @param role 查询条件
     * @return 总行数
     */
    long count(Role role);

    /**
     * 新增数据
     *
     * @param role 实例对象
     * @return 影响行数
     */
    int insert(Role role);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Role> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Role> entities);

    /**
     * 修改数据
     *
     * @param role 实例对象
     * @return 影响行数
     */
    int update(Role role);

    /**
     * 通过主键集合批量删除数据
     *
     * @param ids List<Long> 主键id集合
     * @return 影响行数
     */
    int deleteBatchByIds(@Param("ids") List<Long> ids);
}

