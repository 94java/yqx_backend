package cc.jiusi.yqx.model.entity;

import java.util.Date;
import java.io.Serializable;

import cc.jiusi.yqx.annotation.AutoFill;
import cc.jiusi.yqx.annotation.AutoId;
import cc.jiusi.yqx.common.PageRequest;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-10 21:27:28
 * @Description: 点赞信息(Likes)实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Likes", description = "点赞信息")
public class Likes extends PageRequest implements Serializable {
    private static final long serialVersionUID = 975553107689643405L;
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @AutoId
    private Long id;
    /**
     * 类型 0-笔记 1-视频 2-动态
     */
    @ApiModelProperty(value = "类型 0-笔记 1-视频 2-动态")
    private String type;
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long uid;
    /**
     * 内容id
     */
    @ApiModelProperty(value = "内容id")
    private Long contentId;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @AutoFill(type = AutoFill.AutoFillType.INSERT)
    private Date createTime;
    /**
     * 删除标记 0-已删除 1-未删除
     */
    @ApiModelProperty(value = "删除标记 0-已删除 1-未删除")
    private String delFlag;
}

