package cc.jiusi.springbootinit.model.dto.video;


import cc.jiusi.springbootinit.model.entity.Category;
import cc.jiusi.springbootinit.model.entity.User;
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
@ApiModel(value = "NoteAddRequest", description = "笔记新增请求")
public class VideoAddRequest implements Serializable {
    private static final long serialVersionUID = -22570870117449867L;
    /**
     * 视频封面
     */
    @ApiModelProperty(value = "视频封面")
    private String coverImg;
    /**
     * 视频标题
     */
    @ApiModelProperty(value = "视频标题")
    private String title;
    /**
     * 视频简介
     */
    @ApiModelProperty(value = "视频简介")
    private String summary;
    /**
     * 视频资源链接
     */
    @ApiModelProperty(value = "视频资源链接")
    private String url;
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
}

