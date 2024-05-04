package cc.jiusi.springbootinit.model.dto.questionBank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-02 21:27:53
 * @Description: 笔记信息(Note)实体类
 */
@Data
@ApiModel(value = "NoteUpdateRequest", description = "笔记更新信息")
public class QuestionBankUpdateRequest implements Serializable {
    private static final long serialVersionUID = -60799118686942121L;
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
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
}

