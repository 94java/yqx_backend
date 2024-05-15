package cc.jiusi.yqx.model.entity;

import java.io.Serializable;

import cc.jiusi.yqx.annotation.AutoId;
import cc.jiusi.yqx.common.PageRequest;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-05 11:11:20
 * @Description: 题目选项信息(Answer)实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Answer", description = "题目选项信息")
public class Answer extends PageRequest implements Serializable {
    private static final long serialVersionUID = -14594452953038436L;
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @AutoId
    private Long id;
    /**
     * 选项内容
     */
    @ApiModelProperty(value = "选项内容")
    private String content;
    /**
     * 选项图片
     */
    @ApiModelProperty(value = "选项图片")
    private String contentImg;
    /**
     * 是否正确选项 0-错误 1-正确
     */
    @ApiModelProperty(value = "是否正确选项 0-错误 1-正确")
    private String isRight;
    /**
     * 所属题目id
     */
    @ApiModelProperty(value = "所属题目id")
    private Long subjectId;
    /**
     * 删除标记 0-已删除 1-未删除
     */
    @ApiModelProperty(value = "删除标记 0-已删除 1-未删除")
    private String delFlag;
}

