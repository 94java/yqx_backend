package cc.jiusi.springbootinit.service;

import cc.jiusi.springbootinit.common.DeleteRequest;
import cc.jiusi.springbootinit.common.StatusUpdateRequest;
import cc.jiusi.springbootinit.model.dto.subject.SubjectAddRequest;
import cc.jiusi.springbootinit.model.dto.subject.SubjectQueryRequest;
import cc.jiusi.springbootinit.model.dto.subject.SubjectUpdateRequest;
import cc.jiusi.springbootinit.model.entity.Subject;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-05 08:52:15
 * @Description: 题目信息(Subject)表服务接口
 */
public interface SubjectService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Subject queryById(Long id);

    /**
     * 通过条件查询所有数据
     *
     * @param subjectQueryRequest 查询条件
     * @return List<Subject> 实例对象列表
     */
    List<Subject> queryAll(SubjectQueryRequest subjectQueryRequest);

    /**
     * 通过条件查询分页数据
     *
     * @param subjectQueryRequest 查询条件
     * @return PageInfo<Subject> 分页信息对象
     */
    PageInfo<Subject> queryPage(SubjectQueryRequest subjectQueryRequest);

    /**
     * 根据条件统计总行数
     *
     * @param subjectQueryRequest 查询条件
     * @return 总行数
     */
    long queryCount(SubjectQueryRequest subjectQueryRequest);

    /**
     * 新增数据
     *
     * @param subjectAddRequest 实例对象
     * @return 实例对象
     */
    Subject insert(SubjectAddRequest subjectAddRequest);

    /**
     * 批量新增数据
     *
     * @param entities List<SubjectAddRequest> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(List<SubjectAddRequest> entities);

    /**
     * 修改数据
     *
     * @param subjectUpdateRequest 实例对象
     * @return 实例对象
     */
    Subject update(SubjectUpdateRequest subjectUpdateRequest);

    /**
     * 通过主键集合批量删除数据
     *
     * @param deleteRequest 删除请求对象
     * @return 影响行数
     */
    int deleteBatchByIds(DeleteRequest deleteRequest);

    void changeStatus(StatusUpdateRequest statusUpdateRequest);
}

