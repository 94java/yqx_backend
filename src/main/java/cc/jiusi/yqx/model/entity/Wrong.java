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
     * 题库id
     */
    @ApiModelProperty(value = "题库id")
    private Long bankId;
    private QuestionBank bank;
    private Integer count;
    /**
     * 题目id
     */
    @ApiModelProperty(value = "题目id")
    private Long subjectId;

    private Subject subject;

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

