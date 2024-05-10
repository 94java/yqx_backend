package cc.jiusi.springbootinit.service;

import cc.jiusi.springbootinit.common.DeleteRequest;
import cc.jiusi.springbootinit.model.entity.Comment;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-10 13:55:20
 * @Description: 评论信息(Comment)表服务接口
 */
public interface CommentService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Comment queryById(Long id);

    /**
     * 通过条件查询所有数据
     *
     * @param comment 查询条件
     * @return List<Comment> 实例对象列表
     */
    List<Comment> queryAll(Comment comment);

    /**
     * 通过条件查询分页数据
     *
     * @param comment 查询条件
     * @return PageInfo<Comment> 分页信息对象
     */
    PageInfo<Comment> queryPage(Comment comment);

    /**
     * 根据条件统计总行数
     *
     * @param comment 查询条件
     * @return 总行数
     */
    long queryCount(Comment comment);

    /**
     * 新增数据
     *
     * @param comment 实例对象
     * @return 实例对象
     */
    Comment insert(Comment comment);

    /**
     * 批量新增数据
     *
     * @param entities List<Comment> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(List<Comment> entities);

    /**
     * 修改数据
     *
     * @param comment 实例对象
     * @return 实例对象
     */
    Comment update(Comment comment);

    /**
     * 通过主键集合批量删除数据
     *
     * @param deleteRequest 删除请求对象
     * @return 影响行数
     */
    int deleteBatchByIds(DeleteRequest deleteRequest);
}

