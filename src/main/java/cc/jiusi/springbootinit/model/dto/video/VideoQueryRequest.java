package cc.jiusi.springbootinit.model.dto.video;


import cc.jiusi.springbootinit.common.PageRequest;
import cc.jiusi.springbootinit.model.entity.Category;
import cc.jiusi.springbootinit.model.entity.User;
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
@ApiModel(value = "NoteQueryRequest", description = "笔记查询信息")
public class VideoQueryRequest extends PageRequest implements Serializable {
    private static final long serialVersionUID = -22570870117449867L;
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 视频标题
     */
    @ApiModelProperty(value = "视频标题")
    private String title;
    /**
     * 视频摘要
     */
    @ApiModelProperty(value = "视频摘要")
    private String summary;
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

    /**
     * 作者id
     */
    @ApiModelProperty(value = "作者id")
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

