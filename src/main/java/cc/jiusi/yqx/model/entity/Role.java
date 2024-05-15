package cc.jiusi.yqx.model.entity;

import java.util.Date;
import java.io.Serializable;

import cc.jiusi.yqx.annotation.AutoFill;
import cc.jiusi.yqx.annotation.AutoId;
import cc.jiusi.yqx.common.PageRequest;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-12 14:00:27
 * @Description: 角色信息(Role)实体类
 */
@Data
@ApiModel(value = "Role", description = "角色信息")
public class Role extends PageRequest implements Serializable {
    private static final long serialVersionUID = -78701147675370407L;
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @AutoId
    private Long id;
    /**
     * 角色名
     */
    @ApiModelProperty(value = "角色名")
    private String name;
    /**
     * 角色标识
     */
    @ApiModelProperty(value = "角色标识")
    private String roleTag;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @AutoFill(type = AutoFill.AutoFillType.INSERT)
    private Date createTime;
    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    @AutoFill(type = AutoFill.AutoFillType.INSERT,fieldType = AutoFill.FieldType.USER_ID)
    private Long createBy;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @AutoFill
    private Date updateTime;
    /**
     * 修改者
     */
    @ApiModelProperty(value = "修改者")
    @AutoFill(fieldType = AutoFill.FieldType.USER_ID)
    private Long updateBy;
    /**
     * 删除标记 0-已删除 1-未删除
     */
    @ApiModelProperty(value = "删除标记 0-已删除 1-未删除")
    private String delFlag;
}

