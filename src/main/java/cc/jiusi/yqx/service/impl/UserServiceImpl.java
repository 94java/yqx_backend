package cc.jiusi.yqx.service.impl;

import cc.jiusi.yqx.common.DeleteRequest;
import cc.jiusi.yqx.common.ErrorCode;
import cc.jiusi.yqx.common.StatusUpdateRequest;
import cc.jiusi.yqx.common.UserContextHolder;
import cc.jiusi.yqx.constant.UserConstant;
import cc.jiusi.yqx.exception.BusinessException;
import cc.jiusi.yqx.mapper.*;
import cc.jiusi.yqx.model.dto.user.*;
import cc.jiusi.yqx.model.entity.*;
import cc.jiusi.yqx.model.vo.UserVO;
import cc.jiusi.yqx.service.UserService;
import cc.jiusi.yqx.utils.NetUtils;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.github.biezhi.ome.OhMyEmail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.bean.BeanUtil;

import static io.github.biezhi.ome.OhMyEmail.SMTP_QQ;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-04-29 20:39:07
 * @Description: 用户信息(User)表服务实现类
 */
@Service("userService")
@Slf4j
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private NoteMapper noteMapper;
    @Resource
    private VideoMapper videoMapper;
    @Resource
    private FollowMapper followMapper;
    @Resource
    private PopularMapper popularMapper;
    @Resource
    private CommentMapper commentMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Value("${email.username}")
    private String emailUsername;

    @Value("${email.password}")
    private String emailPwd;

    // region 登录注册

    /**
     * 盐值，混淆密码
     */
    private static final String SALT = "jiusi66";

    /**
     * 用户注册
     *
     * @param userRegisterRequest 用户注册请求对象
     */
    @Override
    public void register(UserRegisterRequest userRegisterRequest) {
        // 查询用户名是否存在
        String username = userRegisterRequest.getUsername();
        User userQuery = new User();
        userQuery.setUsername(username);
        List<User> users = userMapper.selectAll(userQuery);
        if (CollUtil.isNotEmpty(users)) {
            // 用户名已存在
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名已存在");
        }
        // 用户名不存在，则进行注册
        // 密码加密存储
        String password = userRegisterRequest.getPassword();
        userRegisterRequest.setPassword(DigestUtil.md5Hex(SALT + password));
        // 插入数据
        User user = BeanUtil.copyProperties(userRegisterRequest, User.class);
        // 设置初始角色
        user.setRole(UserConstant.USER_ROLE);
        // 设置初始状态
        user.setStatus(UserConstant.STATUS_OK);
        userMapper.insert(user);
    }


    /**
     * 根据邮箱快捷登录
     *
     * @param email 邮箱
     * @param code  验证码
     */
    @Override
    public String loginByEmail(HttpServletRequest request, String email, String code) {
        // 判断验证码是否正确
        String codeRedis = stringRedisTemplate.opsForValue().get(UserConstant.LOGIN_CODE + email);
        if (!StrUtil.equals(codeRedis, code)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "验证码不正确");
        }
        // 查询邮箱是否存在
        User user = new User();
        user.setEmail(email);
        List<User> users = userMapper.selectAll(user);
        if (CollUtil.isNotEmpty(users)) {
            // 存在用户，直接登录
            // 因为 username 唯一，所以查到的一定是只有一个
            user = users.get(0);
            // 判断用户状态是否正常
            if (UserConstant.STATUS_NO.equals(user.getStatus())) {
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "用户已被封禁");
            }
        } else {
            // 用户不存在，自动注册
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setUsername("email_" + RandomUtil.randomNumbers(10));
            newUser.setPassword(DigestUtil.md5Hex(SALT + RandomUtil.randomString(10)));
            newUser.setStatus(UserConstant.STATUS_OK);
            // 设置初始角色
            newUser.setRole(UserConstant.USER_ROLE);
            userMapper.insert(newUser);
            user = newUser;
        }
        // 生成 token（Redis存储UUID-用户id）
        String token = UUID.randomUUID().toString(true);
        stringRedisTemplate.opsForValue().set(UserConstant.LOGIN_TOKEN + token, user.getId().toString());
        // token 默认 30 分钟过期时间
        stringRedisTemplate.expire(UserConstant.LOGIN_TOKEN + token, UserConstant.LOGIN_TOKEN_EXPIRE, TimeUnit.MINUTES);

        // 更新用户登录记录
        String ip = NetUtils.getIpAddress(request);
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setLastLoginIp(ip);
        updateUser.setLastLoginTime(new Date());
        // 更新
        userMapper.update(user);
        return token;
    }

    /**
     * 发送邮箱验证码
     *
     * @param email 邮箱
     */
    @Override
    public void sendEmailCode(String email) {
        // 生成验证码
        String code = RandomUtil.randomNumbers(4);
        // 发送邮箱验证码
        OhMyEmail.config(SMTP_QQ(false), emailUsername, emailPwd);
        try {
            OhMyEmail.subject("【易启学】登录验证码")
                    .from("易启学")
                    .to(email)
                    .html("感谢您使用易启学系统，您的验证码为：<strong>" + code + "</strong>，有效期为5分钟，请勿告知他人~")
                    .send();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "验证码发送失败");
        }
        // 存入Redis，5分钟有效
        stringRedisTemplate.opsForValue().set(UserConstant.LOGIN_CODE + email, code, 5L, TimeUnit.MINUTES);
        log.info("邮箱验证码为：{}", code);
    }

    /**
     * 退出登录
     */
    @Override
    public void logout(String token) {
        // 删除 token
        stringRedisTemplate.delete(UserConstant.LOGIN_TOKEN + token);
        UserContextHolder.clear();
    }

    /**
     * 通过账号密码登录
     *
     * @param username 用户名
     * @param password 密码
     * @return token
     */
    @Override
    public String login(HttpServletRequest request, String username, String password) {
        // 密码加密
        String encryptPassword = DigestUtil.md5Hex(SALT + password);
        // 构建查询对象
        User user = new User();
        user.setUsername(username);
        user.setPassword(encryptPassword);
        // 根据用户名密码查询
        List<User> users = userMapper.selectAll(user);
        if (CollUtil.isEmpty(users)) {
            // 用户未查到---账号或密码不正确
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在或密码错误");
        }
        // 因为 username 唯一，所以查到的一定是只有一个
        user = users.get(0);
        // 判断用户状态是否正常
        if (UserConstant.STATUS_NO.equals(user.getStatus())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户已被封禁");
        }
        // 生成 token（Redis存储UUID-用户id）
        String token = UUID.randomUUID().toString(true);
        stringRedisTemplate.opsForValue().set(UserConstant.LOGIN_TOKEN + token, user.getId().toString());
        // token 默认 30 分钟过期时间
        stringRedisTemplate.expire(UserConstant.LOGIN_TOKEN + token, UserConstant.LOGIN_TOKEN_EXPIRE, TimeUnit.MINUTES);
        // 更新用户登录记录
        String ip = NetUtils.getIpAddress(request);
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setLastLoginIp(ip);
        updateUser.setLastLoginTime(new Date());
        // 更新
        userMapper.update(updateUser);

        return token;
    }
    // endregion

    // region 增删改查

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public User queryById(Long id) {
        if (UserContextHolder.getUserId() == null) {
            // 当前未登录
            return userMapper.selectById(id);
        }
        // 记录访客信息
        if (UserConstant.ADMIN_ROLE.equals(UserContextHolder.getUserRole()) ||
                UserContextHolder.getUserId().equals(id)) {
            // 管理员或者自己访问自己不需要记录
            return userMapper.selectById(id);
        }
        // 其他用户访问记录访客信息，设置当前时间戳为score
        // 判断是否已经存在访客记录，并且时间是24小时内
        User user = userMapper.selectById(id);
        boolean flag = false;
        if (stringRedisTemplate.opsForZSet().score(UserConstant.USER_VISITOR_KEY + id,
                UserContextHolder.getUserId().toString()) != null) {
            // 已经存在，判断是否在24小时内
            if (!(stringRedisTemplate.opsForZSet().score(UserConstant.USER_VISITOR_KEY + id,
                    UserContextHolder.getUserId().toString()) > System.currentTimeMillis() - 24 * 60 * 60 * 1000)) {
                //24小时内无记录，设置访客数+1
                flag = true;
            }
        } else {
            // 不存在
            flag = true;
        }
        if (flag) {
            // 需要访客数 + 1
            user.setVisitorCount(user.getVisitorCount() + 1);
            userMapper.update(user);
        }
        // 更新访客记录
        stringRedisTemplate.opsForZSet().add(UserConstant.USER_VISITOR_KEY + id,
                UserContextHolder.getUserId().toString(), System.currentTimeMillis());
        return user;
    }

    /**
     * 通过条件查询所有数据
     *
     * @param userQueryRequest 查询条件
     * @return List<User> 实例对象列表
     */
    @Override
    public List<User> queryAll(UserQueryRequest userQueryRequest) {
        User user = BeanUtil.copyProperties(userQueryRequest, User.class);
        return userMapper.selectAll(user);
    }

    /**
     * 通过条件查询分页数据
     *
     * @param userQueryRequest 查询条件
     * @return PageInfo<User> 分页信息对象
     */
    @Override
    public PageInfo<User> queryPage(UserQueryRequest userQueryRequest) {
        int pageNum = userQueryRequest.getPageNum();
        int pageSize = userQueryRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        User user = BeanUtil.copyProperties(userQueryRequest, User.class);
        List<User> users = userMapper.selectAll(user);
        PageInfo<User> pageInfo = new PageInfo<>(users);
        return pageInfo;
    }

    /**
     * 根据条件统计总行数
     *
     * @param userQueryRequest 查询条件
     * @return 总行数
     */
    @Override
    public long queryCount(UserQueryRequest userQueryRequest) {
        User user = BeanUtil.copyProperties(userQueryRequest, User.class);
        return userMapper.count(user);
    }

    /**
     * 新增数据
     *
     * @param userAddRequest 实例对象
     * @return 实例对象
     */
    @Override
    public User insert(UserAddRequest userAddRequest) {
        User user = BeanUtil.copyProperties(userAddRequest, User.class);
        // 设置初始角色
        user.setRole(UserConstant.USER_ROLE);
        userMapper.insert(user);
        return user;
    }

    /**
     * 批量新增数据
     *
     * @param entities List<UserAddRequest> 实例对象列表
     * @return 影响行数
     */
    @Override
    public int insertBatch(List<UserAddRequest> entities) {
        List<User> users = entities.stream()
                .map(item -> {
                    // 设置初始角色
                    item.setRole(UserConstant.USER_ROLE);
                    return BeanUtil.copyProperties(item, User.class);
                })
                .collect(Collectors.toList());
        return userMapper.insertBatch(users);
    }

    /**
     * 修改数据
     *
     * @param userUpdateRequest 实例对象
     * @return 实例对象
     */
    @Override
    public User update(UserUpdateRequest userUpdateRequest) {
        User user = BeanUtil.copyProperties(userUpdateRequest, User.class);
        // 密码加密
        String pwd = user.getPassword();
        if (StrUtil.isNotEmpty(pwd)) {
            // 密码加密存储
            user.setPassword(DigestUtil.md5Hex(SALT + pwd));
        }
        userMapper.update(user);
        return queryById(user.getId());
    }

    /**
     * 通过主键集合批量删除数据
     *
     * @param deleteRequest 删除请求对象
     * @return 影响行数
     */
    @Override
    public int deleteBatchByIds(DeleteRequest deleteRequest) {
        return userMapper.deleteBatchByIds(deleteRequest.getIds());
    }

    /**
     * 通过主键集合批量修改状态
     *
     * @param statusUpdateRequest 状态变更请求对象
     */
    @Override
    public void changeStatus(StatusUpdateRequest statusUpdateRequest) {
        userMapper.updateStatus(statusUpdateRequest.getIds(), statusUpdateRequest.getStatus());
    }

    @Override
    public List<UserVO> getActivityUser() {
        List<User> users = userMapper.selectAllOrderByActivity();
        // 用户信息脱敏
        return users.stream().
                map(this::getSafeUser).collect(Collectors.toList());
    }

    @Override
    public List<UserVO> queryCurrentVistor() {
        // 从Redis获取数据（zset）
        Set<String> visitors = stringRedisTemplate.opsForZSet()
                .range(UserConstant.USER_VISITOR_KEY + UserContextHolder.getUserId(), 0, -1);
        // 遍历，将scorer（时间戳）大于7天的移除
        visitors.removeIf(item -> {
            Double score = stringRedisTemplate.opsForZSet().score(UserConstant.USER_VISITOR_KEY + UserContextHolder.getUserId(), item);
            if (score != null && score < System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000) {
                // 从redis移除
                stringRedisTemplate.opsForZSet().remove(UserConstant.USER_VISITOR_KEY + UserContextHolder.getUserId(), item);
                return true;
            }
            return false;
        });
        if (CollUtil.isEmpty(visitors)) {
            return new ArrayList<>();
        }
        // 获取用户数据，封装vo对象
        return visitors.stream().map(item -> {
            User user = userMapper.selectById(Long.valueOf(item));
            return getSafeUser(user);
        }).collect(Collectors.toList());
    }

    @Override
    public Integer queryRange(Long id) {
        // 获取全部用户数据
        User user = new User();
        user.setStatus(UserConstant.STATUS_OK);
        List<User> users = userMapper.selectAll(user);
        // 根据visitor_count排序
        users.sort((o1, o2) -> o2.getVisitorCount().compareTo(o1.getVisitorCount()));
        // 获取对应id的排序
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                return i + 1;
            }
        }
        return 0;
    }

    @Override
    public Map<String, Object> getStatistics() {
        // 获取用户id
        Long userId = UserContextHolder.getUserId();
        if (userId == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        Map<String, Object> map = new HashMap<>();
        // 获取粉丝数、笔记数、视频数、动态数
        Follow follow = new Follow();
        follow.setRefUid(userId);
        long fansCount = followMapper.count(follow);
        Note note = new Note();
        note.setUserId(userId);
        List<Note> notes = noteMapper.selectAll(note);
        Video video = new Video();
        video.setUserId(userId);
        List<Video> videos = videoMapper.selectAll(video);
        Popular popular = new Popular();
        popular.setCreateBy(userId);
        long popularCount = popularMapper.count(popular);
        map.put("fansCount", fansCount);
        map.put("noteCount", notes.size());
        map.put("videoCount", videos.size());
        map.put("popularCount", popularCount);
        // 获取评论总数、浏览总数、点赞总数
        long commentCount = 0;
        long viewCount = 0;
        long likesCount = 0;
        Comment comment = new Comment();
        for (Note item : notes) {
            viewCount += item.getViews();
            likesCount += item.getLikes();
            // 统计评论总数
            comment.setType("0");
            comment.setContentId(item.getId());
            commentCount += commentMapper.count(comment);
        }
        for (Video item : videos) {
            viewCount += item.getViews();
            likesCount += item.getLikes();
            // 统计评论总数
            comment.setType("1");
            comment.setContentId(item.getId());
            commentCount += commentMapper.count(comment);
        }
        map.put("commentCount", commentCount);
        map.put("viewCount", viewCount);
        map.put("likesCount", likesCount);
        // 获取粉丝增长数据
        List<Map<String, Object>> maps = followMapper.selectFansCountGroupByDate(userId);
        map.put("fansLineData", maps);
        return map;
    }
    // endregion


    private UserVO getSafeUser(User user) {
        if (user == null) {
            return null;
        }
        String email = user.getEmail();
        if (StrUtil.isNotBlank(user.getPhone())) {
            user.setPhone(StrUtil.hide(user.getPhone(), 3, 8));
        }
        if (StrUtil.isNotBlank(email)) {
            user.setEmail(StrUtil.hide(email, 2, email.indexOf("@") - 2));
        }
        return BeanUtil.copyProperties(user, UserVO.class, "password");
    }
}

