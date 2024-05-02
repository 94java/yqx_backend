package cc.jiusi.springbootinit.model.dto.category;

import java.util.Date;
import java.io.Serializable;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-02 17:00:08
 * @Description: 分类信息(Category)实体类
 */
@Data
@ApiModel(value = "CategoryUpdateRequest", description = "分类更新请求信息")
public class CategoryUpdateRequest implements Serializable {
    private static final long serialVersionUID = -12439815453089360L;
    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Long id;
    /**
     * 分类名
     */
    @ApiModelProperty(value = "分类名")
    private String name;
    /**
     * 分类类型：0-笔记 1-视频 2-题库 3-资源
     */
    @ApiModelProperty(value = "分类类型：0-笔记 1-视频 2-题库 3-资源")
    private String type;
    /**
     * 状态：0-停用 1-正常
     */
    @ApiModelProperty(value = "状态：0-停用 1-正常")
    private String status;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer order;
}

