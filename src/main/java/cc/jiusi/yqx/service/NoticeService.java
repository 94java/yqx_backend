package cc.jiusi.yqx.service;

import cc.jiusi.yqx.common.DeleteRequest;
import cc.jiusi.yqx.model.entity.Notice;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-15 15:38:53
 * @Description: 公告信息(Notice)表服务接口
 */
public interface NoticeService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Notice queryById(Long id);

    /**
     * 通过条件查询所有数据
     *
     * @param notice 查询条件
     * @return List<Notice> 实例对象列表
     */
    List<Notice> queryAll(Notice notice);

    /**
     * 通过条件查询分页数据
     *
     * @param notice 查询条件
     * @return PageInfo<Notice> 分页信息对象
     */
    PageInfo<Notice> queryPage(Notice notice);

    /**
     * 根据条件统计总行数
     *
     * @param notice 查询条件
     * @return 总行数
     */
    long queryCount(Notice notice);

    /**
     * 新增数据
     *
     * @param notice 实例对象
     * @return 实例对象
     */
    Notice insert(Notice notice);

    /**
     * 批量新增数据
     *
     * @param entities List<Notice> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(List<Notice> entities);

    /**
     * 修改数据
     *
     * @param notice 实例对象
     * @return 实例对象
     */
    Notice update(Notice notice);

    /**
     * 通过主键集合批量删除数据
     *
     * @param deleteRequest 删除请求对象
     * @return 影响行数
     */
    int deleteBatchByIds(DeleteRequest deleteRequest);
}

