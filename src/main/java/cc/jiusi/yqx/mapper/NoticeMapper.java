package cc.jiusi.yqx.mapper;

import cc.jiusi.yqx.model.entity.Notice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-15 15:38:49
 * @Description: 公告信息(Notice)表数据库访问层
 */
public interface NoticeMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Notice selectById(Long id);

    /**
     * 查询所有数据
     *
     * @param notice 查询条件
     * @return List<Notice> 实例对象列表
     */
    List<Notice> selectAll(Notice notice);

    /**
     * 统计总行数
     *
     * @param notice 查询条件
     * @return 总行数
     */
    long count(Notice notice);

    /**
     * 新增数据
     *
     * @param notice 实例对象
     * @return 影响行数
     */
    int insert(Notice notice);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Notice> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Notice> entities);

    /**
     * 修改数据
     *
     * @param notice 实例对象
     * @return 影响行数
     */
    int update(Notice notice);

    /**
     * 通过主键集合批量删除数据
     *
     * @param ids List<Long> 主键id集合
     * @return 影响行数
     */
    int deleteBatchByIds(@Param("ids") List<Long> ids);
}

