package cc.jiusi.yqx;

import cc.jiusi.yqx.common.WordType;
import cc.jiusi.yqx.utils.WordContext;
import cc.jiusi.yqx.utils.WordFilter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-04-28  16:54
 * @Description: 主类测试
 */
@SpringBootTest
class MainApplicationTests {

    /**
     * 词库上下文环境
     */
    private final WordContext context = new WordContext();
    private final WordFilter filter = new WordFilter(context);

    /**
     * 测试替换敏感词
     */
    @Test
    public void replace() {
        String text = "利于上游行业发展兼职的政策逐渐发布";
        System.out.println(filter.replace(text));   //利于上游行业发展的政策逐渐发布
        context.removeWord(Collections.singletonList("上游行业"), WordType.WHITE);
        System.out.println(filter.replace(text));   //利于上**业发展的政策逐渐发布
    }

    /**
     * 测试是否包含敏感词
     */
    @Test
    public void include() {
        String text = "利于上游行业发展的政兼职业策逐渐发布";
        System.out.println(filter.include(text));   //false
        context.removeWord(Collections.singletonList("上游行业"), WordType.WHITE);
        System.out.println(filter.include(text));   //true
    }

    /**
     * 获取敏感词数
     */
    @Test
    public void wordCount() {
        String text = "利于上游行业兼职发展的政兼容职业策兼职逐渐网购发布";
        System.out.println(filter.wordCount(text));   //0
        context.removeWord(Collections.singletonList("上游行业"), WordType.WHITE);
        System.out.println(filter.wordCount(text));   //1
    }

    /**
     * 获取敏感词列表
     */
    @Test
    public void wordList() {
        String text = "利于上游1行业发展的帮忙点下政策逐渐发布";
        System.out.println(filter.wordList(text));   //[]]
        context.removeWord(Collections.singletonList("上游行业"), WordType.WHITE);
        System.out.println(filter.wordList(text));   //[游行]
    }

}
