package cc.jiusi.yqx.manager;

import cc.jiusi.yqx.mapper.NoteMapper;
import cc.jiusi.yqx.mapper.UserItemScoreMapper;
import cc.jiusi.yqx.mapper.UserMapper;
import cc.jiusi.yqx.mapper.VideoMapper;
import cc.jiusi.yqx.model.entity.UserItemScore;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-16  17:50
 * @Description:
 */
@SpringBootTest
public class RecommendTest {
    @Resource
    private UserMapper userMapper;
    @Resource
    private NoteMapper noteMapper;
    @Resource
    private VideoMapper videoMapper;
    @Resource
    private UserItemScoreMapper userItemScoreMapper;
    @Test
    public void generateScores(){
        Random random = new Random();

        List<Long> userIds = userMapper.selectAll(null).stream().map(item -> item.getId())
                .collect(Collectors.toList());
        List<Long> videoIds = videoMapper.selectAll(null).stream().map(item -> item.getId())
                .collect(Collectors.toList());
        List<Long> noteIds = noteMapper.selectAll(null).stream().map(item -> item.getId())
                .collect(Collectors.toList());

        for (long userId : userIds) {
            // 模拟视频评分数据
            int ratingsCount = random.nextInt(videoIds.size()) + 1; // 每个用户至少对一个物品有评分
            for (int i = 0; i < ratingsCount; i++) {
                long itemId = videoIds.get(random.nextInt(videoIds.size()));
                float score = random.nextInt(9) + 1; // 生成1到9的评分
                UserItemScore userItemScore = new UserItemScore();
                userItemScore.setUserId(userId);
                userItemScore.setItemId(itemId);
                userItemScore.setScore((double) score);
                userItemScore.setType("1");
                userItemScoreMapper.insert(userItemScore);
            }

            // 模拟笔记评分数据
            int ratingsCount1 = random.nextInt(noteIds.size()) + 1; // 每个用户至少对一个物品有评分
            for (int i = 0; i < ratingsCount1; i++) {
                long itemId = noteIds.get(random.nextInt(noteIds.size()));
                float score = random.nextInt(9) + 1; // 生成1到9的评分
                UserItemScore userItemScore = new UserItemScore();
                userItemScore.setUserId(userId);
                userItemScore.setItemId(itemId);
                userItemScore.setScore((double) score);
                userItemScore.setType("0");
                userItemScoreMapper.insert(userItemScore);
            }
        }
        //生成结束
        System.out.println("生成结束");
    }

}
