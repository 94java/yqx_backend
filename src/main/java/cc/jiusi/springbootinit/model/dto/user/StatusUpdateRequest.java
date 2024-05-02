package cc.jiusi.springbootinit.model.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-02  15:39
 * @Description: 状态修改请求
 */
@Data
public class StatusUpdateRequest implements Serializable {
    private static final long serialVersionUID = 3191241716373120793L;

    /**
     * 变更主键集
     */
    @ApiModelProperty(value = "变更主键集")
    private List<Long> ids;
    /**
     * 变更值
     */
    @ApiModelProperty(value = "变更值")
    private String status;
}
