package cc.jiusi.yqx.service;

import cc.jiusi.yqx.common.DeleteRequest;
import cc.jiusi.yqx.common.StatusUpdateRequest;
import cc.jiusi.yqx.model.dto.video.VideoAddRequest;
import cc.jiusi.yqx.model.dto.video.VideoQueryRequest;
import cc.jiusi.yqx.model.dto.video.VideoUpdateRequest;
import cc.jiusi.yqx.model.entity.Video;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-03 20:16:03
 * @Description: 视频信息(Video)表服务接口
 */
public interface VideoService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Video queryById(Long id);

    /**
     * 通过条件查询所有数据
     *
     * @param videoQueryRequest 查询条件
     * @return List<Video> 实例对象列表
     */
    List<Video> queryAll(VideoQueryRequest videoQueryRequest);

    /**
     * 通过条件查询分页数据
     *
     * @param videoQueryRequest 查询条件
     * @return PageInfo<Video> 分页信息对象
     */
    PageInfo<Video> queryPage(VideoQueryRequest videoQueryRequest);

    /**
     * 根据条件统计总行数
     *
     * @param videoQueryRequest 查询条件
     * @return 总行数
     */
    long queryCount(VideoQueryRequest videoQueryRequest);

    /**
     * 新增数据
     *
     * @param videoAddRequest 实例对象
     * @return 实例对象
     */
    Video insert(VideoAddRequest videoAddRequest);

    /**
     * 批量新增数据
     *
     * @param entities List<VideoAddRequest> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(List<VideoAddRequest> entities);

    /**
     * 修改数据
     *
     * @param videoUpdateRequest 实例对象
     * @return 实例对象
     */
    Video update(VideoUpdateRequest videoUpdateRequest);

    /**
     * 通过主键集合批量删除数据
     *
     * @param deleteRequest 删除请求对象
     * @return 影响行数
     */
    int deleteBatchByIds(DeleteRequest deleteRequest);

    void changeStatus(StatusUpdateRequest statusUpdateRequest);

    List<Video> getListOrderByViews(int start, int end);

    List<Video> getVideoByUserCF(Long videoId);
}

