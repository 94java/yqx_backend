package cc.jiusi.yqx.manager;

import cc.jiusi.yqx.common.WordType;
import cc.jiusi.yqx.constant.UserConstant;
import cc.jiusi.yqx.mapper.CategoryMapper;
import cc.jiusi.yqx.mapper.NoteMapper;
import cc.jiusi.yqx.mapper.UserMapper;
import cc.jiusi.yqx.mapper.VideoMapper;
import cc.jiusi.yqx.model.entity.Category;
import cc.jiusi.yqx.model.entity.Note;
import cc.jiusi.yqx.model.entity.User;
import cc.jiusi.yqx.model.entity.Video;
import cc.jiusi.yqx.utils.MarkdownUtils;
import cc.jiusi.yqx.utils.WordContext;
import cc.jiusi.yqx.utils.WordFilter;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-04-28  16:54
 * @Description: 主类测试
 */
@SpringBootTest
class XingHuoTests {

    @Resource
    private SparkManager sparkManager;
    @Resource
    private UserMapper userMapper;
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private NoteMapper noteMapper;
    @Resource
    private VideoMapper videoMapper;

    /**
     * 生成20个用户模拟数据
     */
    @Test
    public void generateUser() throws InterruptedException {
        List<User> users = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            User user = new User();
            user.setStatus("1");
            String nickname = sparkManager.sendMesToAIUseXingHuo(
                    "请帮我起一个昵称（2-4字中文），用于一个在线学习社区系统（我是其中的用户，创作者，不强制一定要和学习有关），不要生成任何无关内容，只需要给我最终生成的昵称即可（可以是有诗意的、幽默的、阳光的、可爱的等等），注意：你的回答只要昵称，不要除昵称外的任何其他文字，也不需要任何解释说明！只生成最终起名结果即可，你的回答只能是2-4字的昵称，不要其他任何内容和说明"
            );
            while(nickname.length() > 4){
                nickname = sparkManager.sendMesToAIUseXingHuo(
                        "请帮我起一个昵称（2-4字中文），用于一个在线学习社区系统（我是其中的用户，创作者，不强制一定要和学习有关），不要生成任何无关内容，只需要给我最终生成的昵称即可（可以是有诗意的、幽默的、阳光的、可爱的等等），注意：你的回答只要昵称，不要除昵称外的任何其他文字，也不需要任何解释说明！只生成最终起名结果即可，你的回答只能是2-4字的昵称，不要其他任何内容和说明"
                );
                Thread.sleep(500);
            }
            user.setNickname(nickname);
            // 随机用户名
            user.setUsername(RandomUtil.randomString(6));
            // 随机密码
            user.setPassword(DigestUtil.md5Hex("jiusi66" + nickname + "12345678"));
            user.setVisitorCount(0L);
            user.setRole(UserConstant.USER_ROLE);
            // 随机性别
            user.setSex(RandomUtil.randomEle(Arrays.asList("1", "0")));
            // 随机年龄
            user.setAge(RandomUtil.randomInt(10,50));

            users.add(user);
            Thread.sleep(500);
        }
        userMapper.insertBatch(users);
        System.out.println("生成完成!");
    }

    /**
     * 生成20个笔记模拟数据
     */
    @Test
    public void generateNote() throws InterruptedException {
        // 获取分类
        Category category = new Category();
        category.setType("0");
        List<Category> categories = categoryMapper.selectAll(category);
        // 获取用户
        List<User> userList = userMapper.selectAll(null);

        for(int i = 0; i < 50; i++){
            Category cate = categories.get(RandomUtil.randomInt(0, categories.size() - 1));
            User user = userList.get(RandomUtil.randomInt(0, userList.size() - 1));

            Note note = new Note();
            note.setStatus("1");
            note.setUserId(user.getId());
            note.setCategoryId(cate.getId());
            note.setLikes(0L);
            note.setViews(0L);
            note.setType(RandomUtil.randomEle(Arrays.asList("1", "0","2")));
            note.setCoverImg("http://images.jiusi.cc/yqx/552e91e80e88490c898447f3bf48de6b");
            // 生成内容
            String content = sparkManager.sendMesToAIUseXingHuo(
                    "我现在开发了一个社区系统，是面向在校学生的（大学生为主），有一个笔记模块，现在缺少数据，请你帮我生成一篇500字左右的文章（要markdown，字数不统计markdown语法，纯内容，内容和"+cate.getName()+"相关，是属于该分了下的），明白我的需求后直接给出生成的内容，不需要任何和内容无关的信息"
            );
            note.setTitle(content.trim().substring(0,10));
            note.setSummary(content.trim().substring(0,225));
            note.setContent(content);
            Thread.sleep(1000);
            noteMapper.insert(note);
            System.out.println("=========已插入【" + i + 1 +"】条数据=================");
        }

    }

    /**
     * 生成20个视频模拟数据
     */
    @Test
    public void generateVideo() throws InterruptedException {
        // 获取分类
        Category category = new Category();
        category.setType("1");
        List<Category> categories = categoryMapper.selectAll(category);
        // 获取用户
        List<User> userList = userMapper.selectAll(null);
        List<String> imgList = Arrays.asList(
                "c5d85d46b05ccf9d1979a06eb20520eebf9667a6.jpg",
                "43ae52e56bbd5b1b7a0dc1fdad92131cdb5a9133.jpg",
                "e02dfc404b64690adc71379b5c70fc597fa57a1b.jpg",
                "e2eabea4339e7d51892f40dd2ddee341bfb177f0.jpg",
                "99e2edda64a50b67a8d6fc07dd9d8c0a977c8319.jpg",
                "73d374264b90342d2a0f1d65de8f2c06d4d02c4e.jpg",
                "9a25d6be3c12ec9f1b56ac5c1d3a158d5f1d796d.jpg",
                "8413c9d3fc16a92185db304884ed4b2bbe1f77f6.jpg",
                "6de7124346361a3ad6bb680b45f1bacbdb3b0756.jpg",
                "d7279a0200c3f67cd6cc16de8e15e1283013a0ce.jpg",
                "a3b587dbaaff48998a2df7e749d373ae27d344f0.jpg",
                "f38e78ad7bbd1a2d97d46e0cdf6b16ce1b1e2e4a.jpg",
                "db6b697c69b2d9ab1b99112b1b081ef82ef0a698.jpg",
                "90c659992828a0d9d7a646dd352b0c3d4be9e908.jpg",
                "0f2985cb7976d21eaf3d2a876dd96bfc6fe3dd3b.jpg",
                "fec13cb99652f7b21d9cbbffa4f7c7e78934f27f.jpg",
                "e0f993aabdd85088136966c773aeae1c45adbc82.png",
                "eda8a89ca16fb755a67a2af34d6a8fe6c6b1b8c7.jpg");
        List<String> titleList = Arrays.asList(
                "最喜欢霉霉的《love story》！",
                "结束一段让人窒息的感情，假期结束，下一站成都",
                "工作不干了，回到家乡小县城，睡醒就去买冰淇淋，真的好快乐",
                "谁给我报的名儿！",
                "长期带耳机有什么危害？快转发给你爱用耳机的朋友！",
                "网络迷踪，地理推理侦探挑战赛，飞机上拍的机场",
                "这里发现大量石油！然后呢？",
                "老道长解读老子的三宝，通俗易懂，听完一下就明白了",
                "有趣的知识问答！",
                "假如海平面上升50米",
                "戴高乐，“法国最后一个男人”，为何拯救了法国两次的他，又被法国人民抛弃了两次？他是如何让一个在二战开始仅39天后就投降的国家，在战后位列联合国五常？",
                "生源都不足了，还要高价抢学区房吗？",
                "小论文题觉醒（S1E1）：结构法秒杀“变与不变”【历史老师定哥】",
                "居然还可以自动添加边框，厉害了",
                "动画",
                "【青年艺术家指导】艺术家思想发展指南",
                "blender 雕刻小激发，短平快学会",
                "【MG动画】行业最全MG动画素材包，动画制作从未如此简单!");
        List<String> summaryList = Arrays.asList(
                "音视频分开录制",
                "-",
                "-",
                "谁踏马给我投的稿！？\n" +
                        "\n" +
                        "BGM：《阳光男孩阳光女孩》",
                "-",
                "网络迷踪，地理推理侦探挑战赛，飞机上拍的机场",
                "-",
                "老道长解读老子的三宝，通俗易懂，听完一下就明白了",
                "-",
                "-",
                "-",
                "生源都不足了，还要高价抢学区房吗？",
                "想听小论文？定哥满足你，小论文题觉醒第一季正式来袭！第一集，定哥用结构法带你搞定小论文题！\n" +
                        "(2021湖南卷）（12分）阅读材料，完成下列要求。\n" +
                        "材料 从秦汉至晚清的二千多年间，中国的政治、经济、文化、社会等领域各有大致不变的方面，但就总的趋势而言，这些方面又并非没有变化。例如，男耕女织的小农经济维持不变，但土地私有日益发展，农业生产水平不断提高。这些变化是平和的、渐进的和累积的，从变化迅速的现时代或短时段的眼光看，社会不免呈“停滞”或“缓进”之象，但它的内部实在又酝酿并产生了相当的变化。\n" +
                        "——摘编自何怀宏《选举社会及其终结》\n" +
                        "结合所学知识，就中国古代历史某一方面的“变与不变”加以阐述。（要求：自拟标题，史论结合，论证充分，逻辑严密，阐述时不能抄写材料所举事例）",
                "-",
                "-",
                "亲身经历，经验分享！欢迎交流～",
                "https://www.youtube.com/watch?v=azM_ZlToG-M\n" +
                        "Blender Q群 ：1007322491\n" +
                        "qq 频道加入：微博首页扫码！！！https://weibo.com/1737479295\n" +
                        "群主的A站：https://lujiabin.artstation.com/\n" +
                        "喜欢blender游戏概念设计，可以加群讨论，大佬与小萌新的乐园。插件整理齐齐的，不用买了。白嫖有助于健康，在这可以大量节约学习时间。",
                "-");
        List<String> urlList = Arrays.asList(
                "SnapAny_5.mp4",
                "SnapAny_6.mp4",
                "SnapAny_7.mp4",
                "SnapAny_8.mp4",
                "1543894633-1-192.mp4",
                "SnapAny_9.mp4",
                "1524238784-1-192.mp4",
                "SnapAny_10.mp4",
                "SnapAny_11.mp4",
                "SnapAny_12.mp4",
                "SnapAny_13.mp4",
                "SnapAny_14.mp4",
                "SnapAny_15.mp4",
                "SnapAny_16.mp4",
                "SnapAny_17.mp4",
                "SnapAny_18.mp4",
                "SnapAny_19.mp4",
                "SnapAny_20.mp4");

        for(int i = 0; i < 18; i++){
            Category cate = categories.get(RandomUtil.randomInt(0, categories.size()));
            User user = userList.get(RandomUtil.randomInt(0, userList.size()));

            Video video = new Video();
            video.setStatus("1");
            video.setUserId(user.getId());
            video.setCreateBy(user.getId());
            video.setUpdateBy(user.getId());
            video.setCategoryId(cate.getId());
            video.setLikes(0L);
            video.setViews(0L);
            video.setType(RandomUtil.randomEle(Arrays.asList("1", "0","2","3")));
            video.setCoverImg("http://images.jiusi.cc/yqx/" + imgList.get(i));
            // 生成内容
            video.setTitle(titleList.get(i));
            video.setSummary(summaryList.get(i));
            video.setUrl("http://images.jiusi.cc/yqx/" + urlList.get(i));
            Thread.sleep(100);
            videoMapper.insert(video);
            System.out.println("=========已插入【" + (i + 1) +"】条数据==============");
        }

    }


    /**
     * 修改笔记信息
     */
    @Test
    public void updateNote() {
        List<Note> noteList = noteMapper.selectAll(null);
        for (Note note : noteList) {
            String content1 = note.getContent();
            int indexOf = content1.indexOf("#");
            if(indexOf == -1){
                // 不存在标题，不修改
            }else{
                int endIndex = content1.indexOf("\n\n");
                note.setTitle(content1.substring(indexOf + 1, endIndex).trim());
            }
            // 修改摘要
            // markdown格式只要文本（前端传递默认是markdown而非html）
            String content = note.getContent();
            int end = Math.min(content.length(), 256);
            note.setSummary(MarkdownUtils.parseMarkdownToPlainText(content).substring(0, end));
            note.setCreateBy(note.getUserId());
            noteMapper.update(note);
            // 更新完成
            System.out.println("======更新完成=======");
        }
    }

    /**
     * 修改笔记封面
     */
    @Test
    public void updateNoteCover() throws IOException {
        // 指定文件夹路径
        String directoryPath = "D:\\tmp";

        // 创建File对象
        File directory = new File(directoryPath);

        // 检查文件夹是否存在且是否是一个目录
        if (directory.exists() && directory.isDirectory()) {
            // 获取文件夹下所有文件的名称
            String[] fileNames = directory.list();
            // 获取笔记列表
            List<Note> noteList = noteMapper.selectAll(null);
            for (int i = 0; i < noteList.size(); i++) {
                Note note = noteList.get(i);
                note.setCoverImg("http://images.jiusi.cc/yqx/" + fileNames[i]);
                noteMapper.update(note);
            }
            System.out.println("更新成功");
        } else {
            System.out.println("The specified path is not a directory or does not exist.");
        }
    }

    /**
     * 发送消息
     */
    @Test
    public void sendMsg() {
        String result = sparkManager.sendMesToAIUseXingHuo(
                "我现在开发了一个社区系统，是面向在校学生的（大学生为主），有一个笔记模块，现在缺少数据，请你帮我生成一篇200字左右的文章（要markdown，字数不统计markdown语法，纯内容，内容和读书笔记相关），明白我的需求后直接给出生成的内容，不需要任何和内容无关的信息"
        );
        System.out.println(result);
    }


}
