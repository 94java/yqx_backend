package cc.jiusi.springbootinit.mapper;

import cc.jiusi.springbootinit.model.entity.Subject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-05 08:52:14
 * @Description: 题目信息(Subject)表数据库访问层
 */
public interface SubjectMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Subject selectById(Long id);

    /**
     * 查询所有数据
     *
     * @param subject 查询条件
     * @return List<Subject> 实例对象列表
     */
    List<Subject> selectAll(Subject subject);

    /**
     * 统计总行数
     *
     * @param subject 查询条件
     * @return 总行数
     */
    long count(Subject subject);

    /**
     * 新增数据
     *
     * @param subject 实例对象
     * @return 影响行数
     */
    int insert(Subject subject);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Subject> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Subject> entities);

    /**
     * 修改数据
     *
     * @param subject 实例对象
     * @return 影响行数
     */
    int update(Subject subject);

    /**
     * 通过主键集合批量删除数据
     *
     * @param ids List<Long> 主键id集合
     * @return 影响行数
     */
    int deleteBatchByIds(@Param("ids") List<Long> ids);

    void updateStatus(@Param("ids") List<Long> ids, @Param("status") String status);
}

