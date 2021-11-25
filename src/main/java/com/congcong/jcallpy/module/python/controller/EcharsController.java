package com.congcong.jcallpy.module.python.controller;

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

    @GetMapping("data")
    public List<Object> getData(){
        List<Object> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 19; i++) {
            Object[] arr =new Object[]{random.nextInt(18),random.nextInt(18),random.nextInt(324),i,random.nextInt(2)};
            list.add(arr);
        }
        return list;
    }
}
