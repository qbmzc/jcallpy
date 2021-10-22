package com.congcong.jcallpy.module.page;

import com.congcong.jcallpy.module.python.pojo.PythonFile;
import com.congcong.jcallpy.module.python.service.PythonFileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.ApiOperation;

/**
 * PageController
 */
@Controller
public class PageController {


    @Autowired
    private PythonFileService service;

    @RequestMapping("/")
    public String index() {
        return "redirect:/index";
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

}