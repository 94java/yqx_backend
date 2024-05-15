package cc.jiusi.yqx.service;

import cc.jiusi.yqx.common.DeleteRequest;
import cc.jiusi.yqx.model.entity.Answer;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-05 11:11:20
 * @Description: 题目选项信息(Answer)表服务接口
 */
public interface AnswerService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Answer queryById(Long id);

    /**
     * 通过条件查询所有数据
     *
     * @param answer 查询条件
     * @return List<Answer> 实例对象列表
     */
    List<Answer> queryAll(Answer answer);

    /**
     * 通过条件查询分页数据
     *
     * @param answer 查询条件
     * @return PageInfo<Answer> 分页信息对象
     */
    PageInfo<Answer> queryPage(Answer answer);

    /**
     * 根据条件统计总行数
     *
     * @param answer 查询条件
     * @return 总行数
     */
    long queryCount(Answer answer);

    /**
     * 新增数据
     *
     * @param answer 实例对象
     * @return 实例对象
     */
    Answer insert(Answer answer);

    /**
     * 批量新增数据
     *
     * @param entities List<Answer> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(List<Answer> entities);

    /**
     * 修改数据
     *
     * @param answer 实例对象
     * @return 实例对象
     */
    Answer update(Answer answer);

    /**
     * 通过主键集合批量删除数据
     *
     * @param deleteRequest 删除请求对象
     * @return 影响行数
     */
    int deleteBatchByIds(DeleteRequest deleteRequest);
}

