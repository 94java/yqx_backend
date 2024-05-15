package cc.jiusi.yqx.model.dto.category;

import java.util.Date;
import java.io.Serializable;

import cc.jiusi.yqx.common.PageRequest;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-02 17:00:08
 * @Description: 分类信息(Category)实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CategoryQueryRequest", description = "分类查询请求信息")
public class CategoryQueryRequest extends PageRequest implements Serializable {
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
     * 分类类型：0-笔记 1-视频 2-题库
     */
    @ApiModelProperty(value = "分类类型：0-笔记 1-视频 2-题库")
    private String type;
    /**
     * 状态：0-停用 1-正常
     */
    @ApiModelProperty(value = "状态：0-停用 1-正常")
    private String status;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}

