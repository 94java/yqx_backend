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
 * @CreateTime: 2024-05-10 20:11:03
 * @Description: 用户关注信息(Follow)实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Follow", description = "用户关注信息")
public class Follow extends PageRequest implements Serializable {
    private static final long serialVersionUID = -95073945371744672L;
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @AutoId
    private Long id;
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long uid;
    /**
     * 被关注用户id
     */
    @ApiModelProperty(value = "被关注用户id")
    private Long refUid;
    /**
     * 关注时间
     */
    @ApiModelProperty(value = "关注时间")
    @AutoFill(type = AutoFill.AutoFillType.INSERT)
    private Date createTime;
    /**
     * 删除标记
     */
    @ApiModelProperty(value = "删除标记")
    private String delFlag;
}

