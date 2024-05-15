package cc.jiusi.yqx.model.entity;

import java.util.Date;
import java.io.Serializable;

import cc.jiusi.yqx.annotation.AutoFill;
import cc.jiusi.yqx.annotation.AutoId;
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
@ApiModel(value = "Category", description = "分类信息")
public class Category implements Serializable {
    private static final long serialVersionUID = -12439815453089360L;
    /**
     * id
     */
    @ApiModelProperty(value = "id")
    @AutoId
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
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer order;
    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    @AutoFill(type = AutoFill.AutoFillType.INSERT,fieldType = AutoFill.FieldType.USER_ID)
    private Long createBy;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @AutoFill(type = AutoFill.AutoFillType.INSERT)
    private Date createTime;
    /**
     * 修改者
     */
    @ApiModelProperty(value = "修改者")
    @AutoFill(fieldType = AutoFill.FieldType.USER_ID)
    private Long updateBy;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @AutoFill
    private Date updateTime;
    /**
     * 删除标记 0-删除 1-未删除
     */
    @ApiModelProperty(value = "删除标记 0-删除 1-未删除")
    private String delFlag;
}

