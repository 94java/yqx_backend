package cc.jiusi.springbootinit.common;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-04-28  16:54
 * @Description: 用户上下文对象
 */
public class UserContextHolder {
    private static final ThreadLocal<Long> USER_CONTEXT = new ThreadLocal<>();
    private static final ThreadLocal<String> USER_ROLE = new ThreadLocal<>();

    public static void setUserId(Long userId) {
        USER_CONTEXT.set(userId);
    }

    public static Long getUserId() {
        return USER_CONTEXT.get();
    }

    public static void setUserRole(String role) {
        USER_ROLE.set(role);
    }

    public static String getUserRole() {
        return USER_ROLE.get();
    }

    public static void clear() {
        USER_ROLE.remove();
        USER_CONTEXT.remove();
    }
}