package cc.jiusi.yqx.service;

import cc.jiusi.yqx.common.DeleteRequest;
import cc.jiusi.yqx.model.entity.Follow;
import cc.jiusi.yqx.model.vo.UserVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-10 20:11:04
 * @Description: 用户关注信息(Follow)表服务接口
 */
public interface FollowService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Follow queryById(Long id);

    /**
     * 通过条件查询所有数据
     *
     * @param follow 查询条件
     * @return List<Follow> 实例对象列表
     */
    List<Follow> queryAll(Follow follow);

    /**
     * 通过条件查询分页数据
     *
     * @param follow 查询条件
     * @return PageInfo<Follow> 分页信息对象
     */
    PageInfo<Follow> queryPage(Follow follow);

    /**
     * 根据条件统计总行数
     *
     * @param follow 查询条件
     * @return 总行数
     */
    long queryCount(Follow follow);

    /**
     * 新增数据
     *
     * @param follow 实例对象
     * @return 实例对象
     */
    Follow insert(Follow follow);

    /**
     * 批量新增数据
     *
     * @param entities List<Follow> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(List<Follow> entities);

    /**
     * 修改数据
     *
     * @param follow 实例对象
     * @return 实例对象
     */
    Follow update(Follow follow);

    /**
     * 通过主键集合批量删除数据
     *
     * @param deleteRequest 删除请求对象
     * @return 影响行数
     */
    int deleteBatchByIds(DeleteRequest deleteRequest);

    /**
     * 改变关注状态
     *
     * @param follow 关注对象
     */
    void changeFollow(Follow follow);

    List<UserVO> getCurrentFollows();

    List<UserVO> getCurrentFans();

    List<UserVO> getCurrentFollowsActivity();
}

