package cc.jiusi.springbootinit.model.entity;

import java.util.Date;
import java.io.Serializable;

import cc.jiusi.springbootinit.annotation.AutoFill;
import cc.jiusi.springbootinit.annotation.AutoId;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-03 20:16:02
 * @Description: 视频信息(Video)实体类
 */
@Data
@ApiModel(value = "Video", description = "视频信息")
public class Video implements Serializable {
    private static final long serialVersionUID = -22570870117449867L;
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @AutoId
    private Long id;
    /**
     * 视频封面
     */
    @ApiModelProperty(value = "视频封面")
    private String coverImg;
    /**
     * 视频标题
     */
    @ApiModelProperty(value = "视频标题")
    private String title;
    /**
     * 视频简介
     */
    @ApiModelProperty(value = "视频简介")
    private String summary;
    /**
     * 视频资源链接
     */
    @ApiModelProperty(value = "视频资源链接")
    private String url;
    /**
     * 播放量
     */
    @ApiModelProperty(value = "播放量")
    private Long views;
    /**
     * 点赞量
     */
    @ApiModelProperty(value = "点赞量")
    private Long likes;

    private boolean isLike;

    private Long comments;

    /**
     * 视频状态 0-待审核 1-已发布
     */
    @ApiModelProperty(value = "视频状态 0-待审核 1-已发布")
    private String status;
    /**
     * 视频类型 0-原创 2-搬运 3-剪辑 4-翻拍
     */
    @ApiModelProperty(value = "视频类型 0-原创 1-搬运 2-剪辑 3-翻拍")
    private String type;
    /**
     * 分类id
     */
    @ApiModelProperty(value = "分类id")
    private Long categoryId;

    private Category category;

    /**
     * 作者id
     */
    @ApiModelProperty(value = "作者id")
    private Long userId;

    private User user;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    @AutoFill(type = AutoFill.AutoFillType.INSERT,fieldType = AutoFill.FieldType.USER_ID)
    private Long createBy;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @AutoFill(type = AutoFill.AutoFillType.INSERT)
    private Date createTime;
    /**
     * 修改者
     */
    @ApiModelProperty(value = "修改者")
    @AutoFill(fieldType = AutoFill.FieldType.USER_ID)
    private Long updateBy;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @AutoFill
    private Date updateTime;
    /**
     * 逻辑删除 0-已删除 1-未删除
     */
    @ApiModelProperty(value = "逻辑删除 0-已删除 1-未删除")
    private String delFlag;
}

