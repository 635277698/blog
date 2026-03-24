package com.jun.blog.common.untils;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

public class MarkdownUtils {
    private static final Parser parser = Parser.builder().build();
    private static final HtmlRenderer renderer = HtmlRenderer.builder().build();

    public static String renderToHtml(String markdown) {
        if (markdown == null || markdown.isEmpty()) {
            return "<p class='text-gray-500'>暂无内容</p>";
        }

        // 关键修复：处理数据库中存储的转义字符
        String processedMarkdown = unescapeMarkdown(markdown);

        Node document = parser.parse(processedMarkdown);
        String html = renderer.render(document);

        // 为代码块添加语言类（可选）
        html = addCodeLanguageClass(html);

        return html;
    }

    /**
     * 将数据库中的转义字符还原为真实字符
     */
    private static String unescapeMarkdown(String text) {
        if (text == null) {
            return null;
        }
        return text
                .replace("\\n", "\n")      // 换行符
                .replace("\\t", "\t")      // 制表符
                .replace("\\r", "\r")      // 回车符
                .replace("\\\"", "\"")     // 双引号
                .replace("\\'", "'")       // 单引号
                .replace("\\\\", "\\");    // 反斜杠
    }

    /**
     * 为代码块添加语言类，便于 highlight.js 识别
     */
    private static String addCodeLanguageClass(String html) {
        // 为 Java 代码块添加 language-java 类
        html = html.replace("<pre><code>java\n", "<pre><code class=\"language-java\">");
        html = html.replace("<pre><code>java\n", "<pre><code class=\"language-java\">");
        html = html.replace("<pre><code>", "<pre><code class=\"language-plaintext\">");
        return html;
    }

}
