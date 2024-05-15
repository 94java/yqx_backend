package cc.jiusi.yqx.mapper;

import cc.jiusi.yqx.model.entity.UserItemScore;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-15 21:23:56
 * @Description: 用户笔记分值信息(UserItemScore)表数据库访问层
 */
public interface UserItemScoreMapper {

    /**
     * 查询所有数据
     *
     * @param userItemScore 查询条件
     * @return List<UserItemScore> 实例对象列表
     */
    List<UserItemScore> selectAll(UserItemScore userItemScore);

    /**
     * 新增数据
     *
     * @param userItemScore 实例对象
     * @return 影响行数
     */
    int insert(UserItemScore userItemScore);

    /**
     * 修改数据
     *
     * @param userItemScore 实例对象
     * @return 影响行数
     */
    int update(UserItemScore userItemScore);

    /**
     * 通过主键集合批量删除数据
     *
     * @param ids List<Long> 主键id集合
     * @return 影响行数
     */
    int deleteBatchByIds(@Param("ids") List<Long> ids);
}

