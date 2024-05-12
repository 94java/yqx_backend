package cc.jiusi.springbootinit.mapper;

import cc.jiusi.springbootinit.model.entity.Popular;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-12 16:05:54
 * @Description: 动态信息(Popular)表数据库访问层
 */
public interface PopularMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Popular selectById(Long id);

    /**
     * 查询所有数据
     *
     * @param popular 查询条件
     * @return List<Popular> 实例对象列表
     */
    List<Popular> selectAll(Popular popular);

    /**
     * 统计总行数
     *
     * @param popular 查询条件
     * @return 总行数
     */
    long count(Popular popular);

    /**
     * 新增数据
     *
     * @param popular 实例对象
     * @return 影响行数
     */
    int insert(Popular popular);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Popular> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Popular> entities);

    /**
     * 修改数据
     *
     * @param popular 实例对象
     * @return 影响行数
     */
    int update(Popular popular);

    /**
     * 通过主键集合批量删除数据
     *
     * @param ids List<Long> 主键id集合
     * @return 影响行数
     */
    int deleteBatchByIds(@Param("ids") List<Long> ids);
}

