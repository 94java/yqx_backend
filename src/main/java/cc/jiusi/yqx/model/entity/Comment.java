package cc.jiusi.yqx.model.entity;

import java.util.Date;
import java.io.Serializable;

import cc.jiusi.yqx.annotation.AutoFill;
import cc.jiusi.yqx.annotation.AutoId;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-10 13:55:20
 * @Description: 评论信息(Comment)实体类
 */
@Data
@ApiModel(value = "Comment", description = "评论信息")
public class Comment implements Serializable {
    private static final long serialVersionUID = -18715993750122917L;
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
    private Long uid;

    private User user;
    /**
     * 评论内容
     */
    @ApiModelProperty(value = "评论内容")
    private String content;
    /**
     * 父级id（二级评论使用）
     */
    @ApiModelProperty(value = "父级id（二级评论使用）")
    private Long parentId;
    /**
     * 评论类型 0-笔记 1-视频
     */
    @ApiModelProperty(value = "评论类型 0-笔记 1-视频")
    private String type;
    /**
     * 被评论内容id
     */
    @ApiModelProperty(value = "被评论内容id")
    private Long contentId;

    /**
     * 评论时间
     */
    @ApiModelProperty(value = "评论时间")
    @AutoFill(type = AutoFill.AutoFillType.INSERT)
    private Date createTime;
    /**
     * 删除标记 0-已删除 1-未删除
     */
    @ApiModelProperty(value = "删除标记 0-已删除 1-未删除")
    private String delFlag;

    private Integer pageNum;

    private Integer pageSize;
}

