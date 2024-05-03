package cc.jiusi.springbootinit.service;

import cc.jiusi.springbootinit.common.DeleteRequest;
import cc.jiusi.springbootinit.common.StatusUpdateRequest;
import cc.jiusi.springbootinit.model.dto.note.NoteAddRequest;
import cc.jiusi.springbootinit.model.dto.note.NoteQueryRequest;
import cc.jiusi.springbootinit.model.dto.note.NoteUpdateRequest;
import cc.jiusi.springbootinit.model.entity.Note;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-02 21:27:54
 * @Description: 笔记信息(Note)表服务接口
 */
public interface NoteService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Note queryById(Long id);

    /**
     * 通过条件查询所有数据
     *
     * @param noteQueryRequest 查询条件
     * @return List<Note> 实例对象列表
     */
    List<Note> queryAll(NoteQueryRequest noteQueryRequest);

    /**
     * 通过条件查询分页数据
     *
     * @param noteQueryRequest 查询条件
     * @return PageInfo<Note> 分页信息对象
     */
    PageInfo<Note> queryPage(NoteQueryRequest noteQueryRequest);

    /**
     * 根据条件统计总行数
     *
     * @param noteQueryRequest 查询条件
     * @return 总行数
     */
    long queryCount(NoteQueryRequest noteQueryRequest);

    /**
     * 新增数据
     *
     * @param noteAddRequest 实例对象
     * @return 实例对象
     */
    Note insert(NoteAddRequest noteAddRequest);

    /**
     * 批量新增数据
     *
     * @param entities List<NoteAddRequest> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(List<NoteAddRequest> entities);

    /**
     * 修改数据
     *
     * @param noteUpdateRequest 实例对象
     * @return 实例对象
     */
    Note update(NoteUpdateRequest noteUpdateRequest);

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
}

