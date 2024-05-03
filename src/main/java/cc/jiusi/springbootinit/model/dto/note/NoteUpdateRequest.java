package cc.jiusi.springbootinit.model.dto.note;

import java.io.Serializable;

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
@ApiModel(value = "NoteUpdateRequest", description = "笔记更新信息")
public class NoteUpdateRequest implements Serializable {
    private static final long serialVersionUID = -22075265205462483L;
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
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
     * 笔记状态（0-待审核 1-已发布 2-草稿）
     */
    @ApiModelProperty(value = "笔记状态（0-待审核 1-已发布 2-草稿）")
    private String status;
    /**
     * 笔记分类
     */
    @ApiModelProperty(value = "笔记分类")
    private Long categoryId;
}

