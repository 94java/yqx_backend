package cc.jiusi.springbootinit.service.impl;

import cc.jiusi.springbootinit.common.DeleteRequest;
import cc.jiusi.springbootinit.mapper.UserMapper;
import cc.jiusi.springbootinit.model.entity.Follow;
import cc.jiusi.springbootinit.mapper.FollowMapper;
import cc.jiusi.springbootinit.model.entity.Note;
import cc.jiusi.springbootinit.model.entity.User;
import cc.jiusi.springbootinit.model.entity.Video;
import cc.jiusi.springbootinit.service.FollowService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;

import cn.hutool.core.bean.BeanUtil;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-10 20:11:04
 * @Description: 用户关注信息(Follow)表服务实现类
 */
@Service("followService")
public class FollowServiceImpl implements FollowService {
    @Resource
    private FollowMapper followMapper;
    @Resource
    private UserMapper userMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Follow queryById(Long id) {
        return followMapper.selectById(id);
    }

    /**
     * 通过条件查询所有数据
     *
     * @param followQueryRequest 查询条件
     * @return List<Follow> 实例对象列表
     */
    @Override
    public List<Follow> queryAll(Follow followQueryRequest) {
        Follow follow = BeanUtil.copyProperties(followQueryRequest, Follow.class);
        return followMapper.selectAll(follow);
    }

    /**
     * 通过条件查询分页数据
     *
     * @param followQueryRequest 查询条件
     * @return PageInfo<Follow> 分页信息对象
     */
    @Override
    public PageInfo<Follow> queryPage(Follow followQueryRequest) {
        int pageNum = followQueryRequest.getPageNum();
        int pageSize = followQueryRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        Follow follow = BeanUtil.copyProperties(followQueryRequest, Follow.class);
        List<Follow> follows = followMapper.selectAll(follow);
        PageInfo<Follow> pageInfo = new PageInfo<>(follows);
        return pageInfo;
    }

    /**
     * 根据条件统计总行数
     *
     * @param follow 查询条件
     * @return 总行数
     */
    @Override
    public long queryCount(Follow follow) {
        return followMapper.count(follow);
    }

    /**
     * 新增数据
     *
     * @param follow 实例对象
     * @return 实例对象
     */
    @Override
    public Follow insert(Follow follow) {
        followMapper.insert(follow);
        return follow;
    }

    /**
     * 批量新增数据
     *
     * @param entities List<FollowAddRequest> 实例对象列表
     * @return 影响行数
     */
    @Override
    public int insertBatch(List<Follow> entities) {
        return followMapper.insertBatch(entities);
    }

    /**
     * 修改数据
     *
     * @param follow 实例对象
     * @return 实例对象
     */
    @Override
    public Follow update(Follow follow) {
        followMapper.update(follow);
        return queryById(follow.getId());
    }

    /**
     * 通过主键集合批量删除数据
     *
     * @param deleteRequest 删除请求对象
     * @return 影响行数
     */
    @Override
    public int deleteBatchByIds(DeleteRequest deleteRequest) {
        return followMapper.deleteBatchByIds(deleteRequest.getIds());
    }

    /**
     * 改变关注状态
     *
     * @param follow 关注对象
     */
    @Override
    public void changeFollow(Follow follow) {
        // 根据 uid 和 ref_uid查询
        List<Follow> follows = followMapper.selectAll(follow);
        if (follows.size() > 0) {
            // 已经关注过了，取关（删除该记录）
            followMapper.deleteBatchByIds(follows.stream().map(Follow::getId).collect(Collectors.toList()));
        } else {
            // 未关注，关注（添加记录）
            followMapper.insert(follow);
        }
    }
}

