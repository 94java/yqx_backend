package cc.jiusi.yqx.model.dto.subject;


import cc.jiusi.yqx.common.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-02 21:27:53
 * @Description: 笔记信息(Note)实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "NoteQueryRequest", description = "题目查询信息")
public class SubjectQueryRequest extends PageRequest implements Serializable {
    private static final long serialVersionUID = 916107855743065354L;
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 题目内容
     */
    @ApiModelProperty(value = "题目内容")
    private String content;
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
     * 题库id
     */
    @ApiModelProperty(value = "题库id")
    private Long bankId;

    /**
     * 模式 0-顺序 1-随机
     */
    @ApiModelProperty(value = "模式 0-顺序 1-随机")
    private String mode;
    /**
     * 题目状态 0-停用 1-正常
     */
    @ApiModelProperty(value = "题目状态 0-停用 1-正常")
    private String status;
    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private Long createBy;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}

