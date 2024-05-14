package cc.jiusi.springbootinit.service.impl;

import cc.jiusi.springbootinit.common.DeleteRequest;
import cc.jiusi.springbootinit.model.entity.Wrong;
import cc.jiusi.springbootinit.mapper.WrongMapper;
import cc.jiusi.springbootinit.service.WrongService;
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
        List<Wrong> wrongs = wrongMapper.selectAll(wrong);
        Wrong res;
        if(wrongs.size() <= 0){
            // 第一次加入错题本，新增
            wrong.setWrongCount(1);
            wrongMapper.insert(wrong);
            res = wrong;
        }else{
            // 已经在错题本中了，错误次数+1
            Wrong updateWrong = wrongs.get(0);
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
}

