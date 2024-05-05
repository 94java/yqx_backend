package cc.jiusi.springbootinit.mapper;

import cc.jiusi.springbootinit.model.entity.Answer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-05 11:11:20
 * @Description: 题目选项信息(Answer)表数据库访问层
 */
public interface AnswerMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Answer selectById(Long id);

    /**
     * 查询所有数据
     *
     * @param answer 查询条件
     * @return List<Answer> 实例对象列表
     */
    List<Answer> selectAll(Answer answer);

    /**
     * 统计总行数
     *
     * @param answer 查询条件
     * @return 总行数
     */
    long count(Answer answer);

    /**
     * 新增数据
     *
     * @param answer 实例对象
     * @return 影响行数
     */
    int insert(Answer answer);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Answer> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Answer> entities);

    /**
     * 修改数据
     *
     * @param answer 实例对象
     * @return 影响行数
     */
    int update(Answer answer);

    /**
     * 通过主键集合批量删除数据
     *
     * @param ids List<Long> 主键id集合
     * @return 影响行数
     */
    int deleteBatchByIds(@Param("ids") List<Long> ids);
}

