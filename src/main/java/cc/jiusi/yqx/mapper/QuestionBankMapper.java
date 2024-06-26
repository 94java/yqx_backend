package cc.jiusi.yqx.mapper;

import cc.jiusi.yqx.model.entity.QuestionBank;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-04 17:16:02
 * @Description: 题库信息(QuestionBank)表数据库访问层
 */
public interface QuestionBankMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    QuestionBank selectById(Long id);

    /**
     * 查询所有数据
     *
     * @param questionBank 查询条件
     * @return List<QuestionBank> 实例对象列表
     */
    List<QuestionBank> selectAll(QuestionBank questionBank);

    /**
     * 统计总行数
     *
     * @param questionBank 查询条件
     * @return 总行数
     */
    long count(QuestionBank questionBank);

    /**
     * 新增数据
     *
     * @param questionBank 实例对象
     * @return 影响行数
     */
    int insert(QuestionBank questionBank);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<QuestionBank> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<QuestionBank> entities);

    /**
     * 修改数据
     *
     * @param questionBank 实例对象
     * @return 影响行数
     */
    int update(QuestionBank questionBank);

    /**
     * 通过主键集合批量删除数据
     *
     * @param ids List<Long> 主键id集合
     * @return 影响行数
     */
    int deleteBatchByIds(@Param("ids") List<Long> ids);

    void updateStatus(@Param("ids") List<Long> ids, @Param("status") String status);
}

