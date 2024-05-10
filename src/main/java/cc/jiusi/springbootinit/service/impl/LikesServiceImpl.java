package cc.jiusi.springbootinit.service.impl;

import cc.jiusi.springbootinit.common.DeleteRequest;
import cc.jiusi.springbootinit.mapper.NoteMapper;
import cc.jiusi.springbootinit.mapper.VideoMapper;
import cc.jiusi.springbootinit.model.entity.Follow;
import cc.jiusi.springbootinit.model.entity.Likes;
import cc.jiusi.springbootinit.mapper.LikesMapper;
import cc.jiusi.springbootinit.model.entity.Note;
import cc.jiusi.springbootinit.model.entity.Video;
import cc.jiusi.springbootinit.service.LikesService;
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
 * @CreateTime: 2024-05-10 21:27:28
 * @Description: 点赞信息(Likes)表服务实现类
 */
@Service("likesService")
public class LikesServiceImpl implements LikesService {
    @Resource
    private LikesMapper likesMapper;
    @Resource
    private NoteMapper noteMapper;
    @Resource
    private VideoMapper videoMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Likes queryById(Long id) {
        return likesMapper.selectById(id);
    }

    /**
     * 通过条件查询所有数据
     *
     * @param likes 查询条件
     * @return List<Likes> 实例对象列表
     */
    @Override
    public List<Likes> queryAll(Likes likes) {
        return likesMapper.selectAll(likes);
    }

    /**
     * 通过条件查询分页数据
     *
     * @param likes 查询条件
     * @return PageInfo<Likes> 分页信息对象
     */
    @Override
    public PageInfo<Likes> queryPage(Likes likes) {
        int pageNum = likes.getPageNum();
        int pageSize = likes.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<Likes> likesLit = likesMapper.selectAll(likes);
        PageInfo<Likes> pageInfo = new PageInfo<>(likesLit);
        return pageInfo;
    }

    /**
     * 根据条件统计总行数
     *
     * @param likes 查询条件
     * @return 总行数
     */
    @Override
    public long queryCount(Likes likes) {
        return likesMapper.count(likes);
    }

    /**
     * 新增数据
     *
     * @param likes 实例对象
     * @return 实例对象
     */
    @Override
    public Likes insert(Likes likes) {
        likesMapper.insert(likes);
        return likes;
    }

    /**
     * 批量新增数据
     *
     * @param entities List<Likes> 实例对象列表
     * @return 影响行数
     */
    @Override
    public int insertBatch(List<Likes> entities) {
        return likesMapper.insertBatch(entities);
    }

    /**
     * 修改数据
     *
     * @param likes 实例对象
     * @return 实例对象
     */
    @Override
    public Likes update(Likes likes) {
        likesMapper.update(likes);
        return queryById(likes.getId());
    }

    /**
     * 通过主键集合批量删除数据
     *
     * @param deleteRequest 删除请求对象
     * @return 影响行数
     */
    @Override
    public int deleteBatchByIds(DeleteRequest deleteRequest) {
        return likesMapper.deleteBatchByIds(deleteRequest.getIds());
    }

    @Override
    public void changeLikes(Likes likes) {

        // 根据 uid 和 ref_uid查询
        List<Likes> likesList = likesMapper.selectAll(likes);
        if (likesList.size() > 0) {
            // 已经关注过了，取关（删除该记录）
            likesMapper.deleteBatchByIds(likesList.stream()
                    .map(Likes::getId)
                    .collect(Collectors.toList()));
            // 更新内容信息
            if ("0".equals(likes.getType())) {
                // 笔记
                Note note = noteMapper.selectById(likes.getContentId());
                note.setLikes(note.getLikes() - 1);
                noteMapper.update(note);
            } else {
                // 视频
                Video video = videoMapper.selectById(likes.getContentId());
                video.setLikes(video.getLikes() - 1);
                videoMapper.update(video);
            }
        } else {
            // 未关注，关注（添加记录）
            likesMapper.insert(likes);
            // 更新内容信息
            if ("0".equals(likes.getType())) {
                // 笔记
                Note note = noteMapper.selectById(likes.getContentId());
                note.setLikes(note.getLikes() + 1);
                noteMapper.update(note);
            } else {
                // 视频
                Video video = videoMapper.selectById(likes.getContentId());
                video.setLikes(video.getLikes() + 1);
                videoMapper.update(video);
            }
        }
    }
}

