package cc.jiusi.yqx.service;

import cc.jiusi.yqx.common.DeleteRequest;
import cc.jiusi.yqx.common.StatusUpdateRequest;
import cc.jiusi.yqx.model.dto.user.*;
import cc.jiusi.yqx.model.entity.User;
import cc.jiusi.yqx.model.vo.UserVO;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-04-29 20:39:07
 * @Description: 用户信息(User)表服务接口
 */
public interface UserService {

    // region 登录注册

    /**
     * 通过用户名密码登录
     *
     * @param username 用户名
     * @param password 密码
     * @return token
     */
    String login(HttpServletRequest request, String username, String password);

    /**
     * 用户注册
     *
     * @param userRegisterRequest 用户注册请求
     */
    void register(UserRegisterRequest userRegisterRequest);

    /**
     * 根据邮箱快捷登录
     *
     * @param email 邮箱
     * @param code  验证码
     */
    String loginByEmail(HttpServletRequest request, String email, String code);

    /**
     * 发送邮箱验证码
     *
     * @param email 邮箱
     */
    void sendEmailCode(String email);

    /**
     * 退出登录
     *
     */
    void logout(String token);

    // endregion

    // region 增删改查

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    User queryById(Long id);

    /**
     * 通过条件查询所有数据
     *
     * @param userQueryRequest 查询条件
     * @return List<User> 实例对象列表
     */
    List<User> queryAll(UserQueryRequest userQueryRequest);

    /**
     * 通过条件查询分页数据
     *
     * @param userQueryRequest 查询条件
     * @return PageInfo<User> 分页信息对象
     */
    PageInfo<User> queryPage(UserQueryRequest userQueryRequest);

    /**
     * 根据条件统计总行数
     *
     * @param userQueryRequest 查询条件
     * @return 总行数
     */
    long queryCount(UserQueryRequest userQueryRequest);

    /**
     * 新增数据
     *
     * @param userAddRequest 实例对象
     * @return 实例对象
     */
    User insert(UserAddRequest userAddRequest);

    /**
     * 批量新增数据
     *
     * @param entities List<UserAddRequest> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(List<UserAddRequest> entities);

    /**
     * 修改数据
     *
     * @param userUpdateRequest 实例对象
     * @return 实例对象
     */
    User update(UserUpdateRequest userUpdateRequest);

    /**
     * 通过主键集合批量删除数据
     *
     * @param deleteRequest 删除请求对象
     * @return 影响行数
     */
    int deleteBatchByIds(DeleteRequest deleteRequest);

    /**
     * 通过主键集合批量修改状态
     *
     * @param statusUpdateRequest 状态变更请求对象
     */
    void changeStatus(StatusUpdateRequest statusUpdateRequest);

    List<UserVO> getActivityUser();

    List<UserVO> queryCurrentVistor();

    Integer queryRange(Long id);

    Map<String,Object> getStatistics();

    // endregion
}

