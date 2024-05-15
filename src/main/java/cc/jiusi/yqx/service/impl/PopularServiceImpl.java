package cc.jiusi.yqx.service.impl;

import cc.jiusi.yqx.common.DeleteRequest;
import cc.jiusi.yqx.common.ErrorCode;
import cc.jiusi.yqx.common.UserContextHolder;
import cc.jiusi.yqx.exception.BusinessException;
import cc.jiusi.yqx.mapper.CommentMapper;
import cc.jiusi.yqx.mapper.FollowMapper;
import cc.jiusi.yqx.mapper.LikesMapper;
import cc.jiusi.yqx.model.entity.*;
import cc.jiusi.yqx.mapper.PopularMapper;
import cc.jiusi.yqx.service.PopularService;
import cc.jiusi.yqx.utils.SensitiveWordUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import cn.hutool.core.bean.BeanUtil;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-12 16:05:54
 * @Description: 动态信息(Popular)表服务实现类
 */
@Service("popularService")
public class PopularServiceImpl implements PopularService {
    @Resource
    private PopularMapper popularMapper;
    @Resource
    private FollowMapper followMapper;
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private LikesMapper likesMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Popular queryById(Long id) {
        Popular popular = popularMapper.selectById(id);
        // 用户信息脱敏
        popular.setUser(popular.getUser());
        return popular;
    }

    /**
     * 通过条件查询所有数据
     *
     * @param popular 查询条件
     * @return List<Popular> 实例对象列表
     */
    @Override
    public List<Popular> queryAll(Popular popular) {
        List<Popular> populars = popularMapper.selectAll(popular);
        for (Popular item : populars) {
            // 用户信息脱敏
            item.setUser(getSafeUser(item.getUser()));
            // 填充信息（评论数、是否点赞）
            fillInfo(item,UserContextHolder.getUserId());
        }

        return populars;
    }

    /**
     * 通过条件查询分页数据
     *
     * @param popular 查询条件
     * @return PageInfo<Popular> 分页信息对象
     */
    @Override
    public PageInfo<Popular> queryPage(Popular popular) {
        int pageNum = popular.getPageNum();
        int pageSize = popular.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<Popular> populars = popularMapper.selectAll(popular);
        for (Popular item : populars) {
            // 用户信息脱敏
            item.setUser(getSafeUser(item.getUser()));
            // 填充信息（评论数、是否点赞）
            fillInfo(item,UserContextHolder.getUserId());
        }
        PageInfo<Popular> pageInfo = new PageInfo<>(populars);
        return pageInfo;
    }

    /**
     * 根据条件统计总行数
     *
     * @param popular 查询条件
     * @return 总行数
     */
    @Override
    public long queryCount(Popular popular) {
        return popularMapper.count(popular);
    }

    /**
     * 新增数据
     *
     * @param popular 实例对象
     * @return 实例对象
     */
    @Override
    public Popular insert(Popular popular) {
        // 敏感内容替换
        popular.setContent(SensitiveWordUtil.WORD_FILTER.replace(popular.getContent()));
        popularMapper.insert(popular);
        return popular;
    }

    /**
     * 批量新增数据
     *
     * @param entities List<Popular> 实例对象列表
     * @return 影响行数
     */
    @Override
    public int insertBatch(List<Popular> entities) {
        for (Popular entity : entities) {
            // 敏感内容替换
            entity.setContent(SensitiveWordUtil.WORD_FILTER.replace(entity.getContent()));
        }
        return popularMapper.insertBatch(entities);
    }

    /**
     * 修改数据
     *
     * @param popular 实例对象
     * @return 实例对象
     */
    @Override
    public Popular update(Popular popular) {
        // 敏感内容替换
        popular.setContent(SensitiveWordUtil.WORD_FILTER.replace(popular.getContent()));
        popularMapper.update(popular);
        return queryById(popular.getId());
    }

    /**
     * 通过主键集合批量删除数据
     *
     * @param deleteRequest 删除请求对象
     * @return 影响行数
     */
    @Override
    public int deleteBatchByIds(DeleteRequest deleteRequest) {
        return popularMapper.deleteBatchByIds(deleteRequest.getIds());
    }

    @Override
    public List<Popular> currentFollow() {
        Long userId = UserContextHolder.getUserId();
        if (userId == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 获取当前用户关注列表
        Follow follow = new Follow();
        follow.setUid(userId);
        List<Follow> follows = followMapper.selectAll(follow);
        // 拼接当前用户动态+当前用户关注用户所发动态
        List<Popular> res = new ArrayList<>();
        Popular p = new Popular();
        p.setCreateBy(userId);
        res.addAll(popularMapper.selectAll(p));
        for (Follow item : follows) {
            Long refUid = item.getRefUid();
            Popular popular = new Popular();
            popular.setCreateBy(refUid);
            // 查询被关注者所发动态
            List<Popular> populars = popularMapper.selectAll(popular);

            res.addAll(populars);
        }
        for (Popular popu : res) {
            // 用户信息脱敏
            popu.setUser(getSafeUser(popu.getUser()));
            // 填充信息（评论数、是否点赞）
            fillInfo(popu,UserContextHolder.getUserId());
        }
        // 根据时间降序对res排序（最新发布的排名最前）
        res.sort((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime()));
        return res;
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

    private void fillInfo(Popular popular, Long userId) {
        // 填充点赞信息
        Long popularId = popular.getId();
        Likes likes = new Likes();
        likes.setType("2");
        likes.setUid(userId);
        likes.setContentId(popularId);
        long count = likesMapper.count(likes);
        if (count > 0) {
            // 当前登录用户点赞过
            popular.setLike(true);
        }
        // 填充评论数
        fillCommentCount(popular);
    }

    private void fillCommentCount(Popular popular) {
        Comment comment = new Comment();
        comment.setType("2");
        comment.setContentId(popular.getId());
        long count = commentMapper.count(comment);
        popular.setCommentCount(count);
    }
}

