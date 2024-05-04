package cc.jiusi.springbootinit.controller;

import cc.jiusi.springbootinit.common.BaseResponse;
import cc.jiusi.springbootinit.common.DeleteRequest;
import cc.jiusi.springbootinit.common.ErrorCode;
import cc.jiusi.springbootinit.common.StatusUpdateRequest;
import cc.jiusi.springbootinit.exception.BusinessException;
import cc.jiusi.springbootinit.model.dto.video.VideoAddRequest;
import cc.jiusi.springbootinit.model.dto.video.VideoQueryRequest;
import cc.jiusi.springbootinit.model.dto.video.VideoUpdateRequest;
import cc.jiusi.springbootinit.model.entity.Video;
import cc.jiusi.springbootinit.service.VideoService;
import cc.jiusi.springbootinit.utils.ResultUtils;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @blog: <a href="https://www.jiusi.cc">九思_Java之路</a>
 * @Author: 九思.
 * @CreateTime: 2024-05-03 20:16:02
 * @Description: 视频信息(Video)表控制层
 */
@RestController
@RequestMapping("video")
@Slf4j
@Api(value = "视频信息", tags = {"视频信息"})
public class VideoController {
    /**
     * 服务对象
     */
    @Resource
    private VideoService videoService;


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/get")
    @ApiOperation("通过主键查询单条数据")
    public BaseResponse<Video> getById(Long id) {
        return ResultUtils.success(videoService.queryById(id));
    }

    /**
     * 通过条件查询所有数据
     *
     * @param videoQueryRequest 查询条件
     * @return List<Video> 实例对象列表
     */
    @PostMapping("/list")
    @ApiOperation("通过条件查询所有数据")
    public BaseResponse<List<Video>> getList(@RequestBody VideoQueryRequest videoQueryRequest) {
        return ResultUtils.success(videoService.queryAll(videoQueryRequest));
    }

    /**
     * 通过条件查询分页数据
     *
     * @param videoQueryRequest 查询条件
     * @return List<Video> 实例对象列表
     */
    @PostMapping("/page")
    @ApiOperation("通过条件查询分页数据")
    public BaseResponse<PageInfo<Video>> getPage(@RequestBody VideoQueryRequest videoQueryRequest) {
        return ResultUtils.success(videoService.queryPage(videoQueryRequest));
    }

    /**
     * 根据条件统计总行数
     *
     * @param videoQueryRequest 查询条件
     * @return 总行数
     */
    @PostMapping("/count")
    @ApiOperation("根据条件统计总行数")
    public BaseResponse<Long> getCount(@RequestBody VideoQueryRequest videoQueryRequest) {
        return ResultUtils.success(videoService.queryCount(videoQueryRequest));
    }

    /**
     * 新增数据
     *
     * @param videoAddRequest 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    @ApiOperation("新增数据")
    public BaseResponse<Video> add(@RequestBody VideoAddRequest videoAddRequest) {
        // 校验
        if(videoAddRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String title = videoAddRequest.getTitle();
        Long categoryId = videoAddRequest.getCategoryId();
        String url = videoAddRequest.getUrl();
        String coverImg = videoAddRequest.getCoverImg();
        if(!StrUtil.isAllNotBlank(title,url,coverImg) || categoryId == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(videoService.insert(videoAddRequest));
    }

    /**
     * 批量新增数据
     *
     * @param entities List<VideoAddRequest> 实例对象列表
     * @return 影响行数
     */
    @PostMapping("/addBatch")
    @ApiOperation("批量新增数据")
    public BaseResponse<Integer> addBatch(@RequestBody List<VideoAddRequest> entities) {
        // 校验
        if(CollUtil.isEmpty(entities)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        for (VideoAddRequest entity : entities) {
            String title = entity.getTitle();
            Long categoryId = entity.getCategoryId();
            String url = entity.getUrl();
            String coverImg = entity.getCoverImg();
            if(!StrUtil.isAllNotBlank(title,url,coverImg) || categoryId == null){
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        return ResultUtils.success(videoService.insertBatch(entities));
    }

    /**
     * 编辑数据
     *
     * @param videoUpdateRequest 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    @ApiOperation("编辑数据")
    public BaseResponse<Video> update(@RequestBody VideoUpdateRequest videoUpdateRequest) {
        // 校验
        if(videoUpdateRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long id = videoUpdateRequest.getId();
        if(id == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(videoService.update(videoUpdateRequest));
    }

    /**
     * 通过主键集合批量删除数据
     *
     * @param deleteRequest 删除请求对象
     * @return 影响行数
     */
    @PostMapping("/delete")
    @ApiOperation("通过主键集合批量删除数据")
    public BaseResponse<Integer> deleteBatchByIds(@RequestBody DeleteRequest deleteRequest) {
        return ResultUtils.success(videoService.deleteBatchByIds(deleteRequest));
    }

    /**
     * 批量变更状态
     *
     * @param statusUpdateRequest 实体
     * @return 编辑结果
     */
    @PostMapping("/changeStatus")
    @ApiOperation("批量变更状态")
    public BaseResponse<Void> changeStatus(@RequestBody StatusUpdateRequest statusUpdateRequest) {
        if(statusUpdateRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String status = statusUpdateRequest.getStatus();
        List<Long> ids = statusUpdateRequest.getIds();
        if(CollUtil.isEmpty(ids) || StrUtil.isBlank(status)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        videoService.changeStatus(statusUpdateRequest);
        return ResultUtils.success(null);
    }
}

