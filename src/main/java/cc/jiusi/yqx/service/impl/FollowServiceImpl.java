package cc.jiusi.yqx.service.impl;

import cc.jiusi.yqx.common.DeleteRequest;
import cc.jiusi.yqx.common.ErrorCode;
import cc.jiusi.yqx.common.UserContextHolder;
import cc.jiusi.yqx.exception.BusinessException;
import cc.jiusi.yqx.mapper.UserMapper;
import cc.jiusi.yqx.model.entity.Follow;
import cc.jiusi.yqx.mapper.FollowMapper;
import cc.jiusi.yqx.model.entity.User;
import cc.jiusi.yqx.model.vo.UserVO;
import cc.jiusi.yqx.service.FollowService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
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

    @Override
    public List<UserVO> getCurrentFollows() {
        // 获取当前登录用户
        Long curUid = UserContextHolder.getUserId();
        if (curUid == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        Follow follow = new Follow();
        follow.setUid(curUid);
        // 获取当前登录用户的关注信息
        List<Follow> follows = followMapper.selectAll(follow);
        List<UserVO> users = new ArrayList<>(follows.size());
        // 根据关注信息，获取被关注用户id，并封装为User集合返回
        for (Follow item : follows) {
            Long refUid = item.getRefUid();
            User user = userMapper.selectById(refUid);
            users.add(BeanUtil.copyProperties(user,UserVO.class));
        }
        return users;
    }

    @Override
    public List<UserVO> getCurrentFans() {
        // 获取当前登录用户
        Long curUid = UserContextHolder.getUserId();
        if (curUid == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        Follow follow = new Follow();
        follow.setRefUid(curUid);
        // 获取当前登录用户的关注信息
        List<Follow> follows = followMapper.selectAll(follow);
        List<UserVO> users = new ArrayList<>(follows.size());
        // 根据关注信息，获取被关注用户id，并封装为User集合返回
        for (Follow item : follows) {
            Long uid = item.getUid();
            User user = userMapper.selectById(uid);
            users.add(BeanUtil.copyProperties(user,UserVO.class));
        }
        return users;
    }

    @Override
    public List<UserVO> getCurrentFollowsActivity() {
        Long userId = UserContextHolder.getUserId();
        if(userId == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        Follow follow = new Follow();
        follow.setUid(userId);
        // 查询当前用户关注列表
        List<Follow> follows = followMapper.selectAll(follow);
        List<UserVO> userVOList = new ArrayList<>(follows.size());
        for (Follow item : follows) {
            // 获取被关注用户信息，封装为集合
            Long refUid = item.getRefUid();
            User user = userMapper.selectById(refUid);
            // 未登录则默认
            if(user.getLastLoginTime() == null){
                user.setLastLoginTime(new Date(0));
            }
            userVOList.add(BeanUtil.copyProperties(user,UserVO.class));
        }
        // 根据最后一次登录时间排序
        return userVOList.stream().sorted(Comparator.comparing(UserVO::getLastLoginTime).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }
}

