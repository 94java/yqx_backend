package cc.jiusi.yqx.service.impl;

import cc.jiusi.yqx.common.DeleteRequest;
import cc.jiusi.yqx.common.ErrorCode;
import cc.jiusi.yqx.exception.BusinessException;
import cc.jiusi.yqx.model.entity.Comment;
import cc.jiusi.yqx.mapper.CommentMapper;
import cc.jiusi.yqx.model.entity.User;
import cc.jiusi.yqx.service.CommentService;
import cc.jiusi.yqx.utils.SensitiveWordUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import javax.annotation.Resource;

import cn.hutool.core.bean.BeanUtil;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-10 13:55:20
 * @Description: 评论信息(Comment)表服务实现类
 */
@Service("commentService")
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentMapper commentMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Comment queryById(Long id) {
        return commentMapper.selectById(id);
    }

    /**
     * 通过条件查询所有数据
     *
     * @param comment 查询条件
     * @return List<Comment> 实例对象列表
     */
    @Override
    public List<Comment> queryAll(Comment comment) {
        List<Comment> comments = commentMapper.selectAll(comment);
        // 用户信息脱敏
        for (Comment item : comments) {
            item.setUser(getSafeUser(item.getUser()));
        }
        return comments;
    }

    /**
     * 通过条件查询分页数据
     *
     * @param comment 查询条件
     * @return PageInfo<Comment> 分页信息对象
     */
    @Override
    public PageInfo<Comment> queryPage(Comment comment) {
        int pageNum = comment.getPageNum();
        int pageSize = comment.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> comments = commentMapper.selectAll(comment);
        // 用户信息脱敏
        for (Comment item : comments) {
            item.setUser(getSafeUser(item.getUser()));
        }
        PageInfo<Comment> pageInfo = new PageInfo<>(comments);
        return pageInfo;
    }

    /**
     * 根据条件统计总行数
     *
     * @param comment 查询条件
     * @return 总行数
     */
    @Override
    public long queryCount(Comment comment) {
        return commentMapper.count(comment);
    }

    /**
     * 新增数据
     *
     * @param comment 实例对象
     * @return 实例对象
     */
    @Override
    public Comment insert(Comment comment) {
        if (comment.getParentId() == null) {
            comment.setParentId(-1L);
        }
        // 判断是否含有敏感词
        boolean include = SensitiveWordUtil.WORD_FILTER.include(comment.getContent());
        if (include) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "评论内容包含敏感词，禁止发布");
        }
        try {
            commentMapper.insert(comment);
        } catch (Exception e) {
            if (e.getMessage().contains("Duplicate entry")) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "不能重复评论同一内容");
            }
            throw new RuntimeException(e);
        }
        return comment;
    }

    /**
     * 批量新增数据
     *
     * @param entities List<Comment> 实例对象列表
     * @return 影响行数
     */
    @Override
    public int insertBatch(List<Comment> entities) {
        return commentMapper.insertBatch(entities);
    }

    /**
     * 修改数据
     *
     * @param comment 实例对象
     * @return 实例对象
     */
    @Override
    public Comment update(Comment comment) {
        commentMapper.update(comment);
        return queryById(comment.getId());
    }

    /**
     * 通过主键集合批量删除数据
     *
     * @param deleteRequest 删除请求对象
     * @return 影响行数
     */
    @Override
    public int deleteBatchByIds(DeleteRequest deleteRequest) {
        return commentMapper.deleteBatchByIds(deleteRequest.getIds());
    }

    private User getSafeUser(User user) {
        if (user == null) {
            return user;
        }
        String email = user.getEmail();
        if (StrUtil.isNotBlank(user.getPhone())) {
            user.setPhone(StrUtil.hide(user.getPhone(), 3, 8));
        }
        if (StrUtil.isNotBlank(email)) {
            user.setEmail(StrUtil.hide(email, 2, email.indexOf("@") - 2));
        }
        return BeanUtil.copyProperties(user, User.class, "password");
    }
}

