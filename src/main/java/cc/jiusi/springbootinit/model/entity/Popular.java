package cc.jiusi.springbootinit.model.entity;

import java.util.Date;
import java.io.Serializable;

import cc.jiusi.springbootinit.annotation.AutoFill;
import cc.jiusi.springbootinit.annotation.AutoId;
import cc.jiusi.springbootinit.common.PageRequest;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-12 16:05:54
 * @Description: 动态信息(Popular)实体类
 */
@Data
@ApiModel(value = "Popular", description = "动态信息")
public class Popular extends PageRequest implements Serializable {
    private static final long serialVersionUID = 411325166131603164L;
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @AutoId
    private Long id;
    /**
     * 动态内容
     */
    @ApiModelProperty(value = "动态内容")
    private String content;
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
}

