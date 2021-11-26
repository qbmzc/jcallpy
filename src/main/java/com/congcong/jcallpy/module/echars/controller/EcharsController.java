package com.congcong.jcallpy.module.echars.controller;

import com.congcong.jcallpy.common.R;
import com.congcong.jcallpy.module.echars.service.CsvReader;
import com.sun.org.apache.bcel.internal.generic.NEW;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author cong
 * @since 2021/11/25 15:26
 */
@RestController
@RequestMapping("echars")
public class EcharsController {

    @Autowired
    private CsvReader reader;

    /**
     * 获取数据
     * @return
     */
    @ApiOperation("获取数据结果")
    @GetMapping("data")
    public R getData(){
        List<Object> data = reader.getData();
        if (null!=data){
            return new R(data);
        }
        return new R(400,"没有更多数据");
    }

    @ApiOperation("生成模拟数据")
    @GetMapping("test")
    public List<Object> test(){
        List<Object> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 19; i++) {
            Integer[] arr =new Integer[]{random.nextInt(18),random.nextInt(18),random.nextInt(324),i,random.nextInt(2)};
            list.add(arr);
        }
        return list;
    }
}
