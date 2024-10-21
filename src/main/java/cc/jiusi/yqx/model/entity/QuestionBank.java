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
 * @CreateTime: 2024-05-04 17:16:02
 * @Description: 题库信息(QuestionBank)实体类
 */
@Data
@ApiModel(value = "QuestionBank", description = "题库信息")
public class QuestionBank implements Serializable {
    private static final long serialVersionUID = -60799118686942121L;
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @AutoId
    private Long id;
    /**
     * 题库名称
     */
    @ApiModelProperty(value = "题库名称")
    private String name;
    /**
     * 题库简介
     */
    @ApiModelProperty(value = "题库简介")
    private String summary;
    /**
     * 题库封面
     */
    @ApiModelProperty(value = "题库封面")
    private String coverImg;
    /**
     * 浏览量
     */
    @ApiModelProperty(value = "浏览量")
    private Long views;
    /**
     * 题库难度 0-简单 1-一般 2-困难
     */
    @ApiModelProperty(value = "题库难度 0-简单 1-一般 2-困难")
    private String difficulty;
    /**
     * 题库状态 0-停用 1-启用
     */
    @ApiModelProperty(value = "题库状态 0-停用 1-启用")
    private String status;
    /**
     * 分类id
     */
    @ApiModelProperty(value = "分类id")
    private Long categoryId;

    private Category category;

    private Long count;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    @AutoFill(fieldType = AutoFill.FieldType.USER_ID,type = AutoFill.AutoFillType.INSERT)
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
     * 删除标记 0-已删除 1-未删除
     */
    @ApiModelProperty(value = "删除标记 0-已删除 1-未删除")
    private String delFlag;
}

