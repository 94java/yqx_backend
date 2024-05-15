package cc.jiusi.yqx.constant;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-04-28  16:54
 * @Description: 用户常量
 */
public interface UserConstant {
    String STATUS_OK = "1";
    String STATUS_NO = "0";

    //  region 登录注册

    /**
     * 用户登录态键
     */
    String LOGIN_TOKEN = "user:login:token:";

    /**
     * 用户快捷登录验证码
     */
    String LOGIN_CODE = "user:login:code:";

    /**
     * token 默认过期时间（单位 分钟）
     */
    Long LOGIN_TOKEN_EXPIRE = 30L;

    /**
     * token 请求头 key 值
     */
    String USER_TOKEN_HEADER = "Token";

    // endregion


    //  region 权限

    /**
     * 默认角色
     */
    String USER_ROLE = "USER";

    /**
     * 管理员角色
     */
    String ADMIN_ROLE = "ADMIN";


    String USER_VISITOR_KEY = "user:visitor:";

    /**
     * 访客记录 默认过期时间（单位 分钟）
     *  7 天 （过期后访客数仍有记录，只是查不到具体访客而已）
     */
    Long USER_VISITOR_EXPIRE = 60L * 24L * 7L;


    // endregion
}
