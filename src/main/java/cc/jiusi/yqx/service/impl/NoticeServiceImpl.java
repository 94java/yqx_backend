package cc.jiusi.yqx.service.impl;

import cc.jiusi.yqx.common.DeleteRequest;
import cc.jiusi.yqx.model.entity.Notice;
import cc.jiusi.yqx.mapper.NoticeMapper;
import cc.jiusi.yqx.service.NoticeService;
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
 * @CreateTime: 2024-05-15 15:38:54
 * @Description: 公告信息(Notice)表服务实现类
 */
@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {
    @Resource
    private NoticeMapper noticeMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Notice queryById(Long id) {
        return noticeMapper.selectById(id);
    }

    /**
     * 通过条件查询所有数据
     *
     * @param notice 查询条件
     * @return List<Notice> 实例对象列表
     */
    @Override
    public List<Notice> queryAll(Notice notice) {
        return noticeMapper.selectAll(notice);
    }

    /**
     * 通过条件查询分页数据
     *
     * @param notice 查询条件
     * @return PageInfo<Notice> 分页信息对象
     */
    @Override
    public PageInfo<Notice> queryPage(Notice notice) {
        int pageNum = notice.getPageNum();
        int pageSize = notice.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<Notice> notices = noticeMapper.selectAll(notice);
        PageInfo<Notice> pageInfo = new PageInfo<>(notices);
        return pageInfo;
    }

    /**
     * 根据条件统计总行数
     *
     * @param notice 查询条件
     * @return 总行数
     */
    @Override
    public long queryCount(Notice notice) {
        return noticeMapper.count(notice);
    }

    /**
     * 新增数据
     *
     * @param notice 实例对象
     * @return 实例对象
     */
    @Override
    public Notice insert(Notice notice) {
        noticeMapper.insert(notice);
        return notice;
    }

    /**
     * 批量新增数据
     *
     * @param entities List<Notice> 实例对象列表
     * @return 影响行数
     */
    @Override
    public int insertBatch(List<Notice> entities) {
        return noticeMapper.insertBatch(entities);
    }

    /**
     * 修改数据
     *
     * @param notice 实例对象
     * @return 实例对象
     */
    @Override
    public Notice update(Notice notice) {
        noticeMapper.update(notice);
        return queryById(notice.getId());
    }

    /**
     * 通过主键集合批量删除数据
     *
     * @param deleteRequest 删除请求对象
     * @return 影响行数
     */
    @Override
    public int deleteBatchByIds(DeleteRequest deleteRequest) {
        return noticeMapper.deleteBatchByIds(deleteRequest.getIds());
    }
}

