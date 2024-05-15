package cc.jiusi.yqx.service;

import cc.jiusi.yqx.common.DeleteRequest;
import cc.jiusi.yqx.model.entity.Likes;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-10 21:27:28
 * @Description: 点赞信息(Likes)表服务接口
 */
public interface LikesService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Likes queryById(Long id);

    /**
     * 通过条件查询所有数据
     *
     * @param likes 查询条件
     * @return List<Likes> 实例对象列表
     */
    Map<String,List> queryAll(Likes likes);

    /**
     * 通过条件查询分页数据
     *
     * @param likes 查询条件
     * @return PageInfo<Likes> 分页信息对象
     */
    PageInfo<Likes> queryPage(Likes likes);

    /**
     * 根据条件统计总行数
     *
     * @param likes 查询条件
     * @return 总行数
     */
    long queryCount(Likes likes);

    /**
     * 新增数据
     *
     * @param likes 实例对象
     * @return 实例对象
     */
    Likes insert(Likes likes);

    /**
     * 批量新增数据
     *
     * @param entities List<Likes> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(List<Likes> entities);

    /**
     * 修改数据
     *
     * @param likes 实例对象
     * @return 实例对象
     */
    Likes update(Likes likes);

    /**
     * 通过主键集合批量删除数据
     *
     * @param deleteRequest 删除请求对象
     * @return 影响行数
     */
    int deleteBatchByIds(DeleteRequest deleteRequest);

    void changeLikes(Likes likes);
}

