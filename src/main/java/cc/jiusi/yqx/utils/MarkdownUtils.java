package cc.jiusi.yqx.utils;

public class MarkdownUtils {
    public static String parseMarkdownToPlainText(String markdown) {
        // 移除代码块
        markdown = markdown.replaceAll("```[\\s\\S]*?```", "");
        // 移除行内代码
        markdown = markdown.replaceAll("`.*?`", "");
        // 移除链接
        markdown = markdown.replaceAll("\\[([^\\]]*)\\]\\([^\\)]*\\)", "$1");
        // 移除图片
        markdown = markdown.replaceAll("!\\[([^\\]]*)\\]\\([^\\)]*\\)", "$1");
        // 移除其他Markdown语法标记
        markdown = markdown.replaceAll("[*#_\\-]", "");
        // 去除空行和多余的空格
        markdown = markdown.trim().replaceAll("\\s+", " ");
        return markdown;
    }
}
