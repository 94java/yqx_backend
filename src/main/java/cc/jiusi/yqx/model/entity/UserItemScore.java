package cc.jiusi.yqx.model.entity;

import java.io.Serializable;

import cc.jiusi.yqx.annotation.AutoId;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-15 21:23:56
 * @Description: 用户笔记分值信息(UserItemScore)实体类
 */
@Data
@ApiModel(value = "UserItemScore", description = "用户笔记分值信息")
public class UserItemScore implements Serializable {
    private static final long serialVersionUID = 326842205306150008L;
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @AutoId
    private Long id;
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long userId;
    /**
     * 笔记id
     */
    @ApiModelProperty(value = "内容id")
    private Long itemId;
    /**
     * 类型 0-笔记 1-视频
     */
    @ApiModelProperty(value = "类型 0-笔记 1-视频")
    private String type;
    /**
     * 记录分值（浏览+1 点赞 + 3 评论 +5）
     */
    @ApiModelProperty(value = "记录分值（浏览+1 点赞 + 3 评论 +5）")
    private Double score;
}

