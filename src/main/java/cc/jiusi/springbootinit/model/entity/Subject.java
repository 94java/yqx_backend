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
 * @CreateTime: 2024-05-05 08:52:14
 * @Description: 题目信息(Subject)实体类
 */
@Data
@ApiModel(value = "Subject", description = "题目信息")
public class Subject implements Serializable {
    private static final long serialVersionUID = 916107855743065354L;
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @AutoId
    private Long id;
    /**
     * 题目内容
     */
    @ApiModelProperty(value = "题目内容")
    private String content;
    /**
     * 题目图片
     */
    @ApiModelProperty(value = "题目图片")
    private String contentImg;
    /**
     * 题目类型 0-单选 1-多选 2-填空
     */
    @ApiModelProperty(value = "题目类型 0-单选 1-多选 2-填空")
    private String type;
    /**
     * 题目难度 0-简单 1-一般 2-困难
     */
    @ApiModelProperty(value = "题目难度 0-简单 1-一般 2-困难")
    private String difficulty;
    /**
     * 题目解析
     */
    @ApiModelProperty(value = "题目解析")
    private String analysis;
    /**
     * 题库id
     */
    @ApiModelProperty(value = "题库id")
    private Long bankId;

    private QuestionBank questionBank;

    /**
     * 题目状态 0-停用 1-正常
     */
    @ApiModelProperty(value = "题目状态 0-停用 1-正常")
    private String status;
    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    @AutoFill(type = AutoFill.AutoFillType.INSERT, fieldType = AutoFill.FieldType.USER_ID)
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
     * 删除标记 0-未删除 1-已删除
     */
    @ApiModelProperty(value = "删除标记 0-未删除 1-已删除")
    private String delFlag;
}

