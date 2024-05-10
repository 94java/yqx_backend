package cc.jiusi.springbootinit.service.impl;

import cc.jiusi.springbootinit.common.DeleteRequest;
import cc.jiusi.springbootinit.common.StatusUpdateRequest;
import cc.jiusi.springbootinit.model.dto.subject.SubjectAddRequest;
import cc.jiusi.springbootinit.model.dto.subject.SubjectQueryRequest;
import cc.jiusi.springbootinit.model.dto.subject.SubjectUpdateRequest;
import cc.jiusi.springbootinit.model.entity.Subject;
import cc.jiusi.springbootinit.mapper.SubjectMapper;
import cc.jiusi.springbootinit.service.SubjectService;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;

import cn.hutool.core.bean.BeanUtil;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-05 08:52:15
 * @Description: 题目信息(Subject)表服务实现类
 */
@Service("subjectService")
public class SubjectServiceImpl implements SubjectService {
    @Resource
    private SubjectMapper subjectMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Subject queryById(Long id) {
        return subjectMapper.selectById(id);
    }

    /**
     * 通过条件查询所有数据
     *
     * @param subjectQueryRequest 查询条件
     * @return List<Subject> 实例对象列表
     */
    @Override
    public List<Subject> queryAll(SubjectQueryRequest subjectQueryRequest) {
        Subject subject = BeanUtil.copyProperties(subjectQueryRequest, Subject.class);
        List<Subject> list = subjectMapper.selectAll(subject);
        String mode = subjectQueryRequest.getMode();
        if(StrUtil.isNotBlank(mode) && "1".equals(mode)){
            // 是随机训练，需要打乱list中的顺序
            Collections.shuffle(list);
        }
        return list;
    }

    /**
     * 通过条件查询分页数据
     *
     * @param subjectQueryRequest 查询条件
     * @return PageInfo<Subject> 分页信息对象
     */
    @Override
    public PageInfo<Subject> queryPage(SubjectQueryRequest subjectQueryRequest) {
        int pageNum = subjectQueryRequest.getPageNum();
        int pageSize = subjectQueryRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        Subject subject = BeanUtil.copyProperties(subjectQueryRequest, Subject.class);
        List<Subject> subjects = subjectMapper.selectAll(subject);
        PageInfo<Subject> pageInfo = new PageInfo<>(subjects);
        return pageInfo;
    }

    /**
     * 根据条件统计总行数
     *
     * @param subjectQueryRequest 查询条件
     * @return 总行数
     */
    @Override
    public long queryCount(SubjectQueryRequest subjectQueryRequest) {
        Subject subject = BeanUtil.copyProperties(subjectQueryRequest, Subject.class);
        return subjectMapper.count(subject);
    }

    /**
     * 新增数据
     *
     * @param subjectAddRequest 实例对象
     * @return 实例对象
     */
    @Override
    public Subject insert(SubjectAddRequest subjectAddRequest) {
        Subject subject = BeanUtil.copyProperties(subjectAddRequest, Subject.class);
        subjectMapper.insert(subject);
        return subject;
    }

    /**
     * 批量新增数据
     *
     * @param entities List<SubjectAddRequest> 实例对象列表
     * @return 影响行数
     */
    @Override
    public int insertBatch(List<SubjectAddRequest> entities) {
        List<Subject> subjects = entities.stream()
                .map(item -> BeanUtil.copyProperties(item, Subject.class))
                .collect(Collectors.toList());
        return subjectMapper.insertBatch(subjects);
    }

    /**
     * 修改数据
     *
     * @param subjectUpdateRequest 实例对象
     * @return 实例对象
     */
    @Override
    public Subject update(SubjectUpdateRequest subjectUpdateRequest) {
        Subject subject = BeanUtil.copyProperties(subjectUpdateRequest, Subject.class);
        subjectMapper.update(subject);
        return queryById(subject.getId());
    }

    /**
     * 通过主键集合批量删除数据
     *
     * @param deleteRequest 删除请求对象
     * @return 影响行数
     */
    @Override
    public int deleteBatchByIds(DeleteRequest deleteRequest) {
        return subjectMapper.deleteBatchByIds(deleteRequest.getIds());
    }

    @Override
    public void changeStatus(StatusUpdateRequest statusUpdateRequest) {
        subjectMapper.updateStatus(statusUpdateRequest.getIds(),statusUpdateRequest.getStatus());
    }
}

