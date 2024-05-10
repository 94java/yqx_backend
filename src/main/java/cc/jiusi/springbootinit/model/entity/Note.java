package cc.jiusi.springbootinit.model.entity;

import java.util.Date;
import java.io.Serializable;

import cc.jiusi.springbootinit.annotation.AutoFill;
import cc.jiusi.springbootinit.annotation.AutoId;
import cc.jiusi.springbootinit.model.vo.UserVO;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-02 21:27:53
 * @Description: 笔记信息(Note)实体类
 */
@Data
@ApiModel(value = "Note", description = "笔记信息")
public class Note implements Serializable {
    private static final long serialVersionUID = -22075265205462483L;
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @AutoId
    private Long id;
    /**
     * 封面图片
     */
    @ApiModelProperty(value = "封面图片")
    private String coverImg;
    /**
     * 笔记标题
     */
    @ApiModelProperty(value = "笔记标题")
    private String title;
    /**
     * 笔记类型（0-原创 1-转载 2-翻译）
     */
    @ApiModelProperty(value = "笔记类型（0-原创 1-转载 2-翻译）")
    private String type;
    /**
     * 笔记内容
     */
    @ApiModelProperty(value = "笔记内容")
    private String content;
    /**
     * 笔记摘要
     */
    @ApiModelProperty(value = "笔记摘要")
    private String summary;
    /**
     * 阅读量
     */
    @ApiModelProperty(value = "阅读量")
    private Long views;
    /**
     * 点赞数
     */
    @ApiModelProperty(value = "点赞数")
    private Long likes;

    /**
     * 当前登录用户是否点赞
     */
    private boolean isLike;

    private Long comments;
    /**
     * 笔记状态（0-待审核 1-已发布 2-草稿）
     */
    @ApiModelProperty(value = "笔记状态（0-待审核 1-已发布 2-草稿）")
    private String status;
    /**
     * 笔记分类
     */
    @ApiModelProperty(value = "笔记分类")
    private Long categoryId;

    private Category category;

    /**
     * 笔记作者
     */
    @ApiModelProperty(value = "笔记作者")
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
     * 逻辑删除（0-已删除 1-未删除）
     */
    @ApiModelProperty(value = "逻辑删除（0-已删除 1-未删除）")
    private String delFlag;
}

