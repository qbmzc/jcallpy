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
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;

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
        File tempFilePath = new File(filePath);
        FileUtils.forceMkdir(tempFilePath);
        file.transferTo(new File(s));
        // 将记录写入数据库
        PythonFile pythonFile = new PythonFile();
        pythonFile.setFilePath(s);
        pythonFile.setFileName(file.getOriginalFilename());
        pythonFile.setRemarks(param.getRemarks());
        int save = service.save(pythonFile);
        if (save == 1)
            return new R(200, "File saved successfully");
        return new R(500, "File saving failed");
    }

    @ApiOperation("执行脚本并返回执行结果")
    @GetMapping("exec/{name}")
    public R execByName(@PathVariable String name) {
        String exec = this.service.exec(name);
        R r = new R();
        r.setBody(exec);
        r.setCode(200);
        r.setMsg("success");
        return r;
    }

    @ApiOperation("分页查询脚本记录")
    @GetMapping("list")
    public R queryPage(@RequestParam(required = false, defaultValue = "0") Integer pageNum,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Page<PythonFile> page = this.service.queryPage(pageNum, pageSize);
        return new R(200, "success", page);
    }

    @ApiOperation("根据名称查询脚本记录")
    @GetMapping("query/{name}")
    public R queryByName(@PathVariable String name) {
        PythonFile file = this.service.queryOneByName(name);
        R r = new R();
        r.setBody(file);
        r.setCode(200);
        r.setMsg("success");
        return r;
    }

    @ApiOperation("根据名称查询脚本记录")
    @GetMapping("delete/{id}")
    public R queryByName(@PathVariable Long id) {
        this.service.delete(id);
        R r = new R();
        r.setCode(200);
        r.setMsg("success");
        return r;
    }

}
