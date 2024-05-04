package cc.jiusi.springbootinit.service.impl;

import cc.jiusi.springbootinit.common.DeleteRequest;
import cc.jiusi.springbootinit.common.StatusUpdateRequest;
import cc.jiusi.springbootinit.model.dto.questionBank.QuestionBankAddRequest;
import cc.jiusi.springbootinit.model.dto.questionBank.QuestionBankQueryRequest;
import cc.jiusi.springbootinit.model.dto.questionBank.QuestionBankUpdateRequest;
import cc.jiusi.springbootinit.model.entity.QuestionBank;
import cc.jiusi.springbootinit.mapper.QuestionBankMapper;
import cc.jiusi.springbootinit.service.QuestionBankService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;

import cn.hutool.core.bean.BeanUtil;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-04 17:16:03
 * @Description: 题库信息(QuestionBank)表服务实现类
 */
@Service("questionBankService")
public class QuestionBankServiceImpl implements QuestionBankService {
    @Resource
    private QuestionBankMapper questionBankMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public QuestionBank queryById(Long id) {
        return questionBankMapper.selectById(id);
    }

    /**
     * 通过条件查询所有数据
     *
     * @param questionBankQueryRequest 查询条件
     * @return List<QuestionBank> 实例对象列表
     */
    @Override
    public List<QuestionBank> queryAll(QuestionBankQueryRequest questionBankQueryRequest) {
        QuestionBank questionBank = BeanUtil.copyProperties(questionBankQueryRequest, QuestionBank.class);
        return questionBankMapper.selectAll(questionBank);
    }

    /**
     * 通过条件查询分页数据
     *
     * @param questionBankQueryRequest 查询条件
     * @return PageInfo<QuestionBank> 分页信息对象
     */
    @Override
    public PageInfo<QuestionBank> queryPage(QuestionBankQueryRequest questionBankQueryRequest) {
        int pageNum = questionBankQueryRequest.getPageNum();
        int pageSize = questionBankQueryRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        QuestionBank questionBank = BeanUtil.copyProperties(questionBankQueryRequest, QuestionBank.class);
        List<QuestionBank> questionBanks = questionBankMapper.selectAll(questionBank);
        PageInfo<QuestionBank> pageInfo = new PageInfo<>(questionBanks);
        return pageInfo;
    }

    /**
     * 根据条件统计总行数
     *
     * @param questionBankQueryRequest 查询条件
     * @return 总行数
     */
    @Override
    public long queryCount(QuestionBankQueryRequest questionBankQueryRequest) {
        QuestionBank questionBank = BeanUtil.copyProperties(questionBankQueryRequest, QuestionBank.class);
        return questionBankMapper.count(questionBank);
    }

    /**
     * 新增数据
     *
     * @param questionBankAddRequest 实例对象
     * @return 实例对象
     */
    @Override
    public QuestionBank insert(QuestionBankAddRequest questionBankAddRequest) {
        QuestionBank questionBank = BeanUtil.copyProperties(questionBankAddRequest, QuestionBank.class);
        // 初始值
        questionBank.setViews(0L);
        questionBankMapper.insert(questionBank);
        return questionBank;
    }

    /**
     * 批量新增数据
     *
     * @param entities List<QuestionBankAddRequest> 实例对象列表
     * @return 影响行数
     */
    @Override
    public int insertBatch(List<QuestionBankAddRequest> entities) {
        List<QuestionBank> questionBanks = entities.stream()
                .map(item -> {
                    QuestionBank questionBank = BeanUtil.copyProperties(item, QuestionBank.class);
                    questionBank.setViews(0L);
                    return questionBank;
                })
                .collect(Collectors.toList());
        return questionBankMapper.insertBatch(questionBanks);
    }

    /**
     * 修改数据
     *
     * @param questionBankUpdateRequest 实例对象
     * @return 实例对象
     */
    @Override
    public QuestionBank update(QuestionBankUpdateRequest questionBankUpdateRequest) {
        QuestionBank questionBank = BeanUtil.copyProperties(questionBankUpdateRequest, QuestionBank.class);
        questionBankMapper.update(questionBank);
        return queryById(questionBank.getId());
    }

    /**
     * 通过主键集合批量删除数据
     *
     * @param deleteRequest 删除请求对象
     * @return 影响行数
     */
    @Override
    public int deleteBatchByIds(DeleteRequest deleteRequest) {
        return questionBankMapper.deleteBatchByIds(deleteRequest.getIds());
    }

    @Override
    public void changeStatus(StatusUpdateRequest statusUpdateRequest) {
        questionBankMapper.updateStatus(statusUpdateRequest.getIds(),statusUpdateRequest.getStatus());
    }
}

