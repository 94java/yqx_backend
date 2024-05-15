package cc.jiusi.yqx.service.impl;

import cc.jiusi.yqx.common.DeleteRequest;
import cc.jiusi.yqx.common.StatusUpdateRequest;
import cc.jiusi.yqx.common.UserContextHolder;
import cc.jiusi.yqx.mapper.CommentMapper;
import cc.jiusi.yqx.mapper.LikesMapper;
import cc.jiusi.yqx.model.dto.note.NoteAddRequest;
import cc.jiusi.yqx.model.dto.note.NoteQueryRequest;
import cc.jiusi.yqx.model.dto.note.NoteUpdateRequest;
import cc.jiusi.yqx.model.entity.*;
import cc.jiusi.yqx.mapper.NoteMapper;
import cc.jiusi.yqx.service.NoteService;
import cc.jiusi.yqx.utils.MarkdownUtils;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;

import cn.hutool.core.bean.BeanUtil;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-02 21:27:54
 * @Description: 笔记信息(Note)表服务实现类
 */
@Service("noteService")
public class NoteServiceImpl implements NoteService {
    @Resource
    private NoteMapper noteMapper;
    @Resource
    private LikesMapper likesMapper;

    @Resource
    private CommentMapper commentMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Note queryById(Long id) {
        Note note = noteMapper.selectById(id);
        // 获取点赞信息
        Long userId = UserContextHolder.getUserId();
        note.setUser(getSafeUser(note.getUser()));
        if (userId == null) {
            return note;
        }
        // 填充点赞信息
        return fillInfo(note, userId);
    }

    /**
     * 通过条件查询所有数据
     *
     * @param noteQueryRequest 查询条件
     * @return List<Note> 实例对象列表
     */
    @Override
    public List<Note> queryAll(NoteQueryRequest noteQueryRequest) {
        Note note = BeanUtil.copyProperties(noteQueryRequest, Note.class);
        // 用户信息脱敏
        List<Note> list = noteMapper.selectAll(note);
        for (Note item : list) {
            item.setUser(getSafeUser(item.getUser()));
        }
        // 当前登录用户点赞信息
        Long userId = UserContextHolder.getUserId();
        if (userId == null) {
            return list;
        }
        return list.stream().map(item -> fillInfo(item, userId))
                .collect(Collectors.toList());
    }

    /**
     * 通过条件查询分页数据
     *
     * @param noteQueryRequest 查询条件
     * @return PageInfo<Note> 分页信息对象
     */
    @Override
    public PageInfo<Note> queryPage(NoteQueryRequest noteQueryRequest) {
        int pageNum = noteQueryRequest.getPageNum();
        int pageSize = noteQueryRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        Note note = BeanUtil.copyProperties(noteQueryRequest, Note.class);
        List<Note> notes = noteMapper.selectAll(note);
        // 用户信息脱敏
        for (Note item : notes) {
            item.setUser(getSafeUser(item.getUser()));
        }
        // 当前登录用户点赞信息
        Long userId = UserContextHolder.getUserId();
        if (userId == null) {
            return new PageInfo<>(notes);
        }
        List<Note> list = notes.stream().map(item -> fillInfo(item, userId))
                .collect(Collectors.toList());
        return new PageInfo<>(list);
    }

    /**
     * 根据条件统计总行数
     *
     * @param noteQueryRequest 查询条件
     * @return 总行数
     */
    @Override
    public long queryCount(NoteQueryRequest noteQueryRequest) {
        Note note = BeanUtil.copyProperties(noteQueryRequest, Note.class);
        return noteMapper.count(note);
    }

    /**
     * 新增数据
     *
     * @param noteAddRequest 实例对象
     * @return 实例对象
     */
    @Override
    public Note insert(NoteAddRequest noteAddRequest) {
        Note note = BeanUtil.copyProperties(noteAddRequest, Note.class);
        // 设置用户id
        note.setUserId(UserContextHolder.getUserId());
        // 如果没有指定摘要，则手动提取
        if (StrUtil.isBlank(note.getSummary()) && StrUtil.isNotBlank(note.getContent())) {
            // markdown格式只要文本（前端传递默认是markdown而非html）
            String content = note.getContent();
            int end = Math.min(content.length(), 256);
            note.setSummary(MarkdownUtils.parseMarkdownToPlainText(content).substring(0, end));
        }
        // 设置默认点赞量和阅读量
        note.setViews(0L);
        note.setLikes(0L);
        // 数据插入
        noteMapper.insert(note);
        return note;
    }

    /**
     * 批量新增数据
     *
     * @param entities List<NoteAddRequest> 实例对象列表
     * @return 影响行数
     */
    @Override
    public int insertBatch(List<NoteAddRequest> entities) {
        List<Note> notes = entities.stream().map(item -> {
            Note note = BeanUtil.copyProperties(item, Note.class);
            // 设置用户id
            note.setUserId(UserContextHolder.getUserId());
            // 如果没有指定摘要，则手动提取
            if (StrUtil.isBlank(note.getSummary()) && StrUtil.isNotBlank(note.getContent())) {
                // markdown格式只要文本（前端传递默认是markdown而非html）
                String content = note.getContent();
                int end = Math.min(content.length(), 256);
                note.setSummary(MarkdownUtils.parseMarkdownToPlainText(content).substring(0, end));
            }
            // 设置默认点赞量和阅读量
            note.setViews(0L);
            note.setLikes(0L);
            return note;
        }).collect(Collectors.toList());
        return noteMapper.insertBatch(notes);
    }

    /**
     * 修改数据
     *
     * @param noteUpdateRequest 实例对象
     * @return 实例对象
     */
    @Override
    public Note update(NoteUpdateRequest noteUpdateRequest) {
        Note note = BeanUtil.copyProperties(noteUpdateRequest, Note.class);
        // 如果没有指定摘要，则手动提取
        if (StrUtil.isBlank(note.getSummary()) && StrUtil.isNotBlank(note.getContent())) {
            // markdown格式只要文本（前端传递默认是markdown而非html）
            String content = note.getContent();
            int end = Math.min(content.length(), 256);
            note.setSummary(MarkdownUtils.parseMarkdownToPlainText(content).substring(0, end));
        }
        noteMapper.update(note);
        return queryById(note.getId());
    }

    /**
     * 通过主键集合批量删除数据
     *
     * @param deleteRequest 删除请求对象
     * @return 影响行数
     */
    @Override
    public int deleteBatchByIds(DeleteRequest deleteRequest) {
        return noteMapper.deleteBatchByIds(deleteRequest.getIds());
    }

    /**
     * 通过主键集合批量修改状态
     *
     * @param statusUpdateRequest 状态变更请求对象
     */
    @Override
    public void changeStatus(StatusUpdateRequest statusUpdateRequest) {
        noteMapper.updateStatus(statusUpdateRequest.getIds(), statusUpdateRequest.getStatus());
    }

    @Override
    public List<Note> getListOrderByViews(int count) {
        List<Note> notes = noteMapper.selectAllOrderByViews();
        notes.forEach(item -> {
            item.setUser(getSafeUser(item.getUser()));
        });
        // 当前登录用户点赞信息
        Long userId = UserContextHolder.getUserId();
        if (userId != null) {
            notes = notes.stream().map(item -> fillInfo(item, userId))
                    .collect(Collectors.toList());
        }
        // 获取前count条数据
        if (notes.size() > count) {
            return notes.subList(0, count);
        }
        return notes;
    }

    private User getSafeUser(User user) {
        if (user == null) {
            return user;
        }
        String email = user.getEmail();
        if (StrUtil.isNotBlank(user.getPhone())) {
            user.setPhone(StrUtil.hide(user.getPhone(), 3, 8));
        }
        if (StrUtil.isNotBlank(email)) {
            user.setEmail(StrUtil.hide(email, 2, email.indexOf("@") - 2));
        }
        return BeanUtil.copyProperties(user, User.class, "password");
    }

    private Note fillInfo(Note note, Long userId) {
        // 填充点赞信息
        Long noteId = note.getId();
        Likes likes = new Likes();
        likes.setType("0");
        likes.setUid(userId);
        likes.setContentId(noteId);
        long count = likesMapper.count(likes);
        if (count > 0) {
            // 当前登录用户点赞过
            note.setLike(true);
        }
        // 填充评论数
        return fillCommentNums(note);
    }

    private Note fillCommentNums(Note note){
        Comment comment = new Comment();
        comment.setType("0");
        comment.setContentId(note.getId());
        long count = commentMapper.count(comment);
        note.setComments(count);
        return note;
    }
}

