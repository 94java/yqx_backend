package cc.jiusi.springbootinit.service.impl;

import cc.jiusi.springbootinit.common.DeleteRequest;
import cc.jiusi.springbootinit.model.dto.video.VideoAddRequest;
import cc.jiusi.springbootinit.model.dto.video.VideoQueryRequest;
import cc.jiusi.springbootinit.model.dto.video.VideoUpdateRequest;
import cc.jiusi.springbootinit.model.entity.Video;
import cc.jiusi.springbootinit.mapper.VideoMapper;
import cc.jiusi.springbootinit.service.VideoService;
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
 * @CreateTime: 2024-05-03 20:16:04
 * @Description: 视频信息(Video)表服务实现类
 */
@Service("videoService")
public class VideoServiceImpl implements VideoService {
    @Resource
    private VideoMapper videoMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Video queryById(Long id) {
        return videoMapper.selectById(id);
    }

    /**
     * 通过条件查询所有数据
     *
     * @param videoQueryRequest 查询条件
     * @return List<Video> 实例对象列表
     */
    @Override
    public List<Video> queryAll(VideoQueryRequest videoQueryRequest) {
        Video video = BeanUtil.copyProperties(videoQueryRequest, Video.class);
        return videoMapper.selectAll(video);
    }

    /**
     * 通过条件查询分页数据
     *
     * @param videoQueryRequest 查询条件
     * @return PageInfo<Video> 分页信息对象
     */
    @Override
    public PageInfo<Video> queryPage(VideoQueryRequest videoQueryRequest) {
        int pageNum = videoQueryRequest.getPageNum();
        int pageSize = videoQueryRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        Video video = BeanUtil.copyProperties(videoQueryRequest, Video.class);
        List<Video> videos = videoMapper.selectAll(video);
        PageInfo<Video> pageInfo = new PageInfo<>(videos);
        return pageInfo;
    }

    /**
     * 根据条件统计总行数
     *
     * @param videoQueryRequest 查询条件
     * @return 总行数
     */
    @Override
    public long queryCount(VideoQueryRequest videoQueryRequest) {
        Video video = BeanUtil.copyProperties(videoQueryRequest, Video.class);
        return videoMapper.count(video);
    }

    /**
     * 新增数据
     *
     * @param videoAddRequest 实例对象
     * @return 实例对象
     */
    @Override
    public Video insert(VideoAddRequest videoAddRequest) {
        Video video = BeanUtil.copyProperties(videoAddRequest, Video.class);
        videoMapper.insert(video);
        return video;
    }

    /**
     * 批量新增数据
     *
     * @param entities List<VideoAddRequest> 实例对象列表
     * @return 影响行数
     */
    @Override
    public int insertBatch(List<VideoAddRequest> entities) {
        List<Video> videos = entities.stream()
                .map(item -> BeanUtil.copyProperties(item, Video.class))
                .collect(Collectors.toList());
        return videoMapper.insertBatch(videos);
    }

    /**
     * 修改数据
     *
     * @param videoUpdateRequest 实例对象
     * @return 实例对象
     */
    @Override
    public Video update(VideoUpdateRequest videoUpdateRequest) {
        Video video = BeanUtil.copyProperties(videoUpdateRequest, Video.class);
        videoMapper.update(video);
        return queryById(video.getId());
    }

    /**
     * 通过主键集合批量删除数据
     *
     * @param deleteRequest 删除请求对象
     * @return 影响行数
     */
    @Override
    public int deleteBatchByIds(DeleteRequest deleteRequest) {
        return videoMapper.deleteBatchByIds(deleteRequest.getIds());
    }
}

