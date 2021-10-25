package com.congcong.jcallpy.module.page;

import java.io.File;

import javax.validation.Valid;

import com.congcong.jcallpy.common.UploadParam;
import com.congcong.jcallpy.module.python.pojo.PythonFile;
import com.congcong.jcallpy.module.python.service.PythonFileService;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * PageController
 */
@Slf4j
@Controller
public class PageController {

    @Autowired
    private PythonFileService service;

      /**
     * 脚本文件路径
     */
    @Value("${jcp.file.path:/Users/cong/Downloads/jcallpy/}")
    private String filePath;
    
    @RequestMapping("/")
    public String home() {
        return "redirect:/index";
    }

       
    @RequestMapping("{page}")
    public String page(@PathVariable String page) {
        return page;
    }
       

    @ApiOperation("分页查询脚本记录")
    @GetMapping("list")
    public String queryPage(Model model, @RequestParam(required = false, defaultValue = "0") Integer pageNum,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Page<PythonFile> pfs = this.service.queryPage(pageNum, pageSize);
        model.addAttribute("pfs", pfs);
        return "list";
    }

    @ApiOperation("根据名称查询脚本记录")
    @GetMapping("delete/{id}")
    public String delete(@PathVariable Long id) {
        this.service.delete(id);
        return "redirect:/list";
    }

    @ApiOperation("禁用/启用脚本")
    @GetMapping("active/{id}")
    public String active(@PathVariable Long id) {
        this.service.active(id);
        return "redirect:/list";
    }

    @SneakyThrows
    @ApiOperation("文件上传,脚本名称和描述不能为空")
    @PostMapping("upload")
    public String upload(@RequestPart MultipartFile file, @Valid UploadParam param) {
        String s = filePath + file.getOriginalFilename();
        log.info("文件:{}保存位置:{}", file.getOriginalFilename(), s);
        File tempFilePath = new File(filePath);
        FileUtils.forceMkdir(tempFilePath);
        file.transferTo(new File(s));
        // 将记录写入数据库
        PythonFile pythonFile = new PythonFile();
        pythonFile.setFilePath(s);
        pythonFile.setFileName(StringUtils.substring(file.getOriginalFilename(), 0, file.getOriginalFilename().lastIndexOf(".")));
        pythonFile.setRemarks(param.getRemarks());
        int save = service.save(pythonFile);
        log.info("上传结果：{}",save==1);
        // if (save == 1)
        return "redirect:/list";

    }

}