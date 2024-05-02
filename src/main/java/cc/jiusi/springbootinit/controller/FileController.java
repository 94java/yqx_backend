package cc.jiusi.springbootinit.controller;

import cc.jiusi.springbootinit.common.BaseResponse;
import cc.jiusi.springbootinit.common.ErrorCode;
import cc.jiusi.springbootinit.exception.BusinessException;
import cc.jiusi.springbootinit.utils.ResultUtils;
import cn.hutool.core.lang.UUID;
import com.upyun.RestManager;
import com.upyun.UpException;
import com.upyun.UpYunUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件接口
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {
    @Value("${upyun.url}")
    private String url;

    @Resource
    private RestManager restManager;

    @PostMapping("/upload")
    public BaseResponse<String> upload(MultipartFile file) throws UpException, IOException {
        String fileName = UUID.fastUUID().toString(true);
        // 上传到又拍云
        Map<String, String> params = new HashMap<>();
        // 设置待上传文件的 Content-MD5 值
        // 如果又拍云服务端收到的文件MD5值与用户设置的不一致，将回报 406 NotAcceptable 错误
        params.put(RestManager.PARAMS.CONTENT_MD5.getValue(), UpYunUtils.md5(file.getBytes()));

        String path = "/yqx/" + fileName;
        Response result = restManager.writeFile(path, file.getBytes(), params);
        if (result.isSuccessful()) {
            return ResultUtils.success(path);
        } else {
            // 上传失败
            throw new BusinessException(result.code(),result.message());
        }
    }

    @PostMapping("/remove")
    public BaseResponse<Void> remove(String fileName) throws UpException, IOException {
        String path = "/yqx/" + fileName;
        Response result = restManager.deleteFile(path,null);
        if (result.isSuccessful()) {
            return ResultUtils.success(null);
        } else {
            // 删除失败
            throw new BusinessException(result.code(),result.message());
        }
    }
}