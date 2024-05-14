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
 * @CreateTime: 2024-05-14 18:41:57
 * @Description: 用户错题信息(Wrong)实体类
 */
@Data
@ApiModel(value = "Wrong", description = "用户错题信息")
public class Wrong extends PageRequest implements Serializable {
    private static final long serialVersionUID = 611649447799576433L;
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
     * 题目id
     */
    @ApiModelProperty(value = "题目id")
    private Long subjectId;
    /**
     * 错误选项id
     */
    @ApiModelProperty(value = "错误选项id")
    private Long answerId;
    /**
     * 错题时间
     */
    @ApiModelProperty(value = "错题时间")
    @AutoFill
    private Date createTime;
    /**
     * 错误次数
     */
    @ApiModelProperty(value = "错误次数")
    private Integer wrongCount;
}

