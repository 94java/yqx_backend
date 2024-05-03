package cc.jiusi.springbootinit.model.dto.note;


import java.util.Date;
import java.io.Serializable;

import cc.jiusi.springbootinit.common.PageRequest;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-02 21:27:53
 * @Description: 笔记信息(Note)实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "NoteQueryRequest", description = "笔记查询信息")
public class NoteQueryRequest extends PageRequest implements Serializable {
    private static final long serialVersionUID = -22075265205462483L;
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;
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
     * 笔记状态（0-待审核 1-已发布 2-草稿）
     */
    @ApiModelProperty(value = "笔记状态（0-待审核 1-已发布 2-草稿）")
    private String status;
    /**
     * 笔记分类
     */
    @ApiModelProperty(value = "笔记分类")
    private Long categoryId;
    /**
     * 笔记作者
     */
    @ApiModelProperty(value = "笔记作者")
    private Long userId;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
}

