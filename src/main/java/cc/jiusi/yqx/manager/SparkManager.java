package cc.jiusi.yqx.manager;

import io.github.briqt.spark4j.SparkClient;
import io.github.briqt.spark4j.constant.SparkApiVersion;
import io.github.briqt.spark4j.model.SparkMessage;
import io.github.briqt.spark4j.model.SparkSyncChatResponse;
import io.github.briqt.spark4j.model.request.SparkRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class SparkManager {
    @Resource
    private SparkClient sparkClient;

    /**
     * AI生成问题的预设条件
     */
    public static final String PRECONDITION = "你是一个起名专家，接下来需要为一个在线学习社区系统的用户起昵称，接下来我会按照以下固定格式给你提供内容：\n" +
            "【起名】\n" +
            "当我发送上述内容时，请你按照以下指定格式生成内容（此外不要输出任何多余的开头、结尾、注释）\n" +
            "{用户昵称，不要生成任何多余的内容}\n" +
            "最终格式是：起名结果";

    /**
     * 向星火AI发送请求
     *
     * @param content
     * @return
     */
    public String sendMesToAIUseXingHuo(final String content) {
        // 消息列表，可以在此列表添加历史对话记录
        List<SparkMessage> messages = new ArrayList<>();
//        messages.add(SparkMessage.systemContent(PRECONDITION));
        messages.add(SparkMessage.userContent(content));
        // 构造请求
        SparkRequest sparkRequest = SparkRequest.builder()
                // 消息列表
                .messages(messages)
                // 模型回答的tokens的最大长度，非必传，默认为2048
                .maxTokens(2048)
                // 结果随机性，取值越高随机性越强，即相同的问题得到的不同答案的可能性越高，非必传，取值为[0,1]，默认为0.5
                .temperature(1D)
                // 指定请求版本
                .apiVersion(SparkApiVersion.V1_5)
                .build();
        // 同步调用
        SparkSyncChatResponse chatResponse = sparkClient.chatSync(sparkRequest);
        String responseContent = chatResponse.getContent();
        log.info("星火AI返回的结果{}", responseContent);
        return responseContent;
    }
}
