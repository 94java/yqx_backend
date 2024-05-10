package cc.jiusi.springbootinit.mapper;

import cc.jiusi.springbootinit.model.entity.Follow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-10 20:11:03
 * @Description: 用户关注信息(Follow)表数据库访问层
 */
public interface FollowMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Follow selectById(Long id);

    /**
     * 查询所有数据
     *
     * @param follow 查询条件
     * @return List<Follow> 实例对象列表
     */
    List<Follow> selectAll(Follow follow);

    /**
     * 统计总行数
     *
     * @param follow 查询条件
     * @return 总行数
     */
    long count(Follow follow);

    /**
     * 新增数据
     *
     * @param follow 实例对象
     * @return 影响行数
     */
    int insert(Follow follow);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Follow> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Follow> entities);

    /**
     * 修改数据
     *
     * @param follow 实例对象
     * @return 影响行数
     */
    int update(Follow follow);

    /**
     * 通过主键集合批量删除数据
     *
     * @param ids List<Long> 主键id集合
     * @return 影响行数
     */
    int deleteBatchByIds(@Param("ids") List<Long> ids);
}

