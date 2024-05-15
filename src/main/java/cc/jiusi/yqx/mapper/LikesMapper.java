package cc.jiusi.yqx.mapper;

import cc.jiusi.yqx.model.entity.Likes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-10 21:27:28
 * @Description: 点赞信息(Likes)表数据库访问层
 */
public interface LikesMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Likes selectById(Long id);

    /**
     * 查询所有数据
     *
     * @param likes 查询条件
     * @return List<Likes> 实例对象列表
     */
    List<Likes> selectAll(Likes likes);

    /**
     * 统计总行数
     *
     * @param likes 查询条件
     * @return 总行数
     */
    long count(Likes likes);

    /**
     * 新增数据
     *
     * @param likes 实例对象
     * @return 影响行数
     */
    int insert(Likes likes);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Likes> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Likes> entities);

    /**
     * 修改数据
     *
     * @param likes 实例对象
     * @return 影响行数
     */
    int update(Likes likes);

    /**
     * 通过主键集合批量删除数据
     *
     * @param ids List<Long> 主键id集合
     * @return 影响行数
     */
    int deleteBatchByIds(@Param("ids") List<Long> ids);
}

