package cc.jiusi.springbootinit.mapper;

import cc.jiusi.springbootinit.model.entity.Wrong;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-14 18:41:57
 * @Description: 用户错题信息(Wrong)表数据库访问层
 */
public interface WrongMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Wrong selectById(Long id);

    /**
     * 查询所有数据
     *
     * @param wrong 查询条件
     * @return List<Wrong> 实例对象列表
     */
    List<Wrong> selectAll(Wrong wrong);

    /**
     * 统计总行数
     *
     * @param wrong 查询条件
     * @return 总行数
     */
    long count(Wrong wrong);

    /**
     * 新增数据
     *
     * @param wrong 实例对象
     * @return 影响行数
     */
    int insert(Wrong wrong);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Wrong> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Wrong> entities);

    /**
     * 修改数据
     *
     * @param wrong 实例对象
     * @return 影响行数
     */
    int update(Wrong wrong);

    /**
     * 通过主键集合批量删除数据
     *
     * @param ids List<Long> 主键id集合
     * @return 影响行数
     */
    int deleteBatchByIds(@Param("ids") List<Long> ids);
}

