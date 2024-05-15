package cc.jiusi.yqx.model.dto.subject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-02 21:27:53
 * @Description: 笔记信息(Note)实体类
 */
@Data
@ApiModel(value = "NoteUpdateRequest", description = "题目更新信息")
public class SubjectUpdateRequest implements Serializable {
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

    /**
     * 题目状态 0-停用 1-正常
     */
    @ApiModelProperty(value = "题目状态 0-停用 1-正常")
    private String status;
}

