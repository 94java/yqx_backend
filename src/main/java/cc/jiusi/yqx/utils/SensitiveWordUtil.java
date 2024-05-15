package cc.jiusi.yqx.utils;

public class SensitiveWordUtil {

    /**
     * 词库上下文环境
     */
    public static final WordContext CONTENT = new WordContext();

    public static final WordFilter WORD_FILTER = new WordFilter(CONTENT);

}
