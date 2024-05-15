package cc.jiusi.yqx.service.impl;

import cc.jiusi.yqx.common.DeleteRequest;
import cc.jiusi.yqx.common.ErrorCode;
import cc.jiusi.yqx.common.UserContextHolder;
import cc.jiusi.yqx.exception.BusinessException;
import cc.jiusi.yqx.model.entity.Wrong;
import cc.jiusi.yqx.mapper.WrongMapper;
import cc.jiusi.yqx.service.WrongService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-14 18:41:57
 * @Description: 用户错题信息(Wrong)表服务实现类
 */
@Service("wrongService")
public class WrongServiceImpl implements WrongService {
    @Resource
    private WrongMapper wrongMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Wrong queryById(Long id) {
        return wrongMapper.selectById(id);
    }

    /**
     * 通过条件查询所有数据
     *
     * @param wrong 查询条件
     * @return List<Wrong> 实例对象列表
     */
    @Override
    public List<Wrong> queryAll(Wrong wrong) {
        return wrongMapper.selectAll(wrong);
    }

    /**
     * 通过条件查询分页数据
     *
     * @param wrong 查询条件
     * @return PageInfo<Wrong> 分页信息对象
     */
    @Override
    public PageInfo<Wrong> queryPage(Wrong wrong) {
        int pageNum = wrong.getPageNum();
        int pageSize = wrong.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<Wrong> wrongs = wrongMapper.selectAll(wrong);
        PageInfo<Wrong> pageInfo = new PageInfo<>(wrongs);
        return pageInfo;
    }

    /**
     * 根据条件统计总行数
     *
     * @param wrong 查询条件
     * @return 总行数
     */
    @Override
    public long queryCount(Wrong wrong) {
        return wrongMapper.count(wrong);
    }

    /**
     * 新增数据
     *
     * @param wrong 实例对象
     * @return 实例对象
     */
    @Override
    public Wrong insert(Wrong wrong) {
        // 保存错误项id并置null后查询（错误选项每次只记录最新的即可）
        Long wrongAnswerId = wrong.getAnswerId();
        wrong.setAnswerId(null);
        List<Wrong> wrongs = wrongMapper.selectAll(wrong);
        Wrong res;
        if (wrongs.size() <= 0) {
            // 第一次加入错题本，新增
            wrong.setWrongCount(1);
            wrong.setAnswerId(wrongAnswerId);
            wrongMapper.insert(wrong);
            res = wrong;
        } else {
            // 已经在错题本中了，错误次数+1
            Wrong updateWrong = wrongs.get(0);
            updateWrong.setAnswerId(wrongAnswerId);
            updateWrong.setWrongCount(updateWrong.getWrongCount() + 1);
            wrongMapper.update(updateWrong);
            res = updateWrong;
        }
        return res;
    }

    /**
     * 批量新增数据
     *
     * @param entities List<Wrong> 实例对象列表
     * @return 影响行数
     */
    @Override
    public int insertBatch(List<Wrong> entities) {
        return wrongMapper.insertBatch(entities);
    }

    /**
     * 修改数据
     *
     * @param wrong 实例对象
     * @return 实例对象
     */
    @Override
    public Wrong update(Wrong wrong) {
        wrongMapper.update(wrong);
        return queryById(wrong.getId());
    }

    /**
     * 通过主键集合批量删除数据
     *
     * @param deleteRequest 删除请求对象
     * @return 影响行数
     */
    @Override
    public int deleteBatchByIds(DeleteRequest deleteRequest) {
        return wrongMapper.deleteBatchByIds(deleteRequest.getIds());
    }

    @Override
    public List<Wrong> currentWrongBanks() {
        Long userId = UserContextHolder.getUserId();
        if (userId == null) {
            // 未登录
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return wrongMapper.selectWrongsGroupByBanks(userId);
    }

    @Override
    public Integer changeWrongCount(Wrong wrong) {
        List<Wrong> wrongs = wrongMapper.selectAll(wrong);
        if (CollUtil.isEmpty(wrongs)) {
            return null;
        }
        Wrong updateWrong = wrongs.get(0);
        updateWrong.setWrongCount(updateWrong.getWrongCount() - 1);
        if (updateWrong.getWrongCount() <= 0) {
            // 删除错题记录
            wrongMapper.deleteBatchByIds(Collections.singletonList(updateWrong.getId()));
        } else {
            // 修改错题记录
            wrongMapper.update(updateWrong);
        }
        return updateWrong.getWrongCount();
    }
}

