package cc.jiusi.yqx.service.impl;

import cc.jiusi.yqx.common.DeleteRequest;
import cc.jiusi.yqx.mapper.NoteMapper;
import cc.jiusi.yqx.mapper.UserItemScoreMapper;
import cc.jiusi.yqx.mapper.VideoMapper;
import cc.jiusi.yqx.model.entity.Likes;
import cc.jiusi.yqx.mapper.LikesMapper;
import cc.jiusi.yqx.model.entity.Note;
import cc.jiusi.yqx.model.entity.UserItemScore;
import cc.jiusi.yqx.model.entity.Video;
import cc.jiusi.yqx.service.LikesService;
import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;

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
    @Resource
    private UserItemScoreMapper userItemScoreMapper;

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
    public Map<String,List> queryAll(Likes likes) {
        List<Likes> list = likesMapper.selectAll(likes);
        List<Note> noteList = new ArrayList<>();
        List<Video> videoList = new ArrayList<>();
        for (Likes item : list) {
            if("0".equals(item.getType())){
                // 笔记
                Note note = noteMapper.selectById(item.getContentId());
                noteList.add(note);
            }else if("1".equals(item.getType())){
                // 视频
                Video video = videoMapper.selectById(item.getContentId());
                videoList.add(video);
            }
        }
        HashMap<String, List> map = new HashMap<>();
        map.put("notes",noteList);
        map.put("videos",videoList);
        return map;
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
        // 更新操作分值
        UserItemScore userItemScore = new UserItemScore();
        userItemScore.setItemId(likes.getContentId());
        userItemScore.setUserId(likes.getUid());
        userItemScore.setType(likes.getType());
        List<UserItemScore> userItemScores = userItemScoreMapper.selectAll(userItemScore);

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
            } else if ("1".equals(likes.getType())){
                // 视频
                Video video = videoMapper.selectById(likes.getContentId());
                video.setLikes(video.getLikes() - 1);
                videoMapper.update(video);
            }
            if(CollUtil.isNotEmpty(userItemScores)){
                // 更新记录（取关，分值减3）
                UserItemScore item = userItemScores.get(0);
                item.setScore(item.getScore() - 3);
                userItemScoreMapper.update(item);
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
            } else if ("1".equals(likes.getType())){
                // 视频
                Video video = videoMapper.selectById(likes.getContentId());
                video.setLikes(video.getLikes() + 1);
                videoMapper.update(video);
            }
            if (CollUtil.isNotEmpty(userItemScores)) {
                // 存在，更新
                userItemScore = userItemScores.get(0);
                userItemScore.setScore(userItemScore.getScore() + 3);
                userItemScoreMapper.update(userItemScore);
            } else {
                // 不存在，添加记录
                userItemScore.setScore(3D);
                userItemScoreMapper.insert(userItemScore);
            }
        }
    }
}

