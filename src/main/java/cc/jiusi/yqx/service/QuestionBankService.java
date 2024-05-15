package cc.jiusi.yqx.service;

import cc.jiusi.yqx.common.DeleteRequest;
import cc.jiusi.yqx.common.StatusUpdateRequest;
import cc.jiusi.yqx.model.dto.questionBank.QuestionBankAddRequest;
import cc.jiusi.yqx.model.dto.questionBank.QuestionBankQueryRequest;
import cc.jiusi.yqx.model.dto.questionBank.QuestionBankUpdateRequest;
import cc.jiusi.yqx.model.entity.QuestionBank;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-04 17:16:03
 * @Description: 题库信息(QuestionBank)表服务接口
 */
public interface QuestionBankService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    QuestionBank queryById(Long id);

    /**
     * 通过条件查询所有数据
     *
     * @param questionBankQueryRequest 查询条件
     * @return List<QuestionBank> 实例对象列表
     */
    List<QuestionBank> queryAll(QuestionBankQueryRequest questionBankQueryRequest);

    /**
     * 通过条件查询分页数据
     *
     * @param questionBankQueryRequest 查询条件
     * @return PageInfo<QuestionBank> 分页信息对象
     */
    PageInfo<QuestionBank> queryPage(QuestionBankQueryRequest questionBankQueryRequest);

    /**
     * 根据条件统计总行数
     *
     * @param questionBankQueryRequest 查询条件
     * @return 总行数
     */
    long queryCount(QuestionBankQueryRequest questionBankQueryRequest);

    /**
     * 新增数据
     *
     * @param questionBankAddRequest 实例对象
     * @return 实例对象
     */
    QuestionBank insert(QuestionBankAddRequest questionBankAddRequest);

    /**
     * 批量新增数据
     *
     * @param entities List<QuestionBankAddRequest> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(List<QuestionBankAddRequest> entities);

    /**
     * 修改数据
     *
     * @param questionBankUpdateRequest 实例对象
     * @return 实例对象
     */
    QuestionBank update(QuestionBankUpdateRequest questionBankUpdateRequest);

    /**
     * 通过主键集合批量删除数据
     *
     * @param deleteRequest 删除请求对象
     * @return 影响行数
     */
    int deleteBatchByIds(DeleteRequest deleteRequest);

    void changeStatus(StatusUpdateRequest statusUpdateRequest);
}

