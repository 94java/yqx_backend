package cc.jiusi.yqx.service.impl;

import cc.jiusi.yqx.common.DeleteRequest;
import cc.jiusi.yqx.common.ErrorCode;
import cc.jiusi.yqx.common.StatusUpdateRequest;
import cc.jiusi.yqx.common.UserContextHolder;
import cc.jiusi.yqx.exception.BusinessException;
import cc.jiusi.yqx.mapper.CommentMapper;
import cc.jiusi.yqx.mapper.LikesMapper;
import cc.jiusi.yqx.mapper.UserItemScoreMapper;
import cc.jiusi.yqx.model.dto.video.VideoAddRequest;
import cc.jiusi.yqx.model.dto.video.VideoQueryRequest;
import cc.jiusi.yqx.model.dto.video.VideoUpdateRequest;
import cc.jiusi.yqx.model.entity.*;
import cc.jiusi.yqx.mapper.VideoMapper;
import cc.jiusi.yqx.service.VideoService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericItemPreferenceArray;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import javax.annotation.Resource;

import cn.hutool.core.bean.BeanUtil;

import static cc.jiusi.yqx.constant.CommonConstant.VIEW_KEY;

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

    @Resource
    private LikesMapper likesMapper;
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private UserItemScoreMapper userItemScoreMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Video queryById(Long id) {
        Video video = videoMapper.selectById(id);
        // 获取点赞信息
        Long userId = UserContextHolder.getUserId();
        if (userId == null) {
            return video;
        }
        video = fillInfo(video, userId);
        // 记录浏览量信息
        // 从redis查询，判断是否存在
        Double score = stringRedisTemplate.opsForZSet().score(
                VIEW_KEY + "video:" + id, userId.toString());
        if(score == null || ((System.currentTimeMillis() - score) > 24 * 60 * 60 * 1000)){
            // 24小时内没访问过，访问数+1
            video.setViews(video.getViews() + 1);
            videoMapper.update(video);
            // redis 更新 score
            stringRedisTemplate.opsForZSet().add(
                    VIEW_KEY + "video:" + id, userId.toString(), System.currentTimeMillis());
            // 记录mahout（内容推荐使用）
            UserItemScore userItemScore = new UserItemScore();
            userItemScore.setUserId(userId);
            userItemScore.setItemId(id);
            userItemScore.setType("1");
            List<UserItemScore> userItemScores = userItemScoreMapper.selectAll(userItemScore);
            if(CollUtil.isNotEmpty(userItemScores)){
                // 存在，更新
                userItemScore = userItemScores.get(0);
                userItemScore.setScore(userItemScore.getScore() + 1);
                userItemScoreMapper.update(userItemScore);
            }else{
                // 不存在，添加记录
                userItemScore.setScore(1D);
                userItemScoreMapper.insert(userItemScore);
            }
        }
        return video;
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
        List<Video> list = videoMapper.selectAll(video);
        // 用户信息脱敏
        for (Video item : list) {
            item.setUser(getSafeUser(item.getUser()));
        }
        // 当前登录用户点赞信息
        Long userId = UserContextHolder.getUserId();
        if (userId == null) {
            return list;
        }
        return list.stream().map(item -> fillInfo(item, userId))
                .collect(Collectors.toList());
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
        // 用户信息脱敏
        for (Video item : videos) {
            item.setUser(getSafeUser(item.getUser()));
        }
        // 当前登录用户点赞信息
        Long userId = UserContextHolder.getUserId();
        if (userId == null) {
            return new PageInfo<>(videos);
        }
        List<Video> list = videos.stream().map(item -> fillInfo(item, userId))
                .collect(Collectors.toList());
        return new PageInfo<>(list);
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
        // 设置用户id
        video.setUserId(UserContextHolder.getUserId());
        // 设置默认点赞量和阅读量
        video.setViews(0L);
        video.setLikes(0L);
        // 数据插入
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
                .map(item -> {
                    Video video = BeanUtil.copyProperties(item, Video.class);
                    // 设置用户id
                    video.setUserId(UserContextHolder.getUserId());
                    // 设置默认点赞量和阅读量
                    video.setViews(0L);
                    video.setLikes(0L);
                    return video;
                })
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

    @Override
    public void changeStatus(StatusUpdateRequest statusUpdateRequest) {
        videoMapper.updateStatus(statusUpdateRequest.getIds(), statusUpdateRequest.getStatus());
    }


    /**
     * 获取轮播图
     *
     * @return video集合
     */
    @Override
    public List<Video> getListOrderByViews(int start, int end) {
        List<Video> videos = videoMapper.selectAllOrderByViews();
        videos.forEach(item -> {
            item.setUser(getSafeUser(item.getUser()));
        });
        // 当前登录用户点赞信息
        Long userId = UserContextHolder.getUserId();
        if (userId != null) {
            videos = videos.stream().map(item -> fillInfo(item, userId))
                    .collect(Collectors.toList());
        }
        // 获取前4条数据
        if (videos.size() > start) {
            end = Math.min(end, videos.size());
            return videos.subList(start, end);
        }
        return videos;
    }

    @Override
    public List<Video> getVideoByItemCF(Long videoId) {
        Long userId = UserContextHolder.getUserId();
        if (userId == null) {
            // 用户未登录
            return new ArrayList<>();
        }
        // 获取视频信息的分值记录
        UserItemScore userItemScore = new UserItemScore();
        userItemScore.setType("1");
        List<UserItemScore> scores = userItemScoreMapper.selectAll(userItemScore);
        // 创建数据模型
        DataModel dataModel = createDataModel(scores);
        List<Video> list = new ArrayList<>();
        try {
            // 获取物品相似度
            ItemSimilarity similarity = new UncenteredCosineSimilarity(dataModel);
            // 构建基于物品的推荐器
            GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(dataModel, similarity);
            // 推荐内容（5个）
            // 给当前登录用户推荐8个与当前视频最相关的视频
            List<RecommendedItem> recommendedItems = recommender.recommendedBecause(
                    userId, videoId, 8);
            List<Long> itemIds = recommendedItems.stream().map(RecommendedItem::getItemID).collect(Collectors.toList());
            // 获取视频信息
            for (Long id : itemIds) {
                Video video = videoMapper.selectById(id);
                video.setUser(getSafeUser(video.getUser()));
                list.add(fillInfo(video, userId));
            }
        } catch (TasteException e) {
            e.printStackTrace();
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "内容推荐失败");
        }
        return list;
    }

    private DataModel createDataModel(List<UserItemScore> scores) {
        FastByIDMap<PreferenceArray> fastByIDMap = new FastByIDMap<>();
        Map<Long, List<UserItemScore>> map =
                scores.stream().collect(Collectors.groupingBy(UserItemScore::getItemId));
        Collection<List<UserItemScore>> list = map.values();
        for (List<UserItemScore> score : list) {
            GenericPreference[] array = new GenericPreference[score.size()];
            for (int i = 0; i < score.size(); i++) {
                UserItemScore userItemScore = score.get(i);
                GenericPreference item = new GenericPreference(
                        userItemScore.getUserId(),
                        userItemScore.getItemId(),
                        userItemScore.getScore().floatValue());
                array[i] = item;
            }
            fastByIDMap.put(array[0].getItemID(), new GenericItemPreferenceArray(Arrays.asList(array)));
        }
        return new GenericDataModel(fastByIDMap);
    }

    private User getSafeUser(User user) {
        if (user == null) {
            return user;
        }
        String email = user.getEmail();
        if (StrUtil.isNotBlank(user.getPhone())) {
            user.setPhone(StrUtil.hide(user.getPhone(), 3, 8));
        }
        if (StrUtil.isNotBlank(email)) {
            user.setEmail(StrUtil.hide(email, 2, email.indexOf("@") - 2));
        }
        return BeanUtil.copyProperties(user, User.class, "password");
    }


    private Video fillInfo(Video video, Long userId) {
        // 填充点赞数
        Long videoId = video.getId();
        Likes likes = new Likes();
        likes.setType("1");
        likes.setUid(userId);
        likes.setContentId(videoId);
        long count = likesMapper.count(likes);
        if (count > 0) {
            // 当前登录用户点赞过
            video.setLike(true);
        }
        // 填充评论数
        return fillCommentNums(video);
    }

    private Video fillCommentNums(Video video) {
        Comment comment = new Comment();
        comment.setType("1");
        comment.setContentId(video.getId());
        long count = commentMapper.count(comment);
        video.setComments(count);
        return video;
    }
}

