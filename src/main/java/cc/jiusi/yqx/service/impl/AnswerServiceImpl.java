package cc.jiusi.yqx.service.impl;

import cc.jiusi.yqx.common.DeleteRequest;
import cc.jiusi.yqx.model.entity.Answer;
import cc.jiusi.yqx.mapper.AnswerMapper;
import cc.jiusi.yqx.service.AnswerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import javax.annotation.Resource;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-05 11:11:20
 * @Description: 题目选项信息(Answer)表服务实现类
 */
@Service("answerService")
public class AnswerServiceImpl implements AnswerService {
    @Resource
    private AnswerMapper answerMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Answer queryById(Long id) {
        return answerMapper.selectById(id);
    }

    /**
     * 通过条件查询所有数据
     *
     * @param answer 查询条件
     * @return List<Answer> 实例对象列表
     */
    @Override
    public List<Answer> queryAll(Answer answer) {
        return answerMapper.selectAll(answer);
    }

    /**
     * 通过条件查询分页数据
     *
     * @param answer 查询条件
     * @return PageInfo<Answer> 分页信息对象
     */
    @Override
    public PageInfo<Answer> queryPage(Answer answer) {
        int pageNum = answer.getPageNum();
        int pageSize = answer.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<Answer> answers = answerMapper.selectAll(answer);
        PageInfo<Answer> pageInfo = new PageInfo<>(answers);
        return pageInfo;
    }

    /**
     * 根据条件统计总行数
     *
     * @param answer 查询条件
     * @return 总行数
     */
    @Override
    public long queryCount(Answer answer) {
        return answerMapper.count(answer);
    }

    /**
     * 新增数据
     *
     * @param answer 实例对象
     * @return 实例对象
     */
    @Override
    public Answer insert(Answer answer) {
        answerMapper.insert(answer);
        return answer;
    }

    /**
     * 批量新增数据
     *
     * @param entities List<AnswerAddRequest> 实例对象列表
     * @return 影响行数
     */
    @Override
    public int insertBatch(List<Answer> entities) {
        return answerMapper.insertBatch(entities);
    }

    /**
     * 修改数据
     *
     * @param answer 实例对象
     * @return 实例对象
     */
    @Override
    public Answer update(Answer answer) {
        answerMapper.update(answer);
        return queryById(answer.getId());
    }

    /**
     * 通过主键集合批量删除数据
     *
     * @param deleteRequest 删除请求对象
     * @return 影响行数
     */
    @Override
    public int deleteBatchByIds(DeleteRequest deleteRequest) {
        return answerMapper.deleteBatchByIds(deleteRequest.getIds());
    }
}

