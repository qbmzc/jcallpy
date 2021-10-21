package com.congcong.jcallpy.module.python.controller;

import com.congcong.jcallpy.common.R;
import com.congcong.jcallpy.common.UploadParam;
import com.congcong.jcallpy.module.python.pojo.PythonFile;
import com.congcong.jcallpy.module.python.service.PythonFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

/**
 * @author cong
 * @since 2021/10/21 14:38
 */
@Api("脚本文件管理")
@RestController
@Slf4j
@RequestMapping("file")
public class PythonFileController {

    @Autowired
    private PythonFileService service;

    /**
     * 脚本文件路径
     */
    @Value("${jcp.file.path:/Users/cong/Downloads/jcallpy/}")
    private String filePath;

    @SneakyThrows
    @ApiOperation("文件上传,脚本名称和描述不能为空")
    @PostMapping("upload")
    public R upload(@RequestPart MultipartFile file, @Valid UploadParam param) {
        String s = filePath + file.getOriginalFilename();
        log.info("文件:{}保存位置:{}", file.getOriginalFilename(), s);
        File tempFile = new File(s);
        FileUtils.forceMkdir(tempFile);
        file.transferTo(tempFile);
        //将记录写入数据库
        PythonFile pythonFile = new PythonFile();
        pythonFile.setPath(s);
        pythonFile.setName(param.getName());
        pythonFile.setDescribe(param.getDescribe());
        int save = service.save(pythonFile);
        if (save == 1)
            return new R(200, "File saved successfully");
        return new R(500, "File saving failed");
    }

    @GetMapping("/{name}")
    public R queryByName(@PathVariable String name) {
        PythonFile pythonFile = this.service.queryOneByName(name);
        R r = new R();
        r.setBody(pythonFile);
        r.setCode(200);
        r.setMsg("success");
        return r;
    }
}
